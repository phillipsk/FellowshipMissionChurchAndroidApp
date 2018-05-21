package io.fmc.data.models;

/**
 * Created by sundayakinsete on 17/05/2018.
 */

public class User {

    String email;
    String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
