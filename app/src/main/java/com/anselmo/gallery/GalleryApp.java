package com.anselmo.gallery;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.anselmo.gallery.db.DataBaseHelper;

import java.io.IOException;

/**
 * Created by Anselmo on 10/21/15.
 */
public class GalleryApp extends Application {
    public static final String TAG = GalleryApp.class.getSimpleName();
    private static GalleryApp sInstance;
    private RequestQueue mRequestQueue;

    // Database helper
    private DataBaseHelper db_helper;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;

        db_helper = new DataBaseHelper(this);

        // Create bata base local
        try {
            db_helper.createDataBase(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized GalleryApp getInstance() {
        return sInstance;
    }

    public void cancelAllRequestVolley() {
        getRequestQueue().cancelAll(getInstance());
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}
