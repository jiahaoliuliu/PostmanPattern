package com.jiahaoliuliu.postmanpattern;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Observable;

public class MainActivity extends PostmanActivity {

    private static final String TAG = "MainActivity";

    // The context
    private Context mContext;

    // Views
    private Button mRequestDataButton;
    private TextView mNumberFollowersTextView;

    // Other data
    private PostmanObservable mPostmanObservable;
    private int mNumberFollowers = -1;

    // Response data
    // This is the data received from the backend.
    private int response = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        mPostmanObservable = new PostmanObservable();

        // Link the views
        mRequestDataButton = (Button) findViewById(R.id.request_data_button);
        mNumberFollowersTextView = (TextView) findViewById(R.id.number_followers_text_view);

        mRequestDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Requesting data to the observer
                // and go to another activity
                Log.v(TAG, "Requesting data to the observable");
                mPostmanObservable.requestNumberFollowers(MainActivity.this);
                Intent startAnotherActivityIntent = new Intent(mContext, AnotherActivity.class);
                startActivity(startAnotherActivityIntent);
            }
        });
    }

    @Override
    public void update(Observable observable, Object o) {
        Log.v(TAG, "Update received from the observable " + observable + " with data " + o);
        if (observable instanceof  PostmanObservable) {
            Log.v(TAG, "It was from the Postman observable");
            if (o instanceof Integer) {
                response = (Integer) o;
                Log.v(TAG, "response received " + response);

                // If at the moment when the data is received
                // the activity is in the foreground, process it.
                if (isInForeground) {
                    Log.v(TAG, "The activity is in the foreground. Processing data");
                    processDataIfExists();
                } else {
                    Log.v(TAG, "The activity is not in the foreground. Not processing data");
                }

                // Remove the observer since it is not used
                Log.v(TAG, "Delete the observer");
                observable.deleteObserver(this);
            }
        }
    }

    @Override
    protected void processDataIfExists() {
        Log.v(TAG, "Processing data if exists");
        // 1. Check if the data exists
        if (response == -1) {
            Log.v(TAG, "There is not new data to be proceed. Exit");
            return;
        }

        // 2. Process data
        // In this case it is a simple assignation. It could be more
        // complicate depending on the nature of the data
        Log.v(TAG, "Processing data");
        mNumberFollowers = response;
        mNumberFollowersTextView.setText(
                getString(R.string.number_followers_text, mNumberFollowers)
        );
        mNumberFollowersTextView.setVisibility(View.VISIBLE);

        // 3. Remove data
        Log.v(TAG, "Removing data");
        response = -1;
    }
}
