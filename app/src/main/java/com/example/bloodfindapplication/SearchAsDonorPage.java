package com.example.bloodfindapplication;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class SearchAsDonorPage extends AppCompatActivity {

    ListView donorListView;
    ArrayList<String> donorList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_as_donor_page);

        DatabaseHelper1 db=new DatabaseHelper1(this);
        Intent prevIntent = getIntent();
        final String emailString = prevIntent.getStringExtra("EMAIL ID");
        donorListView = (ListView) findViewById(R.id.donorListView);
        Cursor searchCursor = db.getSearchResults(emailString,"RECEIVER");
        donorList = new ArrayList<>();
        if(searchCursor.getCount()>0) {
            if (searchCursor != null) {
                searchCursor.moveToFirst();
            }
            do {
                StringBuilder concatStringBuilder = new StringBuilder();
                concatStringBuilder.append(searchCursor.getString(0));
                concatStringBuilder.append("(");
                concatStringBuilder.append(searchCursor.getString(1));
                concatStringBuilder.append(")");
                donorList.add(concatStringBuilder.toString());
            } while (searchCursor.moveToNext());
        }
        else {
            Toast.makeText(getApplicationContext(),"Search Results are empty",Toast.LENGTH_SHORT).show();
        }
        ArrayAdapter<String> adapter =new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,donorList);
        donorListView.setAdapter(adapter);
        donorListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String concatenatedString = donorListView.getItemAtPosition(position).toString();
                String searchedEmailString;
                searchedEmailString=concatenatedString.substring((concatenatedString.lastIndexOf("(")) + 1 ,concatenatedString.lastIndexOf(")") );
                Toast.makeText(getApplicationContext(),searchedEmailString,Toast.LENGTH_SHORT).show();
                Intent toSearchInformationPage = new Intent(SearchAsDonorPage.this,SearchToInformationPage.class);
                toSearchInformationPage.putExtra("USER EMAIL ID",emailString);
                toSearchInformationPage.putExtra("SEARCHED EMAIL ID",searchedEmailString);
                startActivity(toSearchInformationPage);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater donorInflater = getMenuInflater();
        donorInflater.inflate(R.menu.menu,menu);

        MenuItem searchDonorList = menu.findItem(R.id.item_search);
        SearchView donorSearchView = (SearchView) MenuItemCompat.getActionView(searchDonorList);
        donorSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                ArrayList<String> tempSearchList =new ArrayList<>();
                for (String tempString : donorList) {
                    if (tempString.toUpperCase().contains(s.toUpperCase())) {
                        tempSearchList.add(tempString);
                    }
                }
                ArrayAdapter<String> adapter =new ArrayAdapter<>(SearchAsDonorPage.this, android.R.layout.simple_list_item_1,tempSearchList);
                donorListView.setAdapter(adapter);

                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}
