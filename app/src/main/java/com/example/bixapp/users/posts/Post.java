package com.example.bixapp.users.posts;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.bixapp.common.requests.Link;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class Post implements Parcelable {

    public static final Creator<Post> CREATOR = new Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };
    private int id;
    @SerializedName("user_id")
    private String userId;
    private String title;
    private String body;
    @SerializedName("_links")
    private Map<String, Link> links;

    protected Post(Parcel in) {
        id = in.readInt();
        userId = in.readString();
        title = in.readString();
        body = in.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(userId);
        parcel.writeString(title);
        parcel.writeString(body);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Map<String, Link> getLinks() {
        return links;
    }

    public void setLinks(Map<String, Link> links) {
        this.links = links;
    }

}

