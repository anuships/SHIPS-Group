package com.example.ships.myapplication.homepageAndRegistration;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Nawespet on 7/23/2017.
 */

public  class DBManager extends SQLiteOpenHelper{
    private static final int dbVersion = 1;
    private static final String dbName = "shipsdb";
    private static DBManager dbManager;

    public static synchronized DBManager getInstance(Context ct){
        if (dbManager == null){
            dbManager = new DBManager(ct.getApplicationContext());
        }
        return dbManager;
    }

    private DBManager(Context ct){
        super(ct, dbName, null, dbVersion);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        Cursor res =  db.rawQuery("SELECT count(*) FROM sqlite_master WHERE type='table' AND name='modules';", null);
        res.moveToFirst();

        if (res.getInt(0) == 0) {
            db.execSQL("CREATE TABLE IF NOT EXISTS users(UID VARCHAR PRIMARY KEY,USERNAME VARCHAR, " +
                    "EMAIL VARCHAR, PASSWORD VARCHAR, SALT VARCHAR, FIRST_NAME VARCHAR, LAST_NAME VARCHAR);");
            db.execSQL("CREATE TABLE IF NOT EXISTS module_category(CID INTEGER PRIMARY KEY AUTOINCREMENT,TITLE VARCHAR);");
            db.execSQL("INSERT INTO module_category (TITLE) VALUES(\"SELF-ASSESSMENT\"), (\"FACTSHEET\"), (\"THERAPEUTIC TOOLS\"), (\"TREATMENT\");");
            db.execSQL("CREATE TABLE IF NOT EXISTS modules(MID INTEGER PRIMARY KEY AUTOINCREMENT,CID INTEGER, NAME VARCHAR, UNITS INTEGER, DESC VARCHAR, FOREIGN KEY(CID) REFERENCES module_category(CID));");
            db.execSQL("INSERT INTO modules (CID, NAME, UNITS) VALUES(0, \"FAS\", 1), (1, \"FACTSHEET\",1), (2, \"EMDR\", 1), (2, \"Audio: Introduction\", 1),(2,\"Audio: Graduated Progressive Muscle Relaxation\", 1 )" +
                    ",(2, \"Audio: Passive Progressive Relaxation\", 1), (2, \"Audio: Rotation of Awareness\", 1), (2, \"Audio: Breath Awareness\", 1), (2, \"Audio: Word Focus Meditation\", 1), (2, \"Audio: Alternative Nostril Breathing Meditation\", 1), (2, \"Audio: Mindfull Awareness\", 1), (2, \"Audio: Mindfullness Meditation\", 1), (2, \"Audio: Breathing Techniques\", 1), " +
                    "(2, \"Audio: Quick Release Technique\",1 ), (2, \"Audio: Standing Relaxation\", 1 ), (2, \"Audio: Self-Hypnosis\", 1)" +
                    ",(2, \"Biofeedback\", 1), (3, \"Systematic Desensitisation\", 5);");
            db.execSQL("CREATE TABLE IF NOT EXISTS treatmentplan_category(TCID INTEGER PRIMARY KEY AUTOINCREMENT, NAME VARCHAR);");
            db.execSQL("INSERT INTO treatmentplan_category (NAME) VALUES(\"SHORT FLIGHT\"), (\"LONG FLIGHT\"), (\"MISC\");");
            db.execSQL("CREATE TABLE IF NOT EXISTS status(SID INTEGER PRIMARY KEY AUTOINCREMENT, NAME VARCHAR);");
            db.execSQL("INSERT INTO status (NAME) VALUES(\"NOT STARTED\"), (\"IN PROGRESS\"), (\"COMPLETED\"), (\"DELETED\");");
            db.execSQL("CREATE TABLE IF NOT EXISTS treatmentplan(TID INTEGER PRIMARY KEY AUTOINCREMENT, UID VARCHAR, TCID INTEGER, date_added VAR CHAR, FOREIGN KEY(UID) REFERENCES users(UID), FOREIGN KEY(TCID) REFERENCES treatmentplan_category(TCID));");
            db.execSQL("CREATE TABLE IF NOT EXISTS user_modules(TID INTEGER, INDX INTEGER NOT NULL, MID INTEGER, SID INTEGER, progress INTEGER, last_updated VAR CHAR, PRIMARY KEY(TID, INDX), FOREIGN KEY(TID) REFERENCES treatmentplan(TID), FOREIGN KEY(MID) REFERENCES modules(MID), FOREIGN KEY(SID) REFERENCES status(SID));");
            db.execSQL("CREATE TABLE IF NOT EXISTS module_result(TID INTEGER, INDX INTEGER, result VAR CHAR, PRIMARY KEY(TID, INDX), FOREIGN KEY(TID) REFERENCES treatmentplan(TID), FOREIGN KEY(INDX) REFERENCES user_modules(INDX));");
        }
    }
    @Override
    public void onOpen(SQLiteDatabase db){
        Cursor res =  db.rawQuery("SELECT count(*) FROM sqlite_master WHERE type='table' AND name='modules';", null);
        res.moveToFirst();
        if (res.getInt(0) == 0) {

            db.execSQL("CREATE TABLE IF NOT EXISTS users(UID VARCHAR PRIMARY KEY,USERNAME VARCHAR, " +
                    "EMAIL VARCHAR, PASSWORD VARCHAR, SALT VARCHAR, FIRST_NAME VARCHAR, LAST_NAME VARCHAR);");
            db.execSQL("CREATE TABLE IF NOT EXISTS module_category(CID INTEGER PRIMARY KEY AUTOINCREMENT,TITLE VARCHAR);");
            db.execSQL("INSERT INTO module_category (TITLE) VALUES(\"SELF-ASSESSMENT\"), (\"FACTSHEET\"), (\"THERAPEUTIC TOOLS\"), (\"TREATMENT\");");
            db.execSQL("CREATE TABLE IF NOT EXISTS modules(MID INTEGER PRIMARY KEY AUTOINCREMENT,CID INTEGER, NAME VARCHAR, UNITS INTEGER, DESC VARCHAR, FOREIGN KEY(CID) REFERENCES module_category(CID));");
            db.execSQL("INSERT INTO modules (CID, NAME, UNITS) VALUES(0, \"FAS\", 1), (1, \"FACTSHEET\",1), (2, \"EMDR\", 1), (2, \"Audio: Introduction\", 1),(2,\"Audio: Graduated Progressive Muscle Relaxation\", 1 )" +
                    ",(2, \"Audio: Passive Progressive Relaxation\", 1), (2, \"Audio: Rotation of Awareness\", 1), (2, \"Audio: Breath Awareness\", 1), (2, \"Audio: Word Focus Meditation\", 1), (2, \"Audio: Alternative Nostril Breathing Meditation\", 1), (2, \"Audio: Mindfull Awareness\", 1), (2, \"Audio: Mindfullness Meditation\", 1), (2, \"Audio: Breathing Techniques\", 1), " +
                    "(2, \"Audio: Quick Release Technique\",1 ), (2, \"Audio: Standing Relaxation\", 1 ), (2, \"Audio: Self-Hypnosis\", 1)" +
                    ",(2, \"Biofeedback\", 1), (3, \"Systematic Desensitisation\", 5);");
            db.execSQL("CREATE TABLE IF NOT EXISTS treatmentplan_category(TCID INTEGER PRIMARY KEY AUTOINCREMENT, NAME VARCHAR);");
            db.execSQL("INSERT INTO treatmentplan_category (NAME) VALUES(\"SHORT FLIGHT\"), (\"LONG FLIGHT\"), (\"MISC\");");
            db.execSQL("CREATE TABLE IF NOT EXISTS status(SID INTEGER PRIMARY KEY AUTOINCREMENT, NAME VARCHAR);");
            db.execSQL("INSERT INTO status (NAME) VALUES(\"NOT STARTED\"), (\"IN PROGRESS\"), (\"COMPLETED\"), (\"DELETED\");");
            db.execSQL("CREATE TABLE IF NOT EXISTS treatmentplan(TID INTEGER PRIMARY KEY AUTOINCREMENT, UID VARCHAR, TCID INTEGER, date_added VAR CHAR, FOREIGN KEY(UID) REFERENCES users(UID), FOREIGN KEY(TCID) REFERENCES treatmentplan_category(TCID));");
            db.execSQL("CREATE TABLE IF NOT EXISTS user_modules(TID INTEGER, INDX INTEGER NOT NULL, MID INTEGER, SID INTEGER, progress INTEGER, last_updated VAR CHAR, PRIMARY KEY(TID, INDX), FOREIGN KEY(TID) REFERENCES treatmentplan(TID), FOREIGN KEY(MID) REFERENCES modules(MID), FOREIGN KEY(SID) REFERENCES status(SID));");
            db.execSQL("CREATE TABLE IF NOT EXISTS module_result(TID INTEGER, INDX INTEGER, result VAR CHAR, PRIMARY KEY(TID, INDX), FOREIGN KEY(TID) REFERENCES treatmentplan(TID), FOREIGN KEY(INDX) REFERENCES user_modules(INDX));");
        }

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
