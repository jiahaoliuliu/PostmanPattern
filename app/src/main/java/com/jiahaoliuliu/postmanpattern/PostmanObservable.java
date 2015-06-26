package com.jiahaoliuliu.postmanpattern;

import android.os.CountDownTimer;
import android.util.Log;

import java.util.Observable;
import java.util.Observer;
import java.util.Random;

/**
 * Created by jiahaoliuliu on 6/26/15.
 */
public class PostmanObservable extends Observable {

    private static final String TAG = "PostmanObservable";

    // The milliseconds to wait until the data is ready
    private static final int WAITING_MILLISECONDS = 500;

    private static final int MAXIMUM_FOLLOWERS = 10000;

    private SimpleCountDownTimer mSimpleCountDownTimer;

    /**
     * Random number generator
     */
    private Random mRandom;


    public PostmanObservable() {
        super();
        mRandom = new Random();
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
        mSimpleCountDownTimer = new SimpleCountDownTimer(WAITING_MILLISECONDS);
        mSimpleCountDownTimer.start();
    }

    private class SimpleCountDownTimer extends CountDownTimer {

        public SimpleCountDownTimer(long waiting_time) {
            super(waiting_time, waiting_time);
        }

        @Override
        public void onTick(long l) {
            // Not do anything
        }

        @Override
        public void onFinish() {
            int numberFollowers = mRandom.nextInt(MAXIMUM_FOLLOWERS);
            Log.v(TAG, numberFollowers + " followers found. Send the data back to the observer");
            setChanged();
            notifyObservers(numberFollowers);
        }
    }
}
