package com.aurora.sampleproject2.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.aurora.sampleproject2.model.PostRepository;
import com.aurora.sampleproject2.model.db.entity.Post;

import java.util.List;

public class ListViewModel extends AndroidViewModel {

    private PostRepository postRepository;
    private LiveData<List<Post>> listLiveData;

    public ListViewModel(@NonNull Application application) {
        super(application);
        this.postRepository=new PostRepository(application);
    }

    public LiveData<List<Post>> getListLiveData() {
        return this.postRepository.getListLiveData();
    }

    public void updatePost(Post post){
        postRepository.updatePost(post);
    }


}