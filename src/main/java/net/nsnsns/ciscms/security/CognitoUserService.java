package net.nsnsns.ciscms.security;

import lombok.extern.log4j.Log4j2;
import net.nsnsns.ciscms.models.Student;
import net.nsnsns.ciscms.services.StudentService;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.*;

import java.util.*;

@Log4j2
@Service
@ConfigurationProperties(prefix = "ns3.security.cognito")
public class CognitoUserService {
    private final static GrantedAuthority PROVISIONAL_AUTH = new SimpleGrantedAuthority("CHALLENGE_AUTH");
    private final static GrantedAuthority RESET_AUTH = new SimpleGrantedAuthority("RESET_AUTH");
    private final static GrantedAuthority STUDENT_AUTH = new SimpleGrantedAuthority("STUDENT_AUTH");

    private final CognitoIdentityProviderClient client;
    private final StudentService studentService;
    private String poolId;
    private String clientId;
    private String clientSecret;

    CognitoUserService(StudentService studentService) {
        this.studentService = studentService;
        client = CognitoIdentityProviderClient.create();
    }

    private Authentication getAuthenticationToken(final String username) {
        return null;
    }

    private GetUserResponse getUser(final String accessToken) {
        GetUserRequest getUserRequest = GetUserRequest.builder()
                .accessToken(accessToken)
                .build();
        return client.getUser(getUserRequest);
    }

    public void registerUser(final String username, final String email, final String familyName, final String givenName) {
        Collection<AttributeType> userAttributes = new ArrayList<>();
        userAttributes.add(AttributeType.builder().name("email").value(email).build());
        userAttributes.add(AttributeType.builder().name("family_name").value(familyName).build());
        userAttributes.add(AttributeType.builder().name("given_name").value(givenName).build());

        AdminCreateUserRequest request = AdminCreateUserRequest.builder()
                .desiredDeliveryMediums(DeliveryMediumType.EMAIL)
                .userAttributes(userAttributes)
                .username(username)
                .userPoolId(poolId)
                .build();
        try {
            AdminCreateUserResponse response = client.adminCreateUser(request);

            UserType user = response.user();
            Student student = Student.builder().username(user.username()).email(email).build();
            studentService.registerStudent(student);

        } catch (UsernameExistsException e) {
            e.printStackTrace();
        }
    }

    /* Call this to authenticate a user against the Cognito Identity Pool using a username and password */
    public Authentication authenticateUser(final String username, final String password) throws AuthenticationException {
        Map<String, String> authParams = new HashMap<>();
        log.info("Authenticating user with name: " + username);
        authParams.put("USERNAME", username);
        authParams.put("PASSWORD", password);

        AdminInitiateAuthRequest request = AdminInitiateAuthRequest.builder()
                .authFlow(AuthFlowType.ADMIN_NO_SRP_AUTH)
                .authParameters(authParams)
                .userPoolId(poolId)
                .clientId(clientId)
                .build();

        AdminInitiateAuthResponse response;
        try {
            response = client.adminInitiateAuth(request);

        } catch (NotAuthorizedException e) {
            e.printStackTrace();
            throw new BadCredentialsException("User is not authorized");
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            throw new UsernameNotFoundException("User " + username + " doesn't exist.");
        } catch (PasswordResetRequiredException e) {
            log.info("User must reset their password");
            CognitoUser user = new CognitoUser(username, password, Collections.singleton(RESET_AUTH));

            return new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());
        }
        Objects.requireNonNull(response, "Invalid Authentication Response, must not be null");

        CognitoUser user;
        if (response.authenticationResult() != null) {
            var userDetails = getUser(response.authenticationResult().accessToken());
            if (!studentService.isRegistered(userDetails.username())) {
                log.warn("User is authenticated, but does not exist in student database, creating new entity");
                Student student = Student.builder()
                        .username(userDetails.username())
                        .email(userDetails.getValueForField("email", String.class).orElse(""))
                        .build();
                studentService.registerStudent(student);
            }

            log.info("User has authenticated, no further challenges required");
            AuthenticationResultType authResult = response.authenticationResult();
            // TODO: Get user groups via AdminListGroupsForUser
            user = new CognitoUser(userDetails.username(), null, Collections.singleton(STUDENT_AUTH));
            return new UsernamePasswordAuthenticationToken(user, authResult.accessToken(), user.getAuthorities());

        } else if (response.challengeName() != null) {
            log.info("User requires further challenge to authenticate: " + response.challengeName().name());
            user = new CognitoUser(username, password, Collections.singleton(PROVISIONAL_AUTH));
            user.setSession(response.session());
            user.setChallengeName(response.challengeName());
            user.setChallengeParams(response.challengeParameters());
            return new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());
        } else {
            return null;
        }
    }

    public Authentication confirmForgotPassword(final String username, final String confirmCode, final String newPassword) {

        ConfirmForgotPasswordRequest confirmRequest = ConfirmForgotPasswordRequest.builder()
                .username(username)
                .confirmationCode(confirmCode)
                .password(newPassword)
                .clientId(clientId)
                .build();

        ConfirmForgotPasswordResponse response = client.confirmForgotPassword(confirmRequest);
        if (response.sdkHttpResponse().isSuccessful()) {
            CognitoUser user = new CognitoUser(username, null, Collections.singleton(new SimpleGrantedAuthority("STUDENT_AUTH")));
            return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        }
        return null;
    }

    public Authentication respondToNewPasswordChallenge(final CognitoUser user, final String newPassword) {
        Map<String, String> challengeResponses = new HashMap<>();
//        for(var entry : challengeResponses.entrySet()) {
//            log.info("Entry key: " + entry.getKey() + " entry value: " + entry.getValue());
//        }
        challengeResponses.put("USERNAME", user.getChallengeParams().get("USER_ID_FOR_SRP"));
        challengeResponses.put("PASSWORD", user.getPassword());
        challengeResponses.put("NEW_PASSWORD", newPassword);

        AdminRespondToAuthChallengeRequest challengeRequest = AdminRespondToAuthChallengeRequest.builder()
                .challengeName(ChallengeNameType.NEW_PASSWORD_REQUIRED)
                .challengeResponses(challengeResponses)
                .session(user.getSession())
                .clientId(clientId)
                .userPoolId(poolId)
                .build();
        AdminRespondToAuthChallengeResponse challengeResponse = client.adminRespondToAuthChallenge(challengeRequest);
        CognitoUser authUser;
        if (challengeResponse.authenticationResult() != null) {
            authUser = new CognitoUser(user.getUsername(), null, Collections.singleton(STUDENT_AUTH));

            return new UsernamePasswordAuthenticationToken(authUser, null, authUser.getAuthorities());
        } else {
            authUser = new CognitoUser(user.getUsername(), null, Collections.singleton(PROVISIONAL_AUTH));
            authUser.setSession(challengeResponse.session());
            authUser.setChallengeName(challengeResponse.challengeName());
            authUser.setChallengeParams(challengeResponse.challengeParameters());

            return new UsernamePasswordAuthenticationToken(authUser, null, authUser.getAuthorities());
        }
    }

    public String getPoolId() {
        return poolId;
    }

    public void setPoolId(String poolId) {
        this.poolId = poolId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }
}
