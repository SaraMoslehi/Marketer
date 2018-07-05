package com.example.sara.marketer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Raad on 11/4/17.
 */

public class    MerchantAccount {
    @SerializedName("next_page")
    @Expose
    private Boolean nextPage;
    @SerializedName("result")
    @Expose
    private List<Merchant> result = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public MerchantAccount() {
    }

    /**
     *
     * @param result
     * @param nextPage
     */
    public MerchantAccount(Boolean nextPage, List<Merchant> result) {
        super();
        this.nextPage = nextPage;
        this.result = result;
    }

    public Boolean getNextPage() {
        return nextPage;
    }

    public void setNextPage(Boolean nextPage) {
        this.nextPage = nextPage;
    }

    public List<Merchant> getResult() {
        return result;
    }

    public void setResult(List<Merchant> result) {
        this.result = result;
    }

}
