package com.example.testapp;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.example.testapp.R;
import com.example.testapp.feed.FeedScreen;

import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;

import java.io.IOException;

import flow.Flow;

public class MainView extends FrameLayout {

    public MainView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();


        Button button = (Button) findViewById(R.id.btn);

        final EditText userName = (EditText) findViewById(R.id.user_name);
        if (button != null) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("tag", "onClick: user name " + userName.getText());
                    getFlow().set(new FeedScreen(userName.getText().toString() ));
                }
            });
            //v -> getFlow().set(new FeedScreen(userName != null ?
//                    userName.getText().toString() : null)));
        }
    }

    private Flow getFlow() {
        return Flow.get(getContext());
    }
}
