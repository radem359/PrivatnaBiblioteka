package com.example.milica.privatnabiblioteka;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.milica.privatnabiblioteka.BazaPodataka.Author;
import com.example.milica.privatnabiblioteka.BazaPodataka.Genre;

public class AddGenre extends AppCompatActivity {

    TextView tvGenreNameAdd;

    Button btSaveGenre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_genre);

        tvGenreNameAdd = (TextView)findViewById(R.id.tvGenreNameAdd);
        btSaveGenre = (Button) findViewById(R.id.btSaveGenre);

        btSaveGenre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddGenre.this, MainActivity.class);

                Genre genre = new Genre();
                genre.setGenreName(tvGenreNameAdd.getText().toString().trim());

                MainActivity.myDbHandler.addGenre(genre);
                startActivity(intent);
            }
        });
    }
}
