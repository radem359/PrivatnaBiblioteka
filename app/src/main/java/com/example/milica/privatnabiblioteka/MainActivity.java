package com.example.milica.privatnabiblioteka;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.milica.privatnabiblioteka.BazaPodataka.Author;
import com.example.milica.privatnabiblioteka.BazaPodataka.Book;
import com.example.milica.privatnabiblioteka.BazaPodataka.Genre;
import com.example.milica.privatnabiblioteka.BazaPodataka.MyDbHandler;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView lvMain;
    public static MyDbHandler myDbHandler;
    int[] idOfImages = {R.drawable.book, R.drawable.author, R.drawable.genres, R.drawable.favorite};
    String[] names = {"Knjige", "Autori", "Zanrovi", "Omiljene Knjige"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDbHandler = new MyDbHandler(this);
        lvMain = (ListView)findViewById(R.id.lvMain);

        CustomAdapter customAdapter = new CustomAdapter();

        /*Author author = new Author("George R R Martin");
        Genre genre = new Genre("Fantazija");

        List<Author> authors =  myDbHandler.getAllAuthors();
        List<Genre> genres =  myDbHandler.getAllGenres();

        if(authors.isEmpty()){
            myDbHandler.addAuthor(author);
        }
        if(genres.isEmpty()){
            myDbHandler.addGenre(genre);
        }

        Book bookSample = new Book("Pesma leda i vatre", "Najjaci serijal knjiga ikad", true, author, genre);


        List<Book> books =  myDbHandler.getAllBooks();

        if(books.isEmpty()){
            myDbHandler.addBook(bookSample);
        }*/


        lvMain.setAdapter(customAdapter);
    }

    class CustomAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return names.length;
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

            convertView = getLayoutInflater().inflate(R.layout.customlayout, parent, false);
            ImageView ivBooks = (ImageView)convertView.findViewById(R.id.ivBook);
            TextView tvName = (TextView)convertView.findViewById(R.id.tvName);

            tvName.setText(names[position]);
            ivBooks.setImageResource(idOfImages[position]);

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    if(position == 0){
                        intent = new Intent(MainActivity.this, BookListActivity.class);

                        intent.putExtra("position", String.valueOf(position));
                    }else if(position == 1){
                        intent = new Intent(MainActivity.this, AuthorListActivity.class);
                    }else if(position == 2){
                        intent = new Intent(MainActivity.this, GenreListActivity.class);
                    }else{
                        intent = new Intent(MainActivity.this, BookListActivity.class);

                        intent.putExtra("position", String.valueOf(position));
                    }
                    startActivity(intent);
                }
            });



            return convertView;
        }
    }
}
