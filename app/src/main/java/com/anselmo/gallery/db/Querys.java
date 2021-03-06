package com.anselmo.gallery.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.anselmo.gallery.models.ImageGallery;
import com.anselmo.gallery.utils.Constants;

import java.util.ArrayList;


/**
 * Provides functions CRUD Database
 * 
 * @author Anselmo
 */
public class Querys {
	private static SQLiteDatabase dataBaseHelper = SQLiteDatabase.openDatabase(Constants.DB_PATH_DATABASE + Constants.DB_NAME_DATABASE, null, SQLiteDatabase.OPEN_READWRITE);
	
	/**
	 * Add Image
	 */
	public static void addImage( ImageGallery image ) {
		ContentValues newRegister = new ContentValues();
		newRegister.put("path_image", image.getPath());
		newRegister.put("title_image", image.getTitle());
		newRegister.put("liked_count", image.getLiked_count());
		newRegister.put("unliked_count", image.getUnliked_count());

		//Add
		dataBaseHelper.insert("tbl_images", null, newRegister);
	}

	/**
	 * Get all images from database
	 *
	 * @return
	 */
	public static ArrayList<ImageGallery> getImagesFromDB() {
		//Query
		String query = "SELECT  * FROM tbl_images";
		
		//Cursor
		Cursor cursor = dataBaseHelper.rawQuery(query, null);

		ArrayList<ImageGallery> items = null;
		
		if( cursor.getCount() != 0 ) {
			items = new ArrayList<ImageGallery>(cursor.getCount()); //Create array
			while( cursor.moveToNext() ) {
				items.add( new ImageGallery( cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getInt(4) ) );
			}
		}
        cursor.close();
		return items;
	}
	
	public static void clearTable() {
		String query = "DELETE FROM tbl_images";
		dataBaseHelper.execSQL(query);
	}

	public static void updateLike( ImageGallery image ) {
		int currentCount = 0;

		//Query
		String query = "SELECT liked_count FROM tbl_images where id_image =" + image.getId();

		//Cursor
		Cursor cursor = dataBaseHelper.rawQuery(query, null);

		if( cursor.getCount() != 0 ) {
			while( cursor.moveToNext() ) {
				currentCount = cursor.getInt(0);
			}
		}

		cursor.close();

		int total = currentCount += 1;

		ContentValues update = new ContentValues();
		update.put("liked_count", total);

		//Add
		dataBaseHelper.update("tbl_images", update, "id_image = " + image.getId(), null);
	}

	public static void updateUnLike( ImageGallery image ) {
		int currentCount = 0;

		//Query
		String query = "SELECT unliked_count FROM tbl_images where id_image =" + image.getId();

		//Cursor
		Cursor cursor = dataBaseHelper.rawQuery(query, null);

		if( cursor.getCount() != 0 ) {
			while( cursor.moveToNext() ) {
				currentCount = cursor.getInt(0);
			}
		}

		cursor.close();

		int total = currentCount += 1;

		ContentValues update = new ContentValues();
		update.put("unliked_count", total);

		//Add
		dataBaseHelper.update("tbl_images", update, "id_image = " + image.getId(), null);
	}


}