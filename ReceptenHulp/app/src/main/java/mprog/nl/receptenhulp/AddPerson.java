package mprog.nl.receptenhulp;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

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

    String allergie;

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
        allergs.setOnItemClickListener(new itemClick());

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

        //Getting the data from the input and setting it as "" if it had no input
        String firstName = FirstName.getText().toString();
        if (firstName.length() == 0){
            firstName = "";
        }
        String adj = Adj.getText().toString();
        if(adj.length() == 0){
            adj = "";
        }
        String lastName = LastName.getText().toString();
        if(lastName.length() == 0){
            lastName = "";
        }
        //Don't input anything if all fields are blank
        if(firstName == "" || lastName ==""){
            show("ongeldige invoer", "Voer een voor- en achternaam in");
            FirstName.setText("");
            Adj.setText("");
            LastName.setText("");
            return;
        }
        if(myDB.personCheck(firstName,adj,lastName) == true){
            show("ongeldige invoer", "Deze persoon staat al in de database");
            FirstName.setText("");
            Adj.setText("");
            LastName.setText("");
            return;
        }
        String allergies = "";

        //Sorting the allergies and preparing them for the database
        Collections.sort(ingredients);
        for (String ing : ingredients) {
            allergies = allergies + " " + ing;
        }

        //Adding the person to the database with the given info
        myDB.addPerson(firstName, adj, lastName, allergies);
        show("De volgende persoon is toegevoegd:", firstName + " " + adj + " " + lastName);
        //Emptying the inputfields for other inputs
        FirstName.setText("");
        Adj.setText("");
        LastName.setText("");
        ingredients.clear();
        adapter.notifyDataSetChanged();
    }

    //Getting the index of an allergie, so it can be removed from the list
    public int getIndexByname(String name) {
        for (String ing : ingredients) {
            if (ing.equals(name))
                return ingredients.indexOf(ing);
        }
        return -1;
    }

    //Removing allergies from our list
    class itemClick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            ViewGroup vg = (ViewGroup) view;
            TextView name = (TextView) vg.findViewById(R.id.ing);

            allergie = name.getText().toString();
            ingredients.remove(getIndexByname(allergie));
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
