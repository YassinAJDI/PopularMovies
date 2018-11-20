package com.ajdi.yassin.popularmovies.data.local.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author Yassin Ajdi
 * @since 11/20/2018.
 */
public class Genre {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
