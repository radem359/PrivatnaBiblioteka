package com.example.milica.privatnabiblioteka;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.milica.privatnabiblioteka.BazaPodataka.Author;
import com.example.milica.privatnabiblioteka.BazaPodataka.Book;
import com.example.milica.privatnabiblioteka.BazaPodataka.Genre;

import java.util.List;

public class AddAuthor extends AppCompatActivity {

    TextView tvAuthorName;

    Button btSaveAuthor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_author);

        tvAuthorName = (TextView)findViewById(R.id.tvAuthorName);
        btSaveAuthor = (Button) findViewById(R.id.btSaveAuthor);

        btSaveAuthor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddAuthor.this, MainActivity.class);

                Author author = new Author();
                author.setAuthorName(tvAuthorName.getText().toString().trim());

                MainActivity.myDbHandler.addAuthor(author);
                startActivity(intent);
            }
        });

    }
}
