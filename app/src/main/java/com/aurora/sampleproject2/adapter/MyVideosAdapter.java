package com.aurora.sampleproject2.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.allattentionhere.autoplayvideos.AAH_CustomViewHolder;
import com.allattentionhere.autoplayvideos.AAH_VideosAdapter;
import com.aurora.sampleproject2.R;
import com.aurora.sampleproject2.model.MyModel;
import com.squareup.picasso.Picasso;

import java.util.List;


public class MyVideosAdapter extends AAH_VideosAdapter1 {

    private List<MyModel> list;
    Picasso picasso;

    public class MyViewHolder extends AAH_CustomViewHolder1 {
        final TextView tv;
	    final ImageView img_vol,img_playback;
        boolean isMuted; //to mute/un-mute video (optional)
	
        public MyViewHolder(View x) {
            super(x);
            tv =(TextView) x.findViewById(R.id.tv);
	    img_vol =(ImageView)x.findViewById( R.id.img_vol);
	    img_playback =(ImageView) x.findViewById( R.id.img_playback);
        }
    }

    public MyVideosAdapter(List<MyModel> list_urls, Picasso p) {
        this.list = list_urls;
        this.picasso = p;
    }

    @Override
    public AAH_CustomViewHolder1 onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_card, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AAH_CustomViewHolder1 holder, int position) {
        ((MyViewHolder) holder).tv.setText(list.get(position).getName());

        //todo
       // holder.setImageUrl(list.get(position).getImage_url());
        holder.setVideoUrl(list.get(position).getVideo_url());

        holder.getAah_vi().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((MyViewHolder) holder).isMuted) {
                    holder.unmuteVideo();
                    ((MyViewHolder) holder).img_vol.setImageResource(R.drawable.ic_unmute);
                } else {
                    holder.muteVideo();
                    ((MyViewHolder) holder).img_vol.setImageResource(R.drawable.ic_mute);
                }
                ((MyViewHolder) holder).isMuted = !((MyViewHolder) holder).isMuted;
            }
        });



    }
    
    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }
}