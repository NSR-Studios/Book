package com.example.book.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.book.EndlessRecyclerViewScrollListener;
import com.example.book.R;
import com.example.book.Request;
import com.example.book.RequestAdapter;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class TransactionFragment extends Fragment {

    public static final String TAG = "TransactionFragment";
    private RecyclerView pendPosts;
    private RecyclerView compPosts;
    protected RequestAdapter adapter1;
    protected RequestAdapter adapter2;
    protected List<Request> allPendPosts;
    protected List<Request> allCompPosts;
    protected SwipeRefreshLayout swipeContainer;
    protected EndlessRecyclerViewScrollListener scrollListener;
    private LinearLayoutManager linearLayoutManager1;
    private LinearLayoutManager linearLayoutManager2;

    public TransactionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_transactions, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pendPosts = view.findViewById(R.id.pending);
        compPosts = view.findViewById(R.id.completed);
        allPendPosts = new ArrayList<>();
        allCompPosts = new ArrayList<>();
        adapter1 = new RequestAdapter(getContext(), allPendPosts);
        adapter2 = new RequestAdapter(getContext(), allCompPosts);

        pendPosts.setAdapter(adapter1);
        compPosts.setAdapter(adapter2);

        linearLayoutManager1 = new LinearLayoutManager(getContext());
        linearLayoutManager2 = new LinearLayoutManager(getContext());
        pendPosts.setLayoutManager(linearLayoutManager1);
        compPosts.setLayoutManager(linearLayoutManager2);
        queryPendPosts();
        queryCompPosts();
    }

    protected void queryPendPosts() {
        ParseQuery<Request> query = ParseQuery.getQuery(Request.class);
        //get all the transactions
        query.include(Request.Requester);
        query.include(Request.Seller);
        query.include(Request.Postid);
        query.include(Request.Status);
        query.whereEqualTo(Request.Requester, ParseUser.getCurrentUser());
        query.whereEqualTo(Request.Status, "false");
        query.setLimit(5);
        //To Do: Add filters here for pending ones
        query.findInBackground(new FindCallback<Request>() {
            @Override
            public void done(List<Request> requests, ParseException e) {
                if (e != null) {
                    Log.e(TAG,"Issue with getting pending transactions",e);
                }
                for (Request request : requests) {
                    //To do
                    Log.i(TAG, "Pending Requester " + Request.getStatus());
                        addAllP(requests);
                        adapter1.notifyDataSetChanged();
                }
            }
        });

        query = ParseQuery.getQuery(Request.class);
        query.include(Request.Requester);
        query.include(Request.Seller);
        query.include(Request.Postid);
        query.include(Request.Status);
        query.whereEqualTo(Request.Seller, ParseUser.getCurrentUser());
        query.whereEqualTo(Request.Status, "false");
        query.setLimit(5);
        query.findInBackground(new FindCallback<Request>() {
            @Override
            public void done(List<Request> requests, ParseException e) {
                if (e != null) {
                    Log.e(TAG,"Issue with getting pending transactions",e);
                }
                for (Request request : requests) {
                    //To do
                    Log.i(TAG, "Pending Seller " + Request.getStatus());
                        addAllP(requests);
                        adapter1.notifyDataSetChanged();
                }
            }
        });
    }

    protected void queryCompPosts() {
        ParseQuery<Request> query = ParseQuery.getQuery(Request.class);
        //get all the transactions
        query.include(Request.Requester);
        query.include(Request.Seller);
        query.include(Request.Postid);
        query.include(Request.Status);
        query.whereEqualTo(Request.Requester, ParseUser.getCurrentUser());
        query.whereEqualTo(Request.Status, "true");
        query.setLimit(5);
        //To Do: Add filters here for pending ones
        query.findInBackground(new FindCallback<Request>() {
            @Override
            public void done(List<Request> requests, ParseException e) {
                if (e != null) {
                    Log.e(TAG,"Issue with getting pending transactions",e);
                }
                for (Request request : requests) {
                    //To do
                    Log.i(TAG, "Completed Requester " + Request.getStatus());
                        addAllC(requests);
                        adapter2.notifyDataSetChanged();
                }
            }
        });

        query = ParseQuery.getQuery(Request.class);
        query.include(Request.Requester);
        query.include(Request.Seller);
        query.include(Request.Postid);
        query.include(Request.Status);
        query.whereEqualTo(Request.Seller, ParseUser.getCurrentUser());
        query.whereEqualTo(Request.Status, "true");
        query.setLimit(5);
        query.findInBackground(new FindCallback<Request>() {
            @Override
            public void done(List<Request> requests, ParseException e) {
                if (e != null) {
                    Log.e(TAG,"Issue with getting pending transactions",e);
                }
                for (Request request : requests) {
                    //To do
                    Log.i(TAG, "Completed Seller " + Request.getStatus());
                        addAllC(requests);
                        adapter2.notifyDataSetChanged();
                }
            }
        });
    }

    private void addAllP(List<Request> requests) {
        allPendPosts.addAll(requests);
        adapter1.notifyDataSetChanged();
    }

    private void addAllC(List<Request> requests) {
        allCompPosts.addAll(requests);
        adapter2.notifyDataSetChanged();
    }
}
