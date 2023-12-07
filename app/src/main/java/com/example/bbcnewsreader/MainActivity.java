package com.example.bbcnewsreader;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Locale;

import androidx.appcompat.widget.Toolbar;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat; // Import for PreferenceFragmentCompat

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ListView newsListView;
    private ArrayAdapter<String> adapter;
    private ArrayList<NewsItem> newsItems;
    //private AppDatabase appDatabase;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        setupNavigationDrawer();

        getSupportActionBar().setTitle("Home");

        // Fetch news headlines
        new FetchDataTask().execute("https://feeds.bbci.co.uk/news/world/us_and_canada/rss.xml");


        // Handle item click
        newsListView.setOnItemClickListener((parent, view, position, id) -> {
            NewsItem selectedNews = newsItems.get(position);
            Intent intent = new Intent(MainActivity.this, NewsDetailActivity.class);
            intent.putExtra("selectedNews", selectedNews);
            startActivity(intent);
        });

        // Retrieve selected language from SharedPreferences


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String selectedLanguage = sharedPreferences.getString("selected_language", "English");

        // Set the locale based on the selected language
        if (!selectedLanguage.isEmpty()) {
            setLocale(selectedLanguage);
        }


    }

    // Method to set the locale based on the language
    private void setLocale(String language) {
        if (!language.isEmpty()) {
            Locale locale = new Locale(language);
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.setLocale(locale);
            getResources().updateConfiguration(config, getResources().getDisplayMetrics());
            recreate(); // Refresh the activity to apply language changes
        }
    }

    private void initializeViews() {
        newsListView = findViewById(R.id.newsListView);
        newsItems = new ArrayList<>();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>());
        newsListView.setAdapter(adapter);
    }

    private void setupNavigationDrawer() {
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(this);
    }


    private class FetchDataTask extends AsyncTask<String, Void, ArrayList<NewsItem>> {
        private ProgressBar progressBar;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Access ProgressBar
            progressBar = findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE); // Show ProgressBar before fetching data
        }

        @Override
        protected ArrayList<NewsItem> doInBackground(String... urls) {
            ArrayList<NewsItem> result = new ArrayList<>();

            try {
                URL url = new URL(urls[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                try {
                    InputStream in = urlConnection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                    XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                    factory.setNamespaceAware(true);
                    XmlPullParser parser = factory.newPullParser();
                    parser.setInput(in, null);

                    int eventType = parser.getEventType();
                    String title = "", description = "", date = "", link = "";
                    while (eventType != XmlPullParser.END_DOCUMENT) {
                        if (eventType == XmlPullParser.START_TAG) {
                            if ("title".equals(parser.getName())) {
                                title = parser.nextText();
                            } else if ("description".equals(parser.getName())) {
                                description = parser.nextText();
                            } else if ("pubDate".equals(parser.getName())) {
                                date = parser.nextText();
                            } else if ("link".equals(parser.getName())) {
                                link = parser.nextText();
                            }
                        } else if (eventType == XmlPullParser.END_TAG && "item".equals(parser.getName())) {
                            result.add(new NewsItem(title, description, date, link));
                            title = description = date = link = ""; // Reset values for the next item
                        }
                        eventType = parser.next();
                    }

                } catch (XmlPullParserException | IOException e) {
                    e.printStackTrace();
                    runOnUiThread(() -> Toast.makeText(MainActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                } finally {
                    urlConnection.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(ArrayList<NewsItem> result) {
            if (result.isEmpty()) {
                Toast.makeText(MainActivity.this, "No data fetched", Toast.LENGTH_SHORT).show();
            } else {
                newsItems.clear();
                newsItems.addAll(result);

                ArrayList<String> newsTitles = new ArrayList<>();
                for (NewsItem item : newsItems) {
                    newsTitles.add(item.getTitle());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, newsTitles);
                newsListView.setAdapter(adapter);

                Toast.makeText(MainActivity.this, "Data fetched successfully", Toast.LENGTH_SHORT).show();
            }
            progressBar.setVisibility(View.GONE);
        }
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            } else {
                drawerLayout.openDrawer(GravityCompat.START);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.nav_home) {
            // Handle Home item click
        } else if (id == R.id.nav_favorites) {
            // Handle Favorites item click
            Intent intent = new Intent(MainActivity.this, FavoriteArticlesActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_help) {
            // Handle Favorites item click
            Intent intent = new Intent(MainActivity.this, HelpActivity.class);
            startActivity(intent);
        }
        // Add more handling for other menu items as needed

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    private void openSettings() {
        // Example: Start a new SettingsActivity
        Intent intent = new Intent(MainActivity.this, SettingsFragment.class);
        startActivity(intent);
    }
    private void saveToSharedPreferences(String key, boolean value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }
    private class FetchNewsTask extends AsyncTask<Void, Void, ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(Void... voids) {
            // Simulating fetching news headlines
            ArrayList<String> headlines = new ArrayList<>();
            headlines.add("News 1");
            headlines.add("News 2");
            headlines.add("News 3");
            return headlines;
        }

        @Override
        protected void onPostExecute(ArrayList<String> headlines) {
            adapter.clear();
            adapter.addAll(headlines);
            adapter.notifyDataSetChanged();
        }
    }

}
