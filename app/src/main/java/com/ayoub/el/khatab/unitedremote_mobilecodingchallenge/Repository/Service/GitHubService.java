package com.ayoub.el.khatab.unitedremote_mobilecodingchallenge.Repository.Service;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ayoub.el.khatab.unitedremote_mobilecodingchallenge.Model.Repo;
import com.ayoub.el.khatab.unitedremote_mobilecodingchallenge.Model.RepoBuilder;
import com.ayoub.el.khatab.unitedremote_mobilecodingchallenge.Repository.Repository;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.ayoub.el.khatab.unitedremote_mobilecodingchallenge.Utility.Utility.BASE_URL;
import static com.ayoub.el.khatab.unitedremote_mobilecodingchallenge.Utility.Utility.EXCEPTION_NULL_RESPONSE;
import static com.ayoub.el.khatab.unitedremote_mobilecodingchallenge.Utility.Utility.JSON_KEY_REPO_DESCRIPTION;
import static com.ayoub.el.khatab.unitedremote_mobilecodingchallenge.Utility.Utility.JSON_KEY_REPO_ID;
import static com.ayoub.el.khatab.unitedremote_mobilecodingchallenge.Utility.Utility.JSON_KEY_REPO_NAME;
import static com.ayoub.el.khatab.unitedremote_mobilecodingchallenge.Utility.Utility.JSON_KEY_REPO_OWNER_AVATAR;
import static com.ayoub.el.khatab.unitedremote_mobilecodingchallenge.Utility.Utility.JSON_KEY_REPO_OWNER_JSON_OBJECT;
import static com.ayoub.el.khatab.unitedremote_mobilecodingchallenge.Utility.Utility.JSON_KEY_REPO_OWNER_NAME;
import static com.ayoub.el.khatab.unitedremote_mobilecodingchallenge.Utility.Utility.JSON_KEY_REPO_STARS;
import static com.ayoub.el.khatab.unitedremote_mobilecodingchallenge.Utility.Utility.JSON_KEY_RESPONSE_ITEMS;
import static com.ayoub.el.khatab.unitedremote_mobilecodingchallenge.Utility.Utility.RESPONSE_ERROR_403;
import static com.ayoub.el.khatab.unitedremote_mobilecodingchallenge.Utility.Utility.TAG_GET;

public class GitHubService {

    private static final String TAG = "GitHubService";

    private Repository repository;

    private RequestQueue requestQueue;

    public GitHubService(Context context) {

        // init volley request queue
        requestQueue = Volley.newRequestQueue(context);
    }

    /**
     * fetch data from online api then insert it into the room database
     *
     * @param repository
     * @param pageIndex
     */
    public void getReposList(Repository repository, final int pageIndex) {
        this.repository = repository;


        // prepare the Request
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                BASE_URL + pageIndex,
                onResponseListener,
                onErrorResponse
        );

        stringRequest.setTag(TAG_GET);

        // add it to the RequestQueue
        requestQueue.add(stringRequest);
    }


    /**
     * volley callback
     */
    private Response.Listener<String> onResponseListener =
            response -> {
                Log.d(TAG, "onResponse: " + response);
                reformatResponse(response, repository);
            };

    private Response.ErrorListener onErrorResponse = error -> {

        // Handel Errors
        if (error == null || error.getMessage() == null) {

            Log.d(TAG, RESPONSE_ERROR_403);
            throw new NullPointerException(EXCEPTION_NULL_RESPONSE);

        } else {

            if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                Log.d(TAG, error.getMessage());
            } else if (error instanceof AuthFailureError) {
                Log.d(TAG, error.getMessage());
            } else if (error instanceof ServerError) {
                Log.d(TAG, error.getMessage());
            } else if (error instanceof NetworkError) {
                Log.d(TAG, error.getMessage());
            } else if (error instanceof ParseError) {
                Log.d(TAG, error.getMessage());
            }

        }

    };


    /**
     * reformat a json string and extract needed fields to return in a arrayList of Repo
     *
     * @param response json object in a string format
     */
    private void reformatResponse(String response, Repository repository) {

        try {
            JSONObject jsonResponse = new JSONObject(response);
            JSONArray items = jsonResponse.getJSONArray(JSON_KEY_RESPONSE_ITEMS);

            // stop if there is not items in the response
            if (items.length() <= 0) {
                return;
            }

            int index = 0, length = items.length();

            JSONObject repoItem, owner;
            Repo currentRepo;

            while (index < length) {

                repoItem = items.getJSONObject(index);
                owner = repoItem.getJSONObject(JSON_KEY_REPO_OWNER_JSON_OBJECT);

                currentRepo = new RepoBuilder()
                        .setId(
                                repoItem.getInt(JSON_KEY_REPO_ID)
                        )
                        .setName(
                                repoItem.getString(JSON_KEY_REPO_NAME)
                        )
                        .setDescription(
                                repoItem.getString(JSON_KEY_REPO_DESCRIPTION)
                        )
                        .setStars(
                                repoItem.getInt(JSON_KEY_REPO_STARS)
                        )
                        .setOwnerName(
                                owner.getString(JSON_KEY_REPO_OWNER_NAME)
                        )
                        .setOwnerAvatar(
                                owner.getString(JSON_KEY_REPO_OWNER_AVATAR)
                        )
                        .buildRepo();

                // caching the retrieved data into room database
                repository.insert(currentRepo);

                index++;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
