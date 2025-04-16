package com.example.myapplication.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

import com.example.myapplication.model.Category;
import com.example.myapplication.model.Items;

import java.util.ArrayList;
import java.util.List;

public class DAO extends SQLiteOpenHelper {
    private final  static String DATABASE_NAME="nhom7.db";
    private final  static int DATABASE_VERSION1 =1;

    public DAO(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql ="create table categories(id integer primary key autoincrement,"+"name text)";
        db.execSQL(sql);

//         sql ="CREATE TABLE items("+
//
//                 "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
//                 "title TEXT,"+
//                 "category TEXT,"+
//                 "price TEXT,"+
//                 "date TEXT,"+
//                 "Foreign key(cid) references categories(id))";
//
//
//
//        db.execSQL(sql);
         sql = "CREATE TABLE items (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "cid INTEGER, " +
                "quantity INTEGER, " +
                "price REAL, " +
                "updatedAt TEXT, " +
                "FOREIGN KEY(cid) REFERENCES categories(id))";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public void insertCategory1(Category c){
        String sql = "insert into categories(name) values(?)";
        String[] args ={c.getName()};
        try {
            SQLiteDatabase st = getWritableDatabase();
            st.execSQL(sql,args);
        }catch (Exception e){

        }

    }
//public void insertCategory1(Category c) {
//    String sql = "INSERT INTO categories(name) VALUES (?)";
//    try {
//        SQLiteDatabase st = getWritableDatabase();
//        SQLiteStatement statement = st.compileStatement(sql);
//        statement.bindString(1, c.getName());
//        statement.executeInsert();
//    } catch (Exception e) {
//        e.printStackTrace();
//    }
//}

    public void insertCategory2(Category c){
        ContentValues values = new ContentValues();
        values.put("name",c.getName());
        SQLiteDatabase st= getWritableDatabase();
        st.insert("categories",null,values);
    }
    public List<Category> getCate(){
        List<Category> list = new ArrayList<>();
        String sql = "select * from categories";
        try {
            SQLiteDatabase st = getReadableDatabase();
            Cursor rs= st.rawQuery(sql,null);
            while (rs.moveToNext()){
                list.add(new Category(rs.getInt(0), rs.getString(1)));
            }
        }catch (Exception e){

        }
        return  list;
    }
    public List<Category> getCate2(){
        List<Category> list = new ArrayList<>();
        try {
            SQLiteDatabase st = getReadableDatabase();
            Cursor rs = st.query("categories", null,null,null,null,null,null);
            while (rs.moveToNext()){
                list.add(new Category(rs.getInt(0), rs.getString(1)));
            }
        }catch (Exception e){

        }
        return list;
    }
//    public Void insertItem(Items t) {
//        ContentValues values = new ContentValues();
//        values.put("name", t.getName());
//        values.put("cid", t.getCid());
//        values.put("quantity", t.getQuantity());
//        values.put("price", t.getPrice());
//        values.put("updatedAt", t.getUpdatedAt());
//        try {
//            SQLiteDatabase st = getWritableDatabase();
//            st.insert("items", null, values);
//        } catch (Exception e) {
//
//        }
//
//    }
public void insertItem(Items t) {
    ContentValues values = new ContentValues();
    values.put("name", t.getName());
    values.put("cid", t.getCid());
    values.put("quantity", t.getQuantity());
    values.put("price", t.getPrice());
    values.put("updatedAt", t.getUpdatedAt());

    try {
        SQLiteDatabase st = getWritableDatabase(); // dùng writable
        st.insert("items", null, values);
    } catch (Exception e) {
        e.printStackTrace(); // để biết nếu có lỗi xảy ra
    }
}

}
