package com.example.bloodfindapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper1 db;
    EditText emailId, password, confirmPassword;
    Button registerButton,loginInsteadButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //my code
        db=new DatabaseHelper1(this);
        emailId = (EditText) findViewById(R.id.emailReg);
        password = (EditText) findViewById(R.id.passReg);
        confirmPassword = (EditText) findViewById(R.id.cpassReg);
        registerButton = (Button) findViewById(R.id.regButton);
        loginInsteadButton = (Button) findViewById(R.id.regToLogin);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailString = emailId.getText().toString();
                String passwordString = password.getText().toString();
                String confirmPasswordString = confirmPassword.getText().toString();
                if(emailString.equals("") || passwordString.equals("") || confirmPasswordString.equals("")) {
                    Toast.makeText(getApplicationContext(),"Fields are empty",Toast.LENGTH_SHORT).show();
                }
                else {
                    if(passwordString.equals(confirmPasswordString)) {
                        Boolean checkMail= db.checkMail(emailString);
                        if (checkMail==true){
                            Intent toPersonalDetailsRegisterIntent=new Intent(MainActivity.this,PersonalDetailsRegister.class);
                            toPersonalDetailsRegisterIntent.putExtra("EMAIL ID",emailString);
                            toPersonalDetailsRegisterIntent.putExtra("PASSWORD",passwordString);
                            startActivity(toPersonalDetailsRegisterIntent);
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Account already exists",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Passwords do not match",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        loginInsteadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginInsteadIntent=new Intent(MainActivity.this,Login.class);
                startActivity(loginInsteadIntent);
            }
        });
    }
}
