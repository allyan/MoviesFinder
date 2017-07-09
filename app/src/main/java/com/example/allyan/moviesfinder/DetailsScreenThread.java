package com.example.allyan.moviesfinder;

import android.os.Handler;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

class DetailsScreenThread extends Thread{
    private Handler handler = new Handler();
    private String imdb;
    private int position;

    public DetailsScreenThread(String imdb, int position) {
        this.imdb = imdb;
        this.position = position;
    }

    @Override
    public void run() {
        String data = getDataFromTheWeb();
        extractFromData(data);
    }

    private String getDataFromTheWeb() {
        try {
            URL url = new URL(imdb);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            InputStream in = con.getInputStream();
            StringBuilder sb = new StringBuilder();
            String line;
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            while((line = reader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void extractFromData(String data) {
        try {
            JSONObject mainObject = new JSONObject(data);
            String actors = mainObject.getString("Actors");
            String plot = mainObject.getString("Plot");
            SearchMoviesThread.movies.get(position).setActors(actors);
            SearchMoviesThread.movies.get(position).setPlot(plot);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    DetailsActivity.detailsScreenShow();
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
