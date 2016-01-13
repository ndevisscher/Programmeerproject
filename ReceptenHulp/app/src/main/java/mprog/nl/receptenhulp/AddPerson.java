package mprog.nl.receptenhulp;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class AddPerson extends AppCompatActivity {

    //Variable declarations
    DatabaseHelper myDB;

    EditText FirstName;
    EditText Adj;
    EditText LastName;
    EditText allergLine;

    Button back;
    Button add;

    private ArrayList<String> ingredients;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_person);

        //Initializing the buttons and inputfields
        FirstName = (EditText) findViewById(R.id.firstName);
        Adj = (EditText) findViewById(R.id.adj);
        LastName = (EditText) findViewById(R.id.lastName);
        allergLine = (EditText) findViewById(R.id.allergieInput);

        //Setting up the ingredient listview to use for adding allergies
        ListView allergs = (ListView)findViewById(R.id.allergies);
        String[] items = {};
        ingredients = new ArrayList<>(Arrays.asList(items));
        adapter = new ArrayAdapter<String>(this,R.layout.list_item,R.id.ing,ingredients);
        allergs.setAdapter(adapter);

        //Initializing the database for this activity
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

    //Adding an ingredient to the allergies list
    public void addAllergie (View view){
        String allergieItem = allergLine.getText().toString();
        ingredients.add(allergieItem);
        adapter.notifyDataSetChanged();
        allergLine.setText("");
    }

    //Adding the person with all their information to the database
    public void addPerson(View view) {

        //Getting the data from the input
        String firstName = FirstName.getText().toString();
        String adj = Adj.getText().toString();
        String lastName = LastName.getText().toString();
        String allergies = "";

        //Sorting the allergies and preparing them for the database
        Collections.sort(ingredients);
        for (String ing : ingredients) {
            allergies = allergies + " " + ing;
        }

        //Adding the person to the database with the given info
        myDB.addPerson(firstName, adj, lastName, allergies);

        //Emptying the inputfields for other inputs
        FirstName.setText("");
        Adj.setText("");
        LastName.setText("");
        ingredients.clear();
        adapter.notifyDataSetChanged();
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
