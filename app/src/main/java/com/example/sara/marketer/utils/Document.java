package com.example.sara.marketer.utils;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sara on 11/6/17.
 */

public class Document {
    @SerializedName("doc_name")
    String docName;

    @SerializedName("doc_url")
    String docURL;


    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getDocURL() {
        return docURL;
    }

    public void setDocURL(String docURL) {
        this.docURL = docURL;
    }
}
