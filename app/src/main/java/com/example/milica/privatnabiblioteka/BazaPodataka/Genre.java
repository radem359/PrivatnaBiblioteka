package com.example.milica.privatnabiblioteka.BazaPodataka;

import java.util.List;

public class  Genre{

    private int _id;
    private String genreName;

    public Genre(){}

    public Genre(String genreName) {
        this.genreName = genreName;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }
}
