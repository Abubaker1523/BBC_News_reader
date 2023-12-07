package com.example.bbcnewsreader;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Activity responsible for displaying details of a specific article.
 */
public class ArticleDetailActivity extends AppCompatActivity {

    /**
     * Called when the activity is first created. Responsible for initializing the activity.
     *
     * @param savedInstanceState A Bundle containing the activity's previously saved state, or null if no state was saved.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_articles);

    }
}
