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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RecipeFinder extends AppCompatActivity {

    DatabaseHelper myDB;

    private ArrayList<String> ingredients;
    private ArrayAdapter<String> adapter;
    ArrayList<String> recipes;

    EditText ingsIn;
    Button add;
    Button search;

    //
    EditText tests;
    String test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_finder);

        ingsIn = (EditText) findViewById(R.id.ingsIn);

        //
        tests = (EditText) findViewById(R.id.test);

        add = (Button) findViewById(R.id.add);
        search = (Button) findViewById(R.id.search);

        //Setting up the ingredient listview to use for our search
        ListView ings = (ListView)findViewById(R.id.ingsList);
        String[] items = {};
        ingredients = new ArrayList<>(Arrays.asList(items));
        adapter = new ArrayAdapter<String>(this,R.layout.list_item,R.id.ing,ingredients);
        ings.setAdapter(adapter);

        ings.setOnItemClickListener(new itemClick());

        //Initializing the database for this activity
        myDB = new DatabaseHelper(this);


        Toolbar object = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(object);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
        {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    //Searching for the wanted recipe giving the search criteria
    public void Search (View view) {
        Intent intent = new Intent(this, SearchedRecipes.class);
        startActivity(intent);
    }

    //Adding an ingredient to our search
    public void addings (View view){
        String testitem = ingsIn.getText().toString();
        ingredients.add(testitem);
        adapter.notifyDataSetChanged();
        ingsIn.setText("");
    }

    //Searching for the recipes and passing the data to the next activity
    public void searchRecipe(View view){

        recipes = new ArrayList<>();
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

        while(search.moveToNext()){
            recipes.add(search.getString(1));
        }

        Intent intent = new Intent(this,SearchedRecipes.class);
        intent.putStringArrayListExtra("searchResults",recipes);
        startActivity(intent);

    }

    //Test om recepten op ingredient te vinden
    public void test(View view){
        String extra ="";
        Collections.sort(ingredients);
        for (String ing: ingredients){
            extra = extra + "%"+ing+"%";
        }
        String[] check = {extra};

        //String Input = "%"+ingsIn.getText().toString()+"%";
        //String[] test = {Input};
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

    public void show(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

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

            test = name.getText().toString();
            ingredients.remove(getIndexByname(test));
            //Log.d("het recept", recipe);
            adapter.notifyDataSetChanged();
        }
    }

}
