package com.example.testapp;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import com.example.testapp.feed.FeedScreen;

import flow.Flow;

public class MainView extends FrameLayout {

    public MainView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        Button button = (Button) findViewById(R.id.btn_ok);
        final EditText userName = (EditText) findViewById(R.id.user_name);
        if (button != null) {

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getFlow().set(new FeedScreen(userName.getText().toString() ));
                }
            });
        }
    }

    private Flow getFlow() {
        return Flow.get(getContext());
    }
}
