package net.nsnsns.ciscms.controllers;

import lombok.extern.log4j.Log4j2;
import net.nsnsns.ciscms.security.CognitoUser;
import net.nsnsns.ciscms.security.CognitoUserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import software.amazon.awssdk.services.cognitoidentityprovider.model.ChallengeNameType;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@RequestMapping("/challenge")
@Controller
public class ChallengeController {
    private static final GrantedAuthority CHALLENGE_AUTH = new SimpleGrantedAuthority("CHALLENGE_AUTH");
    private final static GrantedAuthority RESET_AUTH = new SimpleGrantedAuthority("RESET_AUTH");
    private static final GrantedAuthority STUDENT_AUTH = new SimpleGrantedAuthority("STUDENT_AUTH");

    private static final String LOGIN_REDIRECT = "redirect:/login";
    private static final String COURSE_REDIRECT = "redirect:/course";
    private static final String CHALLENGE_REDIRECT = "redirect:/challenge";
    private static final String CHALLENGE_FORM = "user/challenge";

    private final CognitoUserService userService;

    ChallengeController(CognitoUserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String checkChallenge(Authentication authentication, Model model) {
        if (authentication == null) {
            log.warn("No authentication object set");
            return LOGIN_REDIRECT;
        }
        CognitoUser user = (CognitoUser) authentication.getPrincipal();

        final List<String> fields = new ArrayList<>();
        model.addAttribute("fields", fields);

        if (authentication.isAuthenticated() && authentication.getAuthorities().contains(STUDENT_AUTH)) {
            return COURSE_REDIRECT;
        }

        if (authentication.getAuthorities().contains(CHALLENGE_AUTH) && user.getChallengeName() == ChallengeNameType.NEW_PASSWORD_REQUIRED) {
            fields.add("newPassword");

            return CHALLENGE_FORM;
        } else if (authentication.getAuthorities().contains(RESET_AUTH)) {
            fields.add("confirmCode");
            fields.add("newPassword");

            return CHALLENGE_FORM;
        } else {
            return LOGIN_REDIRECT;
        }
    }

    @PostMapping(params = {"newPassword"})
    public String changePassword(@RequestParam("newPassword") String newPassword,
                                 Authentication authentication) {
        CognitoUser user = (CognitoUser) authentication.getPrincipal();
        Authentication newAuth = userService.respondToNewPasswordChallenge(user, newPassword);
        if (newAuth != null && newAuth.getAuthorities().contains(STUDENT_AUTH)) {
            SecurityContextHolder.getContext().setAuthentication(newAuth);
            return COURSE_REDIRECT;
        }
        return LOGIN_REDIRECT;
    }

    @PostMapping(params = {"confirmCode", "newPassword"})
    public String resetPassword(@RequestParam("confirmCode") String confirmCode,
                                @RequestParam("newPassword") String newPassword,
                                Authentication authentication) {

        Authentication newAuth = userService.confirmForgotPassword(authentication.getName(), confirmCode, newPassword);
        if (newAuth != null) {
            SecurityContextHolder.getContext().setAuthentication(newAuth);
        }

        return CHALLENGE_REDIRECT;
    }
}
