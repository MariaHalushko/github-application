package com.example.testapp.feed;

import android.os.Parcel;
import android.os.Parcelable;

public class FeedScreen implements Parcelable {
    private final String userName;

    public String getUserName() {
        return userName;
    }

    public FeedScreen(String loginUrl) {
        this.userName = loginUrl;
    }

    protected FeedScreen(Parcel in) {
        userName = in.readString();
    }

    public static final Creator<FeedScreen> CREATOR = new Creator<FeedScreen>() {
        @Override
        public FeedScreen createFromParcel(Parcel in) {
            return new FeedScreen(in.readString());
        }

        @Override
        public FeedScreen[] newArray(int size) {
            return new FeedScreen[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FeedScreen that = (FeedScreen) o;

        return userName.equals(that.userName);
    }

    @Override
    public int hashCode() {
        return userName.hashCode();
    }
}
