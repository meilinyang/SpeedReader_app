package com.example.meilin.speedreader;

import android.app.ActionBar;
import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

import java.util.LinkedList;

/**
 * Created by Mei Lin on 10/14/2015.
 */
public class Reader implements Runnable {

    private Thread t;
    boolean running = true;
    boolean suspended = false;
    TextView tv;
    LinkedList<String> list;
    Activity currentActivity;

    public Reader(TextView tv, LinkedList<String> list, Activity currentActivity) {
        this.tv = tv;
        this.list = list;
        this.currentActivity = currentActivity;
    }

    public void setList(LinkedList<String> list) {
        this.list = list;
    }

    public void start() {
        if (t == null) {
            t = new Thread(this, "derp");
            t.start();
        }
    }

    @Override
    public void run() {
        try {
            while (running) {
                currentActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!list.isEmpty()) {
                            tv.setText(list.remove());
                        } else {
                            //stop
                            running = false;
                        }
                    }
                });
                Thread.sleep(2000);
                synchronized (this) {
                    while (suspended) {
                        wait();
                    }
                }
            }
        } catch (InterruptedException e) {
            running = false;
        }
    }

    void suspend() {
        suspended = true;
    }

    synchronized void resume() {
        suspended = false;
        notify();
    }

}
