package com.example.bloodfindapplication;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

    private void setActivityInformation(final Cursor getActivityInformationCursor, String emailString) {

        final DatabaseHelper2 db2=new DatabaseHelper2(this);
        TextView activityNoValueTextView = (TextView) findViewById(R.id.activityNoValueTextView);
        activityNoValueTextView.setText(getActivityInformationCursor.getString(0));

        final TextView yourActivityStatusValueTextView = (TextView) findViewById(R.id.yourActivityStatusValueTextView);
        TextView inActivityCategoryValueTextView = (TextView) findViewById(R.id.inActivityCategoryValueTextView);
        if((getActivityInformationCursor.getString(3)).equals("OFFER SENT") && (getActivityInformationCursor.getString(1)).equals(emailString)) {
            inActivityCategoryValueTextView.setText("RECEIVER");
            yourActivityStatusValueTextView.setText("OUTGOING OFFER SENT");
            setAllButtonEnabled(false,true,false);
            setAllButtonVisible(false,true,false);
            Button cancelButton = (Button) findViewById(R.id.cancelButton);
            cancelButton.setText("CANCEL OFFER");
            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivityInformationCursor.moveToFirst();
                    db2.updateActivityStatus(getActivityInformationCursor.getString(0),"OFFER CANCELLED");
                    yourActivityStatusValueTextView.setText("OUTGOING OFFER CANCELLED");
                    setAllButtonEnabled(false,false,false);
                    setAllButtonVisible(false,false,false);
                }
            });
        }
        else if((getActivityInformationCursor.getString(3)).equals("OFFER SENT") && (getActivityInformationCursor.getString(2)).equals(emailString)) {
            inActivityCategoryValueTextView.setText("DONOR");
            yourActivityStatusValueTextView.setText("INCOMING OFFER RECEIVED");
            setAllButtonEnabled(true,false,true);
            setAllButtonVisible(true,false,true);
            Button acceptButton = (Button) findViewById(R.id.acceptButton);
            Button declineButton = (Button) findViewById(R.id.declineButton);
            acceptButton.setText("ACCEPT OFFER");
            declineButton.setText("DECLINE OFFER");
            acceptButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivityInformationCursor.moveToFirst();
                    db2.updateActivityStatus(getActivityInformationCursor.getString(0),"OFFER ACCEPTED");
                    yourActivityStatusValueTextView.setText("INCOMING OFFER ACCEPTED");
                    setAllButtonEnabled(false,false,false);
                    setAllButtonVisible(false,false,false);
                }
            });
            declineButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivityInformationCursor.moveToFirst();
                    db2.updateActivityStatus(getActivityInformationCursor.getString(0),"OFFER DECLINED");
                    yourActivityStatusValueTextView.setText("INCOMING OFFER DECLINED");
                    setAllButtonEnabled(false,false,false);
                    setAllButtonVisible(false,false,false);
                }
            });
        }
        else if((getActivityInformationCursor.getString(3)).equals("REQUEST SENT") && (getActivityInformationCursor.getString(1)).equals(emailString)) {
            inActivityCategoryValueTextView.setText("DONOR");
            yourActivityStatusValueTextView.setText("OUTGOING REQUEST SENT");
            setAllButtonEnabled(false,true,false);
            setAllButtonVisible(false,true,false);
            Button cancelButton = (Button) findViewById(R.id.cancelButton);
            cancelButton.setText("CANCEL REQUEST");
            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivityInformationCursor.moveToFirst();
                    db2.updateActivityStatus(getActivityInformationCursor.getString(0),"REQUEST CANCELLED");
                    yourActivityStatusValueTextView.setText("OUTGOING REQUEST CANCELLED");
                    setAllButtonEnabled(false,false,false);
                    setAllButtonVisible(false,false,false);
                }
            });
        }
        else if((getActivityInformationCursor.getString(3)).equals("REQUEST SENT") && (getActivityInformationCursor.getString(2)).equals(emailString)) {
            inActivityCategoryValueTextView.setText("RECEIVER");
            yourActivityStatusValueTextView.setText("INCOMING REQUEST RECEIVED");
            setAllButtonEnabled(true,false,true);
            setAllButtonVisible(true,false,true);
            Button acceptButton = (Button) findViewById(R.id.acceptButton);
            Button declineButton = (Button) findViewById(R.id.declineButton);
            acceptButton.setText("ACCEPT REQUEST");
            declineButton.setText("DECLINE REQUEST");
            acceptButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivityInformationCursor.moveToFirst();
                    db2.updateActivityStatus(getActivityInformationCursor.getString(0),"REQUEST ACCEPTED");
                    yourActivityStatusValueTextView.setText("INCOMING REQUEST ACCEPTED");
                    setAllButtonEnabled(false,false,false);
                    setAllButtonVisible(false,false,false);
                }
            });
            declineButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivityInformationCursor.moveToFirst();
                    db2.updateActivityStatus(getActivityInformationCursor.getString(0),"REQUEST DECLINED");
                    yourActivityStatusValueTextView.setText("INCOMING REQUEST DECLINED");
                    setAllButtonEnabled(false,false,false);
                    setAllButtonVisible(false,false,false);
                }
            });
        }
        else if((getActivityInformationCursor.getString(3)).equals("OFFER ACCEPTED") && (getActivityInformationCursor.getString(1)).equals(emailString)) {
            inActivityCategoryValueTextView.setText("RECEIVER");
            yourActivityStatusValueTextView.setText("OUTGOING OFFER ACCEPTED");
            setAllButtonEnabled(false,false,false);
            setAllButtonVisible(false,false,false);
        }
        else if((getActivityInformationCursor.getString(3)).equals("OFFER ACCEPTED") && (getActivityInformationCursor.getString(2)).equals(emailString)) {
            inActivityCategoryValueTextView.setText("DONOR");
            yourActivityStatusValueTextView.setText("INCOMING OFFER ACCEPTED");
            setAllButtonEnabled(false,false,false);
            setAllButtonVisible(false,false,false);
        }
        else if((getActivityInformationCursor.getString(3)).equals("REQUEST ACCEPTED") && (getActivityInformationCursor.getString(1)).equals(emailString)) {
            inActivityCategoryValueTextView.setText("DONOR");
            yourActivityStatusValueTextView.setText("OUTGOING REQUEST ACCEPTED");
            setAllButtonEnabled(false,false,false);
            setAllButtonVisible(false,false,false);
        }
        else if((getActivityInformationCursor.getString(3)).equals("REQUEST ACCEPTED") && (getActivityInformationCursor.getString(2)).equals(emailString)) {
            inActivityCategoryValueTextView.setText("RECEIVER");
            yourActivityStatusValueTextView.setText("INCOMING REQUEST ACCEPTED");
            setAllButtonEnabled(false,false,false);
            setAllButtonVisible(false,false,false);
        }
        else if((getActivityInformationCursor.getString(3)).equals("OFFER CANCELLED") && (getActivityInformationCursor.getString(1)).equals(emailString)) {
            inActivityCategoryValueTextView.setText("RECEIVER");
            yourActivityStatusValueTextView.setText("OUTGOING OFFER CANCELLED");
            setAllButtonEnabled(false,false,false);
            setAllButtonVisible(false,false,false);
        }
        else if((getActivityInformationCursor.getString(3)).equals("OFFER CANCELLED") && (getActivityInformationCursor.getString(2)).equals(emailString)) {
            inActivityCategoryValueTextView.setText("DONOR");
            yourActivityStatusValueTextView.setText("INCOMING OFFER CANCELLED");
            setAllButtonEnabled(false,false,false);
            setAllButtonVisible(false,false,false);
        }
        else if((getActivityInformationCursor.getString(3)).equals("REQUEST CANCELLED") && (getActivityInformationCursor.getString(1)).equals(emailString)) {
            inActivityCategoryValueTextView.setText("DONOR");
            yourActivityStatusValueTextView.setText("OUTGOING REQUEST CANCELLED");
            setAllButtonEnabled(false,false,false);
            setAllButtonVisible(false,false,false);
        }
        else if((getActivityInformationCursor.getString(3)).equals("REQUEST CANCELLED") && (getActivityInformationCursor.getString(2)).equals(emailString)) {
            inActivityCategoryValueTextView.setText("RECEIVER");
            yourActivityStatusValueTextView.setText("INCOMING REQUEST CANCELLED");
            setAllButtonEnabled(false,false,false);
            setAllButtonVisible(false,false,false);
        }
        else if((getActivityInformationCursor.getString(3)).equals("OFFER DECLINED") && (getActivityInformationCursor.getString(1)).equals(emailString)) {
            inActivityCategoryValueTextView.setText("RECEIVER");
            yourActivityStatusValueTextView.setText("OUTGOING OFFER DECLINED");
            setAllButtonEnabled(false,false,false);
            setAllButtonVisible(false,false,false);
        }
        else if((getActivityInformationCursor.getString(3)).equals("OFFER DECLINED") && (getActivityInformationCursor.getString(2)).equals(emailString)) {
            inActivityCategoryValueTextView.setText("DONOR");
            yourActivityStatusValueTextView.setText("INCOMING OFFER DECLINED");
            setAllButtonEnabled(false,false,false);
            setAllButtonVisible(false,false,false);
        }
        else if((getActivityInformationCursor.getString(3)).equals("REQUEST DECLINED") && (getActivityInformationCursor.getString(1)).equals(emailString)) {
            inActivityCategoryValueTextView.setText("DONOR");
            yourActivityStatusValueTextView.setText("OUTGOING REQUEST DECLINED");
            setAllButtonEnabled(false,false,false);
            setAllButtonVisible(false,false,false);
        }
        else if((getActivityInformationCursor.getString(3)).equals("REQUEST DECLINED") && (getActivityInformationCursor.getString(2)).equals(emailString)) {
            inActivityCategoryValueTextView.setText("RECEIVER");
            yourActivityStatusValueTextView.setText("INCOMING REQUEST DECLINED");
            setAllButtonEnabled(false,false,false);
            setAllButtonVisible(false,false,false);
        }
    }
    private void setAllButtonEnabled(Boolean isEnabledButton1,Boolean isEnabledButton2,Boolean isEnabledButton3) {
        Button acceptButton = (Button) findViewById(R.id.acceptButton);
        Button cancelButton = (Button) findViewById(R.id.cancelButton);
        Button declineButton = (Button) findViewById(R.id.declineButton);

        acceptButton.setEnabled(isEnabledButton1);
        cancelButton.setEnabled(isEnabledButton2);
        declineButton.setEnabled(isEnabledButton3);
    }

    private void setAllButtonVisible(Boolean isVisibleButton1,Boolean isVisibleButton2,Boolean isVisibleButton3) {
        Button acceptButton = (Button) findViewById(R.id.acceptButton);
        Button cancelButton = (Button) findViewById(R.id.cancelButton);
        Button declineButton = (Button) findViewById(R.id.declineButton);

        if (isVisibleButton1 == true) {
            acceptButton.setVisibility(View.VISIBLE);
        }
        else {
            acceptButton.setVisibility(View.INVISIBLE);
        }

        if (isVisibleButton2 == true) {
            cancelButton.setVisibility(View.VISIBLE);
        }
        else {
            cancelButton.setVisibility(View.INVISIBLE);
        }

        if (isVisibleButton3 == true) {
            declineButton.setVisibility(View.VISIBLE);
        }
        else {
            declineButton.setVisibility(View.INVISIBLE);
        }
    }
}
