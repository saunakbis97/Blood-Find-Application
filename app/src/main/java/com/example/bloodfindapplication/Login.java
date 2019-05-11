package com.example.bloodfindapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    EditText emailId,password;
    Button loginButton,loginToRegisterButton;
    DatabaseHelper1 db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db=new DatabaseHelper1(this);
        emailId = (EditText) findViewById(R.id.emailLogin);
        password = (EditText) findViewById(R.id.passwordLogin);
        loginButton = (Button) findViewById(R.id.loginButton);
        loginToRegisterButton = (Button) findViewById(R.id.loginToRegisterButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailString = emailId.getText().toString();
                String passwordString = password.getText().toString();
                Boolean checkMailPassword = db.checkMailPassword(emailString,passwordString);
                if (emailString.equals("") || passwordString.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Fields are empty",Toast.LENGTH_SHORT).show();
                }
                else if(checkMailPassword == true) {
                    Toast.makeText(getApplicationContext(),"Login Success",Toast.LENGTH_SHORT).show();
                    Intent homepageIntent=new Intent(Login.this,Homepage.class);
                    homepageIntent.putExtra("EMAIL ID",emailString);
                    startActivity(homepageIntent);
                }
                else {
                    Toast.makeText(getApplicationContext(),"Wrong Email or password",Toast.LENGTH_SHORT).show();
                }
            }
        });
        loginToRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginToRegisterIntent=new Intent(Login.this,MainActivity.class);
                startActivity(loginToRegisterIntent);
            }
        });
    }
}
