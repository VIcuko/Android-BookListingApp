package com.example.android.booklistingapp;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by Vicuko on 7/8/17.
 */

public class Book {
    String mTitle;
    ArrayList<String> mAuthors;
    String mDescription;
    String mPublishedDate;
    String mIsbn;
    String mThumbnailURL;
    Bitmap mThumbnailBitmap;

    public Book(String title, ArrayList<String> authors, String description,
                String publishedDate, String isbn, String thumbnailUrl, Bitmap thumbnailBitmap){

        mTitle = title;
        mAuthors = authors;
        mDescription = description;
        mPublishedDate = publishedDate;
        mIsbn = isbn;
        mThumbnailURL = thumbnailUrl;
        mThumbnailBitmap = thumbnailBitmap;
    }

    public String getTitle(){
      return mTitle;
    }

    public ArrayList<String> getAuthors(){
        return mAuthors;
    }

    public String getDescription(){
        return mDescription;
    }

    public String getPublishedDate(){
        return mPublishedDate;
    }

    public String getIsbn(){
        return mIsbn;
    }

    public String getThumbnailURL(){
        return mThumbnailURL;
    }
}
