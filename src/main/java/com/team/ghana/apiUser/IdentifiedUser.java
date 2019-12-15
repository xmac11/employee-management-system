package com.team.ghana.apiUser;

import com.team.ghana.enums.UserRole;

public class IdentifiedUser extends User {

    private String identificationToken;

    public IdentifiedUser(String email, String username, String password, UserRole role, String identificationToken) {
        super(email, username, password, role);
        this.identificationToken = identificationToken;
    }

    public String getIdentificationToken() {
        return identificationToken;
    }
}
