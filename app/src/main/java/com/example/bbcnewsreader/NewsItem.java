package com.example.bbcnewsreader;

import java.io.Serializable;

/**
 * Class representing a news item.
 */
public class NewsItem implements Serializable {
    private String title;
    private String description;
    private String date;
    private String link;
    private boolean isFavorite;
    private long articleId; // Change the type to long

    /**
     * Constructor to initialize a NewsItem object.
     *
     * @param title       The title of the news item.
     * @param description The description of the news item.
     * @param date        The date of the news item.
     * @param link        The link of the news item.
     */
    public NewsItem(String title, String description, String date, String link) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.link = link;
        this.isFavorite = false; // Default value for isFavorite is false
    }

    /**
     * Gets the unique ID of the news item.
     *
     * @return The article ID.
     */
    public long getArticleId() {
        return articleId;
    }

    /**
     * Gets the title of the news item.
     *
     * @return The title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the description of the news item.
     *
     * @return The description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the date of the news item.
     *
     * @return The date.
     */
    public String getDate() {
        return date;
    }

    /**
     * Gets the link of the news item.
     *
     * @return The link.
     */
    public String getLink() {
        return link;
    }

    /**
     * Checks if the news item is marked as a favorite.
     *
     * @return True if the news item is marked as a favorite, false otherwise.
     */
    public boolean isFavorite() {
        return isFavorite;
    }

    /**
     * Sets whether the news item is marked as a favorite or not.
     *
     * @param favorite True to mark the news item as a favorite, false otherwise.
     */
    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    /**
     * Sets the unique ID of the news item.
     *
     * @param articleId The article ID to set.
     */
    public void setArticleId(long articleId) {
        this.articleId = articleId;
    }
}
