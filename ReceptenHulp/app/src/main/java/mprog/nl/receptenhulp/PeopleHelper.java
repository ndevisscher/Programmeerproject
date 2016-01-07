package mprog.nl.receptenhulp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lol_l on 7-1-2016.
 */
public class PeopleHelper extends SQLiteOpenHelper{

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

    public static final String DATABASE_NAME = "People.db";

    public PeopleHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_PEOPLE = "CREATE TABLE " + Person.TABLE  + "("
                + Person.ID  + " INTEGER PRIMARY KEY AUTOINCREMENT , "
                + Person.FIRSTNAME + " TEXT ,"
                + Person.ADJ + " TEXT ,"
                + Person.LASTNAME + " TEXT ,"
                + Person.ALLERGIES + " TEXT )";
        db.execSQL(CREATE_TABLE_PEOPLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+Person.TABLE);
        onCreate(db);
    }

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
}
