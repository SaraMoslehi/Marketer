package com.example.sara.marketer.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Raad on 5/25/17.
 */

public class Token {

    @SerializedName("X-ACCESS-TOKEN")
    public String access_token;
    @SerializedName("X-TOKEN-TYPE")
    public String token_type;



}
