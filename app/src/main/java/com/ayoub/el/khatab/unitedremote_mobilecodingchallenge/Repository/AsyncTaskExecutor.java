package com.ayoub.el.khatab.unitedremote_mobilecodingchallenge.Repository;

import androidx.annotation.Nullable;

import com.ayoub.el.khatab.unitedremote_mobilecodingchallenge.Model.Repo;
import com.ayoub.el.khatab.unitedremote_mobilecodingchallenge.Repository.Dao.RepoDAO;

import static com.ayoub.el.khatab.unitedremote_mobilecodingchallenge.Utility.Utility.ACTION_DELETE;
import static com.ayoub.el.khatab.unitedremote_mobilecodingchallenge.Utility.Utility.ACTION_DELETE_ALL;
import static com.ayoub.el.khatab.unitedremote_mobilecodingchallenge.Utility.Utility.ACTION_INSERT;

/**
 * helper class to insert/delete/delete all/get all repos from the room database
 * in the background thread
 */
public class AsyncTaskExecutor extends android.os.AsyncTask<Repo, Void, Integer> {

    private RepoDAO dao;
    private String action;

    AsyncTaskExecutor(@Nullable RepoDAO dao, String action) {
        this.dao = dao;
        this.action = action;
    }

    @Override
    protected Integer doInBackground(Repo... repos) {

        switch (action) {

            case ACTION_DELETE:
                dao.delete(repos[0]);
                return null;


            case ACTION_INSERT:
                dao.insert(repos[0]);
                return null;


            case ACTION_DELETE_ALL:
                dao.deleteAllRepos();
                return null;

            default:
                return null;
        }
    }
}
