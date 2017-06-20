package com.fjnu.edu.helper.datebase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fjnu.edu.helper.food.AddFoodInformation;
import com.fjnu.edu.helper.food.Food;
import com.fjnu.edu.helper.food.MyFood;

import java.util.ArrayList;

/**
 * Created by ZhouShiqiao on 2017/5/30 0030.
 */

public class DBManager {
    private DBHelper helper;
    protected SQLiteDatabase db;

    public DBManager(Context context) {
        helper = new DBHelper(context);
        db = helper.getWritableDatabase();
    }

    public ArrayList<AddFoodInformation> queryaddfoodinformation(String str) {
        String sql = "select * from food where 1 = 1 and materialName like '%" + str + "%'";
        Cursor c = db.rawQuery(sql, null);
        ArrayList<AddFoodInformation> list = new ArrayList<AddFoodInformation>();
        while (c.moveToNext()) {
            String id = c.getString(c.getColumnIndex("mno"));
            String name = c.getString(c.getColumnIndex("materialName"));
            String picture = c.getString(c.getColumnIndex("image"));
            AddFoodInformation afi = new AddFoodInformation(id, name, picture);
            list.add(afi);
        }
        c.close();
        return list;
    }

    public void InsertMyfood(MyFood food) {
        ContentValues cv = new ContentValues();
        cv.put("No", food.getMno());
        cv.put("Quantity", food.getResidue());
        cv.put("Position", food.getPosition());
        cv.put("Unit", food.getUnit());
        cv.put("InputDate", food.getAddtime());
        cv.put("storagetime",food.getStoragetime());
        db.insert("myfood", null, cv);
    }

    public void UpdateMyfood(String inputdate,int quantity) {
        String sql = null;
        sql = "update myfood set Quantity = "+quantity+" where InputDate = '"+inputdate+"'";
        db.execSQL(sql);
    }

    public Food QueryFoodByNo(String mno) {
        String sql = "select * from food where mno = '" + mno + "'";
        Cursor c = db.rawQuery(sql, null);
        Food food = null;
        while (c.moveToNext()) {
            String name = c.getString(c.getColumnIndex("materialName"));
            int storage1 = c.getInt(c.getColumnIndex("storage1"));
            int storage2 = c.getInt(c.getColumnIndex("storage2"));
            int storage3 = c.getInt(c.getColumnIndex("storage3"));
            food = new Food(mno, name, storage1, storage2, storage3);
        }
        return food;
    }

    public ArrayList<MyFood> QueryMyFood() {
        ArrayList<MyFood> list = new ArrayList<MyFood>();
        String sql = "select myfood.No as id," +
                "food.materialName as name," +
                "myfood.quantity as quantity," +
                "myfood.unit as unit," +
                "myfood.position as position," +
                "myfood.InputDate as inputdate, " +
                "food.image as image, " +
                "myfood.storagetime as storagetime " +
                "from food , myfood where myfood.No = food.mno " +
                "order by myfood.No asc ,myfood.Inputdate asc";
        Cursor c = db.rawQuery(sql, null);
        MyFood food = null;
        while (c.moveToNext()) {
            String id = c.getString(c.getColumnIndex("id"));
            String name = c.getString(c.getColumnIndex("name"));
            double quantity = c.getDouble(c.getColumnIndex("quantity"));
            String unit = c.getString(c.getColumnIndex("unit"));
            String position = c.getString(c.getColumnIndex("position"));
            String inputdate = c.getString(c.getColumnIndex("inputdate"));
            String image = c.getString(c.getColumnIndex("image"));
            int storagetime = c.getInt(c.getColumnIndex("storagetime"));
            food = new MyFood(id, name, position, inputdate, quantity, unit, image, storagetime);
            list.add(food);
        }
        return list;
    }

    public void DeleteMyFood(String date){
        String sql = null;
        sql = "delete from myfood where InputDate = '"+date+"'";
        db.execSQL(sql);
    }

    public void InsertHistory(String id){
        ContentValues cv = new ContentValues();
        cv.put("id", id);
        db.insert("history", null, cv);
    }
    public ArrayList<String> QueryHistory(){
        ArrayList<String> list = new ArrayList<String>();
        String sql="select * from history limit 15";
        Cursor c = db.rawQuery(sql,null);
        while (c.moveToNext()){
            list.add(c.getString(c.getColumnIndex("id")));
        }
        return list;
    }

    public void update(String table, ContentValues value, String where, String[] args) {
        db.update(table, value, where, args);
    }

    public void delete(String table, String where, String[] args) {
        db.delete(table, where, args);
    }

    public Cursor query(String table, String[] columns, String selection, String[] selectionArgs,
                        String groupBy, String having, String orderBy, String limit) {
        Cursor c = null;

        return c;
    }
}
