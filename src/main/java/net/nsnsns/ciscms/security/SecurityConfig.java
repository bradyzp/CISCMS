package net.nsnsns.ciscms.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests()
                .antMatchers("/", "/error*", "/login*")
                .permitAll()
                .antMatchers("/**")
                .authenticated()
                .and()
                .formLogin();

        // DEVELOPMENT Options to allow H2 Database console
        httpSecurity
                .authorizeRequests()
                .antMatchers("/h2/**")
                .permitAll();
        httpSecurity.csrf().disable();
        httpSecurity.headers().frameOptions().disable();
    }
}
