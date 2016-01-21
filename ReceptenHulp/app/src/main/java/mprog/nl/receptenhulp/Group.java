package mprog.nl.receptenhulp;

/**
 * Created by lol_l on 21-1-2016.
 */
public class Group {
    // Labels table name
        public static final String TABLE = "group_table";

        // Labels Table Columns names
        public static final String ID = "id";
        public static final String GROUPNAME = "GROUPNAME";
        public static final String PEOPLELIST = "PEOPLELIST";

    //Group table SQL
    public static final String CREATE_TABLE_GROUPS = "CREATE TABLE " + Group.TABLE  + "("
            + Group.ID  + " INTEGER PRIMARY KEY AUTOINCREMENT , "
            + Group.GROUPNAME + " TEXT ,"
            + Group.PEOPLELIST + " TEXT )";
}
