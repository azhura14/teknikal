package com.example.myapplication.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myapplication.model.Barang;
import com.example.myapplication.model.Users;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "teknikal";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_USERS = "users";
    public static final String KEY_ID = "userid";
    public static final String KEY_USER_NAME = "username";
    public static final String KEY_EMAIL = "useremail";
    public static final String KEY_PASSWORD = "userpassword";

    public static final String TABLE_BARANG = "barang";
    public static final String BRG_ID = "id";
    public static final String BRG_NAMA = "nama_barang";
    public static final String BRG_QTY = "qty";
    public static final String BRG_EXPDATE = "exp_date";
    public static final String BRG_HARGA = "harga";


    public static final String SQL_TABLE_USERS = " CREATE TABLE " + TABLE_USERS
            + " ( "
            + KEY_ID + " INTEGER PRIMARY KEY, "
            + KEY_USER_NAME + " TEXT, "
            + KEY_EMAIL + " TEXT, "
            + KEY_PASSWORD + " TEXT"
            + " ) ";

    public static final String SQL_TABLE_BARANG = " CREATE TABLE " + TABLE_BARANG
            + " ( "
            + BRG_ID + " INTEGER PRIMARY KEY, "
            + BRG_NAMA + " TEXT, "
            + BRG_QTY + " TEXT, "
            + BRG_EXPDATE + " DATE,"
            + BRG_HARGA + " INTEGER"
            + " ) ";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_TABLE_USERS);
        sqLiteDatabase.execSQL(SQL_TABLE_BARANG);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_USERS);
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_BARANG);
    }

    public void addUser(Users user) {

        //get writable database
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USER_NAME, user.userName);
        values.put(KEY_EMAIL, user.email);
        values.put(KEY_PASSWORD, user.password);
        long todo_id = db.insert(TABLE_USERS, null, values);
    }

    public void addBarang(Barang barang) {

        //get writable database
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BRG_NAMA, barang.namaBarang);
        values.put(BRG_QTY, barang.qty);
        values.put(BRG_EXPDATE, barang.expdate);
        values.put(BRG_HARGA, barang.harga);
        long todo_id = db.insert(TABLE_BARANG, null, values);
    }

    public void updateBarang(Barang barang) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BRG_NAMA, barang.getNamaBarang());
        values.put(BRG_QTY, barang.getQty());
        values.put(BRG_EXPDATE, barang.getExpdate());
        values.put(BRG_HARGA, barang.getHarga());
        // updating row
        db.update(TABLE_BARANG, values, BRG_ID + " = ?",
                new String[]{String.valueOf(barang.getId())});
        db.close();
    }

    public List<Barang> getAllBarang() {
        String[] columns = {
                BRG_ID,
                BRG_NAMA,
                BRG_QTY,
                BRG_EXPDATE,
                BRG_HARGA
        };
        // sorting orders
        String sortOrder =
                BRG_NAMA + " ASC";
        List<Barang> barangList = new ArrayList<Barang>();
        SQLiteDatabase db = this.getReadableDatabase();
        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query(TABLE_BARANG, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order
        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Barang barang = new Barang();
                barang.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(BRG_ID))));
                barang.setNamaBarang(cursor.getString(cursor.getColumnIndex(BRG_NAMA)));
                barang.setQty(cursor.getString(cursor.getColumnIndex(BRG_QTY)));
                barang.setExpdate(cursor.getString(cursor.getColumnIndex(BRG_EXPDATE)));
                barang.setHarga(cursor.getString(cursor.getColumnIndex(BRG_HARGA)));
                barangList.add(barang);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return user list
        return barangList;
    }

    public void deleteBarang(Barang barang) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete user record by id
        db.delete(TABLE_BARANG, BRG_ID + " = ?",
                new String[]{String.valueOf(barang.getId())});
        db.close();
    }

    public Users Authenticate(Users user) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS,// Selecting Table
                new String[]{KEY_ID, KEY_USER_NAME, KEY_EMAIL, KEY_PASSWORD},//Selecting columns want to query
                KEY_EMAIL + "=?",
                new String[]{user.email},//Where clause
                null, null, null);

        if (cursor != null && cursor.moveToFirst()&& cursor.getCount()>0) {
            Users user1 = new Users(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
            if (user.password.equalsIgnoreCase(user1.password)) {
                return user1;
            }
        }

        //if user password does not matches or there is no record with that email then return @false
        return null;
    }
}
