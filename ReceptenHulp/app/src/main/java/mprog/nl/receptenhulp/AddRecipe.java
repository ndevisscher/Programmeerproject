package mprog.nl.receptenhulp;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class AddRecipe extends AppCompatActivity {

    DatabaseHelper myDB;

    private ArrayList<String> ingredients;
    private ArrayAdapter<String> adapter;

    TextView idView;
    EditText recipeLine;
    EditText ingLine;
    Button add;
    Button search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        idView = (TextView) findViewById(R.id.RecipeName);
        recipeLine = (EditText) findViewById(R.id.addRecipe);
        ingLine = (EditText) findViewById(R.id.searchRecipe);

        add = (Button) findViewById(R.id.add);
        search = (Button) findViewById(R.id.adding);
        ListView ings = (ListView)findViewById(R.id.ingredients);

        String[] items = {"rijst","wortelen","kip"};
        ingredients = new ArrayList<>(Arrays.asList(items));
        adapter = new ArrayAdapter<String>(this,R.layout.list_item,R.id.ing,ingredients);
        ings.setAdapter(adapter);

        myDB = new DatabaseHelper(this);
    }


    public void addRecipe(View view) {

        int ID = 0;
        String recipeName = recipeLine.getText().toString();
        String[] test = {recipeName};
        myDB.addRecipe(recipeName);

        //Cursor get = myDB.getRecipe(test);
        //ID = Integer.parseInt(get.getString(0));

        for (String ing : ingredients) {
            myDB.addIngredients(ing, recipeName);
        }

        addIngredients(recipeName);
        recipeLine.setText("");
    }

    public void addIngredients (String rcpName){

        int ID = 0;

        String[] test = {rcpName};
        Cursor get = myDB.getRecipe(test);
        //ID = Integer.parseInt(get.getString(0));

        for (String ing : ingredients) {
            myDB.addIngredients(ing, rcpName);
        }
    }

    public void back (View view){
        Intent intent = new Intent(this, HomeScreen.class);
        startActivity(intent);
    }

    public void addings (View view){
        String testitem = ingLine.getText().toString();
        ingredients.add(testitem);
        adapter.notifyDataSetChanged();
        ingLine.setText("");
    }

}

