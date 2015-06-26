package com.jiahaoliuliu.postmanpattern;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Observable;

public class MainActivity extends PostmanActivity {

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
                mPostmanObservable.requestNumberFollowers(MainActivity.this);
                Intent startAnotherActivityIntent = new Intent(mContext, AnotherActivity.class);
                startActivity(startAnotherActivityIntent);
            }
        });
    }

    @Override
    public void update(Observable observable, Object o) {
        if (observable instanceof  PostmanObservable) {
            if (o instanceof Integer) {
                response = (Integer) o;

                // If at the moment when the data is received
                // the activity is in the foreground, process it.
                if (isInForeground) {
                    processDataIfExists();
                }

                // Remove the observer since it is not used
                observable.deleteObserver(this);
            }
        }
    }

    @Override
    protected void processDataIfExists() {
        // 1. Check if the data exists
        if (response == -1) {
            return;
        }

        // 2. Process data
        // In this case it is a simple assignation. It could be more
        // complicate depending on the nature of the data
        mNumberFollowers = response;
        mNumberFollowersTextView.setText(
                getString(R.string.number_followers_text, mNumberFollowers)
        );
        mNumberFollowersTextView.setVisibility(View.VISIBLE);

        // 3. Remove data
        response = -1;
    }
}
