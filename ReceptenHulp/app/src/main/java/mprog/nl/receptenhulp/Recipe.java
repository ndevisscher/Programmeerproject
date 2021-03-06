package mprog.nl.receptenhulp;

/**
 * Created by Niek de Visscher (10667474)
 */
public class Recipe {
    DatabaseHelper myDB;

    // Labels table name
    public static final String TABLE = "recipes_table";

    // Labels Table Columns names
    public static final String ID = "id";
    public static final String RcpName = "RcpName";
    public static final String Descript = "Descript";
    public static final String INGS = "Ings";

    //Recipe table SQL
    public static final String CREATE_TABLE_RECIPES = "CREATE TABLE " + Recipe.TABLE  + "("
            + Recipe.ID  + " INTEGER PRIMARY KEY AUTOINCREMENT , "
            + Recipe.RcpName + " TEXT ,"
            + Recipe.Descript + " TEXT,"
            + Recipe.INGS + " TEXT )";


}

