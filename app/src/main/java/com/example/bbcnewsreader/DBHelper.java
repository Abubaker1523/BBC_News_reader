package com.example.bbcnewsreader;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Database helper class for managing favorite articles.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ArticlesDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "favorite_articles";

    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_DATE = "date";
    private static final String KEY_LINK = "link";

    /**
     * Constructor for DBHelper class.
     *
     * @param context The context in which the database helper is created.
     */
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Called when the database is created for the first time.
     *
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_TITLE + " TEXT,"
                + KEY_DESCRIPTION + " TEXT,"
                + KEY_DATE + " TEXT,"
                + KEY_LINK + " TEXT" + ")";
        db.execSQL(CREATE_TABLE);
    }

    /**
     * Called when the database needs to be upgraded.
     *
     * @param db         The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    /**
     * Adds a new favorite article to the database.
     *
     * @param article The article to be added.
     * @return True if the article is added successfully, false otherwise.
     */
    public boolean addFavorite(Article article) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, article.getTitle());
        values.put(KEY_DESCRIPTION, article.getDescription());
        values.put(KEY_DATE, article.getDate());
        values.put(KEY_LINK, article.getLink());
        long result = db.insert(TABLE_NAME, null, values);

        // Set the ID in the Article object after insertion
        article.setArticleId(result);

        db.close();
        return result != -1;
    }

    /**
     * Checks if an article with the given link is already in the favorites.
     *
     * @param link The link of the article.
     * @return True if the article exists in favorites, false otherwise.
     */
    public boolean isArticleInFavorites(String link) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + KEY_ID + " FROM " + TABLE_NAME + " WHERE " + KEY_LINK + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{link});
        boolean exists = cursor.moveToFirst();
        cursor.close();
        db.close();
        return exists;
    }

    /**
     * Removes a favorite article from the database based on the link.
     *
     * @param link The link of the article to be removed.
     */
    public void removeFavorite(String link) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_LINK + "=?", new String[]{link});
        db.close();
    }

    /**
     * Retrieves all favorite articles from the database.
     *
     * @return A list of all favorite articles.
     */
    public List<Article> getAllFavoriteArticles() {
        List<Article> favoriteArticles = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Article article = new Article();
                article.setId(cursor.getInt(0));  // Set the ID of the article
                article.setTitle(cursor.getString(1));
                article.setDescription(cursor.getString(2));
                article.setDate(cursor.getString(3));
                article.setLink(cursor.getString(4));
                article.setArticleId(cursor.getLong(0)); // Setting the articleId using ID from the database
                favoriteArticles.add(article);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return favoriteArticles;
    }
}
