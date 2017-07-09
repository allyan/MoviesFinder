package com.example.allyan.moviesfinder;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

class MoviesAdapter extends ArrayAdapter<Movie> implements View.OnClickListener {

    private Context context;
    private ArrayList<Movie> movies;

    public MoviesAdapter(Context context, ArrayList<Movie> movies) {
        super(context, R.layout.list_item, movies);
        this.context = context;
        this.movies = movies;
    }

    private class ViewHolder {
        TextView movieNameTV;
        TextView movieYearTV;
        ImageView movieImage;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.movieNameTV = (TextView) convertView.findViewById(R.id.list_item_movie_name);
            viewHolder.movieYearTV = (TextView) convertView.findViewById(R.id.list_item_movie_year);
            viewHolder.movieImage = (ImageView) convertView.findViewById(R.id.list_item_image_movie);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.movieImage.setImageBitmap(movies.get(position).getBitmap());
        viewHolder.movieImage.setTag(position);
        viewHolder.movieImage.setOnClickListener(this);
        viewHolder.movieNameTV.setText(movies.get(position).getTitle());
        viewHolder.movieYearTV.setText(movies.get(position).getYear());
        return convertView;
    }


    @Override
    public void onClick(View view) {
        Intent intent = new Intent(context, PosterPreview.class);
        ImageView posterIV = (ImageView) view.findViewById(R.id.list_item_image_movie);
        int position = (int) posterIV.getTag();
        intent.putExtra("position", position);
        context.startActivity(intent);
    }
}
