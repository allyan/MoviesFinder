package com.example.allyan.moviesfinder;

import android.graphics.Bitmap;

public class Movie {

    private String title, year, poster, imdbId, actors, plot;
    private Bitmap bitmap;

    public Movie(String title, String year, String poster, String imdbId) {
        this.title = title;
        this.poster = poster;
        this.imdbId = imdbId;
        this.year = year;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getActors() {

        return actors;
    }

    public String getPlot() {
        return plot;
    }

    public String getTitle() {
        return title;
    }

    public String getPoster() {
        return poster;
    }

    public String getImdbId() {
        return imdbId;
    }

    public String getYear() {
        return year;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
