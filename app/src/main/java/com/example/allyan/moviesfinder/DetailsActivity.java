package com.example.allyan.moviesfinder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {

    private static final String SEARCH_MOVIE_IMDB = "http://www.omdbapi.com/?i=";
    private static TextView actorsNamesTV;
    private static TextView plotTV;
    static int position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_details);

        TextView titleTV = (TextView) findViewById(R.id.activity_details_title_name);
        actorsNamesTV = (TextView) findViewById(R.id.activity_details_actors_name);
        plotTV = (TextView) findViewById(R.id.activity_details_plot_details);
        ImageView poster = (ImageView) findViewById(R.id.activity_details_title_image);

        Intent intent = getIntent();

        position = intent.getIntExtra("position", -1);
        String imdb = SearchMoviesThread.movies.get(position).getImdbId();

        poster.setImageBitmap(SearchMoviesThread.movies.get(position).getBitmap());
        titleTV.setText(SearchMoviesThread.movies.get(position).getTitle());


        new DetailsScreenThread(SEARCH_MOVIE_IMDB + imdb + "&plot=full", position).start();
    }

    public static void detailsScreenShow() {
        actorsNamesTV.setText(SearchMoviesThread.movies.get(position).getActors());
        plotTV.setText(SearchMoviesThread.movies.get(position).getPlot());
    }
}
