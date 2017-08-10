package com.example.android.booklistingapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static com.example.android.booklistingapp.MainActivity.LOG_TAG;

/**
 * Created by Vicuko on 8/8/17.
 */

public final class QueryUtils {

    private QueryUtils() {
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the book JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    public static ArrayList<Book> extractBooks(String bookJSON) {

        if (TextUtils.isEmpty(bookJSON)) {
            return null;
        }

        ArrayList<Book> books = new ArrayList<>();

        try {
            JSONObject jsonObj = new JSONObject(bookJSON);

            JSONArray features = jsonObj.getJSONArray("items");

            for (int i = 0; i < features.length(); i++) {
                JSONObject bookInfo = features.getJSONObject(i);
                JSONObject properties = bookInfo.getJSONObject("volumeInfo");

                String title = properties.has("title") ? properties.getString("title") : "";

                ArrayList<String> authors = new ArrayList<String>();
                JSONArray jArray = properties.has("authors") ? properties.getJSONArray("authors") : null;
                if (jArray != null) {
                    for (int j = 0; j < jArray.length(); j++) {
                        authors.add(jArray.getString(j));
                    }
                }

                String publishedDate = properties.has("publishedDate") ? properties.getString("publishedDate").substring(0, 4) : "";

                String description = properties.has("description") ? properties.getString("description") : "";

                JSONObject imageLinks = properties.has("imageLinks") ? properties.getJSONObject("imageLinks") : null;

                String urlString = "";
                Bitmap bitmap = null;

                if (imageLinks != null) {
                    urlString = imageLinks.has("smallThumbnail") ? imageLinks.getString("smallThumbnail") : "";
                    try {
                        URL url = new URL(urlString);
                        bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

                JSONArray industryIdentifiers = properties.has("industryIdentifiers") ? properties.getJSONArray("industryIdentifiers") : null;

                String isbn = "";
                if (industryIdentifiers != null) {

                    for (int k = 0; k < industryIdentifiers.length(); k++) {
                        JSONObject isbnObject = industryIdentifiers.getJSONObject(k);
                        String isbnType = isbnObject.getString("type");
                        if (industryIdentifiers.length() == 1) {
                            isbn = isbnObject.getString("identifier");
                        } else {
                            if (Integer.parseInt(isbnType.substring(5)) == 13) {
                                isbn = isbnObject.has("identifier") ? isbnObject.getString("identifier") : "";
                            }
                        }
                    }
                }

                Book book = new Book(title, authors, description, publishedDate, isbn, urlString, bitmap);
                books.add(book);
            }

        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the book JSON results", e);
        }

        return books;
    }

    public static List<Book> fetchBooksData(String requestUrl) {
        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }
        List<Book> books = extractBooks(jsonResponse);

        return books;
    }

}
