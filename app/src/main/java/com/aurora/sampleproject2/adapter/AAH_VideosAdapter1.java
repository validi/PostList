package com.aurora.sampleproject2.adapter;


import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

public class AAH_VideosAdapter1 extends RecyclerView.Adapter<AAH_CustomViewHolder1> {

    public AAH_VideosAdapter1() {
    }

    @Override
    public AAH_CustomViewHolder1 onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AAH_CustomViewHolder1(new View(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(AAH_CustomViewHolder1 holder, int position) {
    }

    @Override
    public void onViewDetachedFromWindow(final AAH_CustomViewHolder1 holder) {
        if (holder != null && holder.getAah_vi()!=null) {
            holder.getAah_vi().getCustomVideoView().clearAll();
            holder.getAah_vi().getCustomVideoView().invalidate();
        }
        super.onViewDetachedFromWindow(holder);
    }

    @Override
    public void onViewRecycled(AAH_CustomViewHolder1 holder) {
        if (holder != null && holder.getAah_vi()!=null) {
            holder.getAah_vi().getCustomVideoView().clearAll();
            holder.getAah_vi().getCustomVideoView().invalidate();
        }
        super.onViewRecycled(holder);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }



}