package com.example.milica.privatnabiblioteka.BazaPodataka;

import java.util.List;

public class  Author{

    private int _id;
    private String authorName;

    public Author(){}

    public Author(String authorName) {
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
