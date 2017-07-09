package com.example.allyan.moviesfinder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class PosterPreview extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_poster_preview);

        ImageView posterIV = (ImageView) findViewById(R.id.activity_poster_poster_preview);
        posterIV.setOnClickListener(this);

        Intent intent = getIntent();
        int position = intent.getIntExtra("position", -1);

//        Toast.makeText(this, position + "", Toast.LENGTH_SHORT).show();
        posterIV.setImageBitmap(SearchMoviesThread.movies.get(position).getBitmap());
    }

    @Override
    public void onClick(View view) {
        finish();
    }
}
