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
    private static final String TABLE_YOU = "YOU";
    private static final String TABLE_STUFF = "STUFF";
    private static final String TABLE_PROFILE = "PROFILE";
    private static final String TABLE_APPWIDGET = "APPWIDGET";

    private static final String KEY_YOU_ID = "id";
    private static final String KEY_PAGE = "page";
    private static final String KEY_FELD = "feld";
    private static final String KEY_WIDTH = "width";
    private static final String KEY_HEIGHT = "height";
    private static final String KEY_ITEM = "item";
    private static final String KEY_OPTION = "option";
    private static final String KEY_NOTE = "note";
    
    private static final String KEY_STUFF_ID = "id";
    private static final String KEY_CURRENT_PAGE = "current_page";
    private static final String KEY_PAGES = "pages";
    private static final String KEY_WALLPAPER = "wallpaper";
    private static final String KEY_BGALPHA = "bgalpha";
    private static final String KEY_ITALPHA = "italpha";
    private static final String KEY_ICSIZE = "icsize";
    private static final String KEY_STARTPG = "startpg";
    private static final String KEY_DO = "do";
    
    private static final String KEY_PROFILE_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_STYLE = "style";
    private static final String KEY_INHALT = "inhalt";
    
    private static final String KEY_APPWIDGET_ID = "id";
    private static final String KEY_APPWIDGET_AWID = "awid";
    private static final String KEY_APPWIDGET_ITEM = "item";
    private static final String KEY_APPWIDGET_OPTION = "option";
 
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_YOU_TABLE = "CREATE TABLE " + TABLE_YOU + "("
                + KEY_YOU_ID + " INTEGER PRIMARY KEY,"
        		+ KEY_PAGE + " INTEGER,"
        		+ KEY_FELD + " INTEGER,"
        		+ KEY_WIDTH + " INTEGER,"
        		+ KEY_HEIGHT + " INTEGER,"
        		+ KEY_ITEM + " INTEGER,"
        		+ KEY_OPTION + " TEXT,"
                + KEY_NOTE + " TEXT" + ")";
        db.execSQL(CREATE_YOU_TABLE);
        String CREATE_STUFF_TABLE = "CREATE TABLE " + TABLE_STUFF + "("
                + KEY_STUFF_ID + " INTEGER PRIMARY KEY,"
        		+ KEY_CURRENT_PAGE + " INTEGER,"
        		+ KEY_PAGES + " INTEGER,"
        		+ KEY_WALLPAPER + " TEXT,"
        		+ KEY_BGALPHA + " INTEGER,"
        		+ KEY_ITALPHA + " INTEGER,"
        		+ KEY_ICSIZE + " INTEGER,"
        		+ KEY_STARTPG + " INTEGER,"
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
        		+ KEY_APPWIDGET_ITEM + " INTEGER,"
        		+ KEY_APPWIDGET_OPTION + " TEXT" + ")";
        db.execSQL(CREATE_APPWIDGET_TABLE);
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_YOU);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUFF);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROFILE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_APPWIDGET);
        onCreate(db);
    }
 
    public void addEntry(YouEntry Entry) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_PAGE, Entry.get_page());
        values.put(KEY_FELD, Entry.get_feld());
        values.put(KEY_WIDTH, Entry.get_width());
        values.put(KEY_HEIGHT, Entry.get_height());
        values.put(KEY_ITEM, Entry.get_item());
        values.put(KEY_OPTION, Entry.get_option());
        values.put(KEY_NOTE, Entry.get_note());
 
        db.insert(TABLE_YOU, null, values);
        db.close();
    }
    
    public void addEntry(StuffEntry Entry) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_CURRENT_PAGE, Entry.get_current_page());
        values.put(KEY_PAGES, Entry.get_pages());
        values.put(KEY_WALLPAPER, Entry.get_wallpaper());
        values.put(KEY_BGALPHA, Entry.get_bgalpha());
        values.put(KEY_ITALPHA, Entry.get_italpha());
        values.put(KEY_ICSIZE, Entry.get_icsize());
        values.put(KEY_STARTPG, Entry.get_startpg());
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
        values.put(KEY_APPWIDGET_ITEM, Entry.get_item());
        values.put(KEY_APPWIDGET_OPTION, Entry.get_option());
 
        db.insert(TABLE_APPWIDGET, null, values);
        db.close();
    }
    
    public YouEntry getEntry(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
 
        Cursor cursor = db.query(TABLE_YOU, new String[] {
        		KEY_YOU_ID, KEY_PAGE, KEY_FELD, KEY_WIDTH, KEY_HEIGHT, KEY_ITEM, KEY_OPTION, KEY_NOTE }, KEY_YOU_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        
        if (cursor != null)
            cursor.moveToFirst();
 
        YouEntry Entry = new YouEntry(
        		Integer.parseInt(cursor.getString(0)),
        		Integer.parseInt(cursor.getString(1)),
        		Integer.parseInt(cursor.getString(2)),
        		Integer.parseInt(cursor.getString(3)),
        		Integer.parseInt(cursor.getString(4)),
        		Integer.parseInt(cursor.getString(5)),
        		cursor.getString(6),
        		cursor.getString(7));
        cursor.close();
        return Entry;
    }
    
    public StuffEntry getStuffEntry(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
 
        Cursor cursor = db.query(TABLE_STUFF, new String[] {
        		KEY_STUFF_ID, KEY_CURRENT_PAGE, KEY_PAGES, KEY_WALLPAPER, KEY_BGALPHA, KEY_ITALPHA, KEY_ICSIZE, KEY_STARTPG, KEY_DO}, KEY_STUFF_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        
        if (cursor != null)
            cursor.moveToFirst();
 
        StuffEntry Entry = new StuffEntry(
        		Integer.parseInt(cursor.getString(0)),
        		Integer.parseInt(cursor.getString(1)),
        		Integer.parseInt(cursor.getString(2)),
        		cursor.getString(3),
        		Integer.parseInt(cursor.getString(4)),
        		Integer.parseInt(cursor.getString(5)),
        		Integer.parseInt(cursor.getString(6)),
        		Integer.parseInt(cursor.getString(7)),
        		cursor.getString(8));
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
        		KEY_APPWIDGET_ID, KEY_APPWIDGET_AWID, KEY_APPWIDGET_ITEM, KEY_APPWIDGET_OPTION }, KEY_APPWIDGET_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        
        if (cursor != null)
            cursor.moveToFirst();
 
        AppwidgetEntry Entry = new AppwidgetEntry(
        		Integer.parseInt(cursor.getString(0)),
        		Integer.parseInt(cursor.getString(1)),
        		Integer.parseInt(cursor.getString(2)),
        		cursor.getString(3));
        cursor.close();
        return Entry;
    }
    
    public List<YouEntry> getAllEntrys() {
        List<YouEntry> EntryList = new ArrayList<YouEntry>();
        String selectQuery = "SELECT  * FROM " + TABLE_YOU;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        if (cursor.moveToFirst()) {
            do {
                YouEntry Entry = new YouEntry();
                Entry.setID(Integer.parseInt(cursor.getString(0)));
                Entry.set_page(Integer.parseInt(cursor.getString(1)));
                Entry.set_feld(Integer.parseInt(cursor.getString(2)));
                Entry.set_width(Integer.parseInt(cursor.getString(3)));
                Entry.set_height(Integer.parseInt(cursor.getString(4)));
                Entry.set_item(Integer.parseInt(cursor.getString(5)));
                Entry.set_option(cursor.getString(6));
                Entry.set_note(cursor.getString(7));
                EntryList.add(Entry);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return EntryList;
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
                Entry.set_current_page(Integer.parseInt(cursor.getString(1)));
                Entry.set_pages(Integer.parseInt(cursor.getString(2)));
                Entry.set_wallpaper(cursor.getString(3));
                Entry.set_bgalpha(Integer.parseInt(cursor.getString(4)));
                Entry.set_italpha(Integer.parseInt(cursor.getString(5)));
                Entry.set_icsize(Integer.parseInt(cursor.getString(6)));
                Entry.set_startpg(Integer.parseInt(cursor.getString(7)));
                Entry.set_do(cursor.getString(8));
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
                Entry.set_item(Integer.parseInt(cursor.getString(2)));
                Entry.set_option(cursor.getString(3));
                EntryList.add(Entry);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return EntryList;
    }
 
    public int updateEntry(YouEntry Entry) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_PAGE, Entry.get_page());
        values.put(KEY_FELD, Entry.get_feld());
        values.put(KEY_WIDTH, Entry.get_width());
        values.put(KEY_HEIGHT, Entry.get_height());
        values.put(KEY_ITEM, Entry.get_item());
        values.put(KEY_OPTION, Entry.get_option());
        values.put(KEY_NOTE, Entry.get_note());
 
        return db.update(TABLE_YOU, values, KEY_YOU_ID + " = ?", new String[] {String.valueOf(Entry.getID())});
    }
    
    public int updateEntry(StuffEntry Entry) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_CURRENT_PAGE, Entry.get_current_page());
        values.put(KEY_PAGES, Entry.get_pages());
        values.put(KEY_WALLPAPER, Entry.get_wallpaper());
        values.put(KEY_BGALPHA, Entry.get_bgalpha());
        values.put(KEY_ITALPHA, Entry.get_italpha());
        values.put(KEY_ICSIZE, Entry.get_icsize());
        values.put(KEY_STARTPG, Entry.get_startpg());
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
        values.put(KEY_APPWIDGET_ITEM, Entry.get_item());
        values.put(KEY_APPWIDGET_OPTION, Entry.get_option());
 
        return db.update(TABLE_APPWIDGET, values, KEY_APPWIDGET_ID + " = ?", new String[] {String.valueOf(Entry.getID())});
    }
    
    public void deleteEntry(YouEntry Entry) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_YOU, KEY_YOU_ID + " = ?",
                new String[] { String.valueOf(Entry.getID()) });
        db.close();
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