package com.aurora.sampleproject2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.aurora.sampleproject2.exoPlayer.MyExoPlayerRecyclerView.MyExoPlayerRecyclerView;

import com.aurora.sampleproject2.exoPlayer.myAdapter.PostAdpterExoPlayer;
import com.aurora.sampleproject2.model.db.entity.Post;
import com.aurora.sampleproject2.viewmodel.ListViewModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class MainActivity2 extends AppCompatActivity {


    MyExoPlayerRecyclerView recyclerView;
  //  ActivityMain2Binding binding;
    private  ArrayList<Post> posts = new ArrayList<>();
    ListViewModel mViewModel;
    PostAdpterExoPlayer postsAdapter;
    boolean allowSetPost=true;
    private boolean firstTime = true;
    FloatingActionButton fabInsert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        // binding= DataBindingUtil.setContentView(this,R.layout.activity_main2);
      //recyclerView=binding.rvHome;
        fabInsert=(FloatingActionButton)findViewById(R.id.fabInsert);
        fabInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),InsertPostActivity.class));
            }
        });

        initRecycler();
         mViewModel = new ViewModelProvider(this).get(ListViewModel.class);
        mViewModel.getListLiveData().observe(this, new Observer<List<Post>>() {
            @Override
            public void onChanged(List<Post> p) {
                posts = (ArrayList<Post>) p;
                if(allowSetPost) {
                    Toast.makeText(MainActivity2.this, posts.size() + "", Toast.LENGTH_SHORT).show();
                    recyclerView.setPosts(posts);
                    //Set Adapter
                    postsAdapter.setPosts(posts);
                    //recyclerView.setAdapter(postsAdapter);
                    if (firstTime) {
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                recyclerView.playVideo(false);
                            }
                        });
                        firstTime = false;
                    }
                    allowSetPost=false;
                }
            }
        });


    }


    private RequestManager initGlide() {
        RequestOptions options = new RequestOptions();
        return Glide.with(this)
                .setDefaultRequestOptions(options);
    }
    @Override
    protected void onDestroy() {
        if (recyclerView != null) {
            recyclerView.releasePlayer();
        }
        super.onDestroy();
    }


    private void initRecycler() {
        //recyclerView = binding.exoPlayerRecyclerView;
        recyclerView = (MyExoPlayerRecyclerView)findViewById(R.id.exoPlayerRecyclerView);
        LinearLayoutManager recylerViewLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(recylerViewLayoutManager);
        recyclerView.setHasFixedSize(true);
        postsAdapter = new PostAdpterExoPlayer(initGlide());
        recyclerView.setAdapter(postsAdapter);
        postsAdapter.setPosts(posts);
        postsAdapter.setOnItemClickListener(new PostAdpterExoPlayer.OnItemClickListener() {
            @Override
            public void onItemClick(Post post,String type) {
                if(type.equals("like")){
                    mViewModel.updatePost(post);
                }else {
                    sharePost(post);
                }

            }
        });





//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//
//        //todo before setAdapter
//        recyclerView.setActivity(this);
//
//        //optional - to play only first visible video
//        recyclerView.setPlayOnlyFirstVideo(true); // false by default
//
//        //optional - by default we check if url ends with ".mp4". If your urls do not end with mp4, you can set this param to false and implement your own check to see if video points to url
//        recyclerView.setCheckForMp4(false); //true by default
//
//        //optional - download videos to local storage (requires "android.permission.WRITE_EXTERNAL_STORAGE" in manifest or ask in runtime)
//        // recyclerView.setDownloadPath(Environment.getExternalStorageDirectory() + "/MyVideo"); // (Environment.getExternalStorageDirectory() + "/Video") by default
//
//        // recyclerView.setDownloadVideos(true); // false by default
//
//        recyclerView.setVisiblePercent(100); // percentage of View that needs to be visible to start playing
//
//        //extra - start downloading all videos in background before loading RecyclerView
////        List<String> urls = new ArrayList<>();
////        for (MyModel object : modelList) {
////            if (object.getVideo_url() != null && object.getVideo_url().contains("http"))
////                urls.add(object.getVideo_url());
////        }
//        // recyclerView.preDownload(urls);
//
//        recyclerView.setAdapter(postsAdapter);
//        //call this functions when u want to start autoplay on loading async lists (eg firebase)
//        recyclerView.smoothScrollBy(0,1);
//        recyclerView.smoothScrollBy(0,-1);


    }

    private void sharePost(Post post) {

        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        if(!post.getMode().equals("text")) {
            Uri screenshotUri = Uri.parse(post.getAddressFile());
            sharingIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
        }
        sharingIntent.setType(post.getMode()+"/*");
        //sharingIntent.setType("*/*");
        // sharingIntent.putExtra(Intent.EXTRA_SUBJECT, post.getText());
        sharingIntent.putExtra(Intent.EXTRA_TEXT, post.getText());
        startActivity(Intent.createChooser(sharingIntent, "Share using"));


    }

    @Override
    protected void onPause() {
        recyclerView.onPausePlayer();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
     // recyclerView.playVideo(false);
      //recyclerView.playAvailableVideos(0);
    }
}