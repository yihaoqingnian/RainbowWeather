package com.example.a14531.rainbowweather.db;

/*
 *   authr：  tangzhenhua
 *   Date：   2020.06.10
 *   Contact：1453110533@qq.com
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {


    public DBHelper(Context context) {
        super(context,"forecast.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建表
        String sql = "create table info(_id integer primary key autoincrement,city varchar(20) unique not null,content text no null)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
