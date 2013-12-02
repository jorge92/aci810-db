package com.example.aci570_db.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.aci570_db.db.MyAppContract.People;

public class MyAppDbHelper extends SQLiteOpenHelper {

	// If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "MyApp.db";
    
//  private static final String NULL_TYPE = " NULL";
	private static final String TEXT_TYPE = " TEXT";
//	private static final String INTEGER_TYPE = " INTEGER";
//	private static final String REAL_TYPE = " REAL";
//	private static final String BLOB_TYPE = " BLOB";
	
	private static final String COMMA_SEPARATOR = ",";
	
	private static final String SQL_CREATE_PEOPLE =				//crear tabla
		    "CREATE TABLE " + People.TABLE_NAME + " (" +
		    People._ID + " INTEGER PRIMARY KEY," +
		    People.COLUMN_NAME_FIRST_NAME + TEXT_TYPE + COMMA_SEPARATOR +
		    People.COLUMN_NAME_LAST_NAME + TEXT_TYPE + COMMA_SEPARATOR +
		    People.COLUMN_NAME_EMAIL + TEXT_TYPE + COMMA_SEPARATOR +
		    People.COLUMN_NAME_USER + TEXT_TYPE +
		    " )";
	
	private static final String SQL_DROP_PEOPLE =				//eliminar tabla
		    "DROP TABLE IF EXISTS " + People.TABLE_NAME;
    
    public MyAppDbHelper(Context context) {						
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_PEOPLE);
    }
    
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {			//se ejecuta cada vez que se actualize el numero de version
    	Log.w(
    			MyAppDbHelper.class.getName(),
    			"Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data"
    	);

    	db.execSQL(SQL_DROP_PEOPLE);
    	onCreate(db);
    }
    
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
