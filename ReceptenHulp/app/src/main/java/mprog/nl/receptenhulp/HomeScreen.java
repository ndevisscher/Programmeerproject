package mprog.nl.receptenhulp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class HomeScreen extends AppCompatActivity {

    DatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        myDB = new DatabaseHelper(this);
    }

    //Go to the search activity
    public void FindRecipe (View view) {
        Intent intent = new Intent(this, RecipeFinder.class);
        startActivity(intent);
    }

    //Go to the recipe adding activity
    public void addRecipe (View view) {
        Intent intent = new Intent(this, AddRecipe.class);
        startActivity(intent);
    }

    //Go to the people adding activity
    public void addPeople (View view) {
        Intent intent = new Intent(this, AddPerson.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_screen, menu);
        return true;
    }

    //This is to activate the back button that we use in the toolbar for our app
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
