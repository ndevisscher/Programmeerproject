package mprog.nl.receptenhulp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

public class RecipeFinder extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_finder);

    }

    public void ReturnHome (View view) {
        Intent intent = new Intent(this, HomeScreen.class);
        startActivity(intent);
    }

    public void Search (View view) {
        Intent intent = new Intent(this, SearchedRecipes.class);
        startActivity(intent);
    }

    public void DatabaseTest (View view) {
        Intent intent = new Intent(this, selected_recipe.class);
        startActivity(intent);
    }

    private class MyListAdapter extends ArrayAdapter<String> {
        private int layout;
        public MyListAdapter(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
            layout = resource;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return super.getView(position, convertView, parent);
        }
    }

}
