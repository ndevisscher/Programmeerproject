package mprog.nl.receptenhulp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lol_l on 6-1-2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Recipes.db";
    public static final String TABLE_NAME = "recipes_table";
    public static final String ID = "ID";
    public static final String COLUMN1 = "RECIPE";
    //public static final String INGREDIËNTS = "ingrediënts";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,RECIPE TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean addRecipe (String recipeName){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN1,recipeName);
        long result = db.insert(TABLE_NAME,null,contentValues);
        if (result == -1){
            return false;
        }
        else
            return true;
    }
}
