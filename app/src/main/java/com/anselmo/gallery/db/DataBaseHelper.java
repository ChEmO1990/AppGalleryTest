package com.anselmo.gallery.db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.anselmo.gallery.utils.Constants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Provides functions for managing the local database.
 * 
 * @author Anselmo
 */
public class DataBaseHelper extends SQLiteOpenHelper {
	private SQLiteDatabase myDataBase;
	private final Context myContext;
	
	public DataBaseHelper(Context context) {
		super(context, Constants.DB_NAME_DATABASE, null, 1);
		this.myContext = context;
		
	}
    
    /**
     * Creates a empty database on the system and rewrites it with your own database.
     * 
     * @param
     * @throws java.io.IOException
     */
    public void createDataBase(Context context) throws IOException {
    	boolean dbExist = checkDataBase();

    	if ( dbExist ) {

    	} else {
    		//If doesnt exists this method creates an empty database into the default system path
    		this.getReadableDatabase();

    		try {
    			//Copy our database.sqlite into the new database
    			copyDataBase();
    			close();
    		} catch (IOException e) {
    			throw new Error("Error copying database");
    		}
    	}
    }

    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     *
     * @return - true if it exists, false if it doesn't
     */
    private boolean checkDataBase(){
    	File dbFile = new File(Constants.DB_PATH_DATABASE + Constants.DB_NAME_DATABASE);
    	return dbFile.exists();
    }


    /**
     * Copies your database from your local assets-folder to the just created empty database
     *
     * @throws java.io.IOException
     */
    private void copyDataBase() throws IOException {
    	//Open your local db as the input stream
    	InputStream myInput = myContext.getAssets().open(Constants.DB_NAME_DATABASE);

    	// Path to the just created empty db
    	String outFileName = Constants.DB_PATH_DATABASE  + Constants.DB_NAME_DATABASE;

    	//Open the empty db as the output stream
    	OutputStream myOutput = new FileOutputStream(outFileName);

    	//transfer bytes from the inputfile to the outputfile
    	byte[] buffer = new byte[1024];
    	int length;

    	while ((length = myInput.read(buffer))>0){
    		myOutput.write(buffer, 0, length);
    	}

    	//Close the streams
    	myOutput.flush();
    	myOutput.close();

    	myInput.close();
    }

    /**
     * Open Database
     * @throws android.database.SQLException
     */
    public void openDataBase() throws SQLException {
    	String myPath = Constants.DB_PATH_DATABASE  + Constants.DB_NAME_DATABASE ;
    	myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    }
 
    @Override
	public synchronized void close() {
    	if(myDataBase != null)
    		myDataBase.close();
    	super.close();
    }
    
    @Override
	public void onCreate(SQLiteDatabase db) {}
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

}
