package com.example.radosav.privatnabiblioteka;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.radosav.privatnabiblioteka.BazaPodataka.Author;

public class AddAuthor extends AppCompatActivity {

    TextView tvAuthorName;

    Button btSaveAuthor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_author);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvAuthorName = (TextView)findViewById(R.id.tvAuthorName);
        btSaveAuthor = (Button) findViewById(R.id.btSaveAuthor);


        btSaveAuthor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddAuthor.this, AuthorListActivity.class);

                Author author = new Author();
                author.setAuthorName(tvAuthorName.getText().toString().trim());

                MainActivity.myDbHandler.addAuthor(author);
                startActivity(intent);
            }
        });

    }
}
