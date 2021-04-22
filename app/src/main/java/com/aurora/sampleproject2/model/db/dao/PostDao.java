package com.aurora.sampleproject2.model.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.aurora.sampleproject2.model.db.entity.Post;

import java.util.List;

@Dao
public interface PostDao {

    @Insert
    public long insert(Post post);

    @Delete
    void delete(Post post);

    @Update
    void update(Post post);

    @Query("select * from tblPost order by id Desc")
    LiveData<List<Post>> getAllPost();
}
