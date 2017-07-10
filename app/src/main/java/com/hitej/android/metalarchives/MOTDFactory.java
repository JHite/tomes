package com.hitej.android.metalarchives;

import android.content.Context;

import java.util.UUID;

/**
 * Created by jhite on 3/29/16.
 * Creates and adds 10 new articles to MOTDDb
 */

public class MOTDFactory {


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

    private void addMOTDs(Context context, int motdCount) {
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
