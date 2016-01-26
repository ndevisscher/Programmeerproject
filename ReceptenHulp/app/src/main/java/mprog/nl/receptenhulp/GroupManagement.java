package mprog.nl.receptenhulp;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class GroupManagement extends AppCompatActivity {

    //Declaring the various variables we need in this activity
    DatabaseHelper myDB;

    ArrayList<String> groupChoice;
    ArrayList<String> peopleChoice;
    ArrayList<String> selectedPeople;
    ArrayList<String> selectedGroups;
    ArrayAdapter peopleAdapter;
    ArrayAdapter groupAdapter;

    String idList = "";

    ListView groups;
    ListView people;
    EditText groupName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_management);
        myDB = new DatabaseHelper(this);

        //Initializing the buttons and inputfields
        groupName = (EditText) findViewById(R.id.groupName);

        //Setting up the listview and checkbox for the people choice
        people = (ListView) findViewById(R.id.peopleList);
        people.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        peopleChoice = new ArrayList<>();
        peopleAdapter = new ArrayAdapter<String>(this, R.layout.person_layout, R.id.person, peopleChoice);
        people.setAdapter(peopleAdapter);

        //Setting up the listview and checkbox for the people choice
        groups = (ListView) findViewById(R.id.groupList);
        groups.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        groupChoice = new ArrayList<>();
        groupAdapter = new ArrayAdapter<String>(this, R.layout.person_layout, R.id.person, groupChoice);
        groups.setAdapter(groupAdapter);

        //Selecting people to be added to a group
        selectedPeople = new ArrayList<>();
        people.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String select = peopleChoice.get(position);
                if (selectedPeople.contains(select)) {
                    selectedPeople.remove(select);
                } else {
                    selectedPeople.add(select);
                }
            }
        });

        //Selecting groups to be modified
        selectedGroups = new ArrayList<>();
        groups.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String select = groupChoice.get(position);
                if (selectedGroups.contains(select)) {
                    selectedGroups.remove(select);
                } else {
                    selectedGroups.add(select);
                }
            }
        });

        //This allows us to use the back button on the toolbar
        Toolbar object = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(object);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Add people and groups to the list
        showPeople();
        showGroups();
    }

    //Adding the group to the database
    public void addGroup(View view) {
        //Getting the data from the input
        String name = groupName.getText().toString();
        if(name.length() == 0){
            show("Ongeldige invoer","Elke groep moet een naam hebben");
            return;
        }
        if (myDB.groupCheck(name) == true){
            show("Ongeldige invoer","Deze groep bestaat al");
            groupName.setText("");
            return;
        }
        myDB.addGroup(name);
        show("Deze groep is toegevoegd:",name);
        groupName.setText("");
        groupChoice.add(name);
        groupAdapter.notifyDataSetChanged();
    }

    //Adding people to a group
    public void addPeople (View view) {
        //For every group that is selected people need to be added
        for (String group : selectedGroups) {
            String peopleList = "";
            String logPeople = "";
            boolean full = true;
            //Getting people from the current group
            Cursor data = myDB.getPeopleFromGroup(group);
            while (data.moveToNext()){
                //If the group was empty, we need to fill it with all selected people
                if(data.getString(0)==""){
                    peopleList = "";
                    full = false;
                }
                //If the group had people in it we need to get those people so we can check if we
                //are adding the same person
                else {
                    peopleList = data.getString(0);
                    String[] check = peopleList.split(" ");
                }
            }
            String[] check = peopleList.split(" ");
            //For every person selected we get their ID, so we can add it to the group
            for (String Person : selectedPeople) {
                String[] split = Person.split(" ");
                String firstname = split[0];
                String adj = split[1];
                String lastname = split[2];
                Cursor getID = myDB.getPersonID(firstname, adj, lastname);
                while (getID.moveToNext()) {
                    String id = getID.getString(0);
                    //If the group already had people in it, check if this person was in it, if not add him
                    if (full) {
                        if (!Arrays.asList(check).contains(id)) {
                            idList = id + " " + idList;
                            logPeople = Person + " , " + logPeople;
                        }
                        else{
                            Log.d("zit al in lijst", id);
                        }
                    }
                    //If the group had no people in it, we can just add everyone to the group
                    else{
                        idList = idList + " " + id;
                    }
                }
            }
            //Adding the new people to the already existing list from our group, if the group had no
            //people in it, it will just be the people we just added
            peopleList = peopleList + " " + idList;
            myDB.addPeopleToGroup(group,peopleList);
            if(logPeople.isEmpty()){
                show("Geen toevoeging","al deze mensen zitten al in de groep(en)");
            }
            else {
                show("Toegevoegd aan groep: " + group, logPeople);
            }
            people.clearChoices();
            peopleAdapter.notifyDataSetChanged();
            groups.clearChoices();
            groupAdapter.notifyDataSetChanged();
        }
    }

    //Shows the groups in the listview to be selected
    public void showGroups() {
        String data = "";
        Cursor search = myDB.showGroups();
        while (search.moveToNext()) {
            data = search.getString(1);
            groupChoice.add(data);
        }
    }

    //Shows the people from the database in the listview to be selected
    public void showPeople() {
        String data = "";
        Cursor search = myDB.showPeople();
        while (search.moveToNext()) {
            data = search.getString(1) + " " + search.getString(2) + " " + search.getString(3);
            peopleChoice.add(data);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_screen, menu);
        return true;
    }

    //Using the back button from the toolbar to go to the previous screen
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    //This allows us to show the dialog with info of our group
    public void show(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

}
