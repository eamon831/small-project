package com.example.smallprojet;

public class user
{
    String name;
    String password;
    public user(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public user(String name, String password) {
        this.name = name;
        this.password = password;
    }
}
