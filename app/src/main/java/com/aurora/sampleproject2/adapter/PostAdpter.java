package com.aurora.sampleproject2.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.allattentionhere.autoplayvideos.AAH_VideoImage;
import com.allattentionhere.autoplayvideos.AAH_VideosAdapter;
import com.aurora.sampleproject2.R;
import com.aurora.sampleproject2.databinding.RowPostItemImageBinding;
import com.aurora.sampleproject2.databinding.RowPostItemImageBinding;
import com.aurora.sampleproject2.databinding.RowPostItemTextBinding;
import com.aurora.sampleproject2.databinding.RowPostItemVideoBinding;
import com.aurora.sampleproject2.model.db.entity.Post;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.Callable;

public class PostAdpter extends AAH_VideosAdapter1 {

    private ArrayList<Post> posts;
    public OnItemClickListener onItemClickListener;
    private static final int TYPE_Image = 0;
    private static final int TYPE_Video = 1;
    private static final int TYPE_Text = 2;


    @NonNull
    @Override
    public AAH_CustomViewHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == TYPE_Image) {
            RowPostItemImageBinding rowPostItemBinding =
                    DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.row_post_item_image, parent, false);
            return new AAH_CustomViewHolder1(rowPostItemBinding);
        } else if (viewType == TYPE_Video) {
//            RowPostItemVideoBinding rowPostItemVideoBinding =
//                    DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.row_post_item_video, parent, false);
//            return new PostViewHolder(rowPostItemVideoBinding);

            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_post_item_video, parent, false);
            return new AAH_CustomViewHolder1(itemView);

        } else {
            RowPostItemTextBinding rowPostItemTextBinding =
                    DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.row_post_item_text, parent, false);
            return new AAH_CustomViewHolder1(rowPostItemTextBinding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull AAH_CustomViewHolder1 holder, int position) {
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

            //todo

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    holder.setVideoUrl(post.getAddressFile());
                    holder.txtDescription.setText(post.getText());
                }
            },1000);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // holder.setImageUrl(po);
                    if (post.isLike()) {
                        holder.imgLike.setImageResource(R.drawable.ic_heart_selected);
                    } else {
                        holder.imgLike.setImageResource(R.drawable.ic_heart_no_selected);
                    }

                }
            }, 1500);

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

    public void change_like(ImageView imageView, boolean isLike) {
        if (isLike) {
            imageView.setImageResource(R.drawable.ic_heart_selected);
        } else {
            imageView.setImageResource(R.drawable.ic_heart_no_selected);
        }
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public void setPosts(ArrayList<Post> newPosts) {
        //  this.books = books;
        // notifyDataSetChanged();

        final DiffUtil.DiffResult result = DiffUtil.calculateDiff(new PostsDiffCallback(posts, newPosts), false);
        posts = newPosts;
        result.dispatchUpdatesTo(PostAdpter.this);
    }


//    public class PostViewHolder extends RecyclerView.ViewHolder {
//
//        RowPostItemImageBinding rowPostItemImageBinding;
//        RowPostItemTextBinding rowPostItemTextBinding;
//       // RowPostItemVideoBinding rowPostItemVideoBinding;
////       public PostViewHolder(@NonNull RowPostItemVideoBinding itemView_video) {
////
////           super(itemView_video.getRoot());
////
////           this.rowPostItemVideoBinding = itemView_video;
////           itemView_video.getRoot().setOnClickListener(new View.OnClickListener() {
////               @Override
////               public void onClick(View view) {
////                   int clickPosition = getAdapterPosition();
////                   if (onItemClickListener != null && clickPosition != RecyclerView.NO_POSITION)
////                       onItemClickListener.onItemClick(posts.get(clickPosition));
////               }
////           });
////
////
////       }
//
//        public PostViewHolder(@NonNull RowPostItemImageBinding itemView_image) {
//
//            super(itemView_image.getRoot());
//
//
//            // super(itemView_image.getRoot());
//            this.rowPostItemImageBinding = itemView_image;
//            itemView_image.getRoot().setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    int clickPosition = getAdapterPosition();
//                    if (onItemClickListener != null && clickPosition != RecyclerView.NO_POSITION)
//                        onItemClickListener.onItemClick(posts.get(clickPosition));
//                }
//            });
//
//        }
//        public PostViewHolder(@NonNull RowPostItemTextBinding itemView_text) {
//
//            super(itemView_text.getRoot());
//            this.rowPostItemTextBinding = itemView_text;
//            itemView_text.getRoot().setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    int clickPosition = getAdapterPosition();
//                    if (onItemClickListener != null && clickPosition != RecyclerView.NO_POSITION)
//                        onItemClickListener.onItemClick(posts.get(clickPosition));
//                }
//            });
//
//
//        }
//
//
//        private AAH_VideoImage aah_vi;
//        private String imageUrl;
//        private String videoUrl;
//        private boolean isLooping = true;
//        private boolean isPaused = false;
//        TextView txtDescription;
//
//        public PostViewHolder(View x) {
//            super(x);
//            aah_vi = (AAH_VideoImage) x.findViewWithTag("aah_vi");
//            txtDescription=(TextView)x.findViewById(R.id.txtDescription);
//        }
//
//        public void playVideo() {
//            this.aah_vi.getCustomVideoView().setPaused(false);
//            this.aah_vi.getCustomVideoView().startVideo();
//        }
//
//        public void videoStarted() {
//            this.aah_vi.getImageView().setVisibility(View.GONE);
//        }
//        public void showThumb() {
//            this.aah_vi.getImageView().setVisibility(View.VISIBLE);
//        }
//
//        public void initVideoView(String url, Activity _act) {
//            this.aah_vi.getCustomVideoView().setVisibility(View.VISIBLE);
//            Uri uri = Uri.parse(url);
//            this.aah_vi.getCustomVideoView().setSource(uri);
//            this.aah_vi.getCustomVideoView().setLooping(isLooping);
//            this.aah_vi.getCustomVideoView().set_act(_act);
//            this.aah_vi.getCustomVideoView().setMyFuncIn(new Callable<Integer>() {
//                @Override
//                public Integer call() throws Exception {
//                    videoStarted();
//                    return null;
//                }
//            });
//
//            this.aah_vi.getCustomVideoView().setShowThumb(new Callable<Integer>() {
//                @Override
//                public Integer call() throws Exception {
//                    showThumb();
//                    return null;
//                }
//            });
//        }
//
//        public void setLooping(boolean looping) {
//            isLooping = looping;
//        }
//
//        public void pauseVideo() {
//            this.aah_vi.getCustomVideoView().pauseVideo();
//            this.aah_vi.getCustomVideoView().setPaused(true);
//        }
//
//        public void muteVideo() {
//            this.aah_vi.getCustomVideoView().muteVideo();
//        }
//
//        public void unmuteVideo() {
//            this.aah_vi.getCustomVideoView().unmuteVideo();
//        }
//
//        public AAH_VideoImage getAah_vi() {
//            return aah_vi;
//        }
//
//        public ImageView getAAH_ImageView() {
//            return aah_vi.getImageView();
//        }
//
//        public String getImageUrl() {
//            return imageUrl + "";
//        }
//
//        public void setImageUrl(String imageUrl) {
//            this.imageUrl = imageUrl;
//            this.aah_vi.getImageView().setVisibility(View.VISIBLE);
//            this.aah_vi.getCustomVideoView().setVisibility(View.GONE);
//        }
//
//        public void setAah_vi(AAH_VideoImage aah_vi) {
//            this.aah_vi = aah_vi;
//        }
//
//        public String getVideoUrl() {
//            return videoUrl + "";
//        }
//
//        public boolean isPlaying() {
//            return this.aah_vi.getCustomVideoView().isPlaying();
//        }
//
//        public void setVideoUrl(String videoUrl) {
//            this.videoUrl = videoUrl;
//        }
//
//        public void setPaused(boolean paused) {
//            isPaused = paused;
//        }
//
//        public boolean isPaused() {
//            return isPaused;
//        }
//
//        public boolean isLooping() {
//            return isLooping;
//        }
//
//
//   }


    public interface OnItemClickListener {
        void onItemClick(Post post, String Type);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        if (posts.get(position).getMode().equals("image")) {
            return TYPE_Image;
        } else if (posts.get(position).getMode().equals("video")) {
            return TYPE_Video;
        } else {
            return TYPE_Text;
        }
    }

}
