package com.example.lenovo.dbapp2;

import android.content.ContentValues;
import android.content.Context;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class StudentDbHelper extends SQLiteOpenHelper {
    StudentDbHelper context=this;

    public static  final int DATABASE_VERSION=1;
    public static  final String DATABASE_NAME="university.db";
    public static final String TABLE_NAME = "student";

    public static final String COLUMN_ROLLNO = "student_rollno";
    public static final String COLUMN_NAME = "student_name";

    public String sql1= "CREATE TABLE " + TABLE_NAME + "(" +
            COLUMN_ROLLNO + " TEXT  , " +
             COLUMN_NAME + " TEXT " +
            ")" ;





    public StudentDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
        // ctx=context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(sql1);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME);

    }

    public void insertData(String roll,String name)
    {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues values=new ContentValues();

        values.put(COLUMN_ROLLNO,roll);

        values.put(COLUMN_NAME,name);

        sqLiteDatabase.insert(TABLE_NAME,null,values);

    }

    public Cursor retrieveData()
    {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        Cursor c=sqLiteDatabase.rawQuery("select * from " + TABLE_NAME, null);
        return c;
    }

    public int deleteRecord(String rollno)
    {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        String where_clause = "student_rollno= ?";
// Specify arguments in placeholder order.

        int rowdeleted= sqLiteDatabase.delete(TABLE_NAME,where_clause, new String[]{rollno});
        return  rowdeleted;
    }

    public int updateRecord(String rollno,String name)
    {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        String where_clause = "student_rollno= ?";
        ContentValues values=new ContentValues();
        //values.put(COLUMN_ID,id);
        values.put(COLUMN_ROLLNO,rollno);
        values.put(COLUMN_NAME,name);
       // values.put(COLUMN_CURRENTSEM,sem);
        int no_rows= sqLiteDatabase.update(TABLE_NAME,values,where_clause, new String[]{rollno});
        return no_rows;
    }






}
