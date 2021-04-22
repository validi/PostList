package com.aurora.sampleproject2.model;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.aurora.sampleproject2.model.db.PostDataBase;
import com.aurora.sampleproject2.model.db.dao.PostDao;
import com.aurora.sampleproject2.model.db.entity.Post;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class PostRepository {

    private PostDao postDao;
    private LiveData<List<Post>> listLiveData;

    public PostRepository(Application application) {
        PostDataBase postDataBase = PostDataBase.getInstance(application);
        this.postDao = postDataBase.getPostDao();

    }

    public LiveData<List<Post>> getListLiveData() {
        return postDao.getAllPost();
    }

    public void insertPost(Post post) {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                postDao.insert(post);
            }
        });
    }
    public void deletePost(Post post) {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                postDao.delete(post);
            }
        });
    }
    public void updatePost(Post post) {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                postDao.update(post);
            }
        });
    }


}
