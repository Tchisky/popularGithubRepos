package com.ayoub.el.khatab.unitedremote_mobilecodingchallenge.Repository.Dao;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ayoub.el.khatab.unitedremote_mobilecodingchallenge.Model.Repo;

@Dao
public interface RepoDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Repo repo);

    @Delete
    void delete(Repo repo);

    @Query("DELETE FROM repository")
    void deleteAllRepos();

    @Query("SELECT * FROM repository ORDER BY stars DESC")
    DataSource.Factory<Integer, Repo> getAllReposPaged();


}
