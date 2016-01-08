package mprog.nl.receptenhulp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class SearchedRecipes extends AppCompatActivity {

    private ArrayList<String> ingredients;
    private ArrayAdapter<String> adapter;

    Button back;
    Button newSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searched_recipes);

        back = (Button) findViewById(R.id.Home);
        newSearch = (Button) findViewById(R.id.NewSearch);

        ListView List = (ListView)findViewById(R.id.recipeList);

        String[] items = {"kip teriyaki", "Goulash", "Mosselen"};
        ingredients = new ArrayList<>(Arrays.asList(items));
        adapter = new ArrayAdapter<String>(this,R.layout.list_item,R.id.ing,ingredients);
        List.setAdapter(adapter);

    }

    public void ReturnHome (View view) {
        Intent intent = new Intent(this, HomeScreen.class);
        startActivity(intent);
    }

    public void newSearch (View view) {
        Intent intent = new Intent(this, RecipeFinder.class);
        startActivity(intent);
    }

}
