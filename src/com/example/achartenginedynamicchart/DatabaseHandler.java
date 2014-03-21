package com.example.achartenginedynamicchart;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import org.apache.commons.io.FileUtils;
public class DatabaseHandler extends SQLiteOpenHelper {

	
	//Database Version
	private static final int DATABASE_VERSION = 1;
	
	//Database Name
	private static final String DATABASE_NAME = "ECGDB";
	
	//ecg table name
	private static final String ECG_ARCHIVE = "Ecg_Archive";
	
	//columns names
	private static final String ARCHIVE_ID = "file_id";
	private static final String ARCHIVE_NAME = "file_name";
	private static final String ARCHIVE_PATH = "file_path";
	
	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		
	}
	
	//Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_ECG_ARCHIVE_TABLE = "CREATE TABLE " + ECG_ARCHIVE + "("	
			+ ARCHIVE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," + ARCHIVE_NAME + " TEXT," 
			+ ARCHIVE_PATH + " TEXT" + ")";
		
		db.execSQL(CREATE_ECG_ARCHIVE_TABLE);
		}
	
	//Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		//Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + ECG_ARCHIVE);
		
		//Create tables again
		onCreate(db);
	}
	
	/**
     * All CRUD(Create, Read, Update, Delete) Operations
     */
 
    // Adding new contact
    void addECGFiles(String name, String filePath) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(ARCHIVE_NAME, name); // filename
        values.put(ARCHIVE_PATH, filePath); // filepath
 
        
        // Inserting Row
       db.insert(ECG_ARCHIVE, null, values);
       db.close(); // Closing database connection
    }
    
    
    boolean checkECGFilesExist(String filePath) {
    	
    	String selectQuery =" SELECT  * FROM ecg_archive WHERE " + ARCHIVE_PATH + "='" + filePath + "'";
    	 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        
        int counter =0;
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                counter++;
            } while (cursor.moveToNext());
        }
    	
    	if (counter >0 ){
    		return true;
    	} else {
    		
    		return false;
    	}
    
    }
	
	
}
