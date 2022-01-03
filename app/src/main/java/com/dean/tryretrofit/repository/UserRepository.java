package com.dean.tryretrofit.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.dean.tryretrofit.database.User;
import com.dean.tryretrofit.database.UserDao;
import com.dean.tryretrofit.database.UserRoomDatabase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserRepository {

    private final UserDao userDao;
    private final ExecutorService executorService;

    public UserRepository(Application application) {
        executorService = Executors.newSingleThreadExecutor();
        UserRoomDatabase db = UserRoomDatabase.getDatabase(application);
        userDao = db.noteDao();
    }

    public LiveData<List<User>> getAllNotes() {
        return userDao.getAllNotes();
    }

    public void insert(final User user) {
        executorService.execute(() -> userDao.insert(user));
    }

    public void delete(final User user){
        executorService.execute(() -> userDao.delete(user));
    }

    public void update(final User user){
        executorService.execute(() -> userDao.update(user));
    }
}
