package m.tool.stuff;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {
	 
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "MTOOL.db";
    private static final String TABLE_STUFF = "STUFF";
    private static final String TABLE_PROFILE = "PROFILE";
    private static final String TABLE_APPWIDGET = "APPWIDGET";

    private static final String KEY_STUFF_ID = "id";
    private static final String KEY_DO = "do";
    
    private static final String KEY_PROFILE_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_STYLE = "style";
    private static final String KEY_INHALT = "inhalt";
    
    private static final String KEY_APPWIDGET_ID = "id";
    private static final String KEY_APPWIDGET_AWID = "awid";
    private static final String KEY_APPWIDGET_OPTION = "option";
 
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_STUFF_TABLE = "CREATE TABLE " + TABLE_STUFF + "("
                + KEY_STUFF_ID + " INTEGER PRIMARY KEY,"
        		+ KEY_DO + " TEXT" + ")";
        db.execSQL(CREATE_STUFF_TABLE);
        String CREATE_PROFILE_TABLE = "CREATE TABLE " + TABLE_PROFILE + "("
                + KEY_PROFILE_ID + " INTEGER PRIMARY KEY,"
        		+ KEY_NAME + " TEXT,"
        		+ KEY_STYLE + " TEXT,"
        		+ KEY_INHALT + " TEXT" + ")";
        db.execSQL(CREATE_PROFILE_TABLE);
        String CREATE_APPWIDGET_TABLE = "CREATE TABLE " + TABLE_APPWIDGET + "("
                + KEY_APPWIDGET_ID + " INTEGER PRIMARY KEY,"
                + KEY_APPWIDGET_AWID + " INTEGER,"
        		+ KEY_APPWIDGET_OPTION + " TEXT" + ")";
        db.execSQL(CREATE_APPWIDGET_TABLE);
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUFF);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROFILE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_APPWIDGET);
        onCreate(db);
    }
 
    
    public void addEntry(StuffEntry Entry) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_DO, Entry.get_do());

        db.insert(TABLE_STUFF, null, values);
        db.close();
    }
    
    public void addEntry(ProfileEntry Entry) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, Entry.get_name());
        values.put(KEY_STYLE, Entry.get_style());
        values.put(KEY_INHALT, Entry.get_inhalt());

        db.insert(TABLE_PROFILE, null, values);
        db.close();
    }
    
    public void addEntry(AppwidgetEntry Entry) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_APPWIDGET_AWID, Entry.get_awid());
        values.put(KEY_APPWIDGET_OPTION, Entry.get_option());
 
        db.insert(TABLE_APPWIDGET, null, values);
        db.close();
    }
    
    public StuffEntry getStuffEntry(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
 
        Cursor cursor = db.query(TABLE_STUFF, new String[] {
        		KEY_STUFF_ID, KEY_DO}, KEY_STUFF_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        
        if (cursor != null)
            cursor.moveToFirst();
 
        StuffEntry Entry = new StuffEntry(
        		Integer.parseInt(cursor.getString(0)),
        		cursor.getString(1));
        cursor.close();
        return Entry;
    }
 
    public ProfileEntry getProfileEntry(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
 
        Cursor cursor = db.query(TABLE_PROFILE, new String[] {
        		KEY_PROFILE_ID, KEY_NAME, KEY_STYLE, KEY_INHALT}, KEY_PROFILE_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        
        if (cursor != null)
            cursor.moveToFirst();
 
        ProfileEntry Entry = new ProfileEntry(
        		Integer.parseInt(cursor.getString(0)),
        		cursor.getString(1),
        		cursor.getString(2),
        		cursor.getString(3));
        cursor.close();
        return Entry;
    }
    
    public AppwidgetEntry getAppwidgetEntry(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
 
        Cursor cursor = db.query(TABLE_APPWIDGET, new String[] {
        		KEY_APPWIDGET_ID, KEY_APPWIDGET_AWID, KEY_APPWIDGET_OPTION }, KEY_APPWIDGET_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        
        if (cursor != null)
            cursor.moveToFirst();
 
        AppwidgetEntry Entry = new AppwidgetEntry(
        		Integer.parseInt(cursor.getString(0)),
        		Integer.parseInt(cursor.getString(1)),
        		cursor.getString(2));
        cursor.close();
        return Entry;
    }
    
    
    public List<StuffEntry> getAllStuffEntrys() {
        List<StuffEntry> EntryList = new ArrayList<StuffEntry>();
        String selectQuery = "SELECT  * FROM " + TABLE_STUFF;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        if (cursor.moveToFirst()) {
            do {
                StuffEntry Entry = new StuffEntry();
                Entry.setID(Integer.parseInt(cursor.getString(0)));
                Entry.set_do(cursor.getString(1));
                EntryList.add(Entry);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return EntryList;
    }
    
    public List<ProfileEntry> getAllProfileEntrys() {
        List<ProfileEntry> EntryList = new ArrayList<ProfileEntry>();
        String selectQuery = "SELECT  * FROM " + TABLE_PROFILE;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        if (cursor.moveToFirst()) {
            do {
                ProfileEntry Entry = new ProfileEntry();
                Entry.setID(Integer.parseInt(cursor.getString(0)));
                Entry.set_name(cursor.getString(1));
                Entry.set_style(cursor.getString(2));
                Entry.set_inhalt(cursor.getString(3));
                EntryList.add(Entry);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return EntryList;
    }
    
    public List<AppwidgetEntry> getAllAppwidgetEntrys() {
        List<AppwidgetEntry> EntryList = new ArrayList<AppwidgetEntry>();
        String selectQuery = "SELECT  * FROM " + TABLE_APPWIDGET;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        if (cursor.moveToFirst()) {
            do {
            	AppwidgetEntry Entry = new AppwidgetEntry();
                Entry.setID(Integer.parseInt(cursor.getString(0)));
                Entry.set_awid(Integer.parseInt(cursor.getString(1)));
                Entry.set_option(cursor.getString(2));
                EntryList.add(Entry);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return EntryList;
    }
 
    
    public int updateEntry(StuffEntry Entry) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_DO, Entry.get_do());
 
        return db.update(TABLE_STUFF, values, KEY_STUFF_ID + " = ?", new String[] {String.valueOf(Entry.getID())});
    }
    
    public int updateEntry(ProfileEntry Entry) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, Entry.get_name());
        values.put(KEY_STYLE, Entry.get_style());
        values.put(KEY_INHALT, Entry.get_inhalt());
 
        return db.update(TABLE_PROFILE, values, KEY_PROFILE_ID + " = ?", new String[] {String.valueOf(Entry.getID())});
    }
 
    public int updateEntry(AppwidgetEntry Entry) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_APPWIDGET_AWID, Entry.get_awid());
        values.put(KEY_APPWIDGET_OPTION, Entry.get_option());
 
        return db.update(TABLE_APPWIDGET, values, KEY_APPWIDGET_ID + " = ?", new String[] {String.valueOf(Entry.getID())});
    }
    
    public void deleteEntry(StuffEntry Entry) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_STUFF, KEY_STUFF_ID + " = ?",
                new String[] { String.valueOf(Entry.getID()) });
        db.close();
    }
    
    public void deleteEntry(ProfileEntry Entry) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PROFILE, KEY_PROFILE_ID + " = ?",
                new String[] { String.valueOf(Entry.getID()) });
        db.close();
    }
    
    public void deleteEntry(AppwidgetEntry Entry) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_APPWIDGET, KEY_APPWIDGET_ID + " = ?",
                new String[] { String.valueOf(Entry.getID()) });
        db.close();
    }
 
    public void command(String cmd) {
    	SQLiteDatabase db = this.getWritableDatabase();
    	db.execSQL(cmd);
    	db.close();
    }
}