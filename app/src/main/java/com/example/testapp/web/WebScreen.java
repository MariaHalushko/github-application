package com.example.testapp.web;

import android.os.Parcel;
import android.os.Parcelable;

public class WebScreen implements Parcelable {
    private final String loginUrl;

    public String getLoginUrl() {
        return loginUrl;
    }

    public WebScreen(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    protected WebScreen(Parcel in) {
        loginUrl = in.readString();
    }

    public static final Creator<WebScreen> CREATOR = new Creator<WebScreen>() {
        @Override
        public WebScreen createFromParcel(Parcel in) {
            return new WebScreen(in.readString());
        }

        @Override
        public WebScreen[] newArray(int size) {
            return new WebScreen[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(loginUrl);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WebScreen that = (WebScreen) o;

        return loginUrl.equals(that.loginUrl);
    }

    @Override
    public int hashCode() {
        return loginUrl.hashCode();
    }

}
