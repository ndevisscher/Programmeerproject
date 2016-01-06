package mprog.nl.receptenhulp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class selected_recipe extends AppCompatActivity {

    DatabaseHelper myDB;

    TextView idView;
    EditText recipeLine;
    EditText findLine;
    Button add;
    Button search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_recipe);

        idView = (TextView) findViewById(R.id.RecipeName);
        recipeLine = (EditText) findViewById(R.id.addRecipe);
        findLine = (EditText) findViewById(R.id.searchRecipe);

        add = (Button) findViewById(R.id.add);
        search = (Button) findViewById(R.id.search);

        myDB = new DatabaseHelper(this);
        addRecipe();
    }

    public void addRecipe() {
        add.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean check = myDB.addRecipe(recipeLine.getText().toString());
                        if (check == true) {
                            Log.d("check", "het is gelukt");
                        } else
                            Log.d("fail", "het is niet gelukt");
                    }
                }
        );
    }

}

