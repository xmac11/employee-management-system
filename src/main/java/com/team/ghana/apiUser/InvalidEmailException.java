package com.team.ghana.apiUser;

import java.io.*;

public class InvalidEmailException extends Exception {

    public String msg(User user){
        return user.getEmail() + " already exists";
        //System.exit(911);
    }

}
