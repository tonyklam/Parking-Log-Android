package com.com_4dmacau.parkinglog;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tonylam on 28/04/2016.
 */
public class DBHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "parkinglog";
    // Table Names
    private static final String TABLE_TEXT = "table_text";
    private static final String TABLE_MAP = "table_map";

    // Shops Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_TIME = "time";
    private static final String KEY_FLOOR = "floor";
    private static final String KEY_SPACE_NUM = "space_num";
    private static final String KEY_AREA = "area";
    private static final String KEY_DIR_FROM_LIFT = "direction_from_lift";

    private static final String KEY_PIN_X = "pin_x";
    private static final String KEY_PIN_Y = "pin_y";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_TEXT + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TIME + " TEXT,"
                + KEY_FLOOR + " TEXT," + KEY_SPACE_NUM + " TEXT," + KEY_AREA + " TEXT," + KEY_DIR_FROM_LIFT + " TEXT" + ")";

        db.execSQL(CREATE_TABLE);

        CREATE_TABLE = "CREATE TABLE " + TABLE_MAP + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TIME + " TEXT,"
                + KEY_FLOOR + " TEXT," + KEY_PIN_X + " TEXT," + KEY_PIN_Y + " TEXT" + ")";

        db.execSQL(CREATE_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEXT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MAP);
        // Creating tables again
        onCreate(db);
    }

    public void add_Text_Record(Record_Text record) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TIME, record.getTime());
        values.put(KEY_FLOOR, record.getFloor());
        values.put(KEY_SPACE_NUM, record.getSpace_num());
        values.put(KEY_AREA, record.getArea());
        values.put(KEY_DIR_FROM_LIFT, record.getDirection_from_lift());
        // Inserting Row
        db.insert(TABLE_TEXT, null, values);
        // Closing database connection
        db.close();
    }

    public void add_Map_Record(Record_Map map_record) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TIME, map_record.getTime());
        values.put(KEY_FLOOR, map_record.getFloor());
        values.put(KEY_PIN_X, map_record.getPin_x());
        values.put(KEY_PIN_Y, map_record.getPin_y());
        // Inserting Row
        db.insert(TABLE_MAP, null, values);
        // Closing database connection
        db.close();
    }

    public void clear_All_Text_Record() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TEXT, null, null);
        db.close();
    }

    public void clear_All_Map_Record() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MAP, null, null);
        db.close();
    }

    public void delete_Text_Record(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TEXT, KEY_ID + "=" + id, null);
        db.close();
    }

    public Record_Text get_Text_Record(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_TEXT, new String[] { KEY_ID,
                        KEY_TIME, KEY_FLOOR, KEY_SPACE_NUM, KEY_AREA, KEY_DIR_FROM_LIFT }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Record_Text record = new Record_Text(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));
        return record;
    }

    // Getting All Records
    public List<Record_Text> get_All_Text_Records() {
        List<Record_Text> recordList = new ArrayList<Record_Text>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_TEXT;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Record_Text record = new Record_Text();
                record.setId(Integer.parseInt(cursor.getString(0)));
                record.setTime(cursor.getString(1));
                record.setFloor(cursor.getString(2));
                record.setSpace_num(cursor.getString(3));
                record.setArea(cursor.getString(4));
                record.setDirection_from_lift(cursor.getString(5));
                // Adding contact to list
                recordList.add(record);
            } while (cursor.moveToNext());
        }
        return recordList;
    }

    public List<Record_Map> get_All_Map_Records() {
        List<Record_Map> recordList = new ArrayList<Record_Map>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_MAP;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Record_Map record = new Record_Map();
                record.setId(Integer.parseInt(cursor.getString(0)));
                record.setTime(cursor.getString(1));
                record.setFloor(cursor.getString(2));
                record.setPin_x(cursor.getString(3));
                record.setPin_y(cursor.getString(4));
                // Adding contact to list
                recordList.add(record);
            } while (cursor.moveToNext());
        }
        return recordList;
    }
}
