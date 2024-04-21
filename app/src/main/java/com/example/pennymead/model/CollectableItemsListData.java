package com.example.pennymead.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CollectableItemsListData implements Parcelable {
    @SerializedName("title")
    String title;
    @SerializedName("price")
    String price;
    @SerializedName("image")
    List<String> image;
    @SerializedName("author")
    String author;
    @SerializedName("description")
    String description;
    @SerializedName("sysid")
    String sysid;
    @SerializedName("category")
    String category;

    protected CollectableItemsListData(Parcel in) {
        title = in.readString();
        price = in.readString();
        image = in.createStringArrayList();
        author = in.readString();
        description = in.readString();
        sysid = in.readString();
        category = in.readString();
    }

    public static final Creator<CollectableItemsListData> CREATOR = new Creator<CollectableItemsListData>() {
        @Override
        public CollectableItemsListData createFromParcel(Parcel in) {
            return new CollectableItemsListData(in);
        }

        @Override
        public CollectableItemsListData[] newArray(int size) {
            return new CollectableItemsListData[size];
        }
    };

    public String getSysid() {
        return sysid;
    }

    public String getCategory() {
        return category;
    }

    public String getTitle() {
        return title;
    }

    public String getPrice() {
        return price;
    }

    public List<String> getImage() {
        return image;
    }

    public String getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public int describeContents() {
        return this.hashCode();
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(price);
        dest.writeStringList(image);
        dest.writeString(author);
        dest.writeString(description);
        dest.writeString(sysid);
        dest.writeString(category);
    }
}
