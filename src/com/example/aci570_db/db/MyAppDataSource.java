package com.example.aci570_db.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.aci570_db.db.MyAppContract.People;
import com.example.aci570_db.model.Person;

public class MyAppDataSource {

	private MyAppDbHelper dbHelper;
	private SQLiteDatabase db;
	
	private String[] allColumns = {		//ARREGLO
		    People._ID,
		    People.COLUMN_NAME_FIRST_NAME,
		    People.COLUMN_NAME_LAST_NAME,
		    People.COLUMN_NAME_EMAIL,
		    People.COLUMN_NAME_USER
		    };

	public MyAppDataSource(Context context) {
		this.dbHelper = new MyAppDbHelper(context);
	}
	
	public void open() throws SQLException {
		this.db = dbHelper.getWritableDatabase();		//entrega la base de dato como escritura
	}
	
	public void close() {
		dbHelper.close();
	}

	public Person createPerson(String firstName, String lastName, String email, String user) {
		ContentValues values = new ContentValues();
		values.put(People.COLUMN_NAME_FIRST_NAME, firstName);
		values.put(People.COLUMN_NAME_LAST_NAME, lastName);
		values.put(People.COLUMN_NAME_EMAIL, email);
		values.put(People.COLUMN_NAME_USER, user);
		
	    long insertId = db.insert(People.TABLE_NAME, null, values);		//Inserta el objeto en la BD
	    
	    Cursor c = db.query(											//Hago un Query
	    		People.TABLE_NAME,										//Selecciono una columna y hago un WHERE
	    		this.allColumns, 
	    		People._ID + " = " + insertId, 
	    		null,
	    		null, 
	    		null, 
	    		null,
	    		null
	    	);
	    c.moveToFirst();
	    
	    Person p = cursorToPerson(c);
	    c.close();
	    
	    return p;
	}
	
	public Person updatePerson(Person p, String firstName, String lastName, String email, String user) {		//Actualizar
		ContentValues values = new ContentValues();
		values.put(People.COLUMN_NAME_FIRST_NAME, firstName);
		values.put(People.COLUMN_NAME_LAST_NAME, lastName);
		values.put(People.COLUMN_NAME_EMAIL, email);
		values.put(People.COLUMN_NAME_USER, user);
		
	    db.update(People.TABLE_NAME, values, People._ID + " = " + p.getId(), null);
	    
	    p.setFirstName(firstName);
	    p.setLastName(lastName);
	    p.setEmail(email);
	    p.setUser(user);
	    
	    return p;
	}
	
	public List<Person> getPeople() {								//Muestra la lista las personas 
	    List<Person> people = new ArrayList<Person>();
	    
	    String sortOrder = People.COLUMN_NAME_FIRST_NAME + " DESC";
	    
	    Cursor c = db.query(
			    People.TABLE_NAME,	// The table to query
			    this.allColumns,			// The columns to return
			    null,				// The columns for the WHERE clause
			    null,				// The values for the WHERE clause
			    null,				// don't group the rows
			    null,				// don't filter by row groups
			    null,
			    sortOrder			// The sort order
		    );

	    c.moveToFirst();
	    while (!c.isAfterLast()) {
	      Person p = cursorToPerson(c);
	      people.add(p);
	      c.moveToNext();
	    }
	    
	    // make sure to close the cursor
	    c.close();
	    
	    return people;
	}
	
	public Person deletePerson(Person p) {				//Borrar una persona
	    long id = p.getId();
	    db.delete(People.TABLE_NAME, People._ID + " = " + id, null);
	    p.setId(0);
	    return p;
	}

	
	private Person cursorToPerson(Cursor cursor) {		//llama la posicion de los arreglos
		Person p = new Person();
	    p.setId(cursor.getLong(0));
	    p.setFirstName(cursor.getString(1));
	    p.setLastName(cursor.getString(2));
	    p.setEmail(cursor.getString(3));
	    p.setUser(cursor.getString(4));
	    return p;
	}
}
