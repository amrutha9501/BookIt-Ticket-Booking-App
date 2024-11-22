package com.example.bookit;

public class Event {
    private String name;
    private double rating;
    private String genre;
    private int showId;
    private String language;
    private String posterUrl;

    private int category_id;
    private String description;
    private String cast;
    private String duration;

    public Event(int showId, String name, double rating, String genre, String language, String posterUrl) {
        this.showId = showId;
        this.language = language;
        this.name = name;
        this.rating = rating;
        this.genre = genre;
        this.posterUrl = posterUrl;
    }

    public Event(int showId, String name, double rating, String genre, String language, String posterUrl, int category_id, String description, String cast, String duration) {
        this.showId = showId;
        this.language = language;
        this.name = name;
        this.rating = rating;
        this.genre = genre;
        this.posterUrl = posterUrl;
        this.category_id = category_id;
        this.description = description;
        this.cast = cast;
        this.duration = duration;
    }

    // Getters
    public String getName() {
        return name;
    }

    public double getRating() {
        return rating;
    }

    public String getGenre() {
        return genre;
    }

    public int getId() {
        return showId;
    }

    public String getLanguage() {
        return language;
    }

    public String getPosterUrl() { return posterUrl; }

    public int getCategoryId() {
        return category_id;
    }

    public String getDescription() {
        return description;
    }

    public String getCast() {
        return cast;
    }

    public String getDuration()
    {
        return duration;
    }

    public void setCategoryId(int id)
    {
        this.category_id = id;
        return;
    }



}
