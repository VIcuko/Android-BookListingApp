package com.example.android.booklistingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Vicuko on 7/8/17.
 */

public class BookAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private ArrayList<Book> mbooks;


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
    public View getGroupView(int i, boolean b, View groupView, ViewGroup viewGroup) {
        if (groupView == null) {
            LayoutInflater infalInflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            groupView = infalInflater.inflate(R.layout.list_group, null);
        }

        TextView title = (TextView) groupView.findViewById(R.id.title);
        title.setText(mbooks.get(i).getTitle());

        TextView author = (TextView) groupView.findViewById(R.id.author);
        author.setText(parseAuthors(mbooks.get(i).getAuthors()));

        TextView year = (TextView) groupView.findViewById(R.id.published_year);
        year.setText(mbooks.get(i).getPublishedDate().substring(0,3));

        return groupView;
    }

    @Override
    public int getChildrenCount(int i) {
        return mbooks.size();
    }


    @Override
    public Object getChild(int i, int i1) {
        return mbooks.get(i);
    }


    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }


    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        return null;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

    private String parseAuthors(ArrayList<String> authors) {
        String authorsTogether="";
        if (authors != null || authors.get(0) != null) {
            for (int i = 0; i < authors.size(); i++) {
                authorsTogether.concat(authors.get(i).toString());
                String separator;
                if (i==authors.size()-2){
                    separator = " and ";
                }
                else {
                    separator = (i == (authors.size() - 1)) ? (".") : (", ");
                }
                authorsTogether.concat(separator);
            }
        }
        return authorsTogether;
    }
}
