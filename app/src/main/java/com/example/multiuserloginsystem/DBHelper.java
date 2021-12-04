package com.example.multiuserloginsystem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(@Nullable Context context) {
        super(context, "Userdata.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table Users (email TEXT primary key, password TEXT, name TEXT, id TEXT,type TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists Users");
    }

    public boolean insertData(String name, String id, String email, String password,String type){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",name);
        contentValues.put("id",id);
        contentValues.put("email",email);
        contentValues.put("password",password);
        contentValues.put("type",type);
        long result = db.insert("Users",null,contentValues);
        return result != -1;
    }

    public boolean updateData(String email, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("password",password);

        Cursor cursor = db.rawQuery("Select * from Users where email = ?",new String[] {email});
        if (cursor.getCount()>0){
            long result = db.update("Users", contentValues, "email=?", new String[] {email});
            return result!=-1;
        }
        return false;
    }

    public boolean deleteData(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from Users where email = ?", new String[] {email});
        if (cursor.getCount()>0){
            long result = db.delete("Users","email=?",new String[] {email});
            return result!=-1;
        }
        return false;
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("Select * from users",null);
    }

    public boolean checkEmailThere(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from Users where email = ?", new String[] {email});
        return cursor.getCount()>0;
    }

    public boolean checkIdThere(String id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from Users where id = ?", new String[] {id});
        return cursor.getCount()>0;
    }

    public boolean checkPasswordCorrect(String email, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from Users where email = ? And password = ?",
                new String[] {email,password});
        return cursor.getCount()>0;
    }

    public String typeOfEmail(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from Users where email = ?", new String[] {email});
        if (cursor.getCount()>0){
            cursor.moveToNext();
            return cursor.getString(cursor.getColumnIndex("type"));
        }
        return null;
    }

    public Cursor getDeatils(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from Users where email = ?", new String[] {email});
        if (cursor.getCount()>0){
            return cursor;
        }
        return null;
    }
}
