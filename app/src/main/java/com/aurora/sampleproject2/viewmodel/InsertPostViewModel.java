package com.aurora.sampleproject2.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.aurora.sampleproject2.model.PostRepository;
import com.aurora.sampleproject2.model.db.entity.Post;

import java.util.List;

public class InsertPostViewModel extends AndroidViewModel {
    private PostRepository postRepository;


    public InsertPostViewModel(@NonNull Application application) {
        super(application);
        this.postRepository=new PostRepository(application);
    }

    public void insertPost(Post post){
        postRepository.insertPost(post);
    }
}