package com.example.radosav.privatnabiblioteka.BazaPodataka;

public class Book {

    private int _id;
    private String _bookName;
    private String _bookDescription;
    private boolean _favorite;
    private int _authorId;
    private int _genreId;

    public Book(){}

    public Book(String bookName, String bookDescription, boolean favorite) {
        this._bookName = bookName;
        this._bookDescription = bookDescription;
        this._favorite = favorite;
    }

    public Book(String _bookName, String _bookDescription, boolean _favorite, int _authorId, int _genreId) {
        this._bookName = _bookName;
        this._bookDescription = _bookDescription;
        this._favorite = _favorite;
        this._authorId = _authorId;
        this._genreId = _genreId;
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

    public int get_author() {
        return _authorId;
    }

    public void set_author(int _author) {
        this._authorId = _author;
    }

    public int get_genre() {
        return _genreId;
    }

    public void set_genre(int _genre) {
        this._genreId = _genre;
    }
}
