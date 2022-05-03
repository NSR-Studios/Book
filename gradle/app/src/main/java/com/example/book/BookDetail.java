package com.example.book;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.parceler.Parcels;

import java.net.MalformedURLException;
import java.net.URL;

public class BookDetail extends AppCompatActivity {

    private TextView tvTitle;
    private TextView tvCondition;
    private TextView tvPrice;
    private TextView tvCategory;
    private TextView tvDescription;
    private ImageView Front;
    private ImageView Back;
    private ImageView Cover;
    private Button button;
    private Button button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        androidx.appcompat.app.ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        actionBar.setTitle("");
        actionBar.setDisplayShowCustomEnabled(true);
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#000000"));
        actionBar.setBackgroundDrawable(colorDrawable);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.logo, null);
        actionBar.setCustomView(view);

        tvTitle = findViewById(R.id.tvTitle);
        tvCondition = findViewById(R.id.tvCondition);
        tvDescription = findViewById(R.id.tvDescription);
        tvCategory = findViewById(R.id.tvCategory);
        tvPrice = findViewById(R.id.tv_Price);
        Front = findViewById(R.id.Front);
        Back = findViewById(R.id.Back);
        Cover = findViewById(R.id.Cover);
        button = findViewById(R.id.button);
        button3 = findViewById(R.id.button3);

        Post post = Parcels.unwrap(getIntent().getParcelableExtra("Post"));
        tvTitle.setText(post.getBookTitle());
        tvCondition.setText(post.getCondition());
        tvPrice.setText(String.valueOf(post.getPrice()) + "$");
        tvCategory.setText(post.getBookCategory());
        tvDescription.setText(post.getBookDescription());
        ParseFile FrontImage = post.getFrontImage();
        String url = post.getImageUrl();
        url = url.substring(0,4) + "s" + url.substring(4);
        Log.i("Link", "LinkToImage: "+ url);
        Glide.with(this).load(FrontImage.getUrl()).into(Front);
        ParseFile BackImage = post.getBackImage();
        Glide.with(this).load(BackImage.getUrl()).into(Back);
        Glide.with(this).load(url).into(Cover);

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = post.getPrev();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Request request = new Request();
                    request.setSeller(post.getUser());
                    request.setBuyer(ParseUser.getCurrentUser());
                    request.setPost(post);
                    request.setStatus("false");
                    request.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(com.parse.ParseException e) {
                            if( e != null){
                                Log.e("TAG", "Error while saving", e);
                            }
                            Log.i("TAG", "Transaction was successful!!" );
                        }

                    });
                    Post post = request.getPost();
                    post.setMeetingSet("false");
                    post.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(com.parse.ParseException e) {
                            if( e != null){
                                Log.e("TAG", "Error while saving", e);
                            }
                            Log.i("TAG", "Transaction was successful!!" );
                        }
                    });

                } catch (Exception e) {
                    Log.e("TAG", "Hit exception", e);
                    e.printStackTrace();
                }


                Intent i = new Intent(BookDetail.this, MainActivity.class);
                startActivity(i);
            }
        });

    }
}

