package com.example.testapp.web;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

import com.example.testapp.R;
import com.example.testapp.feed.FeedScreen;

import flow.Flow;

public class WebViewUser extends WebView {
    public WebViewUser(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WebViewUser(Context context) {
        super(context);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getSettings().setJavaScriptEnabled(true);
        WebScreen screen = Flow.getKey(this);
        if (screen == null) {
            return;
        }
        loadUrl(screen.getLoginUrl());
    }
}
