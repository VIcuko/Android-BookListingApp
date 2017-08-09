package com.example.android.booklistingapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import static com.example.android.booklistingapp.R.id.description_text;
import static com.example.android.booklistingapp.R.id.isbn_code;

public class DetailsActivity extends AppCompatActivity {
    String mDescription;
    String mIsbn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_item);

        Bundle extras = getIntent().getExtras();
        mDescription = extras.getString("description");
        mIsbn = extras.getString("isbn");

        TextView descriptionLabel = (TextView) findViewById(R.id.descripton_label);
        descriptionLabel.setText(R.string.description_label);

        TextView descriptionText = (TextView) findViewById(description_text);
        descriptionText.setText(mDescription);

        TextView isbnLabel = (TextView) findViewById(R.id.isbn_label);
        isbnLabel.setText(R.string.isbn_label);

        TextView isbnCode = (TextView) findViewById(isbn_code);
        isbnCode.setText(mIsbn);
    }
}
