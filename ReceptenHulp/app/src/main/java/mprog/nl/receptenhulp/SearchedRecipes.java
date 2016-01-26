package mprog.nl.receptenhulp;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class SearchedRecipes extends AppCompatActivity {

    //Declaring the various variables
    DatabaseHelper myDB;

    ArrayList<String> recipes;
    private ArrayAdapter<String> adapter;

    String recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searched_recipes);

        //Getting the search results from our previous activity
        Intent get = getIntent();
        recipes = get.getStringArrayListExtra("searchResults");

        //Initializing the database for this activity
        myDB = new DatabaseHelper(this);

        //Creating the listview with data, so we can select a Recipe
        ListView List = (ListView)findViewById(R.id.recipeList);
        adapter = new ArrayAdapter<String>(this,R.layout.output_layout,R.id.recipes,recipes);
        List.setAdapter(adapter);
        List.setOnItemClickListener(new itemClick());

        //This allows us to use the back button on the toolbar
        Toolbar object = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(object);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    //Getting the name of a Recipe by clicking the lisview so we can get the info for it
    class itemClick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            ViewGroup vg = (ViewGroup) view;
            TextView name = (TextView) vg.findViewById(R.id.recipes);
            recipe = name.getText().toString();
            String[] showString = {recipe};
            showRecipe(showString);
            Log.d("het recept", recipe);
        }
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

    //Returning to the homescreen function
    public void ReturnHome (View view) {
        Intent intent = new Intent(this, HomeScreen.class);
        startActivity(intent);
    }

    //Return to the search screen
    public void newSearch (View view) {
        Intent intent = new Intent(this, RecipeFinder.class);
        startActivity(intent);
    }

    //Function to show the info of a Recipe given that recipes name
    //I am using html to give some of the lines a different color and style, this is to make it
    //easier fo the user to read
    public void showRecipe(String[] name){
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.AlertDialogStyle));
        builder.setCancelable(true);
        builder.setTitle(Html.fromHtml("<font color='#1f1f14'><i>Receptinformatie</i></font>"));
        StringBuffer buffer = new StringBuffer();
        String test = "";
        Cursor search = myDB.getRecipeInfo(name);
        if (search.getCount() == 0) {
        } else{
            while(search.moveToNext()) {
                test = "<font color='#000000'><i>Naam van het recept:</i></font>" + "<br><br>"
                        + search.getString(1) + "<br><br>";
                //Splitting all the ingredients by so we can display them easily
                String[] ings = search.getString(3).split(",");
                test = test + "<font color='#000000'><i>Ingrediënten:</i></font>" + "<br>";
                for (String ing:ings){
                    if(ing != "") {
                        test = test + ing + "<br>";
                    }
                }
                test = test + "<br>" + "<font color='#000000'><i>bereidingswijze:</i></font>"+ "<br>"
                        + search.getString(2);
            }
        }
        builder.setMessage(Html.fromHtml(test));
        builder.show();
    }

}
