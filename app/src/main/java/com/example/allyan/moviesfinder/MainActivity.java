package com.example.allyan.moviesfinder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    static final String SEARCH = "search";

    private EditText searchET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        searchET = (EditText) findViewById(R.id.activity_main_search_edit_text);

    }


    public void search(View view) {
        String search = searchET.getText().toString().trim();
        if (search.isEmpty()) {
            searchET.setError("Cannot be empty!");
            return;
        }
        Intent intent = new Intent(this, ResultsActivity.class);
        intent.putExtra(SEARCH, search);
        startActivity(intent);
        searchET.setText("");
    }
}
