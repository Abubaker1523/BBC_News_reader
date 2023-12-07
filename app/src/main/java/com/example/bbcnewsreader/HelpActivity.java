package com.example.bbcnewsreader;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        getSupportActionBar().setTitle("Help");

        // Assuming you have a TextView named helpTextView in your layout
        TextView helpTextView = findViewById(R.id.helpTextView);

        // Display help content
        String helpContent = "Welcome to the Help Section!\n\n"
                + "This app allows you to read the latest news from BBC. Here's how you can use it:\n\n"
                + "1. Navigation Drawer: Access different sections like Home, Favorites, and Settings using the menu on the left.\n\n"
                + "2. Home: View the latest news headlines from various categories.\n\n"
                + "3. Favorites: Save articles you like by clicking the 'Add to Favorites' button in the article details.\n\n"
                + "4. Settings: Customize your language preference for news articles.\n\n"
                + "We hope you find this app easy to use and informative! If you encounter any issues or have suggestions, please contact our support team.\n\n"
                + "Thank you for using BBC News Reader!";

        helpTextView.setText(helpContent);
    }
}
