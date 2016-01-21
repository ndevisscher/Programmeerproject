package mprog.nl.receptenhulp;

import android.app.AlertDialog;
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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class RecipeFinder extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    //Declaring the various variables
    DatabaseHelper myDB;

    private ArrayList<String> ingredients;
    private ArrayList<String> peopleChoice;
    private ArrayList<String> selectedPeople;

    //Nog niet geimplementeerd
    private ArrayList<String> groupChoice;
    private ArrayList<String> selectedGroups;
    private ArrayAdapter<String> groupAdapter;

    private ArrayAdapter<String> adapter;
    private ArrayAdapter<String> peopleAdapter;

    ArrayList<String> recipes;
    ArrayList<String> allergies;

    ListView people;

    EditText ingsIn;

    String item;
    String firstname;
    String adj;
    String lastname;

    Switch mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_finder);

        //Initializing the buttons and inputfields
        ingsIn = (EditText) findViewById(R.id.ingsIn);

        mode = (Switch) findViewById(R.id.modeSwitch);

        //Setting up the ingredient listview to use for our search
        ListView ings = (ListView) findViewById(R.id.ingsList);
        String[] items = {};
        ingredients = new ArrayList<>(Arrays.asList(items));
        adapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.ing, ingredients);
        ings.setAdapter(adapter);
        ings.setOnItemClickListener(new itemClick());

        //Setting up the listview and checkbox for the people and group choice
        people = (ListView) findViewById(R.id.personList);
        people.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        peopleChoice = new ArrayList<>();
        groupChoice = new ArrayList<>();
        peopleAdapter = new ArrayAdapter<String>(this, R.layout.person_layout, R.id.person, peopleChoice);
        groupAdapter = new ArrayAdapter<String>(this,R.layout.person_layout,R.id.person,groupChoice);
        people.setAdapter(peopleAdapter);
        selectedGroups = new ArrayList<>();
        selectedPeople = new ArrayList<>();

        //This switches between selecting people or groups for our recipefinder
        mode.setOnCheckedChangeListener(this);

        //We initialize the list the first time, so we don't get an empty listview
        people.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String select = peopleChoice.get(position);
                if (selectedPeople.contains(select)) {
                    selectedPeople.remove(select);
                } else {
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
        showGroups();
    }


    //Using the back button from the toolbar to go to the previous screen
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    //Adding an ingredient to our search
    public void addings(View view) {
        String ingItem = ingsIn.getText().toString();
        ingredients.add(ingItem);
        adapter.notifyDataSetChanged();
        ingsIn.setText("");
    }


    //Searching for the recipes and passing the data to the next activity
    public void fullSearch(View view) {
        String[] items = {};
        //New arraylist for the recipes we find, we pass this to the next activity
        recipes = new ArrayList<>(Arrays.asList(items));
        //Getting the ingredients that we are searching for in the right variables
        String ings = "";
        Collections.sort(ingredients);
        for (String ing : ingredients) {
            ings = ings + "%" + ing + "%";
        }
        //The allergies from the people in the app will be stored in these variables
        allergies = new ArrayList<>();
        String allergs = "";
        //Adding the allergies from the people in the selected groups to the list
        if (!selectedGroups.isEmpty()) {
            for (String name : selectedGroups) {
                String[] listSplit = {};
                Cursor getPeople = myDB.getPeopleFromGroup(name);
                while (getPeople.moveToNext()) {
                    listSplit = getPeople.getString(0).split(" ");
                }
                for (String id : listSplit) {
                    Cursor search = myDB.getPerson(id);
                    while (search.moveToNext()) {
                        firstname = search.getString(1);
                        adj = search.getString(2);
                        lastname = search.getString(3);
                        Cursor getAllergies = myDB.getAllergie(firstname, adj, lastname);
                        while (getAllergies.moveToNext()) {
                            //Splitting the allergies, because we save them as 1 long string with spaces
                            String[] allergSplit = getAllergies.getString(0).split(" ");
                            //Adding the allergies to our array, so we can use them for our search later
                            for (String item : allergSplit) {
                                if (allergies.contains(item)) {
                                    //do nothing, because the item is already in the list
                                } else {
                                    allergies.add(item);
                                }
                            }
                        }
                    }
                }
            }
        }
        //Getting the info from the selected people from the listview
        //Every person is selected individually and we split the name so we can search the database
        for (String Person : selectedPeople) {
            String[] split = Person.split(" ");
            firstname = split[0];
            adj = split[1];
            lastname = split[2];
            //Searching the database for the allergies of 1 person
            Cursor getAllergies = myDB.getAllergie(firstname, adj, lastname);
            while (getAllergies.moveToNext()) {
                //Splitting the allergies, because we save them as 1 long string with spaces
                String[] allergSplit = getAllergies.getString(0).split(" ");
                //Adding the allergies to our array, so we can use them for our search later
                for (String item : allergSplit) {
                    if (allergies.contains(item)) {
                        //do nothing, because the item is already in the list
                    } else {
                        allergies.add(item);
                    }
                }
            }
        }
        //Sorting the allergies alphabetically and removing the first value if needed, because it
        //will be an empty variable
        Collections.sort(allergies);
        if (allergies.size() > 1) {
            allergies.remove(0);
        }

        //Searching the database for recipes with the given ingredients
        Cursor search;
        Cursor removal;
        //If allergies are given search with both allergies and ingredients
        if (!allergies.isEmpty()) {
            //First we get all the recipes with the given ingredients
            search = myDB.searchOnIngredient(ings);
            //We end the function if there are no results
            if (search.getCount() == 0) {
                show("probeer het nogmaals", "er zijn geen resultaten gevonden voor de zoekopdracht");
                return;
            }
            //Adding the Recipe names to the array
            while (search.moveToNext()) {
                recipes.add(search.getString(1));
            }
            //Here we search the database for the recipes with the allergies, if we find a Recipe
            //given the ingredients and allergie we remove it from the array
            for (String allerg : allergies) {
                allergs = "%"+allerg+"%";
                removal = myDB.fullSearch(ings, allergs);
                while (removal.moveToNext()) {
                    if (recipes.contains(removal.getString(1))) {
                        recipes.remove(removal.getString(1));
                    }
                }
            }
            if (recipes.isEmpty()){
                show("probeer het nogmaals", "er zijn geen resultaten gevonden voor de zoekopdracht");
                return;
            }
        }
        //If no allergies are given, just search on ingredients
        else {
            search = myDB.searchOnIngredient(ings);
        }
        //End the function if no recipes are found
        if (search.getCount() == 0) {
            show("probeer het nogmaals", "er zijn geen resultaten gevonden voor de zoekopdracht");
            return;
        }
        //Adding the names of the recipes from our results to the array
        while (search.moveToNext()) {
            recipes.add(search.getString(1));
        }

        //Passing the names of the recipes to the new activity
        Intent intent = new Intent(this, SearchedRecipes.class);
        intent.putStringArrayListExtra("searchResults", recipes);
        startActivity(intent);

    }

    //Shows the people from the database in the list to be selected
    public void showPeople() {
        String data = "";
        Cursor search = myDB.showPeople();
        while (search.moveToNext()) {
            data = search.getString(1) + " " + search.getString(2) + " " + search.getString(3);
            peopleChoice.add(data);
        }
    }

    //Shows the groups from the database
    public void showGroups() {
        String data = "";
        Cursor search = myDB.showGroups();
        while (search.moveToNext()) {
            data = search.getString(1);
            groupChoice.add(data);
        }
    }

    //This will show the message when the search has no results
    public void show(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    //Getting the index from the ingredient listView so we can delete them from our search
    public int getIndexByname(String name) {
        for (String ing : ingredients) {
            if (ing.equals(name))
                return ingredients.indexOf(ing);
        }
        return -1;
    }

    //This handles the switch, so we can swap between selecting people and groups when we are searching
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            mode.setText("groepen");
            people.setAdapter(groupAdapter);
            groupAdapter.notifyDataSetChanged();
            people.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String select = groupChoice.get(position);
                    if (selectedGroups.contains(select)) {
                        selectedGroups.remove(select);
                    } else {
                        selectedGroups.add(select);
                    }
                }
            });
        } else {
            mode.setText("mensen");
            people.setAdapter(peopleAdapter);
            peopleAdapter.notifyDataSetChanged();
            people.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String select = peopleChoice.get(position);
                    if (selectedPeople.contains(select)) {
                        selectedPeople.remove(select);
                    } else {
                        selectedPeople.add(select);
                    }
                }
            });
        }
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