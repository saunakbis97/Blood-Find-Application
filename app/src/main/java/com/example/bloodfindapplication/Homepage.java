package com.example.bloodfindapplication;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Homepage extends AppCompatActivity {
    RadioGroup categoriesGroup;
    RadioButton radioNone,radioDonor,radioReceiver,radioButton;
    Button searchButton;
    DatabaseHelper1 db=new DatabaseHelper1(this);
    Cursor categoryCursor;
    String initialCategoryData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        Intent prevIntent = getIntent();
        String emailString = prevIntent.getStringExtra("EMAIL ID");

        initialCategoryData = categoryDataFromDB(emailString);// function to be changed later
        radioNone =(RadioButton)findViewById(R.id.radio_none);
        radioDonor = (RadioButton)findViewById(R.id.radio_donor);
        radioReceiver = (RadioButton)findViewById(R.id.radio_receiver);
        if(initialCategoryData.equals("NONE")) {
            radioNone.setChecked(true);
        }
        else if(initialCategoryData.equals("DONOR")) {
            radioDonor.setChecked(true);
        }
        else if(initialCategoryData.equals("RECEIVER")) {
            radioReceiver.setChecked(true);
        }

        onClickListenerButton(emailString);
    }

    public void onClickListenerButton(final String email) {
        radioNone =(RadioButton)findViewById(R.id.radio_none);
        radioDonor = (RadioButton)findViewById(R.id.radio_donor);
        radioReceiver = (RadioButton)findViewById(R.id.radio_receiver);
        radioNone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean changedToNone =db.changeCategory(email,"NONE");
                if(changedToNone == true) {
                    Toast.makeText(getApplicationContext(), "Category changed to NONE", Toast.LENGTH_SHORT).show();
                    String categoryFromDatabase = categoryDataFromDB(email);
                }
            }
        });
        radioReceiver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean changedToReceiver =db.changeCategory(email,"RECEIVER");
                if(changedToReceiver == true) {
                    Toast.makeText(getApplicationContext(), "Category changed to RECEIVER", Toast.LENGTH_SHORT).show();
                    String categoryFromDatabase = categoryDataFromDB(email);
                }
            }
        });
        radioDonor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean changedToDonor =db.changeCategory(email,"DONOR");
                if(changedToDonor == true) {
                    Toast.makeText(getApplicationContext(), "Category changed to DONOR", Toast.LENGTH_SHORT).show();
                    String categoryFromDatabase = categoryDataFromDB(email);
                }
            }
        });
        searchButton = (Button)findViewById(R.id.homepageSearchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (categoryDataFromDB(email).equals("NONE")) {
                    Toast.makeText(Homepage.this, "Change choice to search", Toast.LENGTH_SHORT).show();
                }
                else if(categoryDataFromDB(email).equals("DONOR")) {
                    Toast.makeText(Homepage.this, "Showing Blood Donors", Toast.LENGTH_SHORT).show();
                    Intent toSearchAsDonorPageIntent=new Intent(Homepage.this,SearchAsDonorPage.class);
                    toSearchAsDonorPageIntent.putExtra("EMAIL ID",email);
                    startActivity(toSearchAsDonorPageIntent);
                }
                else if(categoryDataFromDB(email).equals("RECEIVER")) {
                    Toast.makeText(Homepage.this, "Showing Blood Receivers", Toast.LENGTH_SHORT).show();
                    Intent toSearchAsReceiverPageIntent=new Intent(Homepage.this,SearchAsReceiverPage.class);
                    toSearchAsReceiverPageIntent.putExtra("EMAIL ID",email);
                    startActivity(toSearchAsReceiverPageIntent);
                }
            }
        });
    }

    public String categoryDataFromDB(String emailString) {
        TextView categoryTextView= (TextView) findViewById(R.id.tempTextView);
        categoryCursor =db.getCategory(emailString);
        if(categoryCursor != null) {
            categoryCursor.moveToFirst();
        }
        String categoryFromdb;
        do{
            categoryFromdb = categoryCursor.getString(0);
        }while (categoryCursor.moveToNext());
        categoryTextView.setText(categoryFromdb);
        return categoryFromdb;
    }

}
