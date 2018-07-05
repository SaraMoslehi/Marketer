package com.example.sara.marketer.login;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


import com.example.sara.marketer.R;
import com.example.sara.marketer.utils.ChangeFragment;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        ChangeFragment.changeFragment(this, new LoginFragment(), R.id.container, false);
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
