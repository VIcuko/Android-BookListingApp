package com.example.android.booklistingapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {

    private ProgressBar mProgressBar;
    private TextView mEmptyView;
    private ImageView mSearchButton;
    private TextInputEditText mSearchText;
    private BookAdapter mAdapter;
    private static final String GOOGLE_BOOKS_REQUEST_URL = "https://www.googleapis.com/books/v1/volumes?q=";
    public static final String LOG_TAG = MainActivity.class.getName();
    private static final int BOOK_LOADER_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mEmptyView = (TextView) findViewById(R.id.empty_view);

        mSearchButton = (ImageView) findViewById(R.id.search_icon);
        mSearchText = (TextInputEditText) findViewById(R.id.search_field);

        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchQuery(mSearchText.getText().toString());
            }
        });

        mSearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                launchQuery(mSearchText.getText().toString());
                return true;
            }
        });
    }

    public boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    @Override
    public Loader<List<Book>> onCreateLoader(int i, Bundle bundle) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {

    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {

    }

    private void launchQuery(String searchText) {
        ExpandableListView listView;

        if (searchText != null && searchText != "") {

            if (isOnline()) {

                LoaderManager loaderManager = getLoaderManager();
                loaderManager.initLoader(BOOK_LOADER_ID, null, this);

                listView = (ExpandableListView) findViewById(R.id.list);
                listView.setEmptyView(mEmptyView);

                mAdapter = new BookAdapter(this, new ArrayList<Book>());

                listView.setAdapter(mAdapter);
            } else {
                mProgressBar.setVisibility(View.GONE);
                mEmptyView.setText(R.string.no_connection);
            }
        }
        else{
            Toast.makeText(this, "You need to introduce some text to search", Toast.LENGTH_LONG);
        }

    }
}