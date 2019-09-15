package com.ayoub.el.khatab.unitedremote_mobilecodingchallenge.Utility;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Utility {

    /**
     * ========================== RepoDatabase constants ========================
     */
    public static final String DATABASE_NAME = "note_database";


    /**
     * ========================== GitHubService API constants ========================
     */
    public static final String TAG_GET = "VOLLEY_GET";
    public static final String BASE_URL = "https://api.github.com/search/repositories?q=created:>2017-10-22&sort=stars&order=desc&page=";

    public static final String RESPONSE_ERROR_403 = "Unexpected response code 403, probably blocked by the API";
    public static final String EXCEPTION_NULL_RESPONSE = "Unexpected response code 403, Too many requests to the API";

    /**
     * this strings keys will be used to extract necessary fields from the response json
     */
    public static final String JSON_KEY_REPO_ID = "id";
    public static final String JSON_KEY_REPO_NAME = "name";
    public static final String JSON_KEY_REPO_DESCRIPTION = "description";
    public static final String JSON_KEY_REPO_STARS = "stargazers_count";
    public static final String JSON_KEY_RESPONSE_ITEMS = "items";
    public static final String JSON_KEY_REPO_OWNER_JSON_OBJECT = "owner";
    public static final String JSON_KEY_REPO_OWNER_NAME = "login";
    public static final String JSON_KEY_REPO_OWNER_AVATAR = "avatar_url";

    /**
     * ========================== ViewModel constants/methods ========================
     */
    // this error will be displayed in a Toast in case no active internet is available
    public static final String INTERNET_UNAVAILABLE_ERROR = "No active internet available.";

    // this link will be used in the ping request check
    private static final String URL_TO_PING = "www.google.com";

    /**
     * this ping request will be used to ping URL_TO_PING checking whether
     * the internet is active or not
     */
    private static final String PING_REQUEST_TO_CHECK_ACTIVE_INTERNET = "ping -c 1 " + URL_TO_PING;

    // this interval will be used to delay each network request to avoid being blocked by api
    public static final int API_REQUEST_DELAY_INTERVAL = 1000;


    /**
     * this string key will allow us to get saved items per page value from shared pref
     */
    public static final String SHARED_PREFERENCES_ITEMS_PER_PAGE_KEY = "items_per_page";

    /**
     * checks the availability of internet connectivity
     *
     * @return boolean, determine whether the device has and internet connectivity
     */
    public static boolean isConnected(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

    /**
     * checks whether the device internet is active or not
     * it simply creates a process to ping to google and get the response, then
     * end the process and return whether the response is positive or negative.
     *
     * @return boolean, determine whether the internet is active to make network requests
     */
    public static boolean isOnline() {
        Process p1 = null;
        try {

            p1 = java.lang.Runtime.getRuntime().exec(PING_REQUEST_TO_CHECK_ACTIVE_INTERNET);
            int returnVal = p1.waitFor();
            boolean reachable = (returnVal == 0);
            p1.destroy();
            return reachable;

        } catch (Exception e) {
            if (p1 != null) {
                p1.destroy();
            }
            e.printStackTrace();
            return false;
        }
    }

    // get saved items per page
    public static int getItemsPerPage(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(SHARED_PREFERENCES_ITEMS_PER_PAGE_KEY, Context.MODE_PRIVATE);
        return sharedPref.getInt(SHARED_PREFERENCES_ITEMS_PER_PAGE_KEY, 10);
    }

    /**
     * ========================== Repository constants/methods ========================
     */

    public static final String ACTION_DELETE = "delete";
    public static final String ACTION_DELETE_ALL = "delete_all";
    public static final String ACTION_INSERT = "insert";


    /**
     * ========================== SettingsFragment constants/methods ========================
     */
    // save integer values into the shared preferences
    public static void saveItemsPerPage(Activity activity, String key, int value) {

        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(key, value);
        editor.apply();
    }
}
