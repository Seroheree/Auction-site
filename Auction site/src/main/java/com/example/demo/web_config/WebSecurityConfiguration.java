package com.example.demo.web_config;

import com.example.demo.services.DetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    public DetailsService UserDetailsService(){
        return new DetailsService();
    }
    @Bean
    public BCryptPasswordEncoder PasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(UserDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(PasswordEncoder());
        return daoAuthenticationProvider;
    }
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.authenticationProvider(daoAuthenticationProvider());
    }


    protected void configure(HttpSecurity http) throws Exception{

        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/add_auction").authenticated()
                .antMatchers("/add_auction_success").authenticated()
                .antMatchers("/user_panel").authenticated()
                .anyRequest().permitAll()
                .and()
                .formLogin().defaultSuccessUrl("/")
                .usernameParameter("email").permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                .permitAll();
    }

}
