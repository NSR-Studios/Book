package com.example.book;


import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.Date;

@ParseClassName("Transactions")
public class Transaction extends ParseObject {

    public static final String KEY_SELLER = "Seller";
    public static final String KEY_BUYER = "Buyer";
    public static final String KEY_POST = "postId";
    public static final String KEY_DATE = "date";
    public static final String KEY_TIME = "time";
    public static final String KEY_LOCATION = "location";
    public static final String CANCELED = "canceled";

    public void setCanceled(String check){put(CANCELED, check);}

    public String getCanceled(){
        return getString(CANCELED);
    }

    public void setSeller(ParseUser user)
    {
        put(KEY_SELLER,user);
    }

    public void setBuyer(ParseUser user)
    {
        put(KEY_BUYER,user);
    }

    public void setPost(ParseObject post)
    {
        put(KEY_POST,post);
    }

    public void setDate(String d)
    {
        put(KEY_DATE,d);
    }

    public void setTime(String t)
    {
        put(KEY_TIME,t);
    }

    public void setLocation(String location)
    {
        put(KEY_LOCATION,location);
    }

    public ParseUser getSeller()
    {
        return getParseUser(KEY_SELLER);
    }

    public ParseUser getBuyer()
    {
        return getParseUser(KEY_BUYER);
    }

    public Post getPost(){
        return (Post) getParseObject(KEY_POST);
    }

    public String getDate()
    {
        return getString(KEY_DATE);
    }

    public String getTime()
    {
        return getString(KEY_TIME);
    }

    public String getLocation()
    {
        return getString(KEY_LOCATION);
    }



}


