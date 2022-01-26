package com.example.projet;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;


public class DBscore extends SQLiteOpenHelper {

    public static final String DBNAME = "Score.db";

    public DBscore(Context context) {
        super(context, "Score.db",null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("create Table hist(ID INTEGER PRIMARY KEY AUTOINCREMENT, Susername TEXT, point TEXT)");
    }



    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("drop Table if exists hist");
    }

    public Boolean insertData(String Susername, String point){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("Susername", Susername);
        contentValues.put("point", point);
        long result = MyDB.insert("hist", null, contentValues);
        if(result==-1)
            return false;
        else
            return true;
    }
    public Cursor viewDatasorted()
    {
        SQLiteDatabase MyDB=this.getReadableDatabase();
        String query="Select Susername , point FROM hist";
        Cursor cursor =MyDB.rawQuery(query,null);
        return cursor;
    }
    public void removeAll()
    {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("hist", null, null);
        db.close();


    }

}
