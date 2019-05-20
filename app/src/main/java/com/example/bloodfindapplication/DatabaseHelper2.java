package com.example.bloodfindapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper2 extends SQLiteOpenHelper {
    public DatabaseHelper2(Context context) {
        super(context, "UserActivities.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table activities(activityno INTEGER primary key,fromemail text , toemail text,activitystatus text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists activities");
    }

    //insert into database
    public  boolean onInsert(String fromEmail,String toEmail,String activityStatus) {
        SQLiteDatabase db2 = this.getWritableDatabase();
        SQLiteDatabase db3 =this.getReadableDatabase();
        int activityNo;
        Cursor maxActivityNoCursor = db3.rawQuery("select MAX(activityno) from activities",null);
        if (maxActivityNoCursor != null) {
            maxActivityNoCursor.moveToFirst();
            do {
                activityNo = maxActivityNoCursor.getInt(0);
                activityNo = activityNo + 1;
            }while (maxActivityNoCursor.moveToNext());
        }
        else {
            activityNo = 1;
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put("activityno",activityNo);
        contentValues.put("fromemail",fromEmail);
        contentValues.put("toemail",toEmail);
        contentValues.put("activitystatus",activityStatus);

        long insert = db2.insert("activities",null,contentValues);
        if(insert == -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public Cursor getActivitiesSearchResults(String emailString) {
        SQLiteDatabase db2=this.getReadableDatabase();
        Cursor activitySearchCursor = db2.rawQuery("select activityno,fromemail,toemail,activitystatus from activities where fromemail=? or toemail=?",new String[]{emailString,emailString});
        return activitySearchCursor;
    }

    public Cursor getActivityInformation(String activityNoClicked) {
        SQLiteDatabase db2=this.getReadableDatabase();
        Cursor activitySearchCursor = db2.rawQuery("select activityno,fromemail,toemail,activitystatus from activities where activityno=?",new String[]{activityNoClicked});
        return activitySearchCursor;
    }

    public void updateActivityStatus(String activityNoUpdated,String newStatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("activitystatus",newStatus);
        db.update("activities",contentValues,"activityno=?",new String[] {activityNoUpdated});
    }
}
