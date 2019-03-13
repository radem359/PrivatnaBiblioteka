package com.example.milica.privatnabiblioteka;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.milica.privatnabiblioteka.BazaPodataka.Author;
import com.example.milica.privatnabiblioteka.BazaPodataka.Genre;

public class UpdateGenreActivity extends AppCompatActivity {

    TextView tvGenreUpdName;
    Button update, delete;
    int genreId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_genre);

        tvGenreUpdName = (TextView)findViewById(R.id.tvUpdGenreName);
        update = (Button) findViewById(R.id.btGenreUpd);
        delete = (Button)findViewById(R.id.btGenreDelete);

        String name = getIntent().getStringExtra("genreName");
        tvGenreUpdName.setText(name);
        genreId = Integer.parseInt(getIntent().getStringExtra("genreId"));

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateGenreActivity.this, MainActivity.class);

                Genre genre = new Genre();
                genre.set_id(genreId);
                genre.setGenreName(tvGenreUpdName.getText().toString().trim());

                MainActivity.myDbHandler.updateGenre(genre);

                startActivity(intent);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateGenreActivity.this, MainActivity.class);

                Genre genre = new Genre();
                genre.set_id(genreId);
                genre.setGenreName(tvGenreUpdName.getText().toString().trim());

                MainActivity.myDbHandler.deleteGenre(genre);

                startActivity(intent);
            }
        });

    }
}
