package com.example.bloodfindapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class PersonalDetailsRegister extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spinner;
    EditText name,mobileno,address;
    Button completeRegistrationButton;
    DatabaseHelper1 db;
    String selectedBloodGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_details_register);

        Intent prevIntent = getIntent();
        final String emailString = prevIntent.getStringExtra("EMAIL ID");
        final String passwordString = prevIntent.getStringExtra("PASSWORD");
        db=new DatabaseHelper1(this);
        name = (EditText) findViewById(R.id.fullNameReg);
        mobileno = (EditText) findViewById(R.id.mobilenoReg);
        address = (EditText) findViewById(R.id.addressReg);
        completeRegistrationButton = (Button) findViewById(R.id.completeRegistrationButton);

        spinner = findViewById(R.id.bloodSpinnerReg);

        ArrayAdapter<CharSequence> Adapter = ArrayAdapter.createFromResource(this,R.array.bloodGroups,android.R.layout.simple_spinner_item);
        Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(Adapter);
        spinner.setOnItemSelectedListener(this);
        completeRegistrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameString = name.getText().toString();
                String mobilenoString = mobileno.getText().toString();
                String addressString = address.getText().toString();
                String emailInner = emailString;
                String passwordInner = passwordString;
                if (nameString.equals("") || mobilenoString.equals("") || addressString.equals("") || selectedBloodGroup.equals("--"))
                {
                    Toast.makeText(getApplicationContext(),"Fields are empty",Toast.LENGTH_SHORT).show();
                }
                else {
                    Boolean insert = db.onInsert(emailInner,passwordInner,nameString,addressString,mobilenoString,selectedBloodGroup);
                    if(insert == true) {
                        Toast.makeText(getApplicationContext(),"Registered Successfully",Toast.LENGTH_SHORT).show();
                        Intent toHomepageIntent=new Intent(PersonalDetailsRegister.this,Homepage.class);
                        toHomepageIntent.putExtra("EMAIL ID",emailInner);
                        startActivity(toHomepageIntent);
                    }
                }
            }
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedBloodGroup = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        selectedBloodGroup = "--";
    }
}
