package com.hamdi.hasan.hi_news.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Owner on 3/22/2018.
 */

public class articles implements Parcelable {

    private source source;
    private String author, title, description, url, urlToImage, publishedAt;
    private int isFav;


    public articles(com.hamdi.hasan.hi_news.Model.source source, String author, String title, String description, String url, String urlToImage, String publishedAt, int IsFav)
    {
        this.source = source;
        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
        this.isFav = IsFav;
    }

    protected articles(Parcel in) {
        author = in.readString();
        title = in.readString();
        description = in.readString();
        url = in.readString();
        urlToImage = in.readString();
        publishedAt = in.readString();
        isFav = in.readInt();
    }

    public static final Creator<articles> CREATOR = new Creator<articles>() {
        @Override
        public articles createFromParcel(Parcel in) {
            return new articles(in);
        }

        @Override
        public articles[] newArray(int size) {
            return new articles[size];
        }
    };

    public com.hamdi.hasan.hi_news.Model.source getSource() {
        return source;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public int getIsFav() {
        return isFav;
    }

    public void setIsFav(int isFav) {
        this.isFav = isFav;
    }

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(author);
        parcel.writeString(title);
        parcel.writeString(description);
        parcel.writeString(url);
        parcel.writeString(urlToImage);
        parcel.writeString(publishedAt);
        parcel.writeInt(isFav);
    }
}
