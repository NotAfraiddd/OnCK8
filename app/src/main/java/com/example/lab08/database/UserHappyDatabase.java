package com.example.lab08.database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.lab08.dao.UserHappyDao;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import entity.UserHappy;

@Database(entities = {UserHappy.class}, version = 1, exportSchema = false)
public abstract class UserHappyDatabase extends RoomDatabase {
    private static final String DB_NAME = "UserHappyDB";
    public static ExecutorService service = Executors.newFixedThreadPool(4);

    private static UserHappyDatabase userHappyDatabase;

    public abstract UserHappyDao userHappyDao();

    public static UserHappyDatabase getInstance(Context context){
        if(userHappyDatabase == null)
            userHappyDatabase = Room.databaseBuilder(context,UserHappyDatabase.class,DB_NAME)
                    .allowMainThreadQueries().build();
        return userHappyDatabase;
    }
}
