package com.team.ghana.apiUser;

import com.team.ghana.apiUserService.UDetailsService;
import com.team.ghana.enums.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//@SuppressWarnings("deprecation")
/*
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
*/
public class Securitas extends WebSecurityConfigurerAdapter{

    @Autowired
    private UDetailsService uDetailsService;  // class that implements UserDetailsService, to confirm UserDetails

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication().withUser("Jimmy").password("111").roles("BOSS");
        // customize auth using UserDetailsService interface
        auth.userDetailsService(uDetailsService).passwordEncoder(encodePass());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        // http.authorizeRequests().anyRequest().fullyAuthenticated().and().httpBasic();

        // authentication for all (probably)

        /*
        http.authorizeRequests()
                .anyRequest().authenticated()
                .and().formLogin().permitAll();
        */

        http.authorizeRequests()
                .antMatchers("/ghana/**").authenticated()
                .antMatchers("**/post").hasRole(UserRole.ADMIN.toString())
                .anyRequest().authenticated()
                .and().httpBasic();
    }

    @Bean
    public BCryptPasswordEncoder encodePass(){
        return new BCryptPasswordEncoder();
    }

//    IGNORE BELOW COMMENTS
//    @Bean
//    public static NoOpPasswordEncoder encoder(){
//        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
//    }
}
