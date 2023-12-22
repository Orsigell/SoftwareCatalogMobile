package ru.pin120.via.accountingsoftwaremobile.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Screens implements Parcelable {
    private long id;
    private String screen;
    private List<Software> softwares;

    public Screens(String path) {
        this.screen = path;
    }

    public long getId() {
        return id;
    }

    public String getScreen() {
        return screen;
    }

    protected Screens(Parcel in) {
        id = in.readLong();
        screen = in.readString();
        softwares = in.createTypedArrayList(Software.CREATOR);
    }

    public static final Creator<Screens> CREATOR = new Creator<Screens>() {
        @Override
        public Screens createFromParcel(Parcel in) {
            return new Screens(in);
        }

        @Override
        public Screens[] newArray(int size) {
            return new Screens[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(screen);
        dest.writeTypedList(softwares);
    }
}
