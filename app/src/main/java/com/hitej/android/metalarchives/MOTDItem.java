package com.hitej.android.metalarchives;

import java.util.UUID;

/**
 * Created by jhite on 3/26/16.
 * A simple Message Of The Day Item (MOTDItem)
 *  title, body ,optional signature
 */
public class MOTDItem {
    private UUID mMotdIdNumber;
    private String mMotdTitle;
    private String mMotdBody;

    public MOTDItem(){
        mMotdIdNumber = UUID.randomUUID();
    }

    public MOTDItem(UUID uuid){
        mMotdIdNumber = uuid;
    }

    public MOTDItem(String title, String body) {
        mMotdIdNumber = UUID.randomUUID();
        mMotdTitle = title;
        mMotdBody = body;

    }

    public MOTDItem(UUID uuid, String title, String body) {
        mMotdIdNumber = uuid;
        mMotdTitle = title;
        mMotdBody = body;
    }

    public UUID getMotdIdNumber() {
        return mMotdIdNumber;
    }

    public void setMotdIdNumber(UUID motdIdNumber) {
        mMotdIdNumber = motdIdNumber;
    }

    public String getMotdTitle() {
        return mMotdTitle;
    }

    public void setMotdTitle(String motdTitle) {
        mMotdTitle = motdTitle;
    }

    public String getMotdBody() {
        return mMotdBody;
    }

    public void setMotdBody(String motdBody) {
        mMotdBody = motdBody;
    }
}
