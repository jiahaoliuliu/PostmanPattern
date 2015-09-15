package com.jiahaoliuliu.postmanpattern;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Observable;
import java.util.Observer;

public class MainActivity extends AppCompatActivity implements Observer {

    private static final String TAG = "MainActivity";

    // The context
    private Context mContext;

    // Views
    private Button mRequestDataButton;
    private TextView mNumberFollowersTextView;

    // Other data
    private SimpleObservable mSimpleObservable;
    private int mNumberFollowers = -1;

    // Response data
    // This is the data received from the backend.
    private int response = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        mSimpleObservable = new SimpleObservable();

        // Link the views
        mRequestDataButton = (Button) findViewById(R.id.request_data_button);
        mNumberFollowersTextView = (TextView) findViewById(R.id.number_followers_text_view);

        mRequestDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Requesting data to the observer
                // and go to another activity
                Log.v(TAG, "Requesting data to the observable");
                Intent startAnotherActivityIntent = new Intent(mContext, AnotherActivity.class);
                startActivity(startAnotherActivityIntent);
                mSimpleObservable.requestNumberFollowers(MainActivity.this);
            }
        });
    }

    @Override
    public void update(Observable observable, Object o) {
        Log.v(TAG, "Update received from the observable " + observable + " with data " + o);
        if (observable instanceof SimpleObservable) {
            Log.v(TAG, "It was from the Postman observable");
            if (o instanceof Integer) {
                response = (Integer) o;
                Log.v(TAG, "response received " + response);

                // Update the text view
                mNumberFollowers = response;
                mNumberFollowersTextView.setText(
                        getString(R.string.number_followers_text, mNumberFollowers)
                );
                mNumberFollowersTextView.setVisibility(View.VISIBLE);

                // Remove the observer since it is not used
                Log.v(TAG, "Delete the observer");
                observable.deleteObserver(this);
            }
        }
    }
}
