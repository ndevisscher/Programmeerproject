package mprog.nl.receptenhulp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class AddRecipe extends AppCompatActivity {

    //Declaring the various variables
    DatabaseHelper myDB;

    private ArrayList<String> ingredients;
    private ArrayAdapter<String> adapter;

    TextView idView;
    EditText recipeLine;
    EditText ingLine;
    Button add;
    Button search;

    String descript = "";
    String ings = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        //Initializing the buttons and inputfields
        idView = (TextView) findViewById(R.id.RecipeName);
        recipeLine = (EditText) findViewById(R.id.addRecipe);
        ingLine = (EditText) findViewById(R.id.searchRecipe);
        add = (Button) findViewById(R.id.add);
        search = (Button) findViewById(R.id.adding);

        //Setting up the ingredient listview to use for adding ingredients
        ListView ings = (ListView)findViewById(R.id.ingredients);
        String[] items = {};
        ingredients = new ArrayList<>(Arrays.asList(items));
        adapter = new ArrayAdapter<String>(this,R.layout.list_item,R.id.ing,ingredients);
        ings.setAdapter(adapter);

        //Initializing the database
        myDB = new DatabaseHelper(this);

        //This allows us to use the back button on the toolbar
        Toolbar object = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(object);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    //Using the back button from the toolbar to go to the previous screen
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
        {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    //Adding the recipe to the database
    public void addRecipe(View view) {

        //Getting the name of the recipe
        String recipeName = recipeLine.getText().toString();
        String[] test = {recipeName};

        //Sorting the ingredients
        Collections.sort(ingredients);
        for (String ing : ingredients) {
            ings = ings + " " + ing;
        }

        //Adding the recipe to the database with the given info
        myDB.addRecipe(recipeName, descript,ings);

        addIngredients(recipeName);
        //Clearing the datafields
        recipeLine.setText("");
        ingredients.clear();
        ings = "";
        adapter.notifyDataSetChanged();
    }

    //Adding the ingredients to the database
    public void addIngredients (String rcpName){

        int ID = 0;

        String[] test = {rcpName};
        Cursor get = myDB.getRecipe(test);
        //ID = Integer.parseInt(get.getString(0));

        for (String ing : ingredients) {
            myDB.addIngredients(ing, rcpName);
        }
    }

    //Adding ingredients to the list to add to the recipe
    public void addings (View view){
        String testitem = ingLine.getText().toString();
        ingredients.add(testitem);
        adapter.notifyDataSetChanged();
        ingLine.setText("");
    }

    //A popup so you can add the description for the recipe
    private void descriptInput() {

        AlertDialog.Builder descriptBuilder = new AlertDialog.Builder(this);
        descriptBuilder.setTitle("bereidingswijze");
        descriptBuilder.setMessage("Voer hier de bereidingswijze van het recept in.");
        final EditText descInput = new EditText(this);
        descInput.setSingleLine();
        descInput.setText("");
        descriptBuilder.setView(descInput);
        descriptBuilder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        descript = descInput.getText().toString();
                    }
                });

        AlertDialog descript = descriptBuilder.create();
        descript.show();
    }

    //Actually dispalying the popup for the description
    public void showDescriptInput (View view){
        descriptInput();
    }


}

