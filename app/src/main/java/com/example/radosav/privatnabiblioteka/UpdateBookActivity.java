package com.example.radosav.privatnabiblioteka;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.radosav.privatnabiblioteka.BazaPodataka.Author;
import com.example.radosav.privatnabiblioteka.BazaPodataka.Book;
import com.example.radosav.privatnabiblioteka.BazaPodataka.Genre;

import java.util.List;

public class UpdateBookActivity extends AppCompatActivity {

    TextView tvBookName, tbBookDescription, autorKnjigeId, zanrKnjigeId;
    Spinner spAuthors, spGenres;
    List<Author> authors;
    String[] authorNames;
    List<Genre> genres;
    String[] genreNames;
    ImageView ivFav;
    Button btUpdateBook, btDeleteBook;
    boolean fav;
    int bookId;
    String positionFromIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_book);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        authors = MainActivity.myDbHandler.getAllAuthors();
        authorNames = new String[authors.size()+1];
        authorNames[0] = "";
        positionFromIntent = getIntent().getStringExtra("position");
        int i = 1;
        for (Author a:
                authors) {
            authorNames[i] = a.getAuthorName();
            i++;
        }

        genres = MainActivity.myDbHandler.getAllGenres();
        genreNames = new String[genres.size()+1];
        genreNames[0] = "";
        i = 1;
        for (Genre a:
                genres) {
            genreNames[i] = a.getGenreName();
            i++;
        }



        btUpdateBook = (Button) findViewById(R.id.btUpdateBook);
        btDeleteBook = (Button) findViewById(R.id.btDeleteBook);
        tvBookName = (TextView) findViewById(R.id.tvUpdBookName);
        tbBookDescription = (TextView) findViewById(R.id.tbUpdBookDescription);
        spAuthors = (Spinner) findViewById(R.id.spUpdAuthors);
        spGenres = (Spinner) findViewById(R.id.spUpdGenres);
        ivFav = (ImageView)findViewById(R.id.ivFav);
        autorKnjigeId = (TextView)findViewById(R.id.autorKnjigeId);
        zanrKnjigeId = (TextView)findViewById(R.id.zanrKnjigeId);

        final ArrayAdapter<String> myAuthorAdapter = new ArrayAdapter<String>(UpdateBookActivity.this, android.R.layout.simple_list_item_1, authorNames);
        myAuthorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spAuthors.setAdapter(myAuthorAdapter);
        spAuthors.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position) != "") {
                    String selectedItemText = (String) parent.getItemAtPosition(position);
                    autorKnjigeId.setText(selectedItemText);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        int autorId = getIntent().getIntExtra("author", 0);
        Author autor = MainActivity.myDbHandler.getAuthorById(autorId);
        autorKnjigeId.setText(autor.getAuthorName());

        ArrayAdapter<String> myGenreAdapter = new ArrayAdapter<String>(UpdateBookActivity.this, android.R.layout.simple_list_item_1, genreNames);
        myGenreAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spGenres.setAdapter(myGenreAdapter);
        spGenres.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position) != "") {
                    String selectedItemText = (String) parent.getItemAtPosition(position);
                    zanrKnjigeId.setText(selectedItemText);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        int zanrId = getIntent().getIntExtra("genre", 0);
        final Genre zanr = MainActivity.myDbHandler.getGenreById(zanrId);
        zanrKnjigeId.setText(zanr.getGenreName());


        String name = getIntent().getStringExtra("bookName");
        tvBookName.setText(name);
        String desc = getIntent().getStringExtra("bookDesc");
        tbBookDescription.setText(desc);

        String image = getIntent().getStringExtra("image");

        System.out.println(image);
        if(image.equals("1")){
            ivFav.setImageResource(R.drawable.favorite);
            fav = true;
        }else{
            ivFav.setImageResource(R.drawable.emptyfavorite);
            fav = false;
        }

        ivFav.setClickable(true);
        ivFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fav){
                    ivFav.setImageResource(R.drawable.emptyfavorite);
                    fav = false;
                }else{
                    ivFav.setImageResource(R.drawable.favorite);
                    fav = true;
                }
            }
        });

        bookId = Integer.parseInt(getIntent().getStringExtra("bookId"));

        btUpdateBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateBookActivity.this, BookListActivity.class);
                intent.putExtra("position", positionFromIntent);

                Book book = new Book();
                book.set_id(bookId);
                book.set_bookName(tvBookName.getText().toString().trim());
                book.set_bookDescription(tbBookDescription.getText().toString().trim());
                book.set_favorite(fav);

                Author author = new Author();
                if(spAuthors.getSelectedItem() != null)
                    author = MainActivity.myDbHandler.getAuthorByName(autorKnjigeId.getText().toString());
                book.set_author(author.get_id());
                Genre genre = new Genre();
                if(spGenres.getSelectedItem() != null)
                    genre = MainActivity.myDbHandler.getGenreByName(zanrKnjigeId.getText().toString());
                book.set_genre(genre.get_id());

                MainActivity.myDbHandler.updateBook(book);
                startActivity(intent);
            }
        });

        btDeleteBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateBookActivity.this, BookListActivity.class);
                intent.putExtra("position", positionFromIntent);
                Book book = new Book();
                book.set_id(bookId);
                book.set_bookName(tvBookName.getText().toString().trim());
                book.set_bookDescription(tbBookDescription.getText().toString().trim());
                book.set_favorite(fav);

                MainActivity.myDbHandler.deleteBook(book);
                startActivity(intent);
            }
        });


    }

}
