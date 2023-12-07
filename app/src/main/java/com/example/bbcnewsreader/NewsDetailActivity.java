package com.example.bbcnewsreader;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.List;
import com.google.android.material.snackbar.Snackbar;

public class NewsDetailActivity extends AppCompatActivity {

    private NewsItem selectedNewsItem;
    private Button addToFavoritesButton;
    private Button removeFromFavoritesButton;
    private DBHelper dbHelper;
    private Article selectedArticle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        dbHelper = new DBHelper(this);

        getSupportActionBar().setTitle("News Detail");


        TextView titleTextView = findViewById(R.id.titleTextView);
        TextView descriptionTextView = findViewById(R.id.descriptionTextView);
        TextView dateTextView = findViewById(R.id.dateTextView);
        TextView linkTextView = findViewById(R.id.linkTextView);

        addToFavoritesButton = findViewById(R.id.addToFavoritesButton);
        removeFromFavoritesButton = findViewById(R.id.removeFromFavoritesButton);

        Intent intent = getIntent();
        if (intent != null) {
            selectedNewsItem = (NewsItem) intent.getSerializableExtra("selectedNews");

            if (selectedNewsItem != null) {
                titleTextView.setText(selectedNewsItem.getTitle());
                descriptionTextView.setText(selectedNewsItem.getDescription());
                dateTextView.setText(selectedNewsItem.getDate());

                linkTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openLinkInBrowser(selectedNewsItem.getLink());
                    }
                });

                updateUI();


                addToFavoritesButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addToFavorites();
                    }
                });

                removeFromFavoritesButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        removeFromFavorites();
                    }
                });
            }
        }
    }

    protected void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        if (selectedNewsItem != null) {
            boolean isArticleInFavorites = dbHelper.isArticleInFavorites(selectedNewsItem.getLink());

            if (isArticleInFavorites) {
                addToFavoritesButton.setVisibility(View.GONE);
                removeFromFavoritesButton.setVisibility(View.VISIBLE);
            } else {
                addToFavoritesButton.setVisibility(View.VISIBLE);
                removeFromFavoritesButton.setVisibility(View.GONE);
            }
        }
    }

    private void addToFavorites() {
        if (selectedNewsItem != null) {
            Article article = new Article(
                    selectedNewsItem.getTitle(),
                    selectedNewsItem.getDescription(),
                    selectedNewsItem.getDate(),
                    selectedNewsItem.getLink()
            );

            dbHelper.addFavorite(article);
            updateUI();
            Snackbar.make(findViewById(android.R.id.content), "Article added to favorites", Snackbar.LENGTH_SHORT).show();

        }

    }

    private void removeFromFavorites() {
        if (selectedNewsItem != null) {
            dbHelper.removeFavorite(selectedNewsItem.getLink());
            updateUI();
            Snackbar.make(findViewById(android.R.id.content), "Article removed to favorites", Snackbar.LENGTH_SHORT).show();

        }
    }

    private void openLinkInBrowser(String link) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
        startActivity(browserIntent);
    }
}