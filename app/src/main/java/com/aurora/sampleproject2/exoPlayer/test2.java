package com.aurora.sampleproject2.exoPlayer;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.aurora.sampleproject2.R;
import com.aurora.sampleproject2.exoPlayer.adapter.ExoPlayerRecyclerView;
import com.aurora.sampleproject2.exoPlayer.adapter.MediaRecyclerAdapter;
import com.aurora.sampleproject2.exoPlayer.model.MediaObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import static android.widget.LinearLayout.VERTICAL;

public class test2 extends AppCompatActivity {
    ExoPlayerRecyclerView mRecyclerView;
    private ArrayList<MediaObject> mediaObjectList = new ArrayList<>();
    private MediaRecyclerAdapter mAdapter;
    private boolean firstTime = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);
        initView();
        // Prepare demo content
        prepareVideoList();
        //set data object
        mRecyclerView.setMediaObjects(mediaObjectList);
        mAdapter = new MediaRecyclerAdapter(mediaObjectList, initGlide());
        //Set Adapter
        mRecyclerView.setAdapter(mAdapter);
        if (firstTime) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    mRecyclerView.playVideo(false);
                }
            });
            firstTime = false;
        }
    }
    @SuppressLint("WrongConstant")
    private void initView() {
        mRecyclerView = findViewById(R.id.exoPlayerRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, VERTICAL, false));
        Drawable dividerDrawable = ContextCompat.getDrawable(this, R.drawable.divider_drawable);
       // mRecyclerView.addItemDecoration(new DividerItemDecoration(dividerDrawable));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }
    private RequestManager initGlide() {
        RequestOptions options = new RequestOptions();
        return Glide.with(this)
                .setDefaultRequestOptions(options);
    }
    @Override
    protected void onDestroy() {
        if (mRecyclerView != null) {
            mRecyclerView.releasePlayer();
        }
        super.onDestroy();
    }

    private void prepareVideoList() {
        MediaObject mediaObject = new MediaObject();
        mediaObject.setId(1);
        mediaObject.setUserHandle("@h.pandya");
        mediaObject.setTitle(
                "Do you think the concept of marriage will no longer exist in the future?");
        mediaObject.setCoverUrl(
                "https://androidwave.com/media/images/exo-player-in-recyclerview-in-android-1.png");
       // mediaObject.setUrl("https://hajifirouz3.cdn.asset.aparat.com/aparat-video/d36ba9bf5f333fa911f3a125371620b232401145-360p.mp4?wmsAuthSign=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbiI6ImNlNWFkODVjMDFhNTA1YTZhMDhkOWE3YWZmYTIwN2JiIiwiZXhwIjoxNjE5MTE0MjUyLCJpc3MiOiJTYWJhIElkZWEgR1NJRyJ9.DlJZSPnT1VU_88wZSieC0G7iFrKTvvK0pv8pjAe6acs");
       mediaObject.setUrl("/storage/emulated/0/ali.mp4");
        MediaObject mediaObject2 = new MediaObject();
        mediaObject2.setId(2);
        mediaObject2.setUserHandle("@hardik.patel");
        mediaObject2.setTitle(
                "If my future husband doesn't cook food as good as my mother should I scold him?");
        mediaObject2.setCoverUrl(
                "https://androidwave.com/media/images/exo-player-in-recyclerview-in-android-2.png");
        mediaObject2.setUrl("https://hajifirouz3.cdn.asset.aparat.com/aparat-video/d36ba9bf5f333fa911f3a125371620b232401145-360p.mp4?wmsAuthSign=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbiI6ImNlNWFkODVjMDFhNTA1YTZhMDhkOWE3YWZmYTIwN2JiIiwiZXhwIjoxNjE5MTE0MjUyLCJpc3MiOiJTYWJhIElkZWEgR1NJRyJ9.DlJZSPnT1VU_88wZSieC0G7iFrKTvvK0pv8pjAe6acs");
        MediaObject mediaObject3 = new MediaObject();
        mediaObject3.setId(3);
        mediaObject3.setUserHandle("@arun.gandhi");
        mediaObject3.setTitle("Give your opinion about the Ayodhya temple controversy.");
        mediaObject3.setCoverUrl(
                "https://androidwave.com/media/images/exo-player-in-recyclerview-in-android-3.png");
        mediaObject3.setUrl("https://hajifirouz3.cdn.asset.aparat.com/aparat-video/d36ba9bf5f333fa911f3a125371620b232401145-360p.mp4?wmsAuthSign=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbiI6ImNlNWFkODVjMDFhNTA1YTZhMDhkOWE3YWZmYTIwN2JiIiwiZXhwIjoxNjE5MTE0MjUyLCJpc3MiOiJTYWJhIElkZWEgR1NJRyJ9.DlJZSPnT1VU_88wZSieC0G7iFrKTvvK0pv8pjAe6acs");
        MediaObject mediaObject4 = new MediaObject();
        mediaObject4.setId(4);
        mediaObject4.setUserHandle("@sachin.patel");
        mediaObject4.setTitle("When did kama founders find sex offensive to Indian traditions");
        mediaObject4.setCoverUrl(
                "https://androidwave.com/media/images/exo-player-in-recyclerview-in-android-4.png");
        mediaObject4.setUrl("https://hajifirouz3.cdn.asset.aparat.com/aparat-video/d36ba9bf5f333fa911f3a125371620b232401145-360p.mp4?wmsAuthSign=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbiI6ImNlNWFkODVjMDFhNTA1YTZhMDhkOWE3YWZmYTIwN2JiIiwiZXhwIjoxNjE5MTE0MjUyLCJpc3MiOiJTYWJhIElkZWEgR1NJRyJ9.DlJZSPnT1VU_88wZSieC0G7iFrKTvvK0pv8pjAe6acs");
        MediaObject mediaObject5 = new MediaObject();
        mediaObject5.setId(5);
        mediaObject5.setUserHandle("@monika.sharma");
        mediaObject5.setTitle("When did you last cry in front of someone?");
        mediaObject5.setCoverUrl(
                "https://androidwave.com/media/images/exo-player-in-recyclerview-in-android-5.png");
        mediaObject5.setUrl("https://hajifirouz3.cdn.asset.aparat.com/aparat-video/d36ba9bf5f333fa911f3a125371620b232401145-360p.mp4?wmsAuthSign=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbiI6ImNlNWFkODVjMDFhNTA1YTZhMDhkOWE3YWZmYTIwN2JiIiwiZXhwIjoxNjE5MTE0MjUyLCJpc3MiOiJTYWJhIElkZWEgR1NJRyJ9.DlJZSPnT1VU_88wZSieC0G7iFrKTvvK0pv8pjAe6acs");
        mediaObjectList.add(mediaObject);
        mediaObjectList.add(mediaObject2);
        mediaObjectList.add(mediaObject3);
        mediaObjectList.add(mediaObject4);
        mediaObjectList.add(mediaObject5);
        mediaObjectList.add(mediaObject);
        mediaObjectList.add(mediaObject2);
        mediaObjectList.add(mediaObject3);
        mediaObjectList.add(mediaObject4);
        mediaObjectList.add(mediaObject5);
    }
}