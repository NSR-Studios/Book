package com.example.book;

import android.app.Application;
import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {

    // Initializes Parse SDK as soon as the application is created
    @Override
    public void onCreate() {
        super.onCreate();
        ParseObject.registerSubclass(Post.class);
        ParseObject.registerSubclass(Request.class);
        ParseObject.registerSubclass(Transaction.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("nUDh8bwuDljZEzPfOaj61gnRdU1RTFnwzMDGLhBP")
                .clientKey("WzFax4OI011GIPNUClHWKORVLGg4loTeLLXSipFe")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
