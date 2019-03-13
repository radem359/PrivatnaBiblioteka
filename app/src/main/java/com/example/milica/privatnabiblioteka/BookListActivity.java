package com.example.milica.privatnabiblioteka;

import android.annotation.SuppressLint;
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

import com.example.milica.privatnabiblioteka.BazaPodataka.Book;

import java.util.List;

public class BookListActivity extends AppCompatActivity {

    Button addButton;
    ListView lvBooks;
    List<Book> books;
    String[] names;
    String[] descriptions;
    int[] imagePos;
    String[] fav;
    String[] idBook;
    String favorite;
    String[] authors;
    String[] genres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
        System.out.println("U BookListActivity sam");

        addButton = (Button) findViewById(R.id.addButton);
        lvBooks = (ListView) findViewById(R.id.lvBooks);
        int position = Integer.parseInt(getIntent().getStringExtra("position"));
        if(position == 0) {
            books = MainActivity.myDbHandler.getAllBooks();
            favorite = "0";
        }else {
            books = MainActivity.myDbHandler.getBooksByFav();
            favorite = "1";
        }
        CustomAdapter customAdapter = new CustomAdapter();

        lvBooks.setAdapter(customAdapter);

        names = new String[books.size()];
        descriptions = new String[books.size()];
        imagePos = new int[books.size()];
        idBook = new String[books.size()];
        fav = new String[books.size()];
        authors = new String[books.size()];
        genres = new String[books.size()];

        int i = 0;
        for (Book b:
             books) {
            System.out.println(b.get_id() + b.get_bookName());
            names[i] = b.get_bookName();
            descriptions[i] = b.get_bookDescription();
            if(b.is_favorite()){
                imagePos[i] = R.drawable.favorite;
                fav[i] = "1";
            }else {
                imagePos[i] = R.drawable.emptyfavorite;
                fav[i] = "0";
            }
            idBook[i]= String.valueOf(b.get_id());
            //System.out.println("Book name is "+b.get_bookName());           System.out.println("Autor knjige je "+b.get_author().getAuthorName());
            //authors[i] = b.get_author().getAuthorName();
            //genres[i] = b.get_genre().getGenreName();
            i++;
        }

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAddBook = new Intent(BookListActivity.this, AddBook.class);
                intentAddBook.putExtra("favorite", favorite);
                startActivity(intentAddBook);
            }
        });

    }

    class CustomAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return books.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @SuppressLint("ResourceAsColor")
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            convertView = getLayoutInflater().inflate(R.layout.activity_book_list_custom, null, false);
            ImageView ivFavorite = (ImageView)convertView.findViewById(R.id.ivFavorite);
            TextView tvName = (TextView)convertView.findViewById(R.id.tvAuthorName);
            TextView tvBookDesc = (TextView)convertView.findViewById(R.id.tvBookDesc);

            tvName.setText(names[position]);
            ivFavorite.setImageResource(imagePos[position]);
            tvBookDesc.setText(descriptions[position]);

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   Intent intent = new Intent(BookListActivity.this, UpdateBookActivity.class);
                   intent.putExtra("bookId", idBook[position]);
                   intent.putExtra("bookName", names[position]);
                    intent.putExtra("bookDesc", descriptions[position]);
                   intent.putExtra("image", fav[position]);
                   //intent.putExtra("author", authors[position]);
                   //intent.putExtra("genre", genres[position]);
                   startActivity(intent);
                }
            });

            return convertView;
        }
    }

}
