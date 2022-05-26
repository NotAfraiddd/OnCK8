package com.example.lab08.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import entity.UserHappy;

@Dao
public interface UserHappyDao {

    @Insert
    void insert(UserHappy user);

    @Query("select * from user_happy where email = :email ")
    UserHappy getUserByEmail(String email);

    @Update
    void update(UserHappy userHappy);
}
