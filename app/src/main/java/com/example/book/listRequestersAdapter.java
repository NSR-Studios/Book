package com.example.book;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;
import com.parse.ParseUser;

import org.parceler.Parcels;

import java.util.List;

public class listRequestersAdapter extends RecyclerView.Adapter<listRequestersAdapter.ViewHolder>{

    private Context context;
    private List<Request> requests;

    public listRequestersAdapter(Context context, List<Request> requests) {
        this.context = context;
        this.requests = requests;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_request,parent,false);
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

        private TextView username;
        private ImageView profilePic3;
        private Button buttonEdit2;
        private RelativeLayout container;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.profile_username3);
            profilePic3 = itemView.findViewById(R.id.profile_pic3);
            buttonEdit2 = itemView.findViewById(R.id.buttonEdit2);
            container = itemView.findViewById(R.id.container);
        }

        public void bind(Request request) {
            //Bind the request data to the view elements
            Log.i("Request User", "Username " + request.getRequester().getUsername());
            username.setText(request.getRequester().getUsername());
            ParseFile image = request.getRequester().getParseFile("ProfilePic");
            if (image != null)
                Glide.with(context).load(image.getUrl()).circleCrop().placeholder(R.drawable.blank).into(profilePic3);
            else
                profilePic3.setImageResource(R.drawable.blank);


                buttonEdit2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(context, ConfirmTransaction.class);
                        i.putExtra("Request", Parcels.wrap(request));
                        context.startActivity(i);
                    }
                });

        }
    }
}
