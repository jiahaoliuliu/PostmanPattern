package com.jiahaoliuliu.postmanpattern;

import android.os.Handler;
import android.util.Log;

import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by jiahaoliuliu on 6/26/15.
 */
public class SimpleObservable extends Observable {

    private static final String TAG = "SimpleObservable";

    // The milliseconds to wait until the data is ready
    private static final int WAITING_MILLISECONDS = 2000;

    private static final int MAXIMUM_FOLLOWERS = 10000;

    /**
     * Random number generator
     */
    private Random mRandom;

    public SimpleObservable() {
        super();
        this.mRandom = new Random();
    }

    /**
     * This method simulates the process which request
     * the number of followes. It has a delay of 1 seconds
     * and then generate a random data.
     *
     * @param observer
     *      The observer to be notified when the data is ready
     */
    public void requestNumberFollowers(Observer observer) {
        Log.v(TAG, "Requesting the number of followers");

        // Register the observer
        addObserver(observer);
        final Handler handler = new Handler();
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        int numberFollowers = mRandom.nextInt(MAXIMUM_FOLLOWERS);
                        Log.v(TAG, numberFollowers + " followers found. Send the data back to the observer");
                        setChanged();
                        notifyObservers(numberFollowers);
                    }
                });
            }
        }, WAITING_MILLISECONDS);
    }
}
