package net.nsnsns.ciscms.security;

import lombok.Data;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import software.amazon.awssdk.services.cognitoidentityprovider.model.ChallengeNameType;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@Data
public class CognitoUser implements UserDetails, CredentialsContainer {
    private final String username;
    private final Collection<? extends GrantedAuthority> authorities;
    private String password;
    private String uuid;
    private String session;
    private ChallengeNameType challengeName;
    private Map<String, String> challengeParams;


    public CognitoUser(final String username, final String password, final Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.authorities = Collections.unmodifiableCollection(authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public void eraseCredentials() {
        password = null;
    }
}
