package com.example.android.booklistingapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ProgressBar mProgressBar;
    private TextView mEmptyView;
    private ImageView mSearchButton;
    private TextInputEditText mSearchText;
    private BookAdapter mAdapter;
    private static final String GOOGLE_BOOKS_REQUEST_URL = "https://www.googleapis.com/books/v1/volumes?q=";
    public static final String LOG_TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mEmptyView = (TextView) findViewById(R.id.empty_view);

        if (isOnline()) {

            ListView listView = (ExpandableListView) findViewById(R.id.list);

            listView.setEmptyView(mEmptyView);

            mAdapter = new BookAdapter(this, new ArrayList<Book>());

            listView.setAdapter(mAdapter);
        }
        else{
            mProgressBar.setVisibility(View.GONE);
            mEmptyView.setText(R.string.no_connection);
        }
    }

    public boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
}
