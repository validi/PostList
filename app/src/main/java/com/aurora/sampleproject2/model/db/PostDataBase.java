package com.aurora.sampleproject2.model.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.aurora.sampleproject2.model.db.dao.PostDao;
import com.aurora.sampleproject2.model.db.entity.Post;

@Database(entities = {Post.class},version = 1)
public abstract class PostDataBase  extends RoomDatabase {

    public abstract PostDao getPostDao();

    public static PostDataBase instance;
    public static synchronized PostDataBase getInstance(Context context){

        if(instance==null){
            instance= Room.databaseBuilder(context.getApplicationContext()
                    ,PostDataBase.class
                    ,"post_datebase"
            ).fallbackToDestructiveMigration()
                    //.addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

//    public static RoomDatabase.Callback roomCallback=new RoomDatabase.Callback() {
//        @Override
//        public void onCreate(@NonNull SupportSQLiteDatabase db) {
//            super.onCreate(db);
//            new IntitialDataAsyncTask(instanse).execute();
//        }
//    };


}
