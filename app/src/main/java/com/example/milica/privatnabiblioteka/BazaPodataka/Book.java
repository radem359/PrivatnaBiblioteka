package com.example.milica.privatnabiblioteka.BazaPodataka;

import java.util.List;

public class Book {

    private int _id;
    private String _bookName;
    private String _bookDescription;
    private boolean _favorite;
    private Author _author;
    private Genre _genre;

    public Book(){}

    public Book(String bookName, String bookDescription, boolean favorite) {
        this._bookName = bookName;
        this._bookDescription = bookDescription;
        this._favorite = favorite;
    }

    public Book(String _bookName, String _bookDescription, boolean _favorite, Author _author, Genre _genre) {
        this._bookName = _bookName;
        this._bookDescription = _bookDescription;
        this._favorite = _favorite;
        this._author = _author;
        this._genre = _genre;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_bookName() {
        return _bookName;
    }

    public void set_bookName(String _bookName) {
        this._bookName = _bookName;
    }

    public String get_bookDescription() {
        return _bookDescription;
    }

    public void set_bookDescription(String _bookDescription) {
        this._bookDescription = _bookDescription;
    }

    public boolean is_favorite() {
        return _favorite;
    }

    public void set_favorite(boolean _favorite) {
        this._favorite = _favorite;
    }

    public Author get_author() {
        return _author;
    }

    public void set_author(Author _author) {
        this._author = _author;
    }

    public Genre get_genre() {
        return _genre;
    }

    public void set_genre(Genre _genre) {
        this._genre = _genre;
    }
}
