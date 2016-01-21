package mprog.nl.receptenhulp;

/**
 * Created by lol_l on 21-1-2016.
 */
public class Person {
    // Labels table name
        public static final String TABLE = "people_table";

        // Labels Table Columns names
        public static final String ID = "id";
        public static final String FIRSTNAME = "FIRSTNAME";
        public static final String ADJ = "ADJ";
        public static final String LASTNAME = "LASTNAME";
        public static final String ALLERGIES = "ALLERGIES";

    //Person table SQL
    public static final String CREATE_TABLE_PEOPLE = "CREATE TABLE " + Person.TABLE  + "("
            + Person.ID  + " INTEGER PRIMARY KEY AUTOINCREMENT , "
            + Person.FIRSTNAME + " TEXT ,"
            + Person.ADJ + " TEXT ,"
            + Person.LASTNAME + " TEXT ,"
            + Person.ALLERGIES + " TEXT )";

}
