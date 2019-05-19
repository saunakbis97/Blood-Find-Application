package com.example.bloodfindapplication;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class SearchToInformationPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_to_information_page);

        String categoryOfSearchedProfile;

        DatabaseHelper1 db=new DatabaseHelper1(this);
        Intent prevIntent = getIntent();
        final String emailString = prevIntent.getStringExtra("EMAIL ID");

        Cursor informationCursor = db.getInformationAfterSearch(emailString);
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
            Button makeOfferOrRequestButton = (Button) findViewById(R.id.makeOfferOrRequestButton);
            makeOfferOrRequestButton.setText("OFFER TO DONATE BLOOD");
        }
        else if (categoryOfSearchedProfile.equals("DONOR")) {
            Button makeOfferOrRequestButton = (Button) findViewById(R.id.makeOfferOrRequestButton);
            makeOfferOrRequestButton.setText("REQUEST BLOOD");
        }
    }
}
