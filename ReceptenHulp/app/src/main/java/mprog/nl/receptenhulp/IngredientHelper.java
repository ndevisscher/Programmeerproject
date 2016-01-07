package mprog.nl.receptenhulp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lol_l on 7-1-2016.
 */
public class IngredientHelper extends SQLiteOpenHelper{

    public class ingredients {
        // Labels table name
        public static final String TABLE = "ingredients_table";

        // Labels Table Columns names
        public static final String ID = "id";
        public static final String INGREDIENT = "INGREDIENT";
        public static final String RCPID = "RCPid";

        // property help us to keep data
        public int RCP_ID;
        public String ingredient;
    }

    public static final String DATABASE_NAME = "Ingredients.db";

    public IngredientHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_INGREDIENTS = "CREATE TABLE " + ingredients.TABLE  + "("
                + ingredients.ID  + " INTEGER PRIMARY KEY AUTOINCREMENT , "
                + ingredients.RCPID + " TEXT ,"
                + ingredients.INGREDIENT + " TEXT )";
        db.execSQL(CREATE_TABLE_INGREDIENTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ingredients.TABLE);
        onCreate(db);
    }

    public boolean addIngredients (String ingredient, String rcpId){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ingredients.INGREDIENT, ingredient);
        contentValues.put(ingredients.RCPID, rcpId);
        long result = db.insert(ingredients.TABLE,null,contentValues);
        if (result == -1){
            return false;
        }
        else
            return true;
    }

}
