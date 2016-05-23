package com.example.testapp;

import android.content.Context;
import android.os.StrictMode;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.testapp.feed.FeedScreen;
import com.example.testapp.web.WebScreen;

import flow.Dispatcher;
import flow.Flow;
import flow.Traversal;
import flow.TraversalCallback;

public class MainActivity extends AppCompatActivity implements Dispatcher {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    @Override
    protected void attachBaseContext(Context baseContext) {
        baseContext = Flow.configure(baseContext, this)
                .dispatcher(this)
                .defaultKey(new MainScreen())
                .install();
        super.attachBaseContext(baseContext);
    }

    @Override
    public void onBackPressed() {
        if (!getFlow().goBack()) {
            super.onBackPressed();
        }
    }

    @Override
    public void dispatch(@NonNull Traversal traversal, @NonNull TraversalCallback callback) {
        Object dest = traversal.destination.top();
        ViewGroup frame = (ViewGroup) findViewById(R.id.main_frame);

        if (traversal.origin != null) {
            if ((frame != null ? frame.getChildCount() : 0) > 0) {
                traversal.getState(traversal.origin.top()).save(frame.getChildAt(0));
                frame.removeAllViews();
            }
        }

        @LayoutRes final int layout;
        if (dest instanceof MainScreen) {
            layout = R.layout.main_screen;
        } else if (dest instanceof FeedScreen) {
            layout = R.layout.feed_screen;
        } else if (dest instanceof WebScreen) {
            layout = R.layout.web_screen;
        } else {
            throw new AssertionError("Unrecognized screen " + dest);
        }

        View incomingView = LayoutInflater.from(traversal.createContext(dest, this))
                .inflate(layout, frame, false);
        traversal.getState(traversal.destination.top()).restore(incomingView);
        if (frame != null) {
            frame.addView(incomingView);
        }
        callback.onTraversalCompleted();
    }

    private Flow getFlow() {
        return Flow.get(this);
    }
}
