package com.example.bloodfindapplication;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
//import android.widget.SearchView;
import android.support.v7.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

public class SearchAsReceiverPage extends AppCompatActivity {

    ListView receiverListView;
    ArrayList<String> receiverList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_as_receiver_page);

       /* receiverListView = (ListView) findViewById(R.id.receiverListView);

        receiverList = new ArrayList<>();
        receiverList.add("ONE");
        receiverList.add("TWO");
        receiverList.add("THREE");
        receiverList.add("FOUR");
        receiverList.add("FIVE");
        receiverList.add("SIX");
        receiverList.add("SEVEN");
        receiverList.add("EIGHT");
        receiverList.add("NINE");
        receiverList.add("TEN");
        receiverList.add("ELEVEN");
        receiverList.add("TWELVE");
        receiverList.add("THIRTEEN");
        receiverList.add("FOURTEEN");
        receiverList.add("FIFTEEN");
        ArrayAdapter<String> adapter =new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,receiverList);
        receiverListView.setAdapter(adapter);

        receiverListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String text = receiverListView.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(),Integer.toString(position),Toast.LENGTH_SHORT).show();
            }
        });*/


        DatabaseHelper1 db=new DatabaseHelper1(this);
        Intent prevIntent = getIntent();
        final String emailString = prevIntent.getStringExtra("EMAIL ID");
        receiverListView = (ListView) findViewById(R.id.receiverListView);
        Cursor searchCursor = db.getSearchResults(emailString,"DONOR");
        receiverList = new ArrayList<>();

        if(searchCursor != null) {
            searchCursor.moveToFirst();
        }
        do{
            StringBuilder concatStringBuilder=new StringBuilder();
            concatStringBuilder.append(searchCursor.getString(0));
            concatStringBuilder.append("(");
            concatStringBuilder.append(searchCursor.getString(1));
            concatStringBuilder.append(")");
            receiverList.add(concatStringBuilder.toString());
        }while (searchCursor.moveToNext());

        ArrayAdapter<String> adapter =new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,receiverList);
        receiverListView.setAdapter(adapter);
        receiverListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String concatenatedString = receiverListView.getItemAtPosition(position).toString();
                String emailString;
                emailString=concatenatedString.substring((concatenatedString.lastIndexOf("(")) + 1 ,concatenatedString.lastIndexOf(")") );
                Toast.makeText(getApplicationContext(),emailString,Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater receiverInflater = getMenuInflater();
        receiverInflater.inflate(R.menu.menu,menu);

        MenuItem searchReceiverList = menu.findItem(R.id.item_search);
        SearchView receiverSearchView = (SearchView) MenuItemCompat.getActionView(searchReceiverList);
        receiverSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                ArrayList<String> tempSearchList =new ArrayList<>();
                for (String tempString : receiverList) {
                    if (tempString.toUpperCase().contains(newText.toUpperCase())) {
                        tempSearchList.add(tempString);
                    }
                }
                ArrayAdapter<String> adapter =new ArrayAdapter<>(SearchAsReceiverPage.this, android.R.layout.simple_list_item_1,tempSearchList);
                receiverListView.setAdapter(adapter);

                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}
