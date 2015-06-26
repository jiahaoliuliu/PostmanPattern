package com.jiahaoliuliu.postmanpattern;

import android.support.v7.app.AppCompatActivity;

import java.util.Observer;

/**
 * Created by jiahaoliuliu on 6/26/15.
 */
public abstract class PostmanActivity extends AppCompatActivity implements Observer{

    protected boolean isInForeground;

    /**
     * Method created to process the data from the observable if exists.
     * It has three main steps:
     * 1. Check if the data exists. If not exit
     * 2. Process the data
     * 3. Remove the data
     */
    protected abstract void processDataIfExists();

    @Override
    protected void onResume() {
        super.onResume();
        isInForeground = true;
        processDataIfExists();
    }

    @Override
    protected void onPause() {
        isInForeground = false;
        super.onPause();
    }
}
