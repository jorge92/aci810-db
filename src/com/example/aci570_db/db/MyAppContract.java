package com.example.aci570_db.db;

import android.provider.BaseColumns;

public final class MyAppContract {

	public MyAppContract() {		//coleccion de clases publicas abstractas constantes
		
		// this empty constructor prevent accidentally instantiating the contract class
	}
	
	public static abstract class People implements BaseColumns {		//genera automaticamente los 'id'
		public static final String TABLE_NAME = "people";
		public static final String COLUMN_NAME_FIRST_NAME = "first_name";
		public static final String COLUMN_NAME_LAST_NAME = "last_name";
		public static final String COLUMN_NAME_EMAIL = "email";
		public static final String COLUMN_NAME_USER = "user";
	}
}
