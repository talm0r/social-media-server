package com.example.notesapp.utils;

import com.example.notesapp.beans.User;

import javax.servlet.http.HttpServletRequest;

public class Session {

    public User isLoggedIn(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("LOGGED_IN_USER");
        if(user == null) {
            return null;
        }
        else {
            return user;
        }
    }
}
