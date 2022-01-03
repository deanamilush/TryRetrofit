package com.dean.tryretrofit.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(User user);
    @Update()
    void update(User user);
    @Delete()
    void delete(User user);
    @Query("SELECT * from user ORDER BY id_user ASC")
    LiveData<List<User>> getAllNotes();
}