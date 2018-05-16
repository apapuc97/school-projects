package com.example.chris.orar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class DataBaseTest extends AppCompatActivity {
    public static SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TextView    textView;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);
       /* db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        createTable();
        COUNT = getCount();
        textView = (TextView)findViewById(R.id.myBigCircle);
        textView.setText(COUNT + "");*/
/*
        setVisibility(0, View.GONE);
        setProcent(1, 45);
        setName(1, "Papuc Alexandru");*/
    }

    private static final String TABLE_NAME = " touch_count";
    public static final String  COLUMN_NAME_TITLE = "count";
    public static int   COUNT = 0;
    private static final String ID = "id";
    private static final String COMMA_SEP = ",";


    public void createTable() {

        db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        /*db.execSQL("CREATE TABLE IF NOT EXISTS" + TABLE_NAME + " ("
                + ID + INT_TYPE + " NOT NULL, "
                + COLUMN_NAME_TITLE + INT_TYPE + ")");*/
        //addData();
    }

    public static int getCount() {
        int count = 0;
        Cursor query = db.rawQuery("SELECT * FROM users;", null);
        if(query.moveToFirst()){
            count = query.getInt(1);
        }
        query.close();
        return count;
    }

    public static void setData(int count) {

    }

    public static void addData() {
        db.execSQL("INSERT INTO" + TABLE_NAME + " VALUES (1, 0);");
    }


}

