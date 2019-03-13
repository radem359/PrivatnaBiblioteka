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

import com.example.milica.privatnabiblioteka.BazaPodataka.Author;

import java.util.List;

public class AuthorListActivity extends AppCompatActivity {

    Button btAddAuth;
    ListView lvAuthors;
    List<Author> authors;
    String[] names;
    String[] authorId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author_list);

        btAddAuth = (Button) findViewById(R.id.btAddAuth);
        lvAuthors = (ListView) findViewById(R.id.lvAuthors);
        authors = MainActivity.myDbHandler.getAllAuthors();


        CustomAdapter customAdapter = new CustomAdapter();

        lvAuthors.setAdapter(customAdapter);

        names = new String[authors.size()];
        authorId = new String[authors.size()];

        int i = 0;
        for (Author a:
                authors) {
            names[i] = a.getAuthorName();
            authorId[i] = String.valueOf(a.get_id());
            i++;
        }

        btAddAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAddAuthor = new Intent(AuthorListActivity.this, AddAuthor.class);
                startActivity(intentAddAuthor);
            }
        });

    }

    class CustomAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return authors.size();
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

            convertView = getLayoutInflater().inflate(R.layout.activity_author_list_custom, null, false);
            TextView tvAuthName = (TextView)convertView.findViewById(R.id.tvAuthName);
           ImageView ivAuthor = (ImageView)convertView.findViewById(R.id.ivAuthor);

            //System.out.println(idBook[position]);
            tvAuthName.setText(names[position]);

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AuthorListActivity.this, UpdateAuthorActivity.class);
                    intent.putExtra("authorId", authorId[position]);
                    intent.putExtra("authorName", names[position]);
                    startActivity(intent);
                }
            });

            return convertView;

        }
    }

}
