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

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.ViewHolder>{

    private Context context;
    private List<Request> requests;

    public RequestAdapter(Context context, List<Request> requests) {
        this.context = context;
        this.requests = requests;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_request,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Request request = requests.get(position);
        holder.bind(request);
    }

    @Override
    public int getItemCount() {
        return requests.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tvBookTitle;
        private ImageView ivImage;
        private TextView tvPrice;
        private TextView tvISBN;
        private TextView tvUser;
        private TextView tvUser2;
        private LinearLayout container;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUser = itemView.findViewById(R.id.tvUser);
            tvUser2 = itemView.findViewById(R.id.tvUser2);
            tvBookTitle = itemView.findViewById(R.id.tvBookTitle);
            ivImage = itemView.findViewById(R.id.cover2);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvISBN = itemView.findViewById(R.id.tvISBN);
            container = itemView.findViewById(R.id.container);
        }

        public void bind(Request request) {
            //Bind the request data to the view elements
            tvUser.setText(request.getSeller().getUsername());
            tvUser2.setText(request.getRequester().getUsername());
            Log.i("Request User", "Username " + tvUser.getText());
            tvPrice.setText("$" + Integer.toString(request.getPost().getPrice()));
            if (request.getPost().getBookTitle() != null)
                tvBookTitle.setText(request.getPost().getBookTitle());
            else
                tvBookTitle.setText("Title Placeholder");
            tvISBN.setText("ISBN: " + request.getPost().getISBN());
            String image = request.getPost().getImageUrl();
            if (image != null) {
                image = image.substring(0,4) + "s" + image.substring(4);
                Glide.with(context).load(image).into(ivImage);
            }

            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context, singleTransaction.class);
                    i.putExtra("Request", Parcels.wrap(request));
                    context.startActivity(i);
                }
            });
        }
    }
}
