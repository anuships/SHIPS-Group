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
    public static final String FAS = "FAS";
    public static final String EMDR = "EMDR";
    public static final String FACTSHEET = "FACTSHEET";
    public static final String Audio1 = "Audio: Introduction";
    public static final String Audio2 = "Audio: Graduated Progressive Muscle Relaxation";
    public static final String Audio3 = "Audio: Passive Progressive Relaxation";
    public static final String Audio4 = "Audio: Rotation of Awareness";
    public static final String Audio5 = "Audio: Breath Awareness";
    public static final String Audio6 = "Audio: Word Focus Meditation";
    public static final String Audio7 = "Audio: Alternative Nostril Breathing Meditation";
    public static final String Audio8 = "Audio: Mindfull Awareness";
    public static final String Audio9 = "Audio: Mindfullness Meditation";
    public static final String Audio10 = "Audio: Breathing Techniques";
    public static final String Audio11 = "Audio: Quick Release Technique";
    public static final String Audio12 = "Audio: Standing Relaxation";
    public static final String Audio13 = "Audio: Self-Hypnosis";
    public static final String BIOFEEDBACK = "Biofeedback";
    public static final String SYSDESEN = "Systematic Desensitisation";

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
            db.execSQL("INSERT INTO modules (CID, NAME, UNITS, DESC) VALUES" +
                    "(0, \"FAS\", 1,\"Self Assessment is a tool that helps you to " +
                    "understand more about yourself on fear of flying.\"), " +
                    "(1, \"FACTSHEET\",1,\"tocome\"), " +
                    "(2, \"EMDR\", 1,  \"EMDR is a kind of therapeutic tool that helps you " +
                    "to distract from the plane by focusing on a moving ball\"), " +
                    "(2, \"Audio: Introduction\", 1,\"Relaxation audio is kind of therapeutic tool" +
                    "that helps you to learn techniques to relax yourself.\")," +
                    "(2,\"Audio: Graduated Progressive Muscle Relaxation\", 1,\"Relaxation audio is kind of therapeutic tool " +
                    "that helps you to learn techniques to relax yourself.\" )," +
                    "(2, \"Audio: Passive Progressive Relaxation\", 1,\"Relaxation audio is kind of therapeutic tool " +
                    "that helps you to learn techniques to relax yourself.\"), " +
                    "(2, \"Audio: Rotation of Awareness\", 1,\"Relaxation audio is kind of therapeutic tool " +
                    "that helps you to learn techniques to relax yourself.\"), " +
                    "(2, \"Audio: Breath Awareness\", 1,\"Relaxation audio is kind of therapeutic tool " +
                    "that helps you to learn techniques to relax yourself.\"), " +
                    "(2, \"Audio: Word Focus Meditation\", 1,\"Relaxation audio is kind of therapeutic tool " +
                    "that helps you to learn techniques to relax yourself.\"), " +
                    "(2, \"Audio: Alternative Nostril Breathing Meditation\", 1,\"Relaxation audio is kind of therapeutic tool " +
                    " that helps you to learn techniques to relax yourself.\"), " +
                    "(2, \"Audio: Mindfull Awareness\", 1,\"Relaxation audio is kind of therapeutic tool " +
                    "that helps you to learn techniques to relax yourself.\"), " +
                    "(2, \"Audio: Mindfullness Meditation\", 1,\"Relaxation audio is kind of therapeutic tool " +
                    "that helps you to learn techniques to relax yourself.\"), " +
                    "(2, \"Audio: Breathing Techniques\", 1,\"Relaxation audio is kind of therapeutic tool " +
                    "that helps you to learn techniques to relax yourself.\"), " +
                    "(2, \"Audio: Quick Release Technique\",1,\"Relaxation audio is kind of therapeutic tool " +
                    "that helps you to learn techniques to relax yourself.\" ), " +
                    "(2, \"Audio: Standing Relaxation\", 1,\"Relaxation audio is kind of therapeutic tool " +
                    "that helps you to learn techniques to relax yourself.\" )," +
                    "(2, \"Audio: Self-Hypnosis\", 1,\"Relaxation audio is kind of therapeutic tool " +
                    "that helps you to learn techniques to relax yourself.\")," +
                    "(2, \"Biofeedback\", 1,\"Biofeedback is kind of the therapeutic tool " +
                    "that helps you to understand whether you become more relax or anxious. " +
                    "At the beginning, some data will be collected from your body and then " +
                    "generate a baseline in BLUE. After that a RED real time monitoring line " +
                    "reflects your current feeling. If the red line goes up means you feel relax. " +
                    "If the red line goes down that means you feel anxious.\"), " +
                    "(3, \"Systematic Desensitisation\", 5,\"Systematic Desensitisation is kind " +
                    "of therapy that allows you to go through some airline like scenarios. " +
                    "You have to apply techniques learned from the provided therapeutic tools " +
                    "and try to relax your self. There are 5 levels of scenarios provided in " +
                    "this version.\");");
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
            db.execSQL("INSERT INTO modules (CID, NAME, UNITS, DESC) VALUES" +
                    "(0, \"FAS\", 1,\"Self Assessment is a tool that helps you to " +
                    "understand more about yourself on fear of flying.\"), " +
                    "(1, \"FACTSHEET\",1,\"tocome\"), " +
                    "(2, \"EMDR\", 1,  \"EMDR is a kind of therapeutic tool that helps you " +
                    "to distract from the plane by focusing on a moving ball\"), " +
                    "(2, \"Audio: Introduction\", 1,\"Relaxation audio is kind of therapeutic tool" +
                    "that helps you to learn techniques to relax yourself.\")," +
                    "(2,\"Audio: Graduated Progressive Muscle Relaxation\", 1,\"Relaxation audio is kind of therapeutic tool " +
                    "that helps you to learn techniques to relax yourself.\" )," +
                    "(2, \"Audio: Passive Progressive Relaxation\", 1,\"Relaxation audio is kind of therapeutic tool " +
                    "that helps you to learn techniques to relax yourself.\"), " +
                    "(2, \"Audio: Rotation of Awareness\", 1,\"Relaxation audio is kind of therapeutic tool " +
                    "that helps you to learn techniques to relax yourself.\"), " +
                    "(2, \"Audio: Breath Awareness\", 1,\"Relaxation audio is kind of therapeutic tool " +
                    "that helps you to learn techniques to relax yourself.\"), " +
                    "(2, \"Audio: Word Focus Meditation\", 1,\"Relaxation audio is kind of therapeutic tool " +
                    "that helps you to learn techniques to relax yourself.\"), " +
                    "(2, \"Audio: Alternative Nostril Breathing Meditation\", 1,\"Relaxation audio is kind of therapeutic tool " +
                    " that helps you to learn techniques to relax yourself.\"), " +
                    "(2, \"Audio: Mindfull Awareness\", 1,\"Relaxation audio is kind of therapeutic tool " +
                    "that helps you to learn techniques to relax yourself.\"), " +
                    "(2, \"Audio: Mindfullness Meditation\", 1,\"Relaxation audio is kind of therapeutic tool " +
                    "that helps you to learn techniques to relax yourself.\"), " +
                    "(2, \"Audio: Breathing Techniques\", 1,\"Relaxation audio is kind of therapeutic tool " +
                    "that helps you to learn techniques to relax yourself.\"), " +
                    "(2, \"Audio: Quick Release Technique\",1,\"Relaxation audio is kind of therapeutic tool " +
                    "that helps you to learn techniques to relax yourself.\" ), " +
                    "(2, \"Audio: Standing Relaxation\", 1,\"Relaxation audio is kind of therapeutic tool " +
                    "that helps you to learn techniques to relax yourself.\" )," +
                    "(2, \"Audio: Self-Hypnosis\", 1,\"Relaxation audio is kind of therapeutic tool " +
                    "that helps you to learn techniques to relax yourself.\")," +
                    "(2, \"Biofeedback\", 1,\"Biofeedback is kind of the therapeutic tool " +
                    "that helps you to understand whether you become more relax or anxious. " +
                    "At the beginning, some data will be collected from your body and then " +
                    "generate a baseline in BLUE. After that a RED real time monitoring line " +
                    "reflects your current feeling. If the red line goes up means you feel relax. " +
                    "If the red line goes down that means you feel anxious.\"), " +
                    "(3, \"Systematic Desensitisation\", 5,\"Systematic Desensitisation is kind " +
                    "of therapy that allows you to go through some airline like scenarios. " +
                    "You have to apply techniques learned from the provided therapeutic tools " +
                    "and try to relax your self. There are 5 levels of scenarios provided in " +
                    "this version.\");");
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
