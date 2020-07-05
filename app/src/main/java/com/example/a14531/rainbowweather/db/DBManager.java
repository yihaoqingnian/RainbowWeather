package com.example.a14531.rainbowweather.db;

/*
 *   authr：  tangzhenhua
 *   Date：   2020.06.10
 *   Contact：1453110533@qq.com
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DBManager {
    public static SQLiteDatabase database;
    private static ContentValues contentValues;
    private static String string;
    private static DatabaseBean bean;

    //初始化数据库信息
    public static void initDB(Context context){
        DBHelper dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    //查找数据库中城市列表
    public static List<String>queryAllCityName(){
        Cursor cursor = database.query("info",null,null,null,null,null,null);//表，列，，分组，排序
        List<String>cityList = new ArrayList<>();
        while (cursor.moveToNext()) {
            String city = cursor.getString(cursor.getColumnIndex("city"));
            cityList.add(city);
        }
        return cityList;
    }

    //根据城市名称替换信息内容
    public static int updateInfoByCity(String city,String content){
        ContentValues values = new ContentValues();
        values.put("content",content);
        return database.update("info",values,"city=?",new String[]{city});
    }

    //新增城市记录
    public static long addCityInfo(String city,String content){
        ContentValues values = new ContentValues();
        values.put("city",city);
        values.put("content",content);
        return database.insert("info",null,values);
    }

    //根据城市名查询数据库中的内容
    public static String queryInfoByCity(String city){
        Cursor cursor = database.query("info",null,"city=?",new String[]{city},null,null,null);
        if (cursor.getCount()>0) {
            cursor.moveToFirst();
            String content = cursor.getString(cursor.getColumnIndex("content"));
            return content;
        }
        return null;
    }

    //存储城市天气最多5个
    public static int getCityCount(){
        Cursor cursor = database.query("info",null,null,null,null,null,null);
        int count = cursor.getCount();
        return count;
    }

    //查询数据库全部信息
    public static List<DatabaseBean>queryAllInfo(){
        Cursor cursor = database.query("info",null,null,null,null,null,null);
        List<DatabaseBean>list = new ArrayList<>();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("_id"));
            String city = cursor.getString(cursor.getColumnIndex("city"));
            String content = cursor.getString(cursor.getColumnIndex("content"));
            DatabaseBean bean = new DatabaseBean(id, city, content);
            list.add(bean);

        }
        return list;

    }

    //根据城市名称，删除这个城市在数据库当中的数据
    public static int deleteInfoByCity(String city){
        return database.delete("info","city=?",new String[]{city});
    }

    //删除表当中所有的数据信息
    public static void deleteAllInfo(){
        String sql = "delete from info";
        database.execSQL(sql);
    }
}
