package com.example.allyan.moviesfinder;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ResultsActivity extends AppCompatActivity implements UpdateListListener, AdapterView.OnItemClickListener {

    private static final String SEARCH_MOVIE_LINK = "http://www.omdbapi.com/?s=";

    private ListView listview;
    private ProgressBar progressBar;
    private  TextView progressPercent;
    private int pageCounter = 1;
    private MoviesAdapter adapter;
    private static String search;
    private FloatingActionButton nextBtn;
    private FloatingActionButton previousBtn;
    private Boolean alreadyToasted;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_results);

        search = getIntent().getStringExtra(MainActivity.SEARCH);
        if (search == null) {
            return;
        }

        new SearchMoviesThread(this, SEARCH_MOVIE_LINK + search + "&plot=full" + "&page=" + pageCounter, this).start();

        progressBar = (ProgressBar) findViewById(R.id.activity_result_progress_bar);
        progressPercent = (TextView) findViewById(R.id.activity_result_percent_progress);
        listview = (ListView) findViewById(R.id.activity_results_list_view);

        listview.setOnItemClickListener(this);

        nextBtn = (FloatingActionButton) findViewById(R.id.floatingActionButtonNext);

        previousBtn = (FloatingActionButton) findViewById(R.id.floatingActionButtonPrevious);
        previousBtn.setVisibility(View.INVISIBLE);

        alreadyToasted = true;
    }

    @Override
    public void updateList(ArrayList<Movie> movies) {
        progressBar.setVisibility(View.INVISIBLE);
        adapter = new MoviesAdapter(this, movies);
        listview.setAdapter(adapter);
        buttonsVisibility();
    }

    private void buttonsVisibility() {
        if(pageCounter == 1 && alreadyToasted){
            Toast.makeText(this, "Total results : " + SearchMoviesThread.totalResult, Toast.LENGTH_SHORT).show();
            alreadyToasted = false;
        }
        if((SearchMoviesThread.totalResult/10) == (pageCounter - 1)){
            nextBtn.setVisibility(View.INVISIBLE);
        }
        if(pageCounter == 1 && SearchMoviesThread.totalResult >= 10){
            previousBtn.setVisibility(View.INVISIBLE);
            nextBtn.setVisibility(View.VISIBLE);
        }
        if(pageCounter > 1){
            previousBtn.setVisibility(View.VISIBLE);
        }
        if((SearchMoviesThread.totalResult/10) > (pageCounter - 1)){
            nextBtn.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showToast() {
        Toast.makeText(ResultsActivity.this, "Movie not found !", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("position", position);
        startActivity(intent);
    }

    public void nextPage(View view) {
        progressBar.setVisibility(View.VISIBLE);
        if(0 < pageCounter && (SearchMoviesThread.totalResult/10) >= pageCounter){
            new SearchMoviesThread(this, SEARCH_MOVIE_LINK + search + "&plot=full&page=" + ++pageCounter, this).start();
        }else{
            Toast.makeText(ResultsActivity.this, "No more movies found", Toast.LENGTH_SHORT).show();
        }
    }

    public void previousPage(View view) {
        progressBar.setVisibility(View.VISIBLE);
        if(1 < pageCounter){
            new SearchMoviesThread(this, SEARCH_MOVIE_LINK + search + "&plot=full&page=" + --pageCounter, this).start();
        }
    }
}
