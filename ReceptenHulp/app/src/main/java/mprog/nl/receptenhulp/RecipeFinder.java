package mprog.nl.receptenhulp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecipeFinder extends AppCompatActivity {

    DatabaseHelper myDB;

    private ArrayList<String> ingredients;
    private ArrayAdapter<String> adapter;

    EditText ingsIn;
    Button add;
    Button search;

    //
    EditText tests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_finder);

        ingsIn = (EditText) findViewById(R.id.ingsIn);

        //
        tests = (EditText) findViewById(R.id.test);

        add = (Button) findViewById(R.id.add);
        search = (Button) findViewById(R.id.search);
        ListView ings = (ListView)findViewById(R.id.ingsList);

        String[] items = {"kip"};
        ingredients = new ArrayList<>(Arrays.asList(items));
        adapter = new ArrayAdapter<String>(this,R.layout.list_item,R.id.ing,ingredients);
        ings.setAdapter(adapter);

        myDB = new DatabaseHelper(this);

    }

    public void ReturnHome (View view) {
        Intent intent = new Intent(this, HomeScreen.class);
        startActivity(intent);
    }

//    public void Search (View view) {
//        Intent intent = new Intent(this, SearchedRecipes.class);
//        startActivity(intent);
//    }

    public void addings (View view){
        String testitem = ingsIn.getText().toString();
        ingredients.add(testitem);
        adapter.notifyDataSetChanged();
        ingsIn.setText("");
    }

    public void searchRecipe(View view){

        Cursor search = myDB.searchRecipe();
        if (search.getCount() == 0) {
            Log.d("geen data", "geen data");
        } else
            Log.d("wel data", "wel data");
        //findLine.setText("");
        StringBuffer buffer = new StringBuffer();
        while(search.moveToNext()){
            buffer.append("ID: "+search.getString(0)+"\n");
            buffer.append("Name: "+search.getString(1)+"\n");
            buffer.append("Ings: "+search.getString(2)+"\n");
        }
        show("data", buffer.toString());
    }

    //Test om recepten op naam te vinden
    public void test(View view){
        String[] test = {tests.getText().toString()};
        Cursor search = myDB.getRecipe(test);
        if (search.getCount() == 0) {
            Log.d("geen data", "geen data");
        } else
            Log.d("wel data", "wel data");
        //findLine.setText("");
        StringBuffer buffer = new StringBuffer();
        while(search.moveToNext()){
            buffer.append("ID: "+search.getString(0)+"\n");
            buffer.append("Name: "+search.getString(1)+"\n");
            buffer.append("Ings: "+search.getString(2)+"\n");
        }
        show("data", buffer.toString());
    }

    public void show(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

}
