package com.mamuncreates.bangladictionary;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class dataBaseHelper extends SQLiteAssetHelper {
    public dataBaseHelper(Context context) {
        super(context, "dictionary.db",  null, 1);
    }


    public Cursor getAllData(){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("select*from Dictionary",null);
        return cursor;
    }

    public Cursor searchword(String word){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursors = db.rawQuery("SELECT * FROM Dictionary WHERE word LIKE ?", new String[]{ word + "%"});

        return cursors;
    }
    public Cursor getWordDetails(String word) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM Dictionary WHERE word = ?", new String[]{word});
    }
}
