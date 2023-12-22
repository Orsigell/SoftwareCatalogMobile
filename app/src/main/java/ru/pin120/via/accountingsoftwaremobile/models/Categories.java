package ru.pin120.via.accountingsoftwaremobile.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Categories implements Parcelable {
    private long id;
    private String name;
    private List<Software> softwares;

    public Categories(String text) {
        this.name = text;
    }

    public String getName() {
        return name;
    }

    protected Categories(Parcel in) {
        id = in.readLong();
        name = in.readString();
        softwares = in.createTypedArrayList(Software.CREATOR);
    }

    public static final Creator<Categories> CREATOR = new Creator<Categories>() {
        @Override
        public Categories createFromParcel(Parcel in) {
            return new Categories(in);
        }

        @Override
        public Categories[] newArray(int size) {
            return new Categories[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeTypedList(softwares);
    }
}
