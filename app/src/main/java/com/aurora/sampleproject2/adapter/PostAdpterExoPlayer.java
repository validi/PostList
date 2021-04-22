package com.aurora.sampleproject2.adapter;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.aurora.sampleproject2.R;
import com.aurora.sampleproject2.databinding.RowPostItemImageBinding;
import com.aurora.sampleproject2.databinding.RowPostItemTextBinding;

import com.aurora.sampleproject2.model.db.entity.Post;
import com.bumptech.glide.RequestManager;

import java.util.ArrayList;

public class PostAdpterExoPlayer extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Post> posts;
    public OnItemClickListener onItemClickListener;
    private static final int TYPE_Image = 0;
    private static final int TYPE_Video = 1;
    private static final int TYPE_Text = 2;
    private static final int TYPE_ExtraSpace = 3;

    private RequestManager requestManager;
    public PostAdpterExoPlayer(RequestManager requestManager) {
        this.requestManager = requestManager;
    }

    @NonNull
    @Override
    public PlayerViewHolderExoPlayer onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == TYPE_Image) {
            RowPostItemImageBinding rowPostItemBinding =
                    DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.row_post_item_image, parent, false);
            return new PlayerViewHolderExoPlayer(rowPostItemBinding);
        } else if (viewType == TYPE_Video) {
//            RowPostItemVideoBinding rowPostItemVideoBinding =
//                    DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.row_post_item_video, parent, false);
//            return new PostViewHolder(rowPostItemVideoBinding);

            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_post_item_video, parent, false);
            return new PlayerViewHolderExoPlayer(itemView);

        } else if (viewType == TYPE_Text) {
            RowPostItemTextBinding rowPostItemTextBinding =
                    DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.row_post_item_text, parent, false);
            return new PlayerViewHolderExoPlayer(rowPostItemTextBinding);
        }
        else {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.extra_space, parent, false);
            return new PlayerViewHolderExoPlayer(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder h, int position) {


        if(position!=posts.size()) {
            PlayerViewHolderExoPlayer holder = ((PlayerViewHolderExoPlayer) h);

            Post post = posts.get(position);

            if (post.getMode().equals("image")) {
                holder.rowPostItemImageBinding.imgLike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        post.setLike(!post.isLike());
                        change_like(holder.rowPostItemImageBinding.imgLike, post.isLike());

                        posts.get(position).setLike(post.isLike());
                        onItemClickListener.onItemClick(post, "like");
                    }
                });
                holder.rowPostItemImageBinding.imgShare.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onItemClickListener.onItemClick(post, "share");
                    }
                });
                holder.rowPostItemImageBinding.setPost(post);
            } else if (post.getMode().equals("video")) {
                holder.onBind(posts.get(position), requestManager);
                //todo

                Bitmap thumb = ThumbnailUtils.createVideoThumbnail(post.getAddressFile(), MediaStore.Images.Thumbnails.MINI_KIND);
                Matrix matrix = new Matrix();
                Bitmap bmThumbnail = Bitmap.createBitmap(thumb, 0, 0,
                        thumb.getWidth(), thumb.getHeight(), matrix, true);
                holder.mediaCoverImage.setImageBitmap(bmThumbnail);

                if (post.isLike()) {
                    holder.imgLike.setImageResource(R.drawable.ic_heart_selected);
                } else {
                    holder.imgLike.setImageResource(R.drawable.ic_heart_no_selected);
                }
                holder.imgLike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        post.setLike(!post.isLike());
                        change_like(holder.imgLike, post.isLike());
                        posts.get(position).setLike(post.isLike());
                        onItemClickListener.onItemClick(post, "like");
                    }
                });
                holder.imgShare.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onItemClickListener.onItemClick(post, "share");
                    }
                });

            } else {
                holder.rowPostItemTextBinding.imgLike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        post.setLike(!post.isLike());
                        posts.get(position).setLike(post.isLike());
                        change_like(holder.rowPostItemTextBinding.imgLike, post.isLike());

                        onItemClickListener.onItemClick(post, "like");

                    }
                });
                holder.rowPostItemTextBinding.imgShare.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onItemClickListener.onItemClick(post, "share");
                    }
                });
                holder.rowPostItemTextBinding.setPost(post);
            }
        }

    }


    public void change_like(ImageView imageView, boolean isLike) {
        if (isLike) {
            imageView.setImageResource(R.drawable.ic_heart_selected);
        } else {
            imageView.setImageResource(R.drawable.ic_heart_no_selected);
        }
    }

    @Override
    public int getItemCount() {
        return posts.size()+1;
    }

    public void setPosts(ArrayList<Post> newPosts) {
        //  this.books = books;
        // notifyDataSetChanged();
        Log.i("TestS",newPosts.size()+"");
        final DiffUtil.DiffResult result = DiffUtil.calculateDiff(new PostsDiffCallback(posts, newPosts), false);
        posts = newPosts;
        result.dispatchUpdatesTo(PostAdpterExoPlayer.this);
    }


    public interface OnItemClickListener {
        void onItemClick(Post post, String Type);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        if(position==posts.size()){
            return TYPE_ExtraSpace;
        }
        if (posts.get(position).getMode().equals("image")) {
            return TYPE_Image;
        } else if (posts.get(position).getMode().equals("video")) {
            return TYPE_Video;
        } else {
            return TYPE_Text;
        }
    }

}
