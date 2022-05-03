package com.example.book;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.Date;

@ParseClassName("User")
public class User extends ParseObject {

    public static final String KEY_MAJOR = "Major";

    public String getMajor() {
        return getString(KEY_MAJOR);
    }

}

