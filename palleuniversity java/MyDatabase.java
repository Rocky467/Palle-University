package com.example.rakesh.palleuniversity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabase {
    MyHelper myHelper;
    SQLiteDatabase sqLiteDatabase;

    public MyDatabase(Context context){
        myHelper = new MyHelper(context,"techpalle.db",null,1);
    }

    public void open(){
        sqLiteDatabase = myHelper.getWritableDatabase();
    }

    public void insertEmp(String name,String mobile,String email){

        ContentValues contentValues = new ContentValues();
        contentValues.put("name",name);
        contentValues.put("mobile",mobile);
        contentValues.put("email",email);
        sqLiteDatabase.insert("Emp",null,contentValues);
        close();
    }

    public Cursor readEmp(){
        return sqLiteDatabase.query("Emp",null,null,null,null,null,null);
    }

    public void deleteEmp(int eno) {
        sqLiteDatabase = myHelper.getWritableDatabase();
        sqLiteDatabase.delete("Emp","_id = ?",new String[]{eno+""});
    }

    public void updateName(int eno, String name, String mobile, String email){
        sqLiteDatabase = myHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",name);
        contentValues.put("mobile",mobile);
        contentValues.put("email",email);
       sqLiteDatabase.update("Emp",contentValues,"_id = ?",new String[]{""+eno});
    }

    public void search(String name){
        sqLiteDatabase = myHelper.getReadableDatabase();
        sqLiteDatabase.query("Emp",null,"name LIKE ?",new String[]{name+"%"},null,null,null);

    }


    public void close(){
        if(sqLiteDatabase!=null){
            sqLiteDatabase.close();
        }
    }

    public class MyHelper extends SQLiteOpenHelper{

        public MyHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table Emp(_id integer primary key autoincrement, name text, mobile text, email text);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }




}
