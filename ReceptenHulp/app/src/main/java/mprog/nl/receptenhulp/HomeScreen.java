package mprog.nl.receptenhulp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by Niek de Visscher (10667474)
 */
public class HomeScreen extends AppCompatActivity {

    DatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        myDB = new DatabaseHelper(this);

        //This allows us to use the back button on the toolbar
        Toolbar object = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(object);
    }

    //Go to the search activity
    public void FindRecipe (View view) {
        Intent intent = new Intent(this, RecipeFinder.class);
        startActivity(intent);
    }

    //Go to the Recipe adding activity
    public void addRecipe (View view) {
        Intent intent = new Intent(this, AddRecipe.class);
        startActivity(intent);
    }

    //Go to the people adding activity
    public void addPeople (View view) {
        Intent intent = new Intent(this, AddPerson.class);
        startActivity(intent);
    }

    //Go to the people adding activity
    public void groupManager (View view) {
        Intent intent = new Intent(this, GroupManagement.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_screen, menu);
        return true;
    }

}
