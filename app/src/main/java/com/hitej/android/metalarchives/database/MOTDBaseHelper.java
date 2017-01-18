package com.hitej.android.metalarchives.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jhite on 3/28/16.
 *
 */
public class MOTDBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final  String DATABASE_NAME = "MOTDBase.db";
    // 3/29 - Name of entire app db or just for this class? ^

    public MOTDBaseHelper(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table " + MOTDDbSchema.MOTDTable.NAME + "(" +
                        "_id integer primary key autoincrement, " +
                        MOTDDbSchema.MOTDTable.Cols.UUID + "," +
                        MOTDDbSchema.MOTDTable.Cols.TITLE + "," +
                        MOTDDbSchema.MOTDTable.Cols.BODY + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){}

}
