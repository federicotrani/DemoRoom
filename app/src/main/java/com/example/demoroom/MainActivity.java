package com.example.demoroom;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.demoroom.db.Post;

public class MainActivity extends AppCompatActivity implements PostAdapter.OnDeleteButtonClickListener {

    private PostAdapter postAdapter;
    private PostViewModel postViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        postAdapter = new PostAdapter(this, this);

        postViewModel = ViewModelProviders.of(this).get(PostViewModel.class);
        postViewModel.getAllPosts().observe(this, posts -> postAdapter.setData(posts));

        RecyclerView recyclerView = findViewById(R.id.rvPostsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(postAdapter);

    }

    @Override
    public void onDeleteButtonClicked(Post post) {

    }
}
