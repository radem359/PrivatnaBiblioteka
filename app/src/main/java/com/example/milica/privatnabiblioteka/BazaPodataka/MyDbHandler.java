package com.example.milica.privatnabiblioteka.BazaPodataka;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.renderscript.ScriptIntrinsicYuvToRGB;

import java.util.ArrayList;
import java.util.List;

public class MyDbHandler extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "privatlibrary.db";

    public static final String TABLE_BOOK = "book";
    public static final String COLUMN_BOOK_ID = "_id";
    public static final String COLUMN_BOOK_NAME = "book_name";
    public static final String COLUMN_BOOK_DESCRIPTION = "book_description";
    public static final String COLUMN_BOOK_FAVORITE = "book_favorite";
    public static final String COLUMN_BOOK_AUTHOR_ID = "book_author_id";
    public static final String COLUMN_BOOK_GENRE_ID = "book_genre_id";

    public static final String TABLE_AUTHOR = "author";
    public static final String COLUMN_AUTHOR_ID = "_id";
    public static final String COLUMN_AUTHOR_NAME = "author_name";

    public static final String TABLE_GENRE = "genre";
    public static final String COLUMN_GENRE_ID = "_id";
    public static final String COLUMN_GENRE_NAME = "genre_name";

    public MyDbHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    public  MyDbHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    String bookQuery = "CREATE TABLE "+TABLE_BOOK + " (" +
            COLUMN_BOOK_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
            COLUMN_BOOK_NAME+" TEXT NOT NULL,"+
            COLUMN_BOOK_DESCRIPTION+ " TEXT NOT NULL,"+
            COLUMN_BOOK_FAVORITE+" INTEGER NOT NULL,"+
            COLUMN_BOOK_AUTHOR_ID+" INTEGER NOT NULL,"+
            COLUMN_BOOK_GENRE_ID+" INTEGER NOT NULL);";

    String authorQuery = "CREATE TABLE "+TABLE_AUTHOR+ " ("+
            COLUMN_AUTHOR_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT,"+
            COLUMN_AUTHOR_NAME+" TEXT NOT NULL);";

    String genreQuery = "CREATE TABLE "+TABLE_GENRE+ " ("+
            COLUMN_GENRE_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT,"+
            COLUMN_GENRE_NAME+" TEXT NOT NULL);";
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(bookQuery);
        db.execSQL(authorQuery);
        db.execSQL(genreQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_BOOK);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_AUTHOR);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_GENRE);
        onCreate(db);
    }

    public void addBook(Book book){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_BOOK_NAME, book.get_bookName());
        values.put(COLUMN_BOOK_DESCRIPTION, book.get_bookDescription());
        if(book.is_favorite()){
            values.put(COLUMN_BOOK_FAVORITE, 1);
        }else{
            values.put(COLUMN_BOOK_FAVORITE, 0);
        }
        Author author = book.get_author();
        if(author != null) {
            System.err.println("!!!!!!!!!!!!!!!!!!SACUVANO!!!!!!!!!!!!!!");
            values.put(COLUMN_BOOK_AUTHOR_ID, author.get_id());
        }

        Genre genre = book.get_genre();
        if(genre != null) {
            values.put(COLUMN_BOOK_GENRE_ID, genre.get_id());
        }

        db.insert(TABLE_BOOK, null, values);
        db.close();
    }

    public void deleteBook(Book book){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_BOOK, COLUMN_BOOK_ID+"= ?", new String[]{ String.valueOf(book.get_id())} );
        db.close();
    }

    public List<Book> getAllBooks(){
        List<Book> listOfBooks = new ArrayList<Book>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_BOOK;

        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do{
                Book book = cursorToBook(cursor);
                listOfBooks.add(book);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return listOfBooks;
    }

    public Book getBookById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_BOOK, new String[] { COLUMN_BOOK_ID,
                        COLUMN_BOOK_NAME, COLUMN_BOOK_DESCRIPTION }, COLUMN_BOOK_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        int flag = Integer.parseInt(cursor.getString(3));
        Book book = new Book(cursor.getString(1), cursor.getString(2), (flag==1)?true:false);
        cursor.close();
        return book;
    }

    public List<Book> getBooksByFav() {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Book> listOfBooks = new ArrayList<Book>();
        Cursor cursor = db.query(TABLE_BOOK, new String[] { COLUMN_BOOK_ID,
                        COLUMN_BOOK_NAME, COLUMN_BOOK_DESCRIPTION, COLUMN_BOOK_FAVORITE, COLUMN_BOOK_AUTHOR_ID, COLUMN_BOOK_GENRE_ID }, COLUMN_BOOK_FAVORITE + "=?",
                new String[] {String.valueOf(1)}, null, null, null, null);
        if(cursor.moveToFirst()){
            do{
                Book book = cursorToBook(cursor);
                listOfBooks.add(book);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return listOfBooks;
    }

    public void updateBook(Book book) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_BOOK_NAME, book.get_bookName());
        values.put(COLUMN_BOOK_DESCRIPTION, book.get_bookDescription());
        if(book.is_favorite()){
            values.put(COLUMN_BOOK_FAVORITE, 1);
        }else{
            values.put(COLUMN_BOOK_FAVORITE, 0);
        }
        Author author = book.get_author();
        if(author != null) {
            System.err.println("!!!!!!!!!!!!!!!!!!UPDATOVANO!!!!!!!!!!!!!!");
            values.put(COLUMN_BOOK_AUTHOR_ID, author.get_id());
        }

        Genre genre = book.get_genre();
        if(genre != null) {
            values.put(COLUMN_BOOK_GENRE_ID, genre.get_id());
        }
        // updating row
        db.update(TABLE_BOOK, values, COLUMN_BOOK_ID + " = "+book.get_id(), null);
    }

    public Book cursorToBook(Cursor cursor) {
        Book book = new Book();
        book.set_id(cursor.getInt(0));
        book.set_bookName(cursor.getString(1));
        book.set_bookDescription(cursor.getString(2));

        int flag = cursor.getInt(3);
        if(flag == 1)
            book.set_favorite(true);
        else
            book.set_favorite(false);

        Author author = null;
        if(cursor.getString(4) != null) {
            author = getAuthorById(Integer.parseInt(cursor.getString(4)));
            System.out.println("CURSOR 4 != NULL");
        }
        if(author != null)
            book.set_author(author);

        Genre genre = null;
        if(cursor.getString(5) != null) {
            genre = getGenreById(Integer.parseInt(cursor.getString(5)));
            System.out.println("CURSOR 5 != NULL");
        }
        if(genre != null)
            book.set_genre(genre);

        return book;
    }

    public void addAuthor(Author author){
        ContentValues values = new ContentValues();
        values.put(COLUMN_AUTHOR_NAME, author.getAuthorName());

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_AUTHOR, null, values);
        db.close();
    }

    public List<Author> getAllAuthors(){
        List<Author> listOfAuthors = new ArrayList<Author>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_AUTHOR;

        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do{
                Author author = cursorToAuthor(cursor);
                listOfAuthors.add(author);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return listOfAuthors;
    }

    /*public Author getAuthorById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Author author = null;
        Cursor cursor = db.query(TABLE_AUTHOR, new String[] { COLUMN_AUTHOR_ID,
                        COLUMN_AUTHOR_NAME }, COLUMN_AUTHOR_ID + "= ?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        System.out.println("Author id is "+id);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            System.out.println("Author id is "+cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR_ID)));
            author = new Author(cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR_NAME)));
            System.out.println("AUTHR NAME IS " + author.getAuthorName());
            cursor.close();
        }
        db.close();
        return author;
    }*/

    public Author getAuthorById(int idAuthor) {
        SQLiteDatabase db = this.getReadableDatabase();
        Author a = null;
        Cursor cursor = db.query(TABLE_AUTHOR, new String[] { COLUMN_AUTHOR_ID,
                        COLUMN_AUTHOR_NAME}, COLUMN_AUTHOR_ID + "=?",
                new String[] { String.valueOf(idAuthor) }, null, null, null, null);
        if (cursor != null) {
            System.out.println("CURSOR JESTE RAZLICIT!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            if(cursor.moveToFirst()){
                System.out.println("!!!!!!!!!!!!!!!!!!!!!!moveToFirst!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                a = new Author(cursor.getString(1));
            }
        }
        return a;
    }

    public Author getAuthorByName(String authorName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Author author = null;
        Cursor cursor = db.query(TABLE_AUTHOR, new String[] { COLUMN_AUTHOR_ID,
                        COLUMN_AUTHOR_NAME }, COLUMN_AUTHOR_NAME + "= ?",
                new String[] { authorName }, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            author = new Author(cursor.getString(1));
        }
        cursor.close();
        return author;
    }

    public void updateAuthor(Author author) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_AUTHOR_NAME, author.getAuthorName());

        // updating row
        db.update(TABLE_AUTHOR, values, COLUMN_AUTHOR_ID + " = "+author.get_id(), null);
    }

    public void deleteAuthor(Author author){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_AUTHOR, COLUMN_AUTHOR_ID+"= ?", new String[]{ String.valueOf(author.get_id())} );
        db.close();
    }

    protected Author cursorToAuthor(Cursor cursor) {
        Author author = new Author();
        author.set_id(cursor.getInt(0));
        author.setAuthorName(cursor.getString(1));
        return author;
    }

    public void addGenre(Genre genre){
        ContentValues values = new ContentValues();
        values.put(COLUMN_GENRE_NAME, genre.getGenreName());

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_GENRE, null, values);
        db.close();
    }

    public List<Genre> getAllGenres(){
        List<Genre> listOfGenres = new ArrayList<Genre>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_GENRE;

        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do{
                Genre genre = cursorToGenre(cursor);
                listOfGenres.add(genre);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return listOfGenres;
    }

    public Genre getGenreById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Genre genre = null;
        Cursor cursor = db.query(TABLE_GENRE, new String[] { COLUMN_GENRE_ID,
                        COLUMN_GENRE_NAME }, COLUMN_GENRE_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null && cursor.moveToFirst())
            genre = new Genre(cursor.getString(1));
        cursor.close();
        return genre;
    }

    public Genre getGenreByName(String genreName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Genre genre = null;
        Cursor cursor = db.query(TABLE_GENRE, new String[] { COLUMN_GENRE_ID,
                        COLUMN_GENRE_NAME }, COLUMN_GENRE_NAME + "= ?",
                new String[] { genreName }, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            genre = new Genre(cursor.getString(1));
        }
        cursor.close();
        return genre;
    }

    public void deleteGenre(Genre genre) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_GENRE, COLUMN_GENRE_ID+" = ?", new String[]{String.valueOf(genre.get_id())});
    }

    public void updateGenre(Genre genre) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_GENRE_NAME, genre.getGenreName());

        // updating row
        db.update(TABLE_GENRE, values, COLUMN_GENRE_ID + " = "+genre.get_id(), null);
    }

    protected Genre cursorToGenre(Cursor cursor) {
        Genre author = new Genre();
        author.set_id(cursor.getInt(0));
        author.setGenreName(cursor.getString(1));
        return author;
    }
}
