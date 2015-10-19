package com.example.meilin.speedreader;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.LinkedList;

public class ReadingActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading);

        Button play = (Button) findViewById(R.id.play);
        play.setOnClickListener(PlayReader);

        Button pause = (Button) findViewById(R.id.pause);
        pause.setOnClickListener(PauseReader);

        Button stop = (Button) findViewById(R.id.stop);
        stop.setOnClickListener(StopReader);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_reading, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected Reader readerThread;

    private View.OnClickListener PlayReader = new View.OnClickListener() {
        public void onClick(View v) {
            if(readerThread == null) {
                TextView tv = (TextView) findViewById(R.id.textView);
                tv.setTextSize(24);
                LinkedList<String> list = parseText();
                readerThread = new Reader(tv, list, ReadingActivity.this);
                readerThread.start();
            } else {
                //resuming
                readerThread.resume();
            }
        }
    };

    private View.OnClickListener PauseReader = new View.OnClickListener() {
        public void onClick(View v) {
            readerThread.suspend();
        }
    };

    private View.OnClickListener StopReader = new View.OnClickListener() {
        public void onClick(View v) {

        }
    };

    private LinkedList<String> parseText() {
        LinkedList<String> list = new LinkedList<String>();
        String temp = "";
        String text = getResources().getString(R.string.string_read);
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == ' ') {//add more conditions
                list.add(temp);
                temp = "";
            } else {
                temp += text.charAt(i);
            }
        }
        return list;
    }



/*
    boolean running;
    boolean onPaused = false;
    final TextView tv = (TextView) findViewById(R.id.textView);

    private View.OnClickListener PlayReader = new View.OnClickListener() {
        public void onClick(View v) {
            tv.setTextSize(24);
            running = true;
            LinkedList<String> list = parseText();
            if(onPaused) {
                ActivateReader(list);
            } else {
                list = parseText();
                ActivateReader(list);
            }
        }
    };

    public void ActivateReader(final LinkedList<String> q) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    while (running) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (!q.isEmpty()) {
                                    tv.setText(q.remove());
                                } else {
                                    //stop
                                    running = false;
                                }
                            }
                        });
                        Thread.sleep(2000);
                    }
                } catch (InterruptedException e) {
                    running = false;
                }
            }
        }).start();
    }

    private View.OnClickListener PauseReader = new View.OnClickListener() {
        public void onClick(View v) {
            running = false;
            onPaused=true;
        }
    };

    private View.OnClickListener StopReader = new View.OnClickListener() {
        public void onClick(View v) {
            running = false;
            onPaused = false;
            //restart
        }
    };

*/
    }
