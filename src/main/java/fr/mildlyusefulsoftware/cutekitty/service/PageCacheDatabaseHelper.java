package fr.mildlyusefulsoftware.cutekitty.service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class PageCacheDatabaseHelper extends SQLiteOpenHelper {

	public static final String TABLE_PAGES= "PAGES";
	public static final String COL_ID = "ID";
	public static final String COL_PAGE_NUMBER = "PAGE_NUMBER";
	public static final String COL_IMAGE = "IMAGE";
 
	private static final String CREATE_BDD = "CREATE TABLE " + TABLE_PAGES + " ("
	+ COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_PAGE_NUMBER + " INTEGER NOT NULL, "
	+ COL_IMAGE + " BLOB NOT NULL);";
 
	public PageCacheDatabaseHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
	}
 
	@Override
	public void onCreate(SQLiteDatabase db) {
		//on créé la table à partir de la requête écrite dans la variable CREATE_BDD
		db.execSQL(CREATE_BDD);
	}
 
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//On peut fait ce qu'on veut ici moi j'ai décidé de supprimer la table et de la recréer
		//comme ça lorsque je change la version les id repartent de 0
		db.execSQL("DROP TABLE " + TABLE_PAGES + ";");
		onCreate(db);
	}

}
