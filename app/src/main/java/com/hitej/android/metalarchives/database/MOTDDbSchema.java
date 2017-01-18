package com.hitej.android.metalarchives.database;

/**
 * Created by jhite on 3/27/16.
 */
public class MOTDDbSchema {
    public static final class MOTDTable {
        public static final String NAME = "MOTDs";

        public static final class Cols{
            public static final String UUID = "UUID";
            public static final String TITLE = "TITLE";
            public static final String BODY = "BODY";
        }
    }
}
