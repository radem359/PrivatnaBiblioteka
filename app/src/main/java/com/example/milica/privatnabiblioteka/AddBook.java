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

public class AddBook extends AppCompatActivity {

    TextView tvBookName, tbBookDescription;
    Spinner spAuthors, spGenres;
    List<Author> authors;
    String[] authorNames;
    List<Genre> genres;
    String[] genreNames;
    String fav;

    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        System.out.println("U AddBooku sam");
        authors = MainActivity.myDbHandler.getAllAuthors();
        authorNames = new  String[authors.size()];
        fav = getIntent().getStringExtra("favorite");
        int i = 0;
        for (Author a:
                authors) {
            authorNames[i] = a.getAuthorName();
            i++;
        }

        genres = MainActivity.myDbHandler.getAllGenres();
        genreNames = new String[genres.size()];
        i = 0;
        for (Genre a:
                genres) {
            genreNames[i] = a.getGenreName();
            i++;
        }

        tvBookName = (TextView)findViewById(R.id.tvAuthorName);
        tbBookDescription = (TextView)findViewById(R.id.tbBookDescription);

        spAuthors = (Spinner) findViewById(R.id.spAuthors);
        spGenres = (Spinner) findViewById(R.id.spGenres);
        btnSave = (Button) findViewById(R.id.btSave);

        final ArrayAdapter<String> myAuthorAdapter = new ArrayAdapter<String>(AddBook.this, android.R.layout.simple_list_item_1, authorNames);
        myAuthorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spAuthors.setAdapter(myAuthorAdapter);

        ArrayAdapter<String> myGenreAdapter = new ArrayAdapter<String>(AddBook.this, android.R.layout.simple_list_item_1, genreNames);
        myGenreAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spGenres.setAdapter(myGenreAdapter);


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddBook.this, MainActivity.class);
                Book book = new Book();
                book.set_bookName(tvBookName.getText().toString().trim());
                book.set_bookDescription(tbBookDescription.getText().toString().trim());
                if(fav.equals("0"))
                    book.set_favorite(false);
                else
                    book.set_favorite(true);

                Author author = null;
                if(spAuthors.getSelectedItem() != null)
                 author = MainActivity.myDbHandler.getAuthorByName(spAuthors.getSelectedItem().toString());
                if(author != null){
                    book.set_author(author);
                }

                Genre genre = null;
                if(spGenres.getSelectedItem() != null)
                    genre = MainActivity.myDbHandler.getGenreByName(spGenres.getSelectedItem().toString());
                if(genre != null){
                    book.set_genre(genre);
                }

                MainActivity.myDbHandler.addBook(book);
                startActivity(intent);
            }
        });

    }
}
