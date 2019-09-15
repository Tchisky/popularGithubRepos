package com.ayoub.el.khatab.unitedremote_mobilecodingchallenge.Repository.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.ayoub.el.khatab.unitedremote_mobilecodingchallenge.Model.Repo;
import com.ayoub.el.khatab.unitedremote_mobilecodingchallenge.Repository.Dao.RepoDAO;

import static com.ayoub.el.khatab.unitedremote_mobilecodingchallenge.Utility.Utility.DATABASE_NAME;

@Database(entities = Repo.class, version = 1, exportSchema = false)
public abstract class RepoDatabase extends RoomDatabase {

    private static RepoDatabase instance;

    public abstract RepoDAO repoDAO();

    public static synchronized RepoDatabase getInstance(Context context) {

        if (instance == null) {

            instance = Room.databaseBuilder(context.getApplicationContext(),
                    RepoDatabase.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();

        }

        return instance;

    }


}
