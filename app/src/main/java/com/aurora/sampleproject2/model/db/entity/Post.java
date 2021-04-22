package com.aurora.sampleproject2.model.db.entity;

import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.aurora.sampleproject2.BR;
import com.aurora.sampleproject2.R;


import java.io.File;


@Entity(tableName = "tblPost")
public class Post extends BaseObservable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String text;
    private String addressFile;
    private String mode;
    private boolean like;


    @BindingAdapter({"poster"})
    public static void loadImage(ImageView imageView, String imageUrl) {
        // datum.setPoster(datum.getPoster());
//        Picasso.with(imageView.getContext())
//                .load(imageUrl)
//                .into(imageView);
        File imageFile = new File(imageUrl);
        if (imageFile.exists()) {
            imageView.setImageBitmap(BitmapFactory.decodeFile(imageFile.getAbsolutePath()));
        }
    }

    @BindingAdapter({"like"})
    public static void loadImage_Like(ImageView imageView, boolean isLike) {

        if (isLike) {
            imageView.setImageResource(R.drawable.ic_heart_selected);
        } else {
            imageView.setImageResource(R.drawable.ic_heart_no_selected);
        }
    }

    @BindingAdapter({"video"})
    public static void loadVideo(VideoView videoView, String imageUrl) {

        File imageFile = new File(imageUrl);
        if (imageFile.exists()) {
            //imageView.setImageBitmap(BitmapFactory.decodeFile(imageFile.getAbsolutePath()));
            //  videoView.setVideoURI(Uri.parse(imageUrl));
            // MediaController mediaController = new MediaController(videoView.getContext());
            //  mediaController.setAnchorView(videoView);
            //  videoView.setMediaController(mediaController);


            // String path = yourAndroidURI.uri.toString() // "file:///mnt/sdcard/FileName.mp3"
            // String path = yourAndroidURI.uri.toString() // "file:///mnt/sdcard/storage/emulated/0/WhatsApp/Media/WhatsApp Video/VID-20210420-WA0009.mp4"
            // File file = new File(new URI(path));

            //  String p="mnt/sdcard/WhatsApp/Media/WhatsApp Video/VID-20210420-WA0009.mp4";

            //  Log.i("Test", p);

            videoView.setVideoPath(imageFile.getAbsolutePath());


            videoView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (videoView.isPlaying()) {
                        videoView.pause();
                    } else {
                        videoView.start();
                    }
                }
            });

        }
    }


    public Post(int id, String text, String addressFile, String mode, boolean like) {
        this.id = id;
        this.text = text;
        this.addressFile = addressFile;
        this.mode = mode;
        this.like = like;
    }

    @Ignore
    public Post() {
    }

    @Bindable
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        notifyPropertyChanged(BR.id);
    }

    @Bindable
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        notifyPropertyChanged(BR.text);
    }

    @Bindable
    public String getAddressFile() {
        return addressFile;
    }

    public void setAddressFile(String addressFile) {
        this.addressFile = addressFile;
        notifyPropertyChanged(BR.addressFile);
    }

    @Bindable
    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
        notifyPropertyChanged(BR.mode);
    }

    @Bindable
    public boolean isLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
        notifyPropertyChanged(BR.like);
    }
}
