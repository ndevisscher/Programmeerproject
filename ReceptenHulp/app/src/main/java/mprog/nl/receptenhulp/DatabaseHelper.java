package mprog.nl.receptenhulp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Niek on 6-1-2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Recipes.db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    //Creating the tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Recipe.CREATE_TABLE_RECIPES);
        db.execSQL(Person.CREATE_TABLE_PEOPLE);
        db.execSQL(Group.CREATE_TABLE_GROUPS);
    }

    //For upgrading the tables
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Recipe.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + Person.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + Group.TABLE);
        onCreate(db);
    }

    //Adding a Recipe to the recipe_table
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

    //Return the data from a Recipe
    public Cursor getRecipeInfo (String[] rcpName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String SelectQ = "SELECT * FROM " + Recipe.TABLE + " WHERE " + Recipe.RcpName + "=?";
        Cursor search = db.rawQuery(SelectQ, rcpName);
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

    //Getting all groups from the database
    public Cursor showGroups(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor search = db.rawQuery("SELECT * FROM "+ Group.TABLE,null);
        return search;
    }

    //Adding a group to the database under group_table
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

    //Check if the group you are trying to create already exists
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
        Cursor search = db.rawQuery("SELECT PEOPLELIST FROM " + Group.TABLE + " WHERE " + Group.GROUPNAME + " =? ", data);
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
        Cursor search = db.rawQuery("SELECT * FROM " + Person.TABLE + " WHERE " + Person.ID + " =? ", data);
        return search;
    }

    //Adding a person to database in the Person_table
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

    //Getting all People from the database
    public Cursor showPeople (){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor search = db.rawQuery("SELECT * FROM "+ Person.TABLE,null);
        return search;
    }

    //Get the ID of a person
    public Cursor getPersonID (String firstName,String adj, String lastName){
        String[] data = {firstName,adj,lastName};
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor search = db.rawQuery("SELECT ID FROM " + Person.TABLE + " WHERE " + Person.FIRSTNAME + " =? AND "
                + Person.ADJ + " =? AND " + Person.LASTNAME + " =? ", data);
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

    //Get the allergie for a single person
    public Cursor getAllergie (String firstname,String adj, String lastname){
        String[] wanted = {firstname,adj,lastname};
        SQLiteDatabase db = this.getReadableDatabase();
        String SelectQ = " SELECT ALLERGIES FROM "+ Person.TABLE + " WHERE " + Person.FIRSTNAME + " =? AND "
                + Person.ADJ + " =? AND " + Person.LASTNAME + " =? ";
        Cursor search = db.rawQuery(SelectQ,wanted);
        return search;
    }
}
