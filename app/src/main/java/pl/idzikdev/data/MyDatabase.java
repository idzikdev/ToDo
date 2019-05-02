package pl.idzikdev.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabase extends SQLiteOpenHelper {

    public MyDatabase(Context context) {
        super(context, "notes.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table note (id integer primary key autoincrement,"
                +"name text,"
                +"date text,"
                +"category text);"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean registerNote(String name,String date,String category){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("name",name);
        values.put("date",date);
        values.put("category",category);
        if (db.insertOrThrow("note",null,values)>-1)
            return true;
        return false;
    }

    public Cursor getAll(){
        String [] columns={"id","name","date","category"};
        SQLiteDatabase db=getReadableDatabase();
        return db.query("note",columns,null,null,null,null,null);
    }

    public void deleteAll(){
        SQLiteDatabase db=getReadableDatabase();
        db.delete("note",null,null);
    }
}
