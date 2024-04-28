package com.example.sql_8a;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;


public class MyDatabaseHelper {

    private final String DATABASE_NAME = "PasswordManager";
    private final int DATABASE_VERSION = 1;

    private final String TABLE_NAME = "User";
    private final String KEY_ID = "_id";
    private final String KEY_USERNAME = "_username";
    private final String KEY_PASSWORD = "_password";

    private final String TABLE_NAME2 = "Password";
    private final String KEY_ID2 = "_id";
    private final String KEY_USERNAME2 = "_username";
    private final String KEY_EMAILADDRESS = "_email";
    private final String KEY_PASSWORD2 = "_password";
    private final String KEY_URL = "_url";

    CreateDataBase helper;
    SQLiteDatabase database;
    Context context;

    public MyDatabaseHelper(Context context)
    {
        this.context = context;
    }

    public void updateUser(int id, String username, String password)
    {
        ContentValues cv = new ContentValues();
        cv.put(KEY_USERNAME, username);
        cv.put(KEY_PASSWORD, password);

        int records = database.update(TABLE_NAME, cv, KEY_ID+"=?", new String[]{id+""});
        if(records>0)
        {
            Toast.makeText(context, "User information updated", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context, "Failed to update user info", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteUser(int id)
    {
        int rows = database.delete(TABLE_NAME, KEY_ID+"=?", new String[]{id+""});
        if(rows>0)
        {
            Toast.makeText(context, "User deleted", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context, "User not deleted", Toast.LENGTH_SHORT).show();
        }
    }

    public void addUser(String username, String password)
    {
        ContentValues cv = new ContentValues();
        cv.put(KEY_USERNAME,username);
        cv.put(KEY_PASSWORD,password);

        long records = database.insert(TABLE_NAME, null, cv);
        if(records == -1)
        {
            Toast.makeText(context, "User info not inserted", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context, "Total "+records+" users added", Toast.LENGTH_SHORT).show();
        }
    }

    public void addPassword(String username, String email, String password, String url)
    {
        ContentValues cv = new ContentValues();
        cv.put(KEY_USERNAME2,username);
        cv.put(KEY_EMAILADDRESS,email);
        cv.put(KEY_PASSWORD2,password);
        cv.put(KEY_URL,url);

        long records = database.insert(TABLE_NAME2, null, cv);
        if(records == -1)
        {
            Toast.makeText(context, "Password info not inserted", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context, "Total "+records+" passwords added", Toast.LENGTH_SHORT).show();
        }
    }

    public User getUser(String username) {
        User user = null;
        Cursor cursor = null;
        int id_Index = cursor.getColumnIndex(KEY_ID);
        int username_Index = cursor.getColumnIndex(KEY_USERNAME);
        int password_Index = cursor.getColumnIndex(KEY_PASSWORD);
        try {
            cursor = database.query(TABLE_NAME, null, KEY_USERNAME + "=?", new String[]{username}, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                // User found, create a User object
                user = new User();
                user.setId(cursor.getInt(id_Index));
                user.setUsername(cursor.getString(username_Index));
                user.setPassword(cursor.getString(password_Index));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
            database.close();
        }
        return user;
    }

    public ArrayList<Password> readAllPasswords(String username)
    {
        ArrayList<Password> records = new ArrayList<>();
        Cursor cursor = database.query(TABLE_NAME, null, KEY_USERNAME2 + "=?", new String[]{username}, null, null, null);
        int id_Index = cursor.getColumnIndex(KEY_ID2);
        int username_Index = cursor.getColumnIndex(KEY_USERNAME2);
        int email_Index = cursor.getColumnIndex(KEY_EMAILADDRESS);
        int password_Index = cursor.getColumnIndex(KEY_PASSWORD2);

        if(cursor.moveToFirst())
        {
            do{
                Password p = new Password();

                p.setId(cursor.getInt(id_Index));
                p.setUsername(cursor.getString(username_Index));
                p.setEmail(cursor.getString(email_Index));
                p.setPassword(cursor.getString(password_Index));

                records.add(p);
            }while(cursor.moveToNext());
        }

        cursor.close();

        return records;
    }

    public ArrayList<User> readAllUsers()
    {
       ArrayList<User> records = new ArrayList<>();
       Cursor cursor = database.rawQuery("SELECT * FROM "+TABLE_NAME, null);
       int id_Index = cursor.getColumnIndex(KEY_ID);
       int username_Index = cursor.getColumnIndex(KEY_USERNAME);
       int password_Index = cursor.getColumnIndex(KEY_PASSWORD);

       if(cursor.moveToFirst())
       {
           do{
               User c = new User();

               c.setId(cursor.getInt(id_Index));
               c.setUsername(cursor.getString(username_Index));
               c.setPassword(cursor.getString(password_Index));

               records.add(c);
           }while(cursor.moveToNext());
       }

       cursor.close();

       return records;
    }

    public void open()
    {
        helper = new CreateDataBase(context, DATABASE_NAME, null, DATABASE_VERSION);
        database = helper.getWritableDatabase();
    }

    public void close()
    {
        database.close();
        helper.close();
    }

    private class CreateDataBase extends SQLiteOpenHelper
    {
        public CreateDataBase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String query = "CREATE TABLE " + TABLE_NAME + "(" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    KEY_USERNAME + " TEXT NOT NULL," +
                    KEY_PASSWORD + " TEXT NOT NULL" +
                    ");";
            db.execSQL(query);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // backup code here
            db.execSQL("DROP TABLE "+TABLE_NAME+" IF EXISTS");
            onCreate(db);
        }
    }
}