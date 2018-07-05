package com.example.sara.marketer.main;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.sara.marketer.MyApplication;
import com.example.sara.marketer.R;
import com.example.sara.marketer.merchantlist.MerchantFragment;
import com.example.sara.marketer.utils.ChangeFragment;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyApplication myApplication = (MyApplication) MainActivity.this.getApplicationContext();


        ChangeFragment.changeFragment(this, new MerchantFragment(), R.id.container, false);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
