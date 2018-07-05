package com.example.sara.marketer.DB;

import com.example.sara.marketer.model.Merchant;

import java.util.List;

/**
 * Created by Raad on 10/28/17.
 */

public interface ResultReceiver {
    void onResult(List<Merchant> obj);
}
