package com.example.android.booklistingapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import static com.example.android.booklistingapp.R.id.description_text;
import static com.example.android.booklistingapp.R.id.isbn_code;

/**
 * Created by Vicuko on 7/8/17.
 */

public class BookAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private ArrayList<Book> mbooks;
    private Bitmap bmp;


    public BookAdapter(Context context, ArrayList<Book> booksArrayList) {
        mContext = context;
        mbooks = booksArrayList;
    }

    @Override
    public int getGroupCount() {
        return mbooks.size();
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public Object getGroup(int i) {
        return mbooks.get(i);
    }

    @Override
    public View getGroupView(int i, boolean b, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }

        TextView title = (TextView) convertView.findViewById(R.id.title);
        title.setText(mbooks.get(i).getTitle());

        TextView author = (TextView) convertView.findViewById(R.id.author);
        author.setText(parseAuthors(mbooks.get(i).getAuthors()));

//        TextView year = (TextView) convertView.findViewById(R.id.published_year);
//        year.setText(mbooks.get(i).getPublishedDate().substring(0,4));

        final ImageView bookThumbnail = (ImageView) convertView.findViewById(R.id.book_image);
        final String bookUrl = mbooks.get(i).getThumbnailURL();


//        new AsyncTask<Void, Void, Void>() {
//            @Override
//            protected Void doInBackground(Void... params) {
                try {
                    InputStream in = new URL(bookUrl).openStream();
                    bmp = BitmapFactory.decodeStream(in);
                    if (bmp != null)
                        bookThumbnail.setImageBitmap(bmp);
                } catch (Exception e) {
                    // log error
                }

        return convertView;
    }

    @Override
    public int getChildrenCount(int i) {
        return mbooks.size();
    }

    @Override
    public long getChildId(int i, int i1) {
        return i;
    }

    @Override
    public Object getChild(int i, int i1) {
        return mbooks.get(i);
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item, null);
        }

        TextView descriptionLabel = (TextView) convertView.findViewById(R.id.descripton_label);
        descriptionLabel.setText(R.string.description_label);

        TextView descriptionText = (TextView) convertView.findViewById(description_text);
        descriptionText.setText(mbooks.get(i).getDescription());

        TextView isbnLabel = (TextView) convertView.findViewById(R.id.isbn_label);
        isbnLabel.setText(R.string.isbn_label);

        TextView isbnCode = (TextView) convertView.findViewById(isbn_code);
        isbnCode.setText(mbooks.get(i).getIsbn());

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }


    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

    private String parseAuthors(ArrayList<String> authors) {
        String authorsTogether="";
        if (authors != null) {
            for (int i = 0; i < authors.size(); i++) {
                authorsTogether = authorsTogether.concat(authors.get(i).toString());
                String separator;
                if (i==authors.size()-2){
                    separator = " and ";
                }
                else {
                    separator = (i == (authors.size() - 1)) ? ("") : (", ");
                }
                authorsTogether = authorsTogether.concat(separator);
            }
        }
        return authorsTogether;
    }
}
