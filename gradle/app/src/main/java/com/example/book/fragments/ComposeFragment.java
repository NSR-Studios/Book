package com.example.book.fragments;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.book.Post;
import com.example.book.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.List;

import okhttp3.Headers;

public class ComposeFragment extends Fragment {
    public static final String TAG = "ComposeFragment";
    public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 42;
    public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE2 = 43;
    private EditText ISBN;
    private EditText Condition;
    private EditText Price;
    private Button FrontCover;
    private ImageView ivFrontImage;
    private Button BackCover;
    private ImageView ivBackImage;
    private Button Submit;
    private File photoFile;
    public String photoFileName = "photo.jpg";
    private File photoFile2;
    public String photoFileName2 = "photo2.jpg";


    public ComposeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_compose, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ISBN = view.findViewById(R.id.ISBN);
        Condition = view.findViewById(R.id.Condition);
        Price = view.findViewById(R.id.Price);
        FrontCover= view.findViewById(R.id.FrontCover);
        ivFrontImage = view.findViewById(R.id.ivFrontImage);
        BackCover= view.findViewById(R.id.BackCover);
        ivBackImage = view.findViewById(R.id.ivBackImage);
        Submit = view.findViewById(R.id.Submit);

        //IMPORTANT!!!!!!!!!!!!! Change url to accept the BOOKNUM!!!!!!!!!!!!!!!!!!!!!!!
//        String extra = ISBN.getText().toString();
//        String url = "https://www.googleapis.com/books/v1/volumes?q=isbn:" + extra;
//
//        AsyncHttpClient client = new AsyncHttpClient();
//        client.get(url, new JsonHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Headers headers, JSON json) {
//                Log.d(TAG, "onSuccess");
//                JSONObject jsonObject = json.jsonObject;
//                try {
//                    JSONArray results = jsonObject.getJSONArray("items");
//                    Log.i(TAG, "Results: " + results.toString());
//                    JSONObject vI = results.getJSONObject(0).getJSONObject("volumeInfo");
//                    Log.i(TAG, "VI: "+ vI.toString());
//                    title = vI.getString("title");
//                    description = vI.getString("description");
//                    Log.i(TAG,"Title: "+title);
//                    category = vI.getJSONArray("categories").getString(0);
//                    Log.i(TAG,"Category: "+category);
//                } catch (Exception e) {
//                    Log.e(TAG, "Hit json exception", e);
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
//                Log.d(TAG, "onFailure");
//            }
//        });

        FrontCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Item Clicked!", Toast.LENGTH_SHORT).show();
                //Fragment fragment = new ProfileFragment();
                //fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();

