package ru.pin120.via.accountingsoftwaremobile.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Comments implements Parcelable {
    private long id;
    private String comment;
    private List<Software> softwares;

    public Comments(String commentText) {
        this.comment = commentText;
    }

    public String getComment() {
        return comment;
    }

    protected Comments(Parcel in) {
        id = in.readLong();
        comment = in.readString();
        softwares = in.createTypedArrayList(Software.CREATOR);
    }

    public static final Creator<Comments> CREATOR = new Creator<Comments>() {
        @Override
        public Comments createFromParcel(Parcel in) {
            return new Comments(in);
        }

        @Override
        public Comments[] newArray(int size) {
            return new Comments[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(comment);
        dest.writeTypedList(softwares);
    }
}
