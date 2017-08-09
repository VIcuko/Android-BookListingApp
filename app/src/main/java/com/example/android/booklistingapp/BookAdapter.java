package com.example.android.booklistingapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Vicuko on 7/8/17.
 */

public class BookAdapter extends ArrayAdapter<Book> {

    private Context mContext;
    private ArrayList<Book> mbooks;
    private Bitmap bmp;


    public BookAdapter(Context context, ArrayList<Book> bookArray) {
        super(context, 0, bookArray);
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listView = convertView;
        if (listView == null) {
            listView = LayoutInflater.from(getContext()).inflate(R.layout.list_group, parent, false);
        }

        final Book book = getItem(position);

        TextView title = (TextView) listView.findViewById(R.id.title);
        title.setText(book.getTitle());

        TextView author = (TextView) listView.findViewById(R.id.author);
        author.setText(parseAuthors(book.getAuthors()));

        TextView year = (TextView) listView.findViewById(R.id.published_year);
        year.setText(book.getPublishedDate());

        final ImageView bookThumbnail = (ImageView) listView.findViewById(R.id.book_image);
        bookThumbnail.setImageBitmap(book.getBitmap());

        RelativeLayout itemLayout = (RelativeLayout) listView.findViewById(R.id.item_layout);
        View.OnClickListener onClickListener = new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent detailsIntent = new Intent(getContext(), DetailsActivity.class);
                detailsIntent.putExtra("description", book.getDescription()).putExtra("isbn", book.getIsbn());
                getContext().startActivity(detailsIntent);
            }
        };
        itemLayout.setOnClickListener(onClickListener);

        return listView;
    }

//    @Override
//    public View getChildView(int i, int i1, boolean b, View convertView, ViewGroup viewGroup) {
//        if (convertView == null) {
//            LayoutInflater infalInflater = (LayoutInflater) mContext
//                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            convertView = infalInflater.inflate(R.layout.list_item, null);
//        }
//
//        TextView descriptionLabel = (TextView) convertView.findViewById(R.id.descripton_label);
//        descriptionLabel.setText(R.string.description_label);
//
//        TextView descriptionText = (TextView) convertView.findViewById(description_text);
//        descriptionText.setText(mbooks.get(i).getDescription());
//
//        TextView isbnLabel = (TextView) convertView.findViewById(R.id.isbn_label);
//        isbnLabel.setText(R.string.isbn_label);
//
//        TextView isbnCode = (TextView) convertView.findViewById(isbn_code);
//        isbnCode.setText(mbooks.get(i).getIsbn());
//
//        return convertView;
//    }

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
