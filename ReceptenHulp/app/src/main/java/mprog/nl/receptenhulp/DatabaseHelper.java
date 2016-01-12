package mprog.nl.receptenhulp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by lol_l on 6-1-2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    //Info for the recipe table
    public class Recipe {
        // Labels table name
        public static final String TABLE = "recipes_table";

        // Labels Table Columns names
        public static final String ID = "id";
        public static final String RcpName = "RcpName";
        public static final String Descript = "Descript";
        public static final String INGS = "Ings";
    }

    //Info for the ingredients table
    public class Ingredients {
        // Labels table name
        public static final String TABLE = "ingredients_table";

        // Labels Table Columns names
        public static final String ID = "id";
        public static final String INGREDIENT = "INGREDIENT";
        public static final String RCPID = "RCPid";
    }

    //Info for the people table
    public class Person {
        // Labels table name
        public static final String TABLE = "people_table";

        // Labels Table Columns names
        public static final String ID = "id";
        public static final String FIRSTNAME = "FIRSTNAME";
        public static final String ADJ = "ADJ";
        public static final String LASTNAME = "LASTNAME";
        public static final String ALLERGIES = "ALLERGIES";

    }

    public static final String DATABASE_NAME = "Recipes.db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    //Database creation SQL's
    //Recipe table SQL
    String CREATE_TABLE_RECIPES = "CREATE TABLE " + Recipe.TABLE  + "("
            + Recipe.ID  + " INTEGER PRIMARY KEY AUTOINCREMENT , "
            + Recipe.RcpName + " TEXT ,"
            + Recipe.Descript + " TEXT,"
            + Recipe.INGS + " TEXT )";

    //Ingredient table SQL
    String CREATE_TABLE_INGREDIENTS = "CREATE TABLE " + Ingredients.TABLE  + "("
            + Ingredients.ID  + " INTEGER PRIMARY KEY AUTOINCREMENT , "
            + Ingredients.RCPID + " TEXT ,"
            + Ingredients.INGREDIENT + " TEXT )";

    //Person table SQL
    String CREATE_TABLE_PEOPLE = "CREATE TABLE " + Person.TABLE  + "("
            + Person.ID  + " INTEGER PRIMARY KEY AUTOINCREMENT , "
            + Person.FIRSTNAME + " TEXT ,"
            + Person.ADJ + " TEXT ,"
            + Person.LASTNAME + " TEXT ,"
            + Person.ALLERGIES + " TEXT )";

    //Creating the tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_RECIPES);
        db.execSQL(CREATE_TABLE_INGREDIENTS);
        db.execSQL(CREATE_TABLE_PEOPLE);
    }

    //For upgrading the tables
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Recipe.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + Ingredients.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + Person.TABLE);
        onCreate(db);
    }

    //Adding a recipe to the recipe_table
    public boolean addRecipe (String recipeName, String descript, String ings){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Recipe.RcpName, recipeName);
        contentValues.put(Recipe.Descript, descript);
        contentValues.put(Recipe.INGS, ings);

        long result = db.insert(Recipe.TABLE, null, contentValues);
        if (result == -1){
            return false;
        }
        else
            return true;
    }

    //Search for recipes given an ingredient
    public Cursor searchOnIngredient (String ingredient){
        String[] input = {ingredient};
        SQLiteDatabase db = this.getWritableDatabase();
        String searchQ = "SELECT * FROM "+ Recipe.TABLE + " WHERE " + Recipe.INGS + " LIKE ?";
        Cursor search = db.rawQuery(searchQ, input);
        return search;
    }
    //Full search with the allergies and ingredients
    public Cursor fullSearch (String ingredients, String allergies){
        String[] etc = {allergies,ingredients};
        SQLiteDatabase db = this.getWritableDatabase();
        String searchQ = "SELECT * FROM "+ Recipe.TABLE + " WHERE " + Recipe.INGS + " LIKE ? AND "
                + Recipe.INGS + " NOT LIKE ?";
        Cursor search = db.rawQuery(searchQ,etc);
        return search;
    }

    //Getting all recipes from the recipe_table
    public Cursor searchRecipe (){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor search = db.rawQuery("SELECT * FROM "+ Recipe.TABLE,null);
        return search;
    }

    //Adding ingredients to the ingredients_table
    public boolean addIngredients (String ingredient, String rcpId){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Ingredients.INGREDIENT, ingredient);
        contentValues.put(Ingredients.RCPID, rcpId);
        long result = db.insert(Ingredients.TABLE, null, contentValues);
        if (result == -1){
            return false;
        }
        else
            return true;
    }

    //Adding a person to the Person_table
    public boolean addPerson (String firstName, String adj, String lastName, String allergies){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Person.FIRSTNAME, firstName);
        contentValues.put(Person.ADJ, adj);
        contentValues.put(Person.LASTNAME, lastName);
        contentValues.put(Person.ALLERGIES, allergies);
        long result = db.insert(Person.TABLE,null,contentValues);
        if (result == -1){
            return false;
        }
        else
            return true;
    }

    //Getting all People from the person_table
    public Cursor showPeople (){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor search = db.rawQuery("SELECT * FROM "+ Person.TABLE,null);
        return search;
    }

    //Get the allergie for a single person
    public Cursor getAllergie (String firstname,String adj, String lastname){
        String[] wanted = {firstname,adj,lastname};
        SQLiteDatabase db = this.getReadableDatabase();
        String SelectQ = " SELECT ALLERGIES FROM "+ Person.TABLE + " WHERE " + Person.FIRSTNAME + " =? AND "
                + Person.ADJ + " =? AND " + Person.LASTNAME + " =? ";
        Log.d("query", SelectQ);
        Cursor search = db.rawQuery(SelectQ,wanted);
        return search;
    }

    //Return the data from a recipe
    public Cursor getRecipeInfo (String[] rcpName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String SelectQ = "SELECT * FROM " + Recipe.TABLE + " WHERE " + Recipe.RcpName + "=?";
        Cursor search = db.rawQuery(SelectQ, rcpName);
        return search;
    }
}
