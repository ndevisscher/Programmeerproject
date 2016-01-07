package mprog.nl.receptenhulp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lol_l on 6-1-2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public class Recipe {
        // Labels table name
        public static final String TABLE = "recipes_table";

        // Labels Table Columns names
        public static final String ID = "id";
        public static final String RcpName = "RcpName";
        public static final String INGS = "INGS";

        // property help us to keep data
        public int RCP_ID;
        public String name;
    }

    public static final String DATABASE_NAME = "Recipes.db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_RECIPES = "CREATE TABLE " + Recipe.TABLE  + "("
                + Recipe.ID  + " INTEGER PRIMARY KEY AUTOINCREMENT , "
                + Recipe.RcpName + " TEXT ,"
                + Recipe.INGS + " TEXT )";
        db.execSQL(CREATE_TABLE_RECIPES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+Recipe.TABLE);
        onCreate(db);
    }

    public boolean addRecipe (String recipeName){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Recipe.RcpName, recipeName);
        //contentValues.put(Recipe.INGS, ings);
        long result = db.insert(Recipe.TABLE,null,contentValues);
        if (result == -1){
            return false;
        }
        else
            return true;
    }

    public Cursor searchRecipe (){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor search = db.rawQuery("SELECT * FROM "+ Recipe.TABLE,null);
        return search;
    }

    public Cursor getRecipeId (String[] rcpName){
        SQLiteDatabase db = this.getReadableDatabase();
        String SelectQ = "SELECT * FROM "+ Recipe.TABLE + " WHERE " + Recipe.RcpName + "=?";
        Cursor search = db.rawQuery(SelectQ,rcpName);
        return search;
    }

    public Cursor getRecipe (String[] Ings){
        SQLiteDatabase db = this.getReadableDatabase();
        String SelectQ = "SELECT * FROM "+ Recipe.TABLE + " WHERE " + Recipe.RcpName + "=?";
        Cursor search = db.rawQuery(SelectQ,Ings);
        return search;
    }
}
