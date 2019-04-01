package net.nsnsns.ciscms.controllers;

import net.nsnsns.ciscms.security.CognitoUser;
import net.nsnsns.ciscms.security.CognitoUserService;
import net.nsnsns.ciscms.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@Controller
@RequestMapping("/register")
public class RegistrationController {
    private final CognitoUserService userService;

    @Autowired
    RegistrationController(CognitoUserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String register(Model model) {

        return "user/register";
    }

    @PostMapping
    public String processRegistration(@RequestParam(name = "userName") String username,
                                      @RequestParam(name = "familyName") String familyName,
                                      @RequestParam(name = "givenName") String givenName,
                                      @RequestParam(name = "email") String email) {

        userService.registerUser(username, email, familyName, givenName);

        CognitoUser tempUser = new CognitoUser(username, null, Collections.singleton(new SimpleGrantedAuthority("CHALLENGE_AUTH")));
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(tempUser, "",
                Collections.singleton(new SimpleGrantedAuthority("CHALLENGE_AUTH"))));

        return "redirect:/challenge";
    }
}
