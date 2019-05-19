package com.example.bloodfindapplication;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SearchToInformationPage extends AppCompatActivity {

    DatabaseHelper2 db2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_to_information_page);

        String categoryOfSearchedProfile;

        DatabaseHelper1 db=new DatabaseHelper1(this);
        db2=new DatabaseHelper2(this);
        Intent prevIntent = getIntent();
        final String userEmailString = prevIntent.getStringExtra("USER EMAIL ID");
        final String searchedEmailString = prevIntent.getStringExtra("SEARCHED EMAIL ID");

        Cursor informationCursor = db.getInformationAfterSearch(searchedEmailString);
        if(informationCursor != null) {
            informationCursor.moveToFirst();
        }
        do{
            TextView nameValueTextView = (TextView) findViewById(R.id.nameValueTextView);
            nameValueTextView.setText(informationCursor.getString(0));

            TextView emailIdValueTextView = (TextView) findViewById(R.id.emailIdValueTextView);
            emailIdValueTextView.setText(informationCursor.getString(1));

            TextView mobileNoValueTextView = (TextView) findViewById(R.id.mobileNoValueTextView);
            mobileNoValueTextView.setText(informationCursor.getString(2));

            TextView bloodGroupValueTextView = (TextView) findViewById(R.id.bloodGroupValueTextView);
            bloodGroupValueTextView.setText(informationCursor.getString(3));

            TextView addressValueTextView = (TextView) findViewById(R.id.addressValueTextView);
            addressValueTextView.setText(informationCursor.getString(4));

            TextView categoryValueTextView = (TextView) findViewById(R.id.categoryValueTextView);
            categoryValueTextView.setText(informationCursor.getString(5));
            categoryOfSearchedProfile = informationCursor.getString(5);
        }while (informationCursor.moveToNext());
        if (categoryOfSearchedProfile.equals("RECEIVER")) {
            final Button makeOfferOrRequestButton = (Button) findViewById(R.id.makeOfferOrRequestButton);
            makeOfferOrRequestButton.setText("OFFER BLOOD");
            makeOfferOrRequestButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Boolean insert = db2.onInsert(userEmailString,searchedEmailString,"OFFER SENT");
                    if (insert == true) {
                        Toast.makeText(getApplicationContext(),"Offer sent Successfully",Toast.LENGTH_SHORT).show();
                    }
                    makeOfferOrRequestButton.setEnabled(false);
                }
            });
        }
        else if (categoryOfSearchedProfile.equals("DONOR")) {
            final Button makeOfferOrRequestButton = (Button) findViewById(R.id.makeOfferOrRequestButton);
            makeOfferOrRequestButton.setText("REQUEST BLOOD");
            makeOfferOrRequestButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Boolean insert = db2.onInsert(userEmailString,searchedEmailString,"REQUEST SENT");
                    if (insert == true) {
                        Toast.makeText(getApplicationContext(),"Request Sent Successfully",Toast.LENGTH_SHORT).show();
                    }
                    makeOfferOrRequestButton.setEnabled(false);
                }
            });
        }
    }
}
