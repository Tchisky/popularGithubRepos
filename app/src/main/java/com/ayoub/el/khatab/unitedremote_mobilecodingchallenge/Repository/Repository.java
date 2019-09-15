package com.ayoub.el.khatab.unitedremote_mobilecodingchallenge.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.Nullable;
import androidx.paging.DataSource;

import com.ayoub.el.khatab.unitedremote_mobilecodingchallenge.Model.Repo;
import com.ayoub.el.khatab.unitedremote_mobilecodingchallenge.Repository.Dao.RepoDAO;
import com.ayoub.el.khatab.unitedremote_mobilecodingchallenge.Repository.Database.RepoDatabase;
import com.ayoub.el.khatab.unitedremote_mobilecodingchallenge.Repository.Service.GitHubService;

import static com.ayoub.el.khatab.unitedremote_mobilecodingchallenge.Utility.Utility.ACTION_DELETE;
import static com.ayoub.el.khatab.unitedremote_mobilecodingchallenge.Utility.Utility.ACTION_DELETE_ALL;
import static com.ayoub.el.khatab.unitedremote_mobilecodingchallenge.Utility.Utility.ACTION_INSERT;

public class Repository {

    private GitHubService gitHubAPI;
    private RepoDAO repoDAO;

    public Repository(Application application) {
        gitHubAPI = new GitHubService(application);

        // get room database instance
        RepoDatabase db = RepoDatabase.getInstance(application.getApplicationContext());

        // get all repos from room database
        repoDAO = db.repoDAO();
    }

    /**
     * insert the given repo into database with help of asyncTask
     *
     * @param repo
     */
    public void insert(Repo repo) {
        new AsyncExecutor(repoDAO, ACTION_INSERT).execute(repo);
    }


    /**
     * delete the given repo from the database
     * @param repo
     */
    public void delete(Repo repo){
        new AsyncExecutor(repoDAO,ACTION_DELETE).execute(repo);
    }

    /**
     * clear all data from the database cache
     */
    public void deleteAllRepos() {
        new AsyncExecutor(repoDAO, ACTION_DELETE_ALL).execute();
    }

    /**
     * returns data source for the pagedListAdapter from database
     *
     * @return
     */
    public DataSource.Factory<Integer, Repo> getReposPaged() {
        return repoDAO.getAllReposPaged();
    }


    /**
     * get repos from github api endpoint
     *
     * @param page
     */
    public void getRemoteRepos(int page) {
        gitHubAPI.getReposList(this, page);
    }


    /**
     * helper class to insert/delete/delete all/get all repos from the room database
     * in the background thread
     */
    private static class AsyncExecutor extends AsyncTask<Repo, Void, Integer> {

        private RepoDAO dao;
        private String action;

        AsyncExecutor(@Nullable RepoDAO dao, String action) {
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
}
