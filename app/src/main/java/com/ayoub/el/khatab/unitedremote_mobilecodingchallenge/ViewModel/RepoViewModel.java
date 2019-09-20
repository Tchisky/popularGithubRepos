package com.ayoub.el.khatab.unitedremote_mobilecodingchallenge.ViewModel;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.ayoub.el.khatab.unitedremote_mobilecodingchallenge.Model.Repo;
import com.ayoub.el.khatab.unitedremote_mobilecodingchallenge.Repository.Repository;
import com.ayoub.el.khatab.unitedremote_mobilecodingchallenge.Utility.Utility;

import static com.ayoub.el.khatab.unitedremote_mobilecodingchallenge.Utility.Utility.API_REQUEST_DELAY_INTERVAL;
import static com.ayoub.el.khatab.unitedremote_mobilecodingchallenge.Utility.Utility.INTERNET_UNAVAILABLE_ERROR;
import static com.ayoub.el.khatab.unitedremote_mobilecodingchallenge.Utility.Utility.SHARED_PREFERENCES_CURRENT_PAGE;
import static com.ayoub.el.khatab.unitedremote_mobilecodingchallenge.Utility.Utility.getCurrentPage;
import static com.ayoub.el.khatab.unitedremote_mobilecodingchallenge.Utility.Utility.getItemsPerPage;
import static com.ayoub.el.khatab.unitedremote_mobilecodingchallenge.Utility.Utility.isConnected;
import static com.ayoub.el.khatab.unitedremote_mobilecodingchallenge.Utility.Utility.isOnline;

public class RepoViewModel extends AndroidViewModel {

    private Repository repository;
    private Context context;
    private int currentPage;

    public RepoViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
        repository = new Repository(application);
    }

    /**
     * retrieve repos data from the room database, in case there is no data in the room
     * database, it makes request to API to get data and cache it in the room database
     * <p>
     * it loads 10 items per page from the room database, and 30 items per page from API
     * in case the database is empty
     *
     * @return liveData<PagedList < Repo>> to be submitted to the PagedListAdapter
     */
    public LiveData<PagedList<Repo>> getAllReposPaged() {

        int itemsPerPage = getItemsPerPage(
                getApplication().getApplicationContext(),
                Utility.SHARED_PREFERENCES_ITEMS_PER_PAGE_KEY);

        return new LivePagedListBuilder<>(
                repository.getReposPaged(), itemsPerPage)
                .setBoundaryCallback(new PagedList.BoundaryCallback<Repo>() {
                    @Override
                    public void onZeroItemsLoaded() {
                        super.onZeroItemsLoaded();
                        retrieveReposFromAPI(repository);
                    }

                    @Override
                    public void onItemAtFrontLoaded(@NonNull Repo itemAtFront) {
                        super.onItemAtFrontLoaded(itemAtFront);
                    }

                    @Override
                    public void onItemAtEndLoaded(@NonNull Repo itemAtEnd) {
                        super.onItemAtEndLoaded(itemAtEnd);
                        retrieveReposFromAPI(repository);
                    }
                })
                .build();
    }


    /**
     * delete the given repo from database
     *
     * @param repo
     */
    public void delete(Repo repo) {
        repository.delete(repo);
    }

    /**
     * makes network request to the API to get repos data
     *
     * @param repository to perform the network request to the API(using volley)
     */
    private void retrieveReposFromAPI(Repository repository) {

        // check for active internet availability
        if (isConnected(context) && isOnline()) {

            currentPage = getCurrentPage(
                    getApplication(),
                    SHARED_PREFERENCES_CURRENT_PAGE);

            // then make network request to the api
            // delay the request (milliseconds) to avoid being blocked by the api
            new Handler().postDelayed(() -> {

                repository.getRemoteRepos(currentPage);
                currentPage++;
                Utility.saveValueInSharedPreferences(
                        getApplication(),
                        SHARED_PREFERENCES_CURRENT_PAGE,
                        currentPage);

            }, API_REQUEST_DELAY_INTERVAL);

        } else
            Toast.makeText(context, INTERNET_UNAVAILABLE_ERROR, Toast.LENGTH_LONG).show();
    }

    public void deleteAllRepos() {

        repository.deleteAllRepos();

    }


}
