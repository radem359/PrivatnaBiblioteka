package com.example.milica.privatnabiblioteka;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.milica.privatnabiblioteka.BazaPodataka.Author;

public class UpdateAuthorActivity extends AppCompatActivity {

    TextView tvAuthorName;
    Button update, delete;
    int authorId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_author);

        tvAuthorName = (TextView)findViewById(R.id.tvAuthorName);
        update = (Button) findViewById(R.id.btUpdAuthor);
        delete = (Button)findViewById(R.id.btDelAuthor);

        String name = getIntent().getStringExtra("authorName");
        tvAuthorName.setText(name);
        authorId = Integer.parseInt(getIntent().getStringExtra("authorId"));

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateAuthorActivity.this, MainActivity.class);

                Author author = new Author();
                author.set_id(authorId);
                author.setAuthorName(tvAuthorName.getText().toString().trim());

                MainActivity.myDbHandler.updateAuthor(author);

                startActivity(intent);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateAuthorActivity.this, MainActivity.class);

                Author author = new Author();
                author.set_id(authorId);
                author.setAuthorName(tvAuthorName.getText().toString().trim());

                MainActivity.myDbHandler.deleteAuthor(author);

                startActivity(intent);
            }
        });

    }
}
