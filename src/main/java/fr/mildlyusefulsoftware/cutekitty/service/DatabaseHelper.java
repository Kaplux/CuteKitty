package fr.mildlyusefulsoftware.cutekitty.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper {

	private static final String DB_NAME = "PICTURES.DB";
	private SQLiteDatabase db;
	private static ReentrantLock LOCK = new ReentrantLock();

	public static DatabaseHelper connect(Context context) {
		LOCK.lock();
		return new DatabaseHelper(context);
	}

	private DatabaseHelper(Context context) {
		super();
		InternalDBHelper internalDBHelper = new InternalDBHelper(context,
				DB_NAME, null, 1);
		db = internalDBHelper.getWritableDatabase();
	}

	public void release() {
		try {
			db.close();
		} finally {
			LOCK.unlock();
		}
	}

	public void putPictures(Collection<Picture> pictures) {
		for (Picture p : pictures) {
			ContentValues values = new ContentValues();
			values.put(InternalDBHelper.COL_ID, p.getId());
			values.put(InternalDBHelper.COL_IMAGE_URL, p.getImageURL());
			values.put(InternalDBHelper.COL_THUMBNAIL, p.getThumbnail());
			db.insert(InternalDBHelper.TABLE_PICTURES, null, values);
		}
	}
	
	public List<Picture> getPictures() {
		List<Picture> pictures=new ArrayList<Picture>();
		Cursor c = db.query(InternalDBHelper.TABLE_PICTURES, new String[] {
				InternalDBHelper.COL_ID,
				InternalDBHelper.COL_IMAGE_URL,
				InternalDBHelper.COL_THUMBNAIL },null,
				null, null, null, null);
		if (c.getCount() != 0) {
			c.moveToFirst();
			while (!c.isAfterLast()) {
				Picture picture = new Picture(c.getInt(0), c.getString(1),
						c.getBlob(2));
				pictures.add(picture);
				c.moveToNext();
			}
		}
		return pictures;
	}

	class InternalDBHelper extends SQLiteOpenHelper {

		public static final String TABLE_PICTURES = "PICTURES";
		public static final String COL_ID = "ID";
		public static final String COL_IMAGE_URL = "URL";
		public static final String COL_THUMBNAIL = "THUMBNAIL";

		private static final String CREATE_BDD = "CREATE TABLE "
				+ TABLE_PICTURES + " (" + COL_ID
				+ " INTEGER PRIMARY KEY, " + COL_IMAGE_URL
				+ " TEXT NOT NULL, " + COL_THUMBNAIL + " BLOB NOT NULL);";

		public InternalDBHelper(Context context, String name,
				CursorFactory factory, int version) {
			super(context, name, factory, version);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// on créé la table à partir de la requête écrite dans la variable
			// CREATE_BDD
			db.execSQL(CREATE_BDD);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// On peut fait ce qu'on veut ici moi j'ai décidé de supprimer la
			// table et de la recréer
			// comme ça lorsque je change la version les id repartent de 0
			db.execSQL("DROP TABLE " + TABLE_PICTURES + ";");
			onCreate(db);
		}
	}
}
