package com.example.bbcnewsreader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import java.util.ArrayList;
import java.util.List;

public class FavoriteArticlesActivity extends AppCompatActivity {

    private RecyclerView favoritesRecyclerView; // RecyclerView to display favorite articles
    private FavoriteArticlesAdapter adapter; // Adapter for managing favorite articles
    private List<Article> favoriteArticles; // List to hold favorite articles
    private EditText editTextSearch; // EditText for filtering articles

    /**
     * onCreate method called when the activity is created.
     * Initializes the UI components and sets up the RecyclerView.
     *
     * @param savedInstanceState A Bundle containing the activity's previously saved state.
     */
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_articles);

        getSupportActionBar().setTitle("BBC Favorite"); // Set the title on the ActionBar
        favoritesRecyclerView = findViewById(R.id.favoritesRecyclerView); // Initialize RecyclerView
        editTextSearch = findViewById(R.id.editTextSearch); // Initialize EditText

        // Retrieve favorite articles from the database using DBHelper
        DBHelper dbHelper = new DBHelper(this);
        favoriteArticles = dbHelper.getAllFavoriteArticles();

        // Set up the RecyclerView with the FavoriteArticlesAdapter
        adapter = new FavoriteArticlesAdapter(favoriteArticles);
        favoritesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        favoritesRecyclerView.setAdapter(adapter);

        // Handle item click events in the RecyclerView
        adapter.setOnItemClickListener(new FavoriteArticlesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Article article) {
                openArticleDetail(article);
            }
        });

        // Add a TextWatcher to the EditText for filtering articles on text change
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filterArticles(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }

    /**
     * Opens the detailed view of the selected article in a web browser.
     *
     * @param article The Article object representing the selected article.
     */
    private void openArticleDetail(Article article) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(article.getLink()));
        startActivity(browserIntent);
    }

    /**
     * Filters the articles based on the provided query string.
     *
     * @param query The search query used for filtering articles.
     */
    private void filterArticles(String query) {
        adapter.getFilter().filter(query);
    }
}