                launchCamera();
            }
        });

        BackCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Item Clicked!", Toast.LENGTH_SHORT).show();
                launchCamera2();
            }
        });


        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Post post = new Post();
                ParseUser currentuser = ParseUser.getCurrentUser();
                String BookNum= ISBN.getText().toString();
                String url = "https://www.googleapis.com/books/v1/volumes?q=isbn:" + BookNum;
                Log.i(TAG, "URL: " + url);

                AsyncHttpClient client = new AsyncHttpClient();
                client.get(url, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Headers headers, JSON json) {
                        Log.d(TAG, "onSuccess");
                        JSONObject jsonObject = json.jsonObject;
                        try {
                            String title  = "";
                            String category = "";
                            String description = "";
                            String image = "";
                            String link = "";
                            JSONArray results = jsonObject.getJSONArray("items");
                            Log.i(TAG, "Results: " + results.toString());
                            JSONObject vI = results.getJSONObject(0).getJSONObject("volumeInfo");
                            image = vI.getJSONObject("imageLinks").getString("smallThumbnail");
                            post.setImageUrl(image);
                            Log.i(TAG, "LinkToImage: "+ image);
                            Log.i(TAG, "VI: "+ vI.toString());
                            title = vI.getString("title");
                            description = vI.getString("description");
                            Log.i(TAG,"Title: "+title);
                            category = vI.getJSONArray("categories").getString(0);
                            Log.i(TAG,"Category: "+category);
                            link =  vI.getString("previewLink");
                            //Log.i(TAG,"prev: "+link);
                            post.setISBN(BookNum);
                            post.setCondition(Condition.getText().toString());
                            post.setBackImage(new ParseFile(photoFile2));
                            post.setFrontImage(new ParseFile(photoFile));
                            post.setPrice(Integer.parseInt(Price.getText().toString()));
                            post.setUser(currentuser);
                            post.setBookTitle(title);
                            post.setBookDescription(description);
                            post.setBookCategory(category);
                            post.setPreview(link);
                            post.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if( e != null){
                                        Log.e(TAG, "Error while saving", e);
                                    }
                                    Log.i(TAG, "Post was successful!!" );
                                    Condition.setText("");
                                    ivFrontImage.setImageResource(0);
                                    ivBackImage.setImageResource(0);
                                }
                            });
                        } catch (Exception e) {
                            Log.e(TAG, "Hit json exception", e);
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                        Log.d(TAG, "onFailure");
                    }
                });



                if(BookNum.isEmpty()){
                    Toast.makeText(getContext(), "Must enter book ISBN number", Toast.LENGTH_SHORT).show();
                    return;
                }
                String Con = Condition.getText().toString();
                if(Con.isEmpty()){
                    Toast.makeText(getContext(), "Must enter book condition", Toast.LENGTH_SHORT).show();
                    return;
                }
                String Pri = Price.getText().toString();
                if(Pri.isEmpty()){
                    Toast.makeText(getContext(), "Must enter book price", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(photoFile == null | photoFile2 == null){
                    Toast.makeText(getContext(), "Must enter book cover Pictures", Toast.LENGTH_SHORT).show();
                    return;
                }

            }
        });

    }

    private void launchCamera() {
        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Create a File reference for future access
        photoFile = getPhotoFileUri(photoFileName);

        // wrap File object into a content provider
        // required for API >= 24
        // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
        Uri fileProvider = FileProvider.getUriForFile(getContext(), "com.codepath.fileprov", photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity(getContext().getPackageManager()) != null) {
            // Start the image capture intent to take photo
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
        else{
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
            Log.e(TAG, "Failed to make it here");
        }
    }

    private void launchCamera2() {
        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Create a File reference for future access
        photoFile2 = getPhotoFileUri(photoFileName2);

        // wrap File object into a content provider
        // required for API >= 24
        // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
        Uri fileProvider = FileProvider.getUriForFile(getContext(), "com.codepath.fileprov", photoFile2);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity(getContext().getPackageManager()) != null) {
            // Start the image capture intent to take photo
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE2);
        }
        else{
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE2);
            Log.e(TAG, "Failed to make it here");
        }
    }

    private void savePost(String ISBN, ParseUser currentUser, String condition,   String Price, File photoFile, File photoFile2, String title, String description, String category){
        Post post = new Post();
        Log.i(TAG,"Title IN SAVEPOST: "+title);
        post.setBookTitle(title);
        post.setBookDescription(description);
        post.setBookCategory(category);
        post.setISBN(ISBN);
        post.setCondition(condition);
        post.setBackImage(new ParseFile(photoFile2));
        post.setFrontImage(new ParseFile(photoFile));
        post.setPrice(Integer.parseInt(Price));
        post.setUser(currentUser);
        post.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if( e != null){
                    Log.e(TAG, "Error while saving", e);
                }
                Log.i(TAG, "Post was successful!!" );
                Condition.setText("");
                ivFrontImage.setImageResource(0);
                ivBackImage.setImageResource(0);
            }
        });
    }

    public File getPhotoFileUri(String fileName) {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        File mediaStorageDir = new File(getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), TAG);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d(TAG, "failed to create directory");
        }
        Log.i(TAG,"Directory was created successfully");
        // Return the file target for the photo based on filename
        return new File(mediaStorageDir.getPath() + File.separator + fileName);

    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // by this point we have the camera photo on disk
                Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                // RESIZE BITMAP, see section below
                // Load the taken image into a preview
                ivFrontImage.setImageBitmap(takenImage);
            } else { // Result was a failure
                Toast.makeText(getContext(), "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE2) {
            if (resultCode == RESULT_OK) {
                // by this point we have the camera photo on disk
                Bitmap takenImage = BitmapFactory.decodeFile(photoFile2.getAbsolutePath());
                // RESIZE BITMAP, see section below
                // Load the taken image into a preview
                ivBackImage.setImageBitmap(takenImage);
            } else { // Result was a failure
                Toast.makeText(getContext(), "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void queryPosts(){
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if(e != null){
                    Log.e(TAG, "Issue with getting posts", e);
                    return;
                }
                for(Post post : posts){
                    Log.i(TAG, "ISBN: "+ post.getISBN());
                }
            }
        });
    }

}

