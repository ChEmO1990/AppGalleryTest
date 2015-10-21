package com.anselmo.gallery;

import android.app.Application;

import com.anselmo.gallery.db.DataBaseHelper;

import java.io.IOException;

/**
 * Created by Anselmo on 10/21/15.
 */
public class GalleryApp extends Application {
    // Database helper
    private DataBaseHelper db_helper;

    @Override
    public void onCreate() {
        super.onCreate();

        db_helper = new DataBaseHelper(this);

        // Create bata base local
        try {
            db_helper.createDataBase(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
