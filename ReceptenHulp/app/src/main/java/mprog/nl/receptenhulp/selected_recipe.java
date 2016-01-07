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

public class selected_recipe extends AppCompatActivity {

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
        setContentView(R.layout.activity_selected_recipe);

        idView = (TextView) findViewById(R.id.RecipeName);
        recipeLine = (EditText) findViewById(R.id.addRecipe);
        ingLine = (EditText) findViewById(R.id.searchRecipe);

        add = (Button) findViewById(R.id.add);
        search = (Button) findViewById(R.id.adding);
        ListView ings = (ListView)findViewById(R.id.ingredients);

        String[] items = {"soufle","goulash","kip teriyaki"};
        ingredients = new ArrayList<>(Arrays.asList(items));
        adapter = new ArrayAdapter<String>(this,R.layout.list_item,R.id.ing,ingredients);
        ings.setAdapter(adapter);

        myDB = new DatabaseHelper(this);
    }


    public void addRecipe(View view) {

        String ings = "";
        for (String collect : ingredients){
            ings = ings  + collect + " , ";
        }

        boolean check = myDB.addRecipe(recipeLine.getText().toString(),ings);
        if (check == true) {
            Log.d("check", "het is gelukt");
        } else
            Log.d("fail", "het is niet gelukt");
        recipeLine.setText("");
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

