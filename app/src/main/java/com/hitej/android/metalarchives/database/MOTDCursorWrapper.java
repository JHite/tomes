package com.hitej.android.metalarchives.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.hitej.android.metalarchives.MOTDItem;

import java.util.UUID;

/**
 * Created by jhite on 3/28/16.
 */
public class MOTDCursorWrapper extends CursorWrapper{
    public MOTDCursorWrapper(Cursor cursor){
        super(cursor);
    }

    public MOTDItem getMOTD(){
        String uuidString = getString(getColumnIndex(MOTDDbSchema.MOTDTable.Cols.UUID));
        String title = getString(getColumnIndex(MOTDDbSchema.MOTDTable.Cols.TITLE));
        String body = getString(getColumnIndex(MOTDDbSchema.MOTDTable.Cols.BODY));

        MOTDItem motdItem = new MOTDItem();
        motdItem.setMotdIdNumber(UUID.fromString(uuidString));
        motdItem.setMotdTitle(title);
        motdItem.setMotdBody(body);

        return motdItem;
    }
}
