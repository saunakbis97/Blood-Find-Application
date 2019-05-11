package com.example.bloodfindapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Homepage extends AppCompatActivity {
    RadioGroup categoriesGroup;
    RadioButton radioNone,radioDonor,radioReceiver,radioButton;
    Button searchButton;
    DatabaseHelper1 db=new DatabaseHelper1(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        Intent prevIntent = getIntent();
        String emailString = prevIntent.getStringExtra("EMAIL ID");

        onClickListenerButton(emailString);
    }

    public void onClickListenerButton(final String email) {
        categoriesGroup = (RadioGroup)findViewById(R.id.radioGroupStatus);
        radioNone =(RadioButton)findViewById(R.id.radio_none);
        radioDonor = (RadioButton)findViewById(R.id.radio_donor);
        radioReceiver = (RadioButton)findViewById(R.id.radio_receiver);
        radioNone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean changedToNone =db.changeCategory(email,"NONE");
                if(changedToNone == true) {
                    Toast.makeText(getApplicationContext(), "Category changed to NONE", Toast.LENGTH_SHORT).show();
                }
            }
        });
        radioReceiver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean changedToReceiver =db.changeCategory(email,"RECEIVER");
                if(changedToReceiver == true) {
                    Toast.makeText(getApplicationContext(), "Category changed to RECEIVER", Toast.LENGTH_SHORT).show();
                }
            }
        });
        radioDonor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean changedToDonor =db.changeCategory(email,"DONOR");
                if(changedToDonor == true) {
                    Toast.makeText(getApplicationContext(), "Category changed to DONOR", Toast.LENGTH_SHORT).show();
                }
            }
        });
        searchButton = (Button)findViewById(R.id.homepageSearchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedCategoryId = categoriesGroup.getCheckedRadioButtonId();
                radioButton = (RadioButton)findViewById(selectedCategoryId);
                Toast.makeText(Homepage.this,radioButton.getText().toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
