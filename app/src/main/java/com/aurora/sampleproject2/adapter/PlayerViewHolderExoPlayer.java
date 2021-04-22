package com.aurora.sampleproject2.adapter;


import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aurora.sampleproject2.R;
import com.aurora.sampleproject2.databinding.RowPostItemImageBinding;
import com.aurora.sampleproject2.databinding.RowPostItemTextBinding;

import com.aurora.sampleproject2.model.db.entity.Post;
import com.bumptech.glide.RequestManager;

public class PlayerViewHolderExoPlayer extends RecyclerView.ViewHolder {
    /**
     * below view have public modifier because
     * we have access PlayerViewHolder inside the ExoPlayerRecyclerView
     */
    public FrameLayout mediaContainer;
    public ImageView mediaCoverImage, volumeControl,imgLike,imgShare;
    public ProgressBar progressBar;
    public RequestManager requestManager;
    private TextView title, userHandle;
    private View parent;

    RowPostItemImageBinding rowPostItemImageBinding;
    RowPostItemTextBinding rowPostItemTextBinding;


    public PlayerViewHolderExoPlayer(@NonNull View itemView) {
        super(itemView);
        parent = itemView;
        mediaContainer = itemView.findViewById(R.id.mediaContainer);
        mediaCoverImage = itemView.findViewById(R.id.ivMediaCoverImage);
        title = itemView.findViewById(R.id.txtDescription);
        progressBar = itemView.findViewById(R.id.progressBar);
        volumeControl = itemView.findViewById(R.id.ivVolumeControl);
        imgLike=itemView.findViewById(R.id.imgLike);
        imgShare=itemView.findViewById(R.id.imgShare);
    }

    void onBind(Post mediaObject, RequestManager requestManager) {
        this.requestManager = requestManager;
        parent.setTag(this);
        title.setText(mediaObject.getText());

    }


    public PlayerViewHolderExoPlayer(@NonNull RowPostItemImageBinding itemView_image) {

        super(itemView_image.getRoot());

        this.rowPostItemImageBinding = itemView_image;

    }
    public PlayerViewHolderExoPlayer(@NonNull RowPostItemTextBinding itemView_text) {

        super(itemView_text.getRoot());
        this.rowPostItemTextBinding = itemView_text;

    }




}