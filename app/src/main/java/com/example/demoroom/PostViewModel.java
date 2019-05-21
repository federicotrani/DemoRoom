package com.example.demoroom;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.demoroom.db.Post;
import com.example.demoroom.db.PostDao;
import com.example.demoroom.db.PostsDatabase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PostViewModel extends AndroidViewModel {

    private PostDao postDao;
    private ExecutorService executorService;

    public PostViewModel(@NonNull Application application) {
        super(application);
        postDao = PostsDatabase.getInstance(application).postDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    LiveData<List<Post>> getAllPosts(){
        return postDao.findAll();
    }

    void savePost(Post post){
        executorService.execute(()-> postDao.save(post));
    }

    void deletePost(Post post){
        executorService.execute(()-> postDao.delete(post));
    }
}
