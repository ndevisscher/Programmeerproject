package mprog.nl.receptenhulp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by Niek de Visscher (10667474)
 */
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
    String preperation = "";
    String item;

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
        ings.setOnItemClickListener(new itemClick());

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

    //Adding the Recipe to the database
    public void addRecipe(View view) {

        //Getting the name of the Recipe
        String recipeName = recipeLine.getText().toString();

        //Sorting the ingredients
        Collections.sort(ingredients);
        for (String ing : ingredients) {
            ings = ings + "," + ing;
        }

        //Adding the Recipe to the database with the given info
        myDB.addRecipe(recipeName, descript,ings);
        show("Het volgende recept is toegevoegd: \n", recipeName);

        //Clearing the datafields
        recipeLine.setText("");
        ingredients.clear();
        ings = "";
        adapter.notifyDataSetChanged();
    }

    //Adding ingredients to the list to add to the Recipe
    public void addings (View view){
        String ingName = ingLine.getText().toString();
        ingredients.add(ingName);
        adapter.notifyDataSetChanged();
        ingLine.setText("");
    }

    //A popup so you can add the description for the Recipe
    private void descriptInput() {
        AlertDialog.Builder descriptBuilder = new AlertDialog.Builder(this);
        descriptBuilder.setTitle("bereidingswijze");
        descriptBuilder.setMessage("Voer hier de bereidingswijze van het recept in.");
        final EditText descInput = new EditText(this);
        descInput.setText(preperation);
        descriptBuilder.setView(descInput);
        descriptBuilder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        descript = descInput.getText().toString();
                        preperation = descript;
                    }
                });
        //Pop-up for the description input
        AlertDialog descript = descriptBuilder.create();
        descript.show();
    }

    //Actually dispalying the popup for the description when te button is pressed
    public void showDescriptInput (View view){
        descriptInput();
    }

    //Getting the index of an ingredient
    public int getIndexByname(String name) {
        for (String ing : ingredients) {
            if (ing.equals(name))
                return ingredients.indexOf(ing);
        }
        return -1;
    }

    //Deleting ingredients from the recipe and listview by index when you click them
    class itemClick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            ViewGroup vg = (ViewGroup) view;
            TextView name = (TextView) vg.findViewById(R.id.ing);

            item = name.getText().toString();
            ingredients.remove(getIndexByname(item));
            adapter.notifyDataSetChanged();
        }
    }

    //Used for showing error messages
    public void show(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

}

