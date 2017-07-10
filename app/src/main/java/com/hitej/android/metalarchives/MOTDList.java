package com.hitej.android.metalarchives;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hitej.android.metalarchives.database.MOTDBaseHelper;
import com.hitej.android.metalarchives.database.MOTDCursorWrapper;
import com.hitej.android.metalarchives.database.MOTDDbSchema;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by jhite on 3/27/16.
 * Pre-Scraper - Singleton representing preset array of MOTDItems
 * to display in MOTDListFragment
 *
 * Post-Scraper - Singleton scraping www for last 10 MOTDs into array
 */
public class MOTDList {
    private static MOTDList sMOTDList;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static MOTDList get(Context context) {
        if (sMOTDList == null){
            sMOTDList = new MOTDList(context);
        }

        return sMOTDList;
    }

    private MOTDList(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new MOTDBaseHelper(context)
                .getWritableDatabase();

    }

    public void addMOTD(MOTDItem m) {
        ContentValues values = getContentValues(m);

        mDatabase.insert(MOTDDbSchema.MOTDTable.NAME, null,values);

    }

    public MOTDItem getMOTD(UUID id) {
        MOTDCursorWrapper cursor = queryMOTDs(
                MOTDDbSchema.MOTDTable.Cols.UUID + " = ?",
                new String[]{id.toString()}
        );

        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getMOTD();
        } finally {
            cursor.close();
        }
    }

    public List<MOTDItem> getMOTDs() {
        List<MOTDItem> motdList = new ArrayList<>();

        MOTDCursorWrapper cursor = queryMOTDs(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                motdList.add(cursor.getMOTD());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return motdList;
    }

    public void updateMOTD(MOTDItem m) {
        String uuid = m.getMotdIdNumber().toString();
        ContentValues values = getContentValues(m);

        mDatabase.update(MOTDDbSchema.MOTDTable.NAME, values,
                MOTDDbSchema.MOTDTable.Cols.UUID + " = ?",
                new String[]{uuid});

    }

    private static ContentValues getContentValues(MOTDItem item) {
        ContentValues values = new ContentValues();
        values.put(MOTDDbSchema.MOTDTable.Cols.UUID, item.getMotdIdNumber().toString());
        values.put(MOTDDbSchema.MOTDTable.Cols.TITLE, item.getMotdTitle());
        values.put(MOTDDbSchema.MOTDTable.Cols.BODY, item.getMotdBody());

        return values;
    }

    private MOTDCursorWrapper queryMOTDs(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                MOTDDbSchema.MOTDTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null


        );
        return new MOTDCursorWrapper(cursor);
    }

    private MOTDItem generateMOTD() {
        MOTDItem item = new MOTDItem(
                UUID.randomUUID(), "test title", "test body" );
        return item;
    }

    private MOTDItem generateMOTD(String title, String body){
        MOTDItem item = new MOTDItem(
                UUID.randomUUID(), title, body);
        return item;
    }

    public void addMOTDs(Context context, int motdCount) {
        /*
          generate *motdCount* messages, place them into the list and then
          post to DB
         */
        //List<MOTDItem> items = new ArrayList<>();
        while (motdCount != 0) {
            context = context.getApplicationContext();
            MOTDList motdList = MOTDList.get(context);
            motdList.addMOTD(generateMOTD());

            motdCount --;
        }
    }
}
