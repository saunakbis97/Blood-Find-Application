package com.example.bloodfindapplication;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ActivityManagePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_page);

        DatabaseHelper1 db=new DatabaseHelper1(this);
        DatabaseHelper2 db2=new DatabaseHelper2(this);
        Intent prevIntent = getIntent();
        final String emailString = prevIntent.getStringExtra("USER EMAIL ID");
        String activityNoClicked = prevIntent.getStringExtra("CLICKED ACTIVITY NO");

        Cursor getActivityInformationCursor = db2.getActivityInformation(activityNoClicked);
        if(getActivityInformationCursor != null) {
            getActivityInformationCursor.moveToFirst();
        }
        do {
            setActivityInformation(getActivityInformationCursor,emailString);

            if((getActivityInformationCursor.getString(1)).equals(emailString)) {
                setProfileInformation(getActivityInformationCursor.getString(2));
            }
            else {
                setProfileInformation(getActivityInformationCursor.getString(1));
            }

        }while (getActivityInformationCursor.moveToNext());
    }

    private void setProfileInformation(String email) {
        DatabaseHelper1 db=new DatabaseHelper1(this);
        Cursor informationCursor = db.getInformationAfterSearch(email);
        if(informationCursor != null) {
            informationCursor.moveToFirst();
        }
        do{
            TextView nameValueTextView = (TextView) findViewById(R.id.nameValueTextView);
            nameValueTextView.setText(informationCursor.getString(0));

            TextView emailValueTextView = (TextView) findViewById(R.id.emailValueTextView);
            emailValueTextView.setText(informationCursor.getString(1));

            TextView mobileNoValueTextView = (TextView) findViewById(R.id.mobileNoValueTextView);
            mobileNoValueTextView.setText(informationCursor.getString(2));

            TextView bloodGroupValueTextView = (TextView) findViewById(R.id.bloodGroupValueTextView);
            bloodGroupValueTextView.setText(informationCursor.getString(3));

            TextView addressValueTextView = (TextView) findViewById(R.id.addressValueTextView);
            addressValueTextView.setText(informationCursor.getString(4));

            TextView currentCategoryValueTextView = (TextView) findViewById(R.id.currentCategoryValueTextView);
            currentCategoryValueTextView.setText(informationCursor.getString(5));
        }while (informationCursor.moveToNext());
    }

    private void setActivityInformation(Cursor getActivityInformationCursor, String emailString) {

        TextView activityNoValueTextView = (TextView) findViewById(R.id.activityNoValueTextView);
        activityNoValueTextView.setText(getActivityInformationCursor.getString(0));

        TextView yourActivityStatusValueTextView = (TextView) findViewById(R.id.yourActivityStatusValueTextView);
        TextView inActivityCategoryValueTextView = (TextView) findViewById(R.id.inActivityCategoryValueTextView);
        if((getActivityInformationCursor.getString(3)).equals("OFFER SENT") && (getActivityInformationCursor.getString(1)).equals(emailString)) {
            inActivityCategoryValueTextView.setText("RECEIVER");
            yourActivityStatusValueTextView.setText("OUTGOING OFFER SENT");
        }
        else if((getActivityInformationCursor.getString(3)).equals("OFFER SENT") && (getActivityInformationCursor.getString(2)).equals(emailString)) {
            inActivityCategoryValueTextView.setText("DONOR");
            yourActivityStatusValueTextView.setText("INCOMING OFFER RECEIVED");
        }
        else if((getActivityInformationCursor.getString(3)).equals("REQUEST SENT") && (getActivityInformationCursor.getString(1)).equals(emailString)) {
            inActivityCategoryValueTextView.setText("DONOR");
            yourActivityStatusValueTextView.setText("OUTGOING REQUEST SENT");
        }
        else if((getActivityInformationCursor.getString(3)).equals("REQUEST SENT") && (getActivityInformationCursor.getString(2)).equals(emailString)) {
            inActivityCategoryValueTextView.setText("RECEIVER");
            yourActivityStatusValueTextView.setText("INCOMING REQUEST RECEIVED");
        }
        else if((getActivityInformationCursor.getString(3)).equals("OFFER ACCEPTED") && (getActivityInformationCursor.getString(1)).equals(emailString)) {
            inActivityCategoryValueTextView.setText("RECEIVER");
            yourActivityStatusValueTextView.setText("OUTGOING OFFER ACCEPTED");
        }
        else if((getActivityInformationCursor.getString(3)).equals("OFFER ACCEPTED") && (getActivityInformationCursor.getString(2)).equals(emailString)) {
            inActivityCategoryValueTextView.setText("DONOR");
            yourActivityStatusValueTextView.setText("INCOMING OFFER ACCEPTED");
        }
        else if((getActivityInformationCursor.getString(3)).equals("REQUEST ACCEPTED") && (getActivityInformationCursor.getString(1)).equals(emailString)) {
            inActivityCategoryValueTextView.setText("DONOR");
            yourActivityStatusValueTextView.setText("OUTGOING REQUEST ACCEPTED");
        }
        else if((getActivityInformationCursor.getString(3)).equals("REQUEST ACCEPTED") && (getActivityInformationCursor.getString(2)).equals(emailString)) {
            inActivityCategoryValueTextView.setText("RECEIVER");
            yourActivityStatusValueTextView.setText("INCOMING REQUEST ACCEPTED");
        }
        else if((getActivityInformationCursor.getString(3)).equals("OFFER CANCELLED") && (getActivityInformationCursor.getString(1)).equals(emailString)) {
            inActivityCategoryValueTextView.setText("RECEIVER");
            yourActivityStatusValueTextView.setText("OUTGOING OFFER CANCELLED");
        }
        else if((getActivityInformationCursor.getString(3)).equals("OFFER CANCELLED") && (getActivityInformationCursor.getString(2)).equals(emailString)) {
            inActivityCategoryValueTextView.setText("DONOR");
            yourActivityStatusValueTextView.setText("INCOMING OFFER CANCELLED");
        }
        else if((getActivityInformationCursor.getString(3)).equals("REQUEST CANCELLED") && (getActivityInformationCursor.getString(1)).equals(emailString)) {
            inActivityCategoryValueTextView.setText("DONOR");
            yourActivityStatusValueTextView.setText("OUTGOING REQUEST CANCELLED");
        }
        else if((getActivityInformationCursor.getString(3)).equals("REQUEST CANCELLED") && (getActivityInformationCursor.getString(2)).equals(emailString)) {
            inActivityCategoryValueTextView.setText("RECEIVER");
            yourActivityStatusValueTextView.setText("INCOMING REQUEST CANCELLED");
        }
        else if((getActivityInformationCursor.getString(3)).equals("OFFER DECLINED") && (getActivityInformationCursor.getString(1)).equals(emailString)) {
            inActivityCategoryValueTextView.setText("RECEIVER");
            yourActivityStatusValueTextView.setText("OUTGOING OFFER DECLINED");
        }
        else if((getActivityInformationCursor.getString(3)).equals("OFFER DECLINED") && (getActivityInformationCursor.getString(2)).equals(emailString)) {
            inActivityCategoryValueTextView.setText("DONOR");
            yourActivityStatusValueTextView.setText("INCOMING OFFER DECLINED");
        }
        else if((getActivityInformationCursor.getString(3)).equals("REQUEST DECLINED") && (getActivityInformationCursor.getString(1)).equals(emailString)) {
            inActivityCategoryValueTextView.setText("DONOR");
            yourActivityStatusValueTextView.setText("OUTGOING REQUEST DECLINED");
        }
        else if((getActivityInformationCursor.getString(3)).equals("REQUEST DECLINED") && (getActivityInformationCursor.getString(2)).equals(emailString)) {
            inActivityCategoryValueTextView.setText("RECEIVER");
            yourActivityStatusValueTextView.setText("INCOMING REQUEST DECLINED");
        }
    }
}
