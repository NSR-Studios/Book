package com.example.book;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity {

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




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_compose);

        ISBN = findViewById(R.id.ISBN);
        Condition = findViewById(R.id.Condition);
        Price = findViewById(R.id.Price);
        FrontCover= findViewById(R.id.FrontCover);
        ivFrontImage = findViewById(R.id.ivFrontImage);
        BackCover= findViewById(R.id.BackCover);
        ivBackImage = findViewById(R.id.ivBackImage);
        Submit = findViewById(R.id.Submit);

        FrontCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Item Clicked!", Toast.LENGTH_SHORT).show();
                launchCamera();
            }
        });

        BackCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Item Clicked!", Toast.LENGTH_SHORT).show();
                launchCamera2();
            }
        });


        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String BookNum= ISBN.getText().toString();
                if(BookNum.isEmpty()){
                    Toast.makeText(MainActivity.this, "Must enter book ISBN number", Toast.LENGTH_SHORT).show();
                    return;
                }
                String Con = Condition.getText().toString();
                if(Con.isEmpty()){
                    Toast.makeText(MainActivity.this, "Must enter book condition", Toast.LENGTH_SHORT).show();
                    return;
                }
                String Pri = Price.getText().toString();
                if(Pri.isEmpty()){
                    Toast.makeText(MainActivity.this, "Must enter book price", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(photoFile == null | photoFile2 == null){
                    Toast.makeText(MainActivity.this, "Must enter book cover Pictures", Toast.LENGTH_SHORT).show();
                    return;
                }

                ParseUser currentuser = ParseUser.getCurrentUser();
                savePost(BookNum, currentuser, Con, Pri, photoFile, photoFile2);

            }
        });

        //queryPosts();

    }

    private void launchCamera() {
        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Create a File reference for future access
        photoFile = getPhotoFileUri(photoFileName);

        // wrap File object into a content provider
        // required for API >= 24
        // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
        Uri fileProvider = FileProvider.getUriForFile(MainActivity.this, "com.codepath.fileprov", photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity(getPackageManager()) != null) {
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
        Uri fileProvider = FileProvider.getUriForFile(MainActivity.this, "com.codepath.fileprov", photoFile2);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity(getPackageManager()) != null) {
            // Start the image capture intent to take photo
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE2);
        }
        else{
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE2);
            Log.e(TAG, "Failed to make it here");
        }
    }

    private void savePost(String ISBN, ParseUser currentUser, String condition,   String Price, File photoFile, File photoFile2){
        Post post = new Post();
        post.setISBN(Integer.parseInt(ISBN));
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
        File mediaStorageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), TAG);

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
                Toast.makeText(MainActivity.this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(MainActivity.this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
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