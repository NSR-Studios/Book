package com.example.book.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;


import com.example.book.EndlessRecyclerViewScrollListener;
import com.example.book.MainActivity;
import com.example.book.Post;
import com.example.book.PostsAdapter;
import com.example.book.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PostsFragment extends Fragment {

    public static final String TAG = "PostsFragment";
    private RecyclerView rvPosts;
    protected PostsAdapter adapter;
    protected List<Post> allPosts;
    Spinner spinner;
    protected SwipeRefreshLayout swipeContainer;
    protected EndlessRecyclerViewScrollListener scrollListener;
    private LinearLayoutManager linearLayoutManager;


    public PostsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_posts, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvPosts = view.findViewById(R.id.rvPosts);
        allPosts = new ArrayList<>();
        adapter = new PostsAdapter(getContext(), allPosts);

        //Spinner spinner = (Spinner)view.findViewById(R.id.action_money);

        // Spinner click listener
        //spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        //Steps to use recycler view
        //0 create layout for one row in the list
        //1 create the adapter
        //2 create the data source
        //3 set the adapter on the recycler view
        rvPosts.setAdapter(adapter);

        //Swipe Refresh
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                clear();
                queryPosts();
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        //4 set the layout manager on the recycler view
        linearLayoutManager = new LinearLayoutManager(getContext());
        rvPosts.setLayoutManager(linearLayoutManager);
        queryPosts();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        Log.i(TAG,"Options selected");

        switch (item.getItemId()) {
            case R.id.action_search: {
                Log.i(TAG, "SEARCH!");
                Toast.makeText(getContext(), "Search!", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.action_money: {
                Log.i(TAG, "MONEY!");
                clear();
                queryPostsPriceHighLow();
                Toast.makeText(getContext(), "High Low!", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.action_lowhigh: {
                Log.i(TAG, "Low High!");
                clear();
                queryPostsPriceLowHigh();
                Toast.makeText(getContext(), "Low High!", Toast.LENGTH_SHORT).show();
                break;
            }

            //return true;
        }

        //return true;
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        //MenuInflater inflater = getMenuInflater();
        //super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final MenuItem spinnerItem = menu.findItem(R.id.action_money);
        /*final Spinner spinner = (Spinner)MenuItemCompat.getActionView(spinnerItem);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i(TAG,"Spinner two");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Log.i(TAG,"Spinner");
            }
        });*/

        Log.i(TAG ,""+spinnerItem.isVisible());
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Fetch the data remotely
                clear();
                queryPostsWithTitle(query);
                // Reset SearchView
                searchView.clearFocus();
                searchView.setQuery("", false);
                searchView.setIconified(true);
                searchItem.collapseActionView();
                // Set activity title to search query
                //BookListActivity.this.setTitle(query);

                return true;

            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        //return true;

    }


    protected void queryPostsPriceHighLow() {
        Log.i(TAG,"Post: hello");
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        //get all the posts
        query.include(Post.KEY_USER);

        query.setLimit(20);
        query.addDescendingOrder(Post.PRICE);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e != null) {
                    Log.e(TAG,"Issue with getting posts",e);
                }
                for (Post post : posts) {
                    Log.i(TAG,"Post: " + post.getBookTitle() + ", description: " + post.getBookDescription() +  ", category: " + post.getBookCategory() +", username: " + post.getUser().getUsername() +", price: " + Integer.toString(post.getPrice()) + " ISBN: " + post.getISBN());
                }
                allPosts.addAll(posts);
                adapter.notifyDataSetChanged();
            }
        });

    }

    protected void queryPostsPriceLowHigh() {
        Log.i(TAG,"Post: hello");
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        //get all the posts
        query.include(Post.KEY_USER);

        query.setLimit(20);
        query.addAscendingOrder(Post.PRICE);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e != null) {
                    Log.e(TAG,"Issue with getting posts",e);
                }
                for (Post post : posts) {
                    Log.i(TAG,"Post: " + post.getBookTitle() + ", description: " + post.getBookDescription() +  ", category: " + post.getBookCategory() +", username: " + post.getUser().getUsername() +", price: " + Integer.toString(post.getPrice()) + " ISBN: " + post.getISBN());
                }
                allPosts.addAll(posts);
                adapter.notifyDataSetChanged();
            }
        });

    }

    //@Override
    protected void queryPostsWithTitle(String title) {
        Log.i(TAG,"Post: hello");
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        //get all the posts
        query.include(Post.KEY_USER);
        if (Character.isDigit(title.charAt(0)))
            query.whereContains(Post.BOOK_NUMBER,title);
        else
            query.whereContains(Post.BOOK_TITLE, title);

        query.setLimit(20);
        query.addDescendingOrder(Post.KEY_CREATED_KEY);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e != null) {
                    Log.e(TAG,"Issue with getting posts",e);
                }
                for (Post post : posts) {
                    Log.i(TAG,"Post: " + post.getBookTitle() + ", description: " + post.getBookDescription() +  ", category: " + post.getBookCategory() +", username: " + post.getUser().getUsername() +", price: " + Integer.toString(post.getPrice()) + " ISBN: " + post.getISBN());
                }
                allPosts.addAll(posts);
                adapter.notifyDataSetChanged();
            }
        });

    }

    //@Override
    protected void queryPosts() {
        Log.i(TAG,"Post: hello");
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        //get all the posts
        query.include(Post.KEY_USER);
        //query.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser());
        query.setLimit(20);
        query.addDescendingOrder(Post.KEY_CREATED_KEY);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e != null) {
                    Log.e(TAG,"Issue with getting posts",e);
                }
                for (Post post : posts) {
                    Log.i(TAG,"Post: " + post.getBookTitle() + ", description: " + post.getBookDescription() +  ", category: " + post.getBookCategory() +", username: " + post.getUser().getUsername() +", price: " + Integer.toString(post.getPrice()) + " ISBN: " + post.getISBN());
                }
                allPosts.addAll(posts);
                adapter.notifyDataSetChanged();
            }
        });

    }

    public void clear() {
        allPosts.clear();
        adapter.notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Post> posts) {
        allPosts.addAll(posts);
        adapter.notifyDataSetChanged();
    }
}