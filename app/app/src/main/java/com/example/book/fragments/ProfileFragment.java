package com.example.book.fragments;


import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.example.book.EndlessRecyclerViewScrollListener;
import com.example.book.LoginActivity;
import com.example.book.Post;
import com.example.book.PostsAdapter;
import com.example.book.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {

    File photoFile;
    ImageView profilePic;
    public static final String TAG = "ProfileFragment";
    List<Post> allPosts;
    RecyclerView rvPosts;
    PostsAdapter adapter;
    SwipeRefreshLayout refreshLayout;
    EndlessRecyclerViewScrollListener scrolling;
    Button EditMe;
    Button Logout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    // Set an adapter for the RecyclerView and query for posts whenever this fragment is opened.
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Set up the username
        TextView username = view.findViewById(R.id.profile_username);
        username.setText("" + ParseUser.getCurrentUser().getUsername());


        //Set up major and class of
        TextView major = view.findViewById(R.id.profile_major);
        //ParseUser.getCurrentUser().put("Major","Computer Science");
        if ((ParseUser.getCurrentUser().getString("Major")) == null) {
            major.setText("Major: ");
        }
        else {
            major.setText(" " +ParseUser.getCurrentUser().getString("Major"));
        }
        EditText majorEdit = view.findViewById(R.id.profile_editMajor);
        EditText classEdit = view.findViewById(R.id.profile_editClass);
        //classEdit.setText("Edit Class");
        TextView classOf = view.findViewById(R.id.profile_classOf);
        //ParseUser.getCurrentUser().put("ClassOf"," CS113");
        if (ParseUser.getCurrentUser().getString("ClassOf") == null) {
            classOf.setText("Class of: ");
        }
        else {
            classOf.setText(" " + ParseUser.getCurrentUser().getString("ClassOf"));
        }

        EditMe = view.findViewById(R.id.buttonEdit);
        Logout = view.findViewById(R.id.buttonLogout);
        EditMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Button Clicked!", Toast.LENGTH_SHORT).show();
                majorEdit.setEnabled(true);
                majorEdit.requestFocus();
                String textMe = "" + majorEdit.getText();

                if(majorEdit.getText().toString().isEmpty()) {
                    // editText is empty
                } else {
                    // editText is not empty
                    ParseUser.getCurrentUser().put("Major",textMe);
                }
                //ParseUser.getCurrentUser().put("Major",textMe);
                major.setText(" " +ParseUser.getCurrentUser().getString("Major"));

                classEdit.setEnabled(true);
                classEdit.requestFocus();
                String textMe2 = "" + classEdit.getText();

                if(classEdit.getText().toString().isEmpty()) {
                    // editText is empty
                } else {
                    // editText is not empty
                    ParseUser.getCurrentUser().put("ClassOf",textMe2);
                }
                //ParseUser.getCurrentUser().put("ClassOf",textMe2);
                classOf.setText(" " +ParseUser.getCurrentUser().getString("ClassOf"));


                try {
                    ParseUser.getCurrentUser().save();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        });

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(this,"Logout!", Toast.LENGTH_SHORT).show();
                ParseUser.logOut();
                ParseUser currentUser = ParseUser.getCurrentUser();
                Intent iTwo = new Intent(getContext(), LoginActivity.class);
                startActivity(iTwo);
            }
        });
        // Set up the password
        ParseFile image = ParseUser.getCurrentUser().getParseFile("ProfilePic");
        profilePic = view.findViewById(R.id.profile_pic);
        // If there is already a profile pic, load it, else load the default
        if (image != null)
            Glide.with(getContext()).load(image.getUrl()).circleCrop().placeholder(R.drawable.blank).into(profilePic);
        else
            profilePic.setImageResource(R.drawable.blank);

        // If we click on the profile picture, take a new picture and assign it to photoFile
        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                photoFile = getPhotoFileUri("profile_pic.jpg");

                Uri fileProvider = FileProvider.getUriForFile(getContext(), "com.codepath.fileprov", photoFile);
                i.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

                startActivityForResult(i, 200);
            }
        });

        allPosts = new ArrayList<>();
        rvPosts = view.findViewById(R.id.rv_posts2);
        refreshLayout = view.findViewById(R.id.swipeContainer2);

        // We need to assign the same layout manager to both RecyclerView and EnlessScrollListener, so they are syncronized
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        // When we scroll, get the next page
        /*scrolling = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                queryPosts();
            }
        };*/

        //rvPosts.addOnScrollListener(scrolling);

        adapter = new PostsAdapter(view.getContext(), allPosts);
        rvPosts.setAdapter(adapter);
        rvPosts.setLayoutManager(linearLayoutManager);

        // Query for posts even before a refresh
        queryPosts();

        // When we refresh, delete the posts we have now and replace with a more updated set
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                allPosts.clear();
                queryPosts();
            }
        });

    }

    public File getPhotoFileUri(String s) {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        File mediaStorageDir = new File(getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), "APP_TAG");

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
            Log.d("APP_TAG", "failed to create directory");
        }

        // Return the file target for the photo based on filename
        File file = new File(mediaStorageDir.getPath() + File.separator + s);

        return file;
    }

    // When we come back from taking a picture, save this image on parse server and refresh the profilePic ImageView
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200) {
            if (resultCode == RESULT_OK) {
                try {
                    // Convert the photoFile into a ParseFile and save it onto the current ParseUser
                    ParseUser.getCurrentUser().put("ProfilePic", new ParseFile(photoFile));
                    ParseUser.getCurrentUser().save();

                    // Set the profilePic ImageView as this new Image
                    String url = ParseUser.getCurrentUser().getParseFile("ProfilePic").getUrl();
                    Glide.with(getContext()).load(url).circleCrop().into(profilePic);
                } catch (ParseException e) {
                    Toast.makeText(getContext(), "Error saving image", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

                return;
            }
            Toast.makeText(getContext(), "Error taking image", Toast.LENGTH_SHORT).show();
        }
    }


    //@Override
    protected void queryPosts() {
        Log.i(TAG,"Post:  sdkfdjksfdkfkkdfksdf hello");
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        //get all the posts
        query.include(Post.KEY_USER);
        query.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser());
        query.setLimit(20);
        query.addDescendingOrder(Post.KEY_CREATED_KEY);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e != null) {
                    Log.e(TAG,"Issue with getting posts",e);
                }
                for (Post post : posts) {
                    Log.i(TAG,"Post: " + post.getCondition() + ", username: " + post.getUser().getUsername() +", price: " + post.getPrice() + " ISBN: " + post.getISBN());
                }
                allPosts.addAll(posts);
                adapter.notifyDataSetChanged();
            }
        });

    }
}