package io.fmc.data.models;

/**
 * Created by  Kevin Phillips and Sunday Akinsete on 17/05/2018.
 */

public class User {

    String email;
    String password;
    String name;
    String photo;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }


}
