
package com.example.bbcnewsreader;
import android.content.Context;
import android.content.SharedPreferences;
import java.util.HashSet;
import java.util.Set;

public class SharedPreferencesManager {

    private static final String PREF_NAME = "FavoritesPref";
    private static final String FAVORITES_KEY = "favoriteArticles";
    private SharedPreferences preferences;

    public SharedPreferencesManager(Context context) {
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void addFavoriteArticle(String articleId) {
        Set<String> favorites = getFavoriteArticles();
        favorites.add(articleId);
        preferences.edit().putStringSet(FAVORITES_KEY, favorites).apply();

    }

    public void removeFavoriteArticle(String articleId) {
        Set<String> favorites = getFavoriteArticles();
        favorites.remove(articleId);
        preferences.edit().putStringSet(FAVORITES_KEY, favorites).apply();
    }

    public Set<String> getFavoriteArticles() {
        return preferences.getStringSet(FAVORITES_KEY, new HashSet<>());
    }
}
