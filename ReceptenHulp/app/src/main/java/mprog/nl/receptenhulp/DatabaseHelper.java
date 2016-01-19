package mprog.nl.receptenhulp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.sql.PreparedStatement;

/**
 * Created by Niek on 6-1-2016.
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

    //Info for the people table
    public class Group {
        // Labels table name
        public static final String TABLE = "group_table";

        // Labels Table Columns names
        public static final String ID = "id";
        public static final String GROUPNAME = "GROUPNAME";
        public static final String PEOPLELIST = "PEOPLELIST";

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

    //Person table SQL
    String CREATE_TABLE_PEOPLE = "CREATE TABLE " + Person.TABLE  + "("
            + Person.ID  + " INTEGER PRIMARY KEY AUTOINCREMENT , "
            + Person.FIRSTNAME + " TEXT ,"
            + Person.ADJ + " TEXT ,"
            + Person.LASTNAME + " TEXT ,"
            + Person.ALLERGIES + " TEXT )";

    //Group table SQL
    String CREATE_TABLE_GROUPS = "CREATE TABLE " + Group.TABLE  + "("
            + Group.ID  + " INTEGER PRIMARY KEY AUTOINCREMENT , "
            + Group.GROUPNAME + " TEXT ,"
            + Group.PEOPLELIST + " TEXT )";

    //Creating the tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_RECIPES);
        db.execSQL(CREATE_TABLE_PEOPLE);
        db.execSQL(CREATE_TABLE_GROUPS);
    }

    //For upgrading the tables
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Recipe.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + Person.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + Group.TABLE);
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

    //Adding a group to the database
    public boolean addGroup (String groupName){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Group.GROUPNAME, groupName);
        contentValues.put(Group.PEOPLELIST,"");

        long result = db.insert(Group.TABLE, null, contentValues);
        if (result == -1){
            return false;
        }
        else
            return true;
    }

    //Getting all groups from the database
    public Cursor showGroups(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor search = db.rawQuery("SELECT * FROM "+ Group.TABLE,null);
        return search;
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
        String[] etc = {ingredients,allergies};
        SQLiteDatabase db = this.getWritableDatabase();
        String searchQ = "SELECT * FROM "+ Recipe.TABLE + " WHERE " + Recipe.INGS + " LIKE ? AND "
                + Recipe.INGS + " LIKE ?";
        Cursor search = db.rawQuery(searchQ, etc);
        return search;
    }

    //Adding a person to the Person_table
    public boolean addPerson (String firstName, String adj, String lastName, String allergies){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Person.FIRSTNAME, firstName);
        contentValues.put(Person.ADJ, adj);
        contentValues.put(Person.LASTNAME, lastName);
        contentValues.put(Person.ALLERGIES, allergies);
        long result = db.insert(Person.TABLE, null, contentValues);
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

    //Get the ID of a person
    public Cursor getPersonID (String firstName,String adj, String lastName){
        String[] data = {firstName,adj,lastName};
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor search = db.rawQuery("SELECT ID FROM "+ Person.TABLE + " WHERE " + Person.FIRSTNAME + " =? AND "
                + Person.ADJ + " =? AND " + Person.LASTNAME + " =? ",data);
        return search;
    }

    //Check if a person is in the database, will return false it its not in the database
    public boolean personCheck (String firstName,String adj, String lastName){
        boolean check;
        String[] data = {firstName,adj,lastName};
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor search = db.rawQuery("SELECT ID FROM " + Person.TABLE + " WHERE " + Person.FIRSTNAME + " =? AND "
                + Person.ADJ + " =? AND " + Person.LASTNAME + " =? ", data);
        if(search!=null && search.getCount()>0) {
            check = true;
        }
        else{
            check = false;
        }
        return check;
    }

    public boolean groupCheck (String name){
        boolean check;
        String[] data = {name};
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor search = db.rawQuery("SELECT ID FROM " + Group.TABLE + " WHERE " + Group.GROUPNAME + " =? ", data);
        if(search!=null && search.getCount()>0) {
            check = true;
        }
        else{
            check = false;
        }
        return check;
    }

    //Getting the people from a specific group
    public Cursor getPeopleFromGroup(String groupName){
        String[] data = {groupName};
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor search = db.rawQuery("SELECT PEOPLELIST FROM "+ Group.TABLE + " WHERE " + Group.GROUPNAME + " =? ",data);
        return search;
    }

    //Adding people to a specific group
    public boolean addPeopleToGroup (String groupName,String idList){
        String data[] = {groupName};
        ContentValues cv = new ContentValues();
        cv.put("PEOPLELIST", idList);
        SQLiteDatabase db = this.getReadableDatabase();
        long result = db.update(Group.TABLE, cv, "GROUPNAME = '" + groupName + "'", null);
        if (result == -1){
            return false;
        }
        else
            return true;
    }

    //Get person by ID
    public Cursor getPerson (String ID){
        String[] data = {ID};
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor search = db.rawQuery("SELECT * FROM "+ Person.TABLE + " WHERE " + Person.ID + " =? ",data);
        return search;
    }

    //Get the allergie for a single person
    public Cursor getAllergie (String firstname,String adj, String lastname){
        String[] wanted = {firstname,adj,lastname};
        SQLiteDatabase db = this.getReadableDatabase();
        String SelectQ = " SELECT ALLERGIES FROM "+ Person.TABLE + " WHERE " + Person.FIRSTNAME + " =? AND "
                + Person.ADJ + " =? AND " + Person.LASTNAME + " =? ";
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
