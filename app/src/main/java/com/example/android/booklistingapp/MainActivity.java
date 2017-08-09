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
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ListView;
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
    private String mQueryText;
    private ListView mListView;
    private LoaderManager mLoaderManager;
    private static final String GOOGLE_BOOKS_REQUEST_URL = "https://www.googleapis.com/books/v1/volumes?q=";
    public static final String LOG_TAG = MainActivity.class.getName();
    private static final int BOOK_LOADER_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLoaderManager = getLoaderManager();

        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mEmptyView = (TextView) findViewById(R.id.empty_view);

        mListView = (ListView) findViewById(R.id.list);
        mListView.setEmptyView(mEmptyView);

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
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    launchQuery(mSearchText.getText().toString());
                    handled = true;}
                return handled;
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
        return new BookLoader(this, GOOGLE_BOOKS_REQUEST_URL + mQueryText);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {
        mAdapter.clear();

        if (books != null && !books.isEmpty()) {
            mAdapter.addAll(books);
        }
        mProgressBar.setVisibility(View.GONE);
        mEmptyView.setText(R.string.empty_view_text);
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        mAdapter.clear();
    }

    private void launchQuery(String searchText) {
        mSearchText.clearFocus();
        mEmptyView.setText("");
        mEmptyView.setVisibility(View.GONE);
        mQueryText = searchText;

        if (!searchText.isEmpty() && searchText != null) {

            if (isOnline()) {
                mProgressBar.setVisibility(View.VISIBLE);

                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mSearchText.getWindowToken() ,InputMethodManager.HIDE_NOT_ALWAYS);

                mLoaderManager.restartLoader(BOOK_LOADER_ID, null, this);

                mAdapter = new BookAdapter(this, new ArrayList<Book>());

                mListView.setAdapter(mAdapter);

            }
            else {
                mProgressBar.setVisibility(View.GONE);
                mEmptyView.setText(R.string.no_connection);
            }
        }
        else{
            Toast.makeText(this, "You need to introduce some text to search", Toast.LENGTH_LONG).show();
        }
    }
}