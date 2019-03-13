package com.example.milica.privatnabiblioteka;

import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.stream.Collectors;

public class BookApiActivity extends AppCompatActivity {
    TextView tvInformation;
    String bookName, authorName, genreName;
    ImageView ivBookApi, ivAuthorApi, ivGenreApi;
    int click;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_api);


        tvInformation = (TextView) findViewById(R.id.tvInformation);
        bookName = getIntent().getStringExtra("bookName");
        authorName = getIntent().getStringExtra("authorName");
        genreName = getIntent().getStringExtra("genreName");

        System.out.println("Ime knjige je "+bookName);
        System.out.println("Ime pisca je "+authorName);
        System.out.println("Ime zanra je "+genreName);

        ivBookApi = (ImageView) findViewById(R.id.ivBookApi);
        ivAuthorApi = (ImageView) findViewById(R.id.ivAuthorApi);
        ivGenreApi = (ImageView) findViewById(R.id.ivGenreApi);

        ivBookApi.setClickable(true);
        ivGenreApi.setClickable(true);
        ivAuthorApi.setClickable(true);

        ivBookApi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DoIt().execute();
                click = 1;
            }
        });
        ivAuthorApi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DoIt().execute();
                click = 2;
            }
        });
        ivGenreApi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DoIt().execute();
                click = 3;
            }
        });


    }

    public class DoIt extends AsyncTask<Void, Void, Void> {

        private static final String encoding = "UTF-8";
        String wikipediaURL ="";
        String searchText;
        String result;
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected Void doInBackground(Void... voids) {
            if(click == 1){
                searchText  = bookName;
            }else if(click == 2){
                searchText = authorName;
            }else{
                searchText = genreName;
            }
            searchText += " Wikipedia";
            System.out.println("SRC TEXT JEEEE "+searchText);
            try {
                Document google = Jsoup.connect("https://www.google.com/search?q=" + URLEncoder.encode(searchText, encoding)).userAgent("Mozilla/5.0").get();

                //Get the first link about Wikipedia
                String wikipediaURL = google.getElementsByTag("cite").get(1).text();

                //Use Wikipedia API to get JSON File
                String wikipediaApiJSON = "https://www.wikipedia.org/w/api.php?format=json&action=query&prop=extracts&exintro=&explaintext=&titles="
                        + URLEncoder.encode(wikipediaURL.substring(wikipediaURL.lastIndexOf("/") + 1, wikipediaURL.length()), encoding);

                //Let's see what it found
                System.out.println(wikipediaURL);
                System.out.println(wikipediaApiJSON);

                HttpURLConnection httpcon = (HttpURLConnection) new URL(wikipediaApiJSON).openConnection();
                httpcon.addRequestProperty("User-Agent", "Mozilla/5.0");
                BufferedReader in = new BufferedReader(new InputStreamReader(httpcon.getInputStream()));

                //Read line by line
                String responseSB = "";
                String line="";
                while((line=in.readLine()) != null){
                    System.out.println(line);
                    responseSB += line;
                }

                in.close();

                //Print the result for us to see
                //System.out.println(responseSB);
                result = responseSB.split("extract\":\"")[1];
                System.out.println(" REzultat je "+result);

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            System.out.println(result);
            tvInformation.setText(result);
        }
    }

}
