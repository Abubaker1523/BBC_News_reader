package com.example.bbcnewsreader;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Activity responsible for displaying settings.
 */
public class SettingsActivity extends AppCompatActivity {

    /**
     * Initializes the activity by setting the title and loading the SettingsFragment.
     *
     * @param savedInstanceState Instance state data.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Setting");
        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }

    /**
     * Restarts the activity to apply changes made in settings.
     */
    public void restartActivity() {
        Intent intent = getIntent(); // Get the current intent
        finish(); // Finish the current activity
        startActivity(intent); // Start the activity again using the obtained intent
    }
}
