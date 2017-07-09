package com.example.allyan.moviesfinder;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

class DownloadImageThread extends Thread {

    private ArrayList<Movie> movies;
    private String poster;
    int position;
    private Handler handler;

    public DownloadImageThread(Handler handler, String poster, ArrayList<Movie> movies, int position) {
        this.poster = poster;
        this.movies = movies;
        this.position = position;
        this.handler = handler;
    }

    @Override
    public void run() {
          try {

            URL url = new URL(poster);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream in = connection.getInputStream();
            final Bitmap bitmap = BitmapFactory.decodeStream(in);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    movies.get(position).setBitmap(bitmap);
                }
            });
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
