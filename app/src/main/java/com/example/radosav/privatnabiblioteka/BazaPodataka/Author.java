package com.example.radosav.privatnabiblioteka.BazaPodataka;

public class  Author{

    private int _id;
    private String authorName;

    public Author(){}

    public Author(String authorName, int _id) {
        this._id = _id;
        this.authorName = authorName;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

}
