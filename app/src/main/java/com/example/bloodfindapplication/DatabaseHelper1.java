package com.example.bloodfindapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class DatabaseHelper1 extends SQLiteOpenHelper {

    public DatabaseHelper1(Context context) {
        super(context, "UserInformation.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table user(email text primary key, password text,name text, mobileno text,address text,bloodgroup text, category text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists user");
    }

    //insert into database
    public  boolean onInsert(String email,String password,String name,String address,String mobileno,String bloodgroup) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email",email);
        contentValues.put("password",password);
        contentValues.put("name",name);
        contentValues.put("mobileno",mobileno);
        contentValues.put("address",address);
        contentValues.put("bloodgroup",bloodgroup);
        contentValues.put("category","NONE");
        long insert = db.insert("user",null,contentValues);
        if(insert == -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    //checking if email exists
    public boolean checkMail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from user where email=?",new String[]{email});
        if(cursor.getCount()>0)
        {
            return false;
        }
        else
        {
            return true;
        }

    }

    //checking emailID and password in login page
    public boolean checkMailPassword(String email,String password) {
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from user where email=? and password=?",new String[]{email,password});
        if(cursor.getCount()>0) {
            return true;
        }
        else {
            return false;
        }
    }

    //changing category in homepage
    public boolean changeCategory(String email,String category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email",email);
        contentValues.put("category",category);
        db.update("user",contentValues,"email=?",new String[] {email});
        return true;
    }

    public Cursor getCategory(String emailString) {
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor categoryCursor = db.rawQuery("select category from user where email=?",new String[]{emailString});
        return categoryCursor;
    }

    public Cursor getSearchResults(String email,String categoryValue) {
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor bloodGroupCursor = db.rawQuery("select bloodgroup from user where email=?",new String[]{email});
        if(bloodGroupCursor!= null) {
            bloodGroupCursor.moveToFirst();
        }
        String bloodGroupFromdb;
        do{
            bloodGroupFromdb = bloodGroupCursor.getString(0);
        }while (bloodGroupCursor.moveToNext());
        Cursor searchCursor = db.rawQuery("select name,email from user where category=? and bloodgroup=?",new String[]{categoryValue,bloodGroupFromdb});
        return searchCursor;
    }
    public Cursor getInformationAfterSearch(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor searchCursor = db.rawQuery("select name,email,mobileno,bloodgroup,address,category from user where email=?",new String[]{email});
        return searchCursor;
    }
}
