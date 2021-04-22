package com.aurora.sampleproject2.adapter;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import com.aurora.sampleproject2.model.db.entity.Post;

import java.util.ArrayList;

public class PostsDiffCallback extends DiffUtil.Callback {

    ArrayList<Post>oldPostList;

    public PostsDiffCallback(ArrayList<Post> oldPostList, ArrayList<Post> newPostList) {
        this.oldPostList = oldPostList;
        this.newPostList = newPostList;
    }

    ArrayList<Post>newPostList;

    @Override
    public int getOldListSize() {
        return oldPostList==null?0:oldPostList.size();
    }

    @Override
    public int getNewListSize() {
        return newPostList==null?0: newPostList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldPostList.get(oldItemPosition).getId()==newPostList.get(newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldPostList.get(oldItemPosition).equals(newPostList.get(newItemPosition));
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
