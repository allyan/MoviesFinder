package com.example.allyan.moviesfinder;

import android.os.Handler;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class SearchMoviesThread extends Thread {

    static ArrayList<Movie> movies;
    private ResultsActivity resultsActivity;
    private String search;
    private UpdateListListener listener;
    private Handler handler;
    public static int totalResult;

    public SearchMoviesThread(UpdateListListener listener, String search, ResultsActivity resultsActivity) {
        this.listener = listener;
        this.search = search;
        handler = new Handler();
        this.resultsActivity = resultsActivity;
    }

    @Override
    public void run() {
        movies = new ArrayList<>();
        String data = getDataFromTheWeb();
        extractFromData(movies, data);
        handler.post(new Runnable() {
            @Override
            public void run() {
                listener.updateList(movies);
            }
        });
    }

    private void extractFromData(ArrayList<Movie> movies, String data) {
        try {
            JSONObject mainObject = new JSONObject(data);
            String response = (String) mainObject.get("Response");
            if(response.equalsIgnoreCase("true")){
                String totalResults = mainObject.getString("totalResults");
                totalResult = Integer.parseInt(totalResults);
                JSONArray arr = mainObject.getJSONArray("Search");
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);
                    String title = obj.getString("Title");
                    String year = obj.getString("Year");
                    String poster = obj.getString("Poster");
                    String imdbID = obj.getString("imdbID");
                    movies.add(new Movie(title, year, poster, imdbID));
                    DownloadImageThread downloadImageThread = new DownloadImageThread(handler, poster, movies, i);
                    downloadImageThread.start();
                    while (downloadImageThread.isAlive()){
                        Thread.sleep(10);
                    }
                }
            }else{
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.showToast();
                    }
                });
                resultsActivity.finish();
                return;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String getDataFromTheWeb() {
        try {
            URL url = new URL(search);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            InputStream in = con.getInputStream();
            StringBuilder sb = new StringBuilder();
            /*
            int actuallyRead;
            byte[] buffer = new byte[1024];
            while((actuallyRead = in.read(buffer)) != -1) {
                sb.append(new String(buffer, 0, actuallyRead));
            }
            */
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

}
