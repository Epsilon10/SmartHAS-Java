package com.mycompany;

public class LoginForm {
    private String username,password;

    public LoginForm(final String username, final String password)
    {
        this.username = username;
        this.password = password;
    }

    public String getUsername()
    {
        return this.username;
    }

    public String getPassword()
    {
        return this.password;
    }

}
