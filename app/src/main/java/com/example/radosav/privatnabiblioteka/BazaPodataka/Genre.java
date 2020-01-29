package com.example.radosav.privatnabiblioteka.BazaPodataka;

public class  Genre{

    private int _id;
    private String genreName;

    public Genre(){}

    public Genre(String genreName, int _id) {
        this._id = _id;
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
