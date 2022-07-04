package com.ai.game.instashop;

public class Firebase_User {
    private String name, email, profession, password;

    public Firebase_User(String name, String email, String profession, String password) {
        this.name = name;
        this.email = email;
        this.profession = profession;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
