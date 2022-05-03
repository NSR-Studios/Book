package com.example.book;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;

import org.parceler.Parcels;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder>{


    private Context context;
    private List<Post> posts;

    public PostsAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(context).inflate(R.layout.item_post,parent,false);
       return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.bind(post);

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvBookTitle;
        private ImageView ivImage;
        private TextView tvPrice;
        private TextView tvISBN;
        private TextView tvUser;
        private LinearLayout Container;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUser = itemView.findViewById(R.id.tvUser);
            tvBookTitle = itemView.findViewById(R.id.tvBookTitle);
            ivImage = itemView.findViewById(R.id.cover2);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvISBN = itemView.findViewById(R.id.tvISBN);
            Container = itemView.findViewById(R.id.Container);
        }

        public void bind(Post post) {
            //Bind the post data to the view elements
            tvUser.setText(post.getUser().getUsername());
            tvPrice.setText("$" + Integer.toString(post.getPrice()));
            if (post.getBookTitle() != null)
                tvBookTitle.setText(post.getBookTitle());
            else
                tvBookTitle.setText("Title Placeholder");
            tvISBN.setText("ISBN: " + post.getISBN());
            ParseFile image = post.getFrontImage();
            String imageURL = post.getImageUrl();
            if (imageURL != null)
            {
                String url = post.getImageUrl();
                url = url.substring(0,4) + "s" + url.substring(4);
                Log.i("Link", "LinkToImage: "+ url);
                Glide.with(context).load(url).into(ivImage);
            }
            else if (image != null) {
                Glide.with(context).load(image.getUrl()).into(ivImage);
            }

            Container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context,BookDetail.class);
                    i.putExtra("Post", Parcels.wrap(post));
                    context.startActivity(i);
                }
            });

        }
    }
}
