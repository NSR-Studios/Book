package com.example.book;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Requests")
public class Request extends ParseObject {

    public static final String Seller = "Seller";
    public static final String Requester = "Requester";
    public static final String Postid = "postId";
    public static final String Status = "status";

    public void setStatus(String status){put(Status, status);}

    public ParseUser getSeller() {
        return getParseUser(Seller);
    }

    public ParseUser getRequester() {
        return getParseUser(Requester);
    }

    public static String getKeyUser() {
        return Postid;
    }

    public static String getStatus() {
        return Status;
    }

    public Post getPost(){
        return (Post) getParseObject(Postid);
    }

    public void setSeller(ParseUser user) {
        put(Seller, user);
    }

    public void setPost(ParseObject post) {
        put(Postid, post);
    }

    public void setBuyer(ParseUser user) {
        put(Requester, user);
    }


}
