package net.nsnsns.ciscms.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.DelegatingAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import java.util.LinkedHashMap;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final AuthenticationProvider authenticationProvider;

    public SecurityConfig(CognitoAuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
    }

    @Bean
    public AuthenticationFailureHandler customAuthenticationFailureHandler() {
        LinkedHashMap<Class<? extends AuthenticationException>, AuthenticationFailureHandler> handlers = new LinkedHashMap<>();
        handlers.put(CredentialsExpiredException.class, new SimpleUrlAuthenticationFailureHandler("/login"));
        return new DelegatingAuthenticationFailureHandler(handlers,
                new SimpleUrlAuthenticationFailureHandler("/login"));
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public SecurityEvaluationContextExtension securityEvaluationContextExtension() {
        return new SecurityEvaluationContextExtension();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.authenticationProvider(authenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests()
                .antMatchers("/profile/**", "/course/**", "/instructor/**", "/admin/**")
                .authenticated()
                .antMatchers("/", "/**")
                .permitAll()
                .and()
                .formLogin()

//                .failureHandler(customAuthenticationFailureHandler())
                .defaultSuccessUrl("/challenge/")
                .and()
                .logout().logoutSuccessUrl("/");


//        httpSecurity.csrf().disable();
//        httpSecurity.headers().frameOptions().disable();
    }

}
