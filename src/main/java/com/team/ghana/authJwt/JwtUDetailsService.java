package com.team.ghana.authJwt;

import com.team.ghana.apiUser.User;
import com.team.ghana.apiUser.UserRepository;
import com.team.ghana.apiUserService.UDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUDetailsService implements UserDetailsService {
    @Autowired
    UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username);    // Method with no implementations, Spring knows what 's its purpose
        UDetails userDetails;
        if (user != null) {
            userDetails = new UDetails();
            userDetails.setUser(user);
        } else {
            throw new UsernameNotFoundException("User with name : " + username + " does not exist");
        }
        return userDetails;
    }
}
