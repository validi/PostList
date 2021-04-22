package com.aurora.sampleproject2.adapter;

import android.app.Activity;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.allattentionhere.autoplayvideos.AAH_VideoImage;
import com.aurora.sampleproject2.R;
import com.aurora.sampleproject2.databinding.RowPostItemImageBinding;
import com.aurora.sampleproject2.databinding.RowPostItemTextBinding;

import java.util.concurrent.Callable;

public class AAH_CustomViewHolder1 extends RecyclerView.ViewHolder {

    private AAH_VideoImage aah_vi;
    private String imageUrl;
    private String videoUrl;
    private boolean isLooping = true;
    private boolean isPaused = false;
    TextView txtDescription;
    ImageView imgLike,imgShare;

    /////////////////////////////////////////////


    RowPostItemImageBinding rowPostItemImageBinding;
    RowPostItemTextBinding rowPostItemTextBinding;

    public AAH_CustomViewHolder1(@NonNull RowPostItemImageBinding itemView_image) {

        super(itemView_image.getRoot());


        // super(itemView_image.getRoot());
        this.rowPostItemImageBinding = itemView_image;
//        itemView_image.getRoot().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int clickPosition = getAdapterPosition();
//                if (onItemClickListener != null && clickPosition != RecyclerView.NO_POSITION)
//                    onItemClickListener.onItemClick(posts.get(clickPosition));
//            }
//        });

    }
    public AAH_CustomViewHolder1(@NonNull RowPostItemTextBinding itemView_text) {

        super(itemView_text.getRoot());
        this.rowPostItemTextBinding = itemView_text;
//        itemView_text.getRoot().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int clickPosition = getAdapterPosition();
//                if (onItemClickListener != null && clickPosition != RecyclerView.NO_POSITION)
//                    onItemClickListener.onItemClick(posts.get(clickPosition));
//            }
//        });


    }


    public AAH_CustomViewHolder1(View x) {
        super(x);
        aah_vi = (AAH_VideoImage) x.findViewWithTag("aah_vi");
        txtDescription=(TextView)x.findViewById(R.id.txtDescription);
        imgLike=(ImageView)x.findViewById(R.id.imgLike);
        imgShare=(ImageView)x.findViewById(R.id.imgShare);

    }

    public void playVideo() {
        this.aah_vi.getCustomVideoView().setPaused(false);
        this.aah_vi.getCustomVideoView().startVideo();
    }

    public void videoStarted() {
        this.aah_vi.getImageView().setVisibility(View.GONE);
    }
    public void showThumb() {
        this.aah_vi.getImageView().setVisibility(View.VISIBLE);
    }

    public void initVideoView(String url, Activity _act) {
        this.aah_vi.getCustomVideoView().setVisibility(View.VISIBLE);
        Uri uri = Uri.parse(url);
        this.aah_vi.getCustomVideoView().setSource(uri);
        this.aah_vi.getCustomVideoView().setLooping(isLooping);
        this.aah_vi.getCustomVideoView().set_act(_act);
        this.aah_vi.getCustomVideoView().setMyFuncIn(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                videoStarted();
                return null;
            }
        });

        this.aah_vi.getCustomVideoView().setShowThumb(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                showThumb();
                return null;
            }
        });
    }

    public void setLooping(boolean looping) {
        isLooping = looping;
    }

    public void pauseVideo() {
        this.aah_vi.getCustomVideoView().pauseVideo();
        this.aah_vi.getCustomVideoView().setPaused(true);
    }

    public void muteVideo() {
        this.aah_vi.getCustomVideoView().muteVideo();
    }

    public void unmuteVideo() {
        this.aah_vi.getCustomVideoView().unmuteVideo();
    }

    public AAH_VideoImage getAah_vi() {
        return aah_vi;
    }

    public ImageView getAAH_ImageView() {
        return aah_vi.getImageView();
    }

    public String getImageUrl() {
        return imageUrl + "";
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        this.aah_vi.getImageView().setVisibility(View.VISIBLE);
        this.aah_vi.getCustomVideoView().setVisibility(View.GONE);
    }

    public void setAah_vi(AAH_VideoImage aah_vi) {
        this.aah_vi = aah_vi;
    }

    public String getVideoUrl() {
        return videoUrl + "";
    }

    public boolean isPlaying() {
        return this.aah_vi.getCustomVideoView().isPlaying();
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public void setPaused(boolean paused) {
        isPaused = paused;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public boolean isLooping() {
        return isLooping;
    }
}