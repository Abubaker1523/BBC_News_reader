package com.example.bbcnewsreader;

import java.io.Serializable;

/**
 * Represents an article with basic information such as title, description, link, date, and ID.
 */
public class Article implements Serializable {

    private String title;
    private String description;
    private String link;
    private String date;
    private long articleId; // Changed to long for consistency
    private int id;

    /**
     * Default constructor for the Article class.
     */
    public Article() {
        // Default constructor
    }

    /**
     * Constructor for creating an Article with specified title, description, date, and link.
     *
     * @param title       The title of the article.
     * @param description The description or content of the article.
     * @param date        The publication date of the article.
     * @param link        The URL link to the article.
     */
    public Article(String title, String description, String date, String link) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.link = link;
    }

    /**
     * Sets the unique identifier for the article.
     *
     * @param articleId The unique identifier for the article.
     */
    public void setArticleId(long articleId) {
        this.articleId = articleId;
    }

    /**
     * Retrieves the title of the article.
     *
     * @return The title of the article.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Retrieves the description or content of the article.
     *
     * @return The description of the article.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Retrieves the URL link to the article.
     *
     * @return The link to the article.
     */
    public String getLink() {
        return link;
    }

    /**
     * Retrieves the publication date of the article.
     *
     * @return The date of the article.
     */
    public String getDate() {
        return date;
    }

    /**
     * Retrieves the unique identifier for the article.
     *
     * @return The unique identifier for the article.
     */
    public long getArticleId() {
        return articleId;
    }

    /**
     * Sets the ID of the article.
     *
     * @param id The ID of the article.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retrieves the ID of the article.
     *
     * @return The ID of the article.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the title of the article.
     *
     * @param title The title of the article.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Sets the description or content of the article.
     *
     * @param description The description of the article.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Sets the URL link to the article.
     *
     * @param link The link to the article.
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * Sets the publication date of the article.
     *
     * @param date The date of the article.
     */
    public void setDate(String date) {
        this.date = date;
    }
}
