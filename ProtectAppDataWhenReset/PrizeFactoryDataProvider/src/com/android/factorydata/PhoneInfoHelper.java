package com.android.factorydata;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class PhoneInfoHelper extends SDSQLiteOpenHelper 
{   
	private final static String DATABASE_NAME = "prize_factory_data"; 
	private final static int DATABASE_VERSION = 1; 
	private final static String TABLE_NAME = "prize_phone";
	public final static String TIMER_NB = "prize_nb"; 
	public final static String TASK_ATTRIBUTE = "prize_info";
	public final static String TASK_CONTENT = "prize_data"; 

	public PhoneInfoHelper (Context context) { 
		// TODO Auto-generated constructor stub 
		super(context, DATABASE_NAME, null, DATABASE_VERSION); 
		} 
	
	@Override 
	public void onCreate(SQLiteDatabase db) { 
	String sql = "CREATE TABLE " + TABLE_NAME + " (" + TIMER_NB 
	  + " INTEGER primary key autoincrement, " + TASK_ATTRIBUTE + " text, "+ TASK_CONTENT +" text);"; 
	db.execSQL(sql); 
	initDatabase(db);
	} 
	public void initDatabase(SQLiteDatabase db){
		ContentValues cv = new ContentValues(); 
		cv.put(TASK_ATTRIBUTE,"imeiNo1"); 
		cv.put(TASK_CONTENT, ""); 
		db.insert(TABLE_NAME, null, cv);
		cv.put(TASK_ATTRIBUTE,"imeiNo2"); 
		cv.put(TASK_CONTENT, "");
		db.insert(TABLE_NAME, null, cv);
		cv.put(TASK_ATTRIBUTE,"snNo"); 
		cv.put(TASK_CONTENT, "N00");
		db.insert(TABLE_NAME, null, cv);
		cv.put(TASK_ATTRIBUTE,"pcbaResult"); 
		cv.put(TASK_CONTENT, "0");
		db.insert(TABLE_NAME, null, cv);
		cv.put(TASK_ATTRIBUTE,"mobileResult"); 
		cv.put(TASK_CONTENT, "0");
		db.insert(TABLE_NAME, null, cv);
	}
	@Override 
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { 
	String sql = "DROP TABLE IF EXISTS " + TABLE_NAME; 
	db.execSQL(sql); 
	onCreate(db); 
	} 
	public Cursor select() { 
	SQLiteDatabase db = this.getReadableDatabase(); 
	Cursor cursor = db .query(TABLE_NAME, null, null, null, null, null, null); 
	return cursor; 
	} 
	
	public Cursor queryItem(String selectiion,String[] selectionArgs) { 
		SQLiteDatabase db = this.getReadableDatabase(); 
		Cursor cursor = db .query(TABLE_NAME, null,selectiion,selectionArgs, null, null, null); 				
		//Cursor cursor = db.rawQuery("select from tasks_table where task_nb <?",whereValue);
		return cursor; 
	}
	
	public long insert(String task_attribute , String task_content) 
	{ 
	SQLiteDatabase db = this.getWritableDatabase(); 
	/* ContentValues */ 
	ContentValues cv = new ContentValues(); 
	cv.put(TASK_ATTRIBUTE, task_attribute); 
	cv.put(TASK_CONTENT, task_content); 
	long row = db.insert(TABLE_NAME, null, cv);
	return row; 
	} 
	public void delete(int id) 
	{ 
	SQLiteDatabase db = this.getWritableDatabase(); 
	String where = TIMER_NB + " = ?"; 
	String[] whereValue ={ Integer.toString(id) }; 
	db.delete(TABLE_NAME, where, whereValue); 
	} 
	public int update(ContentValues values,String selection,String[] selectionArgs) 
	{ 
	SQLiteDatabase db = this.getWritableDatabase(); 
	int tureorfalse=db.update(TABLE_NAME, values, selection, selectionArgs); 
	return tureorfalse;
	}
	
	public void clearFeedTable(){
		  String sql = "DELETE FROM " + TABLE_NAME +";";
		        SQLiteDatabase db =  this.getWritableDatabase();
		  db.execSQL(sql);
		  revertSeq();		  
		 }

    private void revertSeq() {
		  String sql = "update sqlite_sequence set seq=0 where name='"+TABLE_NAME+"'";
		  SQLiteDatabase db =  this.getWritableDatabase();
		  db.execSQL(sql);
		  
		 }
}
