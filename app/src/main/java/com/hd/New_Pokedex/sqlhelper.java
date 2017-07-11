package com.hd.New_Pokedex;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by Haridev on 22-06-2017.
 */

public class sqlhelper extends SQLiteOpenHelper {
    public static final String databasename="galldatabase3171";
    public static final String tsblename="gallery3174";
    public static final  String uid="_id";
    public static final  String detail="detail";
    public static final  String im="image";
    Context context;

    public sqlhelper(Context context) {
        super(context, databasename, null,64);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String ct1 ="CREATE TABLE IF EXIST "+tsblename+"("+uid+"INTEGER PRIMARY KEY AUTOINCREMENT,"+detail+"VARCHAR(255)"+im+"BLOG);";
        String ct2 ="CREATE TABLE  "+tsblename+"("+uid+" INTEGER PRIMARY KEY AUTOINCREMENT, "+detail+" VARCHAR(255) , "+im+" BLOB);";
        db.execSQL(ct2);
        Toast.makeText(context,"oncreatecalled",Toast.LENGTH_LONG);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        message.message(context,"onupdatecalled");
        db.execSQL("DROP TABLE IF EXISTS " + tsblename);
        onCreate(db);

    }
}
