package ru.pin120.via.accountingsoftwaremobile.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
public class Software implements Parcelable {
    protected Software(Parcel in) {
        id = in.readLong();
        name = in.readString();
        description = in.readString();
        image = in.readString();
        link = in.readString();
        systemRequirements = in.readString();
        licenseName = in.readString();
        licenseType = in.readString();
        licensePrice = in.readDouble();
        licenseDuration = in.readDouble();
        comments = new ArrayList<>();
        in.readList(comments, Comments.class.getClassLoader());

        screens = new ArrayList<>();
        in.readList(screens, Screens.class.getClassLoader());

        categories = new ArrayList<>();
        in.readList(categories, Categories.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // Добавьте этот метод
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(image);
        dest.writeString(link);
        dest.writeString(systemRequirements);
        dest.writeString(licenseName);
        dest.writeString(licenseType);
        dest.writeDouble(licensePrice);
        dest.writeDouble(licenseDuration);
        dest.writeList(comments);
        dest.writeList(screens);
        dest.writeList(categories);
        // Дополните остальные поля
    }

    // Добавьте этот статический CREATOR
    public static final Creator<Software> CREATOR = new Creator<Software>() {
        @Override
        public Software createFromParcel(Parcel in) {
            return new Software(in);
        }

        @Override
        public Software[] newArray(int size) {
            return new Software[size];
        }
    };
    private long id;
    private String name;
    private String description;
    private String image;
    private String link;

    private String systemRequirements;
    private String licenseName;
    private String licenseType;
    private double licensePrice;
    private double licenseDuration;
    private List<Categories> categories;
    private List<Comments> comments;
    private List<Screens> screens;

    public Software(String name, String description, String image, String link, String systemRequirements, String licenseName, String licenseType, double licensePrice, double licenseDuration) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.link = link;
        this.systemRequirements = systemRequirements;
        this.licenseName = licenseName;
        this.licenseType = licenseType;
        this.licensePrice = licensePrice;
        this.licenseDuration = licenseDuration;
    }

    public String getName() {
        return name;
    }

    public String getLicenseName() {
        return  licenseName;
    }

    public String getLicenseType() {
        return  licenseType;
    }

    public double getLicensePrice() {
        return  licensePrice;
    }

    public double getLicenseDuration() {
        return  licenseDuration;
    }

    public List<Categories> getCategories() {
        return categories;
    }

    public String getImageUrl() {
        return image;
    }

    public long getId() {
        return id;
    }

    public String getDescription() {
        return  description;
    }

    public String getSystemRequirements() {
        return  systemRequirements;
    }

    public String getLink() {
        return  link;
    }

    public List<Screens> getScreens() {
        return screens;
    }
    public List<Comments> getComments() {
        return  comments;
    }
}
