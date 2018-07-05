package com.example.sara.marketer.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Raad on 11/4/17.
 */

public class Result {

    @SerializedName("message")
    String message;
    @SerializedName("_id")
    String id;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
