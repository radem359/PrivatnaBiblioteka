package com.example.milica.privatnabiblioteka;

import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.milica.privatnabiblioteka.BazaPodataka.Author;
import com.example.milica.privatnabiblioteka.BazaPodataka.Book;
import com.example.milica.privatnabiblioteka.BazaPodataka.Genre;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

public class UpdateBookActivity extends AppCompatActivity {

    TextView tvBookName, tbBookDescription;
    Spinner spAuthors, spGenres;
    List<Author> authors;
    String[] authorNames;
    List<Genre> genres;
    String[] genreNames;
    ImageView ivFav;
    Button btUpdateBook, btDeleteBook, btMoreInformation;
    boolean fav;
    int bookId;
    String podaci;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_book);

        authors = MainActivity.myDbHandler.getAllAuthors();
        authorNames = new  String[authors.size()];
        String author = getIntent().getStringExtra("author");
        int i = 0;
        for (Author a:
                authors) {
            //if(i = 0)
            //authorNames[i] = author;
            authorNames[i] = a.getAuthorName();
            i++;
        }

        genres = MainActivity.myDbHandler.getAllGenres();
        genreNames = new String[genres.size()];
        String genre = getIntent().getStringExtra("genre");
        i = 0;
        for (Genre a:
                genres) {
            //if(i = 0)
            //genreNames[i] = genre;
            genreNames[i] = a.getGenreName();
            i++;
        }



        btUpdateBook = (Button) findViewById(R.id.btUpdateBook);
        btDeleteBook = (Button) findViewById(R.id.btDeleteBook);
        btMoreInformation = (Button) findViewById(R.id.btMore);
        tvBookName = (TextView) findViewById(R.id.tvUpdBookName);
        tbBookDescription = (TextView) findViewById(R.id.tbUpdBookDescription);
        spAuthors = (Spinner) findViewById(R.id.spUpdAuthors);
        spGenres = (Spinner) findViewById(R.id.spUpdGenres);
        ivFav = (ImageView)findViewById(R.id.ivFav);


        final ArrayAdapter<String> myAuthorAdapter = new ArrayAdapter<String>(UpdateBookActivity.this, android.R.layout.simple_list_item_1, authorNames);
        myAuthorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spAuthors.setAdapter(myAuthorAdapter);

        ArrayAdapter<String> myGenreAdapter = new ArrayAdapter<String>(UpdateBookActivity.this, android.R.layout.simple_list_item_1, genreNames);
        myGenreAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spGenres.setAdapter(myGenreAdapter);

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
                Intent intent = new Intent(UpdateBookActivity.this, MainActivity.class);
                Book book = new Book();
                book.set_id(bookId);
                book.set_bookName(tvBookName.getText().toString().trim());
                book.set_bookDescription(tbBookDescription.getText().toString().trim());
                book.set_favorite(fav);

                MainActivity.myDbHandler.updateBook(book);
                startActivity(intent);
            }
        });

        btDeleteBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateBookActivity.this, MainActivity.class);
                Book book = new Book();
                book.set_id(bookId);
                book.set_bookName(tvBookName.getText().toString().trim());
                book.set_bookDescription(tbBookDescription.getText().toString().trim());
                book.set_favorite(fav);

                MainActivity.myDbHandler.deleteBook(book);
                startActivity(intent);
            }
        });

        btMoreInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateBookActivity.this, BookApiActivity.class);
                intent.putExtra("bookName", tvBookName.getText().toString());
                intent.putExtra("authorName", spAuthors.getSelectedItem().toString());
                intent.putExtra("genreName", spGenres.getSelectedItem().toString());
                startActivity(intent);
            }
        });

    }

}
