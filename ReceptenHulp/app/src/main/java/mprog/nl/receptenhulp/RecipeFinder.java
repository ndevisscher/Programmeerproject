package mprog.nl.receptenhulp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RecipeFinder extends AppCompatActivity {

    //Declaring the various variables
    DatabaseHelper myDB;

    private ArrayList<String> ingredients;
    private ArrayList<String> peopleChoice;
    private ArrayList<String> selectedPeople;
    private ArrayAdapter<String> adapter;
    private ArrayAdapter<String> peopleAdapter;
    ArrayList<String> recipes;

    ListView people;

    EditText ingsIn;
    Button add;
    Button search;

    CheckBox checking;

    String item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_finder);

        //Initializing the buttons and inputfields
        add = (Button) findViewById(R.id.add);
        search = (Button) findViewById(R.id.search);
        ingsIn = (EditText) findViewById(R.id.ingsIn);

        //Setting up the ingredient listview to use for our search
        ListView ings = (ListView)findViewById(R.id.ingsList);
        String[] items = {};
        ingredients = new ArrayList<>(Arrays.asList(items));
        adapter = new ArrayAdapter<String>(this,R.layout.list_item,R.id.ing,ingredients);
        ings.setAdapter(adapter);
        ings.setOnItemClickListener(new itemClick());

        //Setting up the listview and checkbox for the people choice
        people = (ListView)findViewById(R.id.personList);
        people.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        peopleChoice = new ArrayList<>();
        peopleAdapter = new ArrayAdapter<String>(this,R.layout.person_layout,R.id.person,peopleChoice);
        people.setAdapter(peopleAdapter);

        //Here we add people to our selected list and show the user what they have selected with a checkbox
        selectedPeople = new ArrayList<>();
        people.setOnItemClickListener(new AdapterView.OnItemClickListener() {
              @Override
              public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                  String select = peopleChoice.get(position);
                  if(selectedPeople.contains(select)){
                      selectedPeople.remove(select);
                  }
                  else{
                      selectedPeople.add(select);
                  }
              }
        });


        //Initializing the database for this activity
        myDB = new DatabaseHelper(this);

        //This allows us to use the back button on the toolbar
        Toolbar object = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(object);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Add people from the database to the list to select them
        showPeople();
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

    //Adding an ingredient to our search
    public void addings (View view){
        String ingItem = ingsIn.getText().toString();
        ingredients.add(ingItem);
        adapter.notifyDataSetChanged();
        ingsIn.setText("");
    }

    //Searching for the recipes and passing the data to the next activity
    public void searchRecipe(View view){

        recipes = new ArrayList<>();
        //Getting the ingredients that we are searching for in the right variables
        String extra ="";
        Collections.sort(ingredients);
        for (String ing: ingredients){
            extra = extra + "%"+ing+"%";
        }
        String[] check = {extra};

        //Searching the database for recipes with the given ingredients
        Cursor search = myDB.searchOnIngredient(check);
        //End the function if no recipes are found
        if (search.getCount() == 0) {
            Log.d("geen data", "geen data");
            show("probeer het nogmaals","er zijn geen resultaten gevonden voor de zoekopdracht");
            return;
        } else
            Log.d("wel data", "wel data");

        //Adding the names of the recipes from our results to the array
        while(search.moveToNext()){
            recipes.add(search.getString(1));
        }

        //Passing the names of the recipes to the new activity
        Intent intent = new Intent(this,SearchedRecipes.class);
        intent.putStringArrayListExtra("searchResults", recipes);
        startActivity(intent);

    }

    //Searching for the recipes and passing the data to the next activity
    public void fullSearch(View view){

        recipes = new ArrayList<>();
        //Getting the ingredients that we are searching for in the right variables
        String ings ="";
        Collections.sort(ingredients);
        for (String ing: ingredients){
            ings = ings + "%"+ing+"%";
        }
        //String[] check = {extra};

        String allergs = "";
        
        //Searching the database for recipes with the given ingredients
        Cursor search = myDB.fullSearch(ings,allergs);
        //End the function if no recipes are found
        if (search.getCount() == 0) {
            Log.d("geen data", "geen data");
            show("probeer het nogmaals","er zijn geen resultaten gevonden voor de zoekopdracht");
            return;
        } else
            Log.d("wel data", "wel data");

        //Adding the names of the recipes from our results to the array
        while(search.moveToNext()){
            recipes.add(search.getString(1));
        }

        //Passing the names of the recipes to the new activity
        Intent intent = new Intent(this,SearchedRecipes.class);
        intent.putStringArrayListExtra("searchResults", recipes);
        startActivity(intent);

    }

    public void showPeople (){
        String data = "";
        Cursor search = myDB.showPeople();
        while (search.moveToNext()){
            data = search.getString(1) + " " + search.getString(2) + " " +search.getString(3);
            peopleChoice.add(data);
        }
        //peopleAdapter.notifyDataSetChanged();
    }

    //Test om recepten op ingredient te vinden
    public void test(View view){
        String extra ="";
        Collections.sort(ingredients);
        for (String ing: ingredients){
            extra = extra + "%"+ing+"%";
        }
        String[] check = {extra};

        Cursor search = myDB.searchOnIngredient(check);
        if (search.getCount() == 0) {
            Log.d("geen data", "geen data");
        } else
            Log.d("wel data", "wel data");
        StringBuffer buffer = new StringBuffer();
        while(search.moveToNext()){
            buffer.append("ID: "+search.getString(0)+"\n");
            buffer.append("Name: "+search.getString(1)+"\n");
            buffer.append("Descript: "+search.getString(2)+"\n");
            buffer.append("Ings: "+search.getString(3)+"\n");
        }
        //show("data", buffer.toString());
        show("data",buffer.toString());
    }

    //This will show the message when the search has no results
    public void show(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    //Getting the index from the ingredient listView so we can delete them from our search
    public int getIndexByname(String name)
    {
        for(String ing :ingredients)
        {
            if(ing.equals(name))
                return ingredients.indexOf(ing);
        }
        return -1;
    }

    //Deleting ingredients from our search
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


}
