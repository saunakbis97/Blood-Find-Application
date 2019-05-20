package com.example.bloodfindapplication;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ActivitiesPage extends AppCompatActivity {

    ListView activitiesListView;
    ArrayList<String> activitiesList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities_page);

        DatabaseHelper1 db=new DatabaseHelper1(this);
        DatabaseHelper2 db2=new DatabaseHelper2(this);
        Intent prevIntent = getIntent();
        final String emailString = prevIntent.getStringExtra("EMAIL ID");
        Cursor activitySearchCursor = db2.getActivitiesSearchResults(emailString);
        activitiesListView =(ListView) findViewById(R.id.activitiesListView);
        activitiesList =new ArrayList<>();

        if (activitySearchCursor != null) {
            activitySearchCursor.moveToFirst();
            do {
                StringBuilder concatStringBuilder=new StringBuilder();
                if(activitySearchCursor.getCount()>0) {
                    if ((activitySearchCursor.getString(1)).equals(emailString)) {
                        Cursor findNameForActivitiesCursor = db.findNameForActivities(activitySearchCursor.getString(2));
                        if (findNameForActivitiesCursor != null) {
                            findNameForActivitiesCursor.moveToFirst();
                            do {
                                concatStringBuilder.append(findNameForActivitiesCursor.getString(0));
                                concatStringBuilder.append("(");
                                concatStringBuilder.append(activitySearchCursor.getString(2));
                                concatStringBuilder.append(")\n");
                                concatStringBuilder.append(activitySearchCursor.getString(3));
                                concatStringBuilder.append("(");
                                concatStringBuilder.append(activitySearchCursor.getString(0));
                                concatStringBuilder.append(")");
                                activitiesList.add(concatStringBuilder.toString());
                            } while (findNameForActivitiesCursor.moveToNext());
                        }
                    } else if ((activitySearchCursor.getString(2)).equals(emailString)) {
                        Cursor findNameForActivitiesCursor = db.findNameForActivities(activitySearchCursor.getString(1));
                        if (findNameForActivitiesCursor != null) {
                            findNameForActivitiesCursor.moveToFirst();
                            do {
                                concatStringBuilder.append(findNameForActivitiesCursor.getString(0));
                                concatStringBuilder.append("(");
                                concatStringBuilder.append(activitySearchCursor.getString(1));
                                concatStringBuilder.append(")\n");
                                concatStringBuilder.append(activitySearchCursor.getString(3));
                                concatStringBuilder.append("(");
                                concatStringBuilder.append(activitySearchCursor.getString(0));
                                concatStringBuilder.append(")");
                                activitiesList.add(concatStringBuilder.toString());
                            } while (findNameForActivitiesCursor.moveToNext());
                        }
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(),"No Activities To display",Toast.LENGTH_SHORT).show();
                }
            }while (activitySearchCursor.moveToNext());
        }
        ArrayAdapter<String> adapter =new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,activitiesList);
        activitiesListView.setAdapter(adapter);
        activitiesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String concatenatedString = activitiesListView.getItemAtPosition(position).toString();
                String activityNoClicked = concatenatedString.substring((concatenatedString.lastIndexOf("(")) + 1 ,concatenatedString.lastIndexOf(")") );
                goToActivityManagePage(emailString,activityNoClicked);

            }
        });
    }

    private void goToActivityManagePage(String emailString, String activityNoClicked) {
        Intent toActivityManagePageIntent = new Intent(ActivitiesPage.this,ActivityManagePage.class);
        toActivityManagePageIntent.putExtra("USER EMAIL ID",emailString);
        toActivityManagePageIntent.putExtra("CLICKED ACTIVITY NO",activityNoClicked);
        startActivity(toActivityManagePageIntent);
    }
}
