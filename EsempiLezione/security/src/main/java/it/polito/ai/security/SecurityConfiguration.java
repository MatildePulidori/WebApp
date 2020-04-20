package it.polito.ai.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


    @Bean
    PasswordEncoder encoder(){
        return  new BCryptPasswordEncoder();
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("Tizio")
                .password(encoder().encode("Alpha"))
                .roles("user")


                .and()
                .withUser("Caio")
                .password(encoder().encode("Beta"))
                .roles("user")

                .and()
                .withUser("Sempronio")
                .password(encoder().encode("Gamma"))
                .roles("admin");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests()
                .antMatchers("/**")
                .hasRole("user")
                .and()
                .formLogin();
    }

    @Override
    public void configure(WebSecurity web) throws Exception{
        super.configure(web);
    }

}