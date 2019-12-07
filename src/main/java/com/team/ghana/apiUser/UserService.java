package com.team.ghana.apiUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository repository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    private void isRegistered(User newUser) throws InvalidEmailException{
        boolean samEmail = false;
        Iterable<User> users = repository.findAll();
        for(User u: users){
            if(newUser.getEmail().equals(u.getEmail()))
                samEmail = true;
        }
        if(!samEmail) {
            String pwd = newUser.getPassword();
            String encryptPwd = encoder.encode(pwd);
            newUser.setPassword(encryptPwd);
            repository.save(newUser);
        }else
            throw new InvalidEmailException();
    }

    public void registerUser(User newUser){
        try{
            isRegistered(newUser);
            System.out.println(newUser.getEmail() + " registered successfully");
        }catch (InvalidEmailException exc){
            System.out.println(exc.msg(newUser));
        }
    }
}
