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

import java.util.ArrayList;
import java.util.Arrays;

public class AddPerson extends AppCompatActivity {

    PeopleHelper myPeople;

    EditText FirstName;
    EditText Adj;
    EditText LastName;

    Button back;
    Button add;

    private ArrayList<String> ingredients;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_person);

        FirstName = (EditText) findViewById(R.id.firstName);
        Adj = (EditText) findViewById(R.id.adj);
        LastName = (EditText) findViewById(R.id.lastName);

        ListView allergs = (ListView)findViewById(R.id.allergies);
        String[] items = {"kip"};
        ingredients = new ArrayList<>(Arrays.asList(items));
        adapter = new ArrayAdapter<String>(this,R.layout.list_item,R.id.ing,ingredients);
        allergs.setAdapter(adapter);

        myPeople = new PeopleHelper(this);

    }

    public void addAllergie (View view){

    }

    public void addPerson(View view) {

        String firstName = FirstName.getText().toString();
        String adj = Adj.getText().toString();
        String lastName = LastName.getText().toString();
        String allergies = "";

        for (String ing : ingredients) {
            myPeople.addPerson(firstName, adj, lastName, allergies);
        }

        //addIngredients(recipeName);
        FirstName.setText("");
        Adj.setText("");
        LastName.setText("");
    }

    public void showPeople(View view){

//        Cursor search = myPeople.showPeople();
//        if (search.getCount() == 0) {
//            Log.d("geen data", "geen data");
//        } else
//            Log.d("wel data", "wel data");
//        //findLine.setText("");
//        StringBuffer buffer = new StringBuffer();
//        while(search.moveToNext()){
//            buffer.append("ID: "+search.getString(0)+"\n");
//            buffer.append("Name: "+search.getString(1)+"\n");
//            buffer.append("Ings: "+search.getString(2)+"\n");
//        }
//        show("data", buffer.toString());
    }

    public void show(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public void Return (View view){
        Intent intent = new Intent(this, HomeScreen.class);
        startActivity(intent);
    }

}
