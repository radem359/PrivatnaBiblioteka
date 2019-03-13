package com.example.milica.privatnabiblioteka;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.milica.privatnabiblioteka.BazaPodataka.Genre;

import java.util.List;

public class GenreListActivity extends AppCompatActivity {

    Button btAddGenre;
    ListView lvGenres;
    List<Genre> genres;
    String[] names;
    String[] genreId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre_list);

        btAddGenre = (Button) findViewById(R.id.btAddGenre);
        lvGenres = (ListView) findViewById(R.id.lvGenres);
        genres = MainActivity.myDbHandler.getAllGenres();


        CustomAdapter customAdapter = new GenreListActivity.CustomAdapter();

        lvGenres.setAdapter(customAdapter);

        names = new String[genres.size()];
        genreId = new String[genres.size()];

        int i = 0;
        for (Genre a:
                genres) {
            names[i] = a.getGenreName();
            genreId[i] = String.valueOf(a.get_id());
            i++;
        }

        btAddGenre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAddAuthor = new Intent(GenreListActivity.this, AddGenre.class);
                startActivity(intentAddAuthor);
            }
        });
    }

    class CustomAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return genres.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            convertView = getLayoutInflater().inflate(R.layout.activity_genre_list_custom, null, false);
            TextView tvGenreName = (TextView)convertView.findViewById(R.id.tvGenreName);
            ImageView ivGenre = (ImageView)convertView.findViewById(R.id.ivGenre);

            System.out.println(names[position]);
            tvGenreName.setText(names[position]);
            ivGenre.setImageResource(R.drawable.genres);

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(GenreListActivity.this, UpdateGenreActivity.class);
                    intent.putExtra("genreId", genreId[position]);
                    intent.putExtra("genreName", names[position]);
                    startActivity(intent);
                }
            });

            return convertView;

        }
    }
}
