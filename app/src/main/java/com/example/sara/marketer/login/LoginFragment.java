package com.example.sara.marketer.login;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.example.sara.marketer.MyApplication;
import com.example.sara.marketer.R;
import com.example.sara.marketer.main.MainActivity;
import com.example.sara.marketer.model.User;
import com.example.sara.marketer.utils.ChangeFragment;
import com.example.sara.marketer.utils.SharedprefrencedUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {
    private static final String ARG = "Identity";

    EditText editTextPhone;
    Button buttonLogin;
    TextView textViewErrorHint;
    View view;
    ProgressBar progressBar;
    ImageView imageViewClose;

    MyApplication myApplication;
    TextView textViewTitle;
    boolean param = false;
    TextView textViewHint;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance(boolean param) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG, param);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            param = getArguments().getBoolean(ARG);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_login, container, false);
        myApplication = (MyApplication) LoginFragment.this.getActivity().getApplicationContext();
        Log.i("", "onCreateView: "+ MyApplication.MY_SHARED_PREFRENCES.getString(SharedprefrencedUtil.DEVICE_TOKEN,null));


        editTextPhone = (EditText) view.findViewById(R.id.edit_text_new_phone);
        buttonLogin = (Button) view.findViewById(R.id.button_no_sim);
        progressBar = (ProgressBar) view.findViewById(R.id.progressbar_login);
        textViewErrorHint = (TextView) view.findViewById(R.id.text_view_error_hint);
        textViewTitle=(TextView)view.findViewById(R.id.text_view_front_title);
        textViewHint=(TextView)view.findViewById(R.id.text_view_hint);


        buttonLogin.setOnClickListener(this);

        showKeyboard();
        editTextPhone.requestFocus();
        editTextPhone.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                checkNumber();
                return false;
            }
        });
        editTextPhone.addTextChangedListener(new TextWatcherLisetner());
        return view;
    }

    private void setLoading(boolean loading) {
        editTextPhone.setEnabled(!loading);
        buttonLogin.setEnabled(!loading);
        progressBar.setVisibility(loading ? View.VISIBLE : View.GONE);

    }
//
//    private void getOTP() {
//        setLoading(true);
//        Web.getInstance().requestOTP(editTextPhone.getText().toString()).
//                enqueue(new Callback<String>() {
//                            @Override
//                            public void onResponse(Call<String> call, Response<String> response) {
//
//                                if (response.isSuccessful()) {
//
//                                    ChangeFragment.changeFragment(getActivity(), OTPFragment.newInstance(editTextPhone.getText().toString()), R.id.container, true);
//
//                                }
//                                setLoading(false);
//                            }
//
//
//                            @Override
//                            public void onFailure(Call<String> call, Throwable t) {
//                                Toast.makeText(getActivity(), R.string.network_error, Toast.LENGTH_SHORT).show();
//                                setLoading(false);
//
//                            }
//                        }
//
//
//                );
//    }

    private void showKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    private void hideKeyboard() {
        if (getActivity().getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_no_sim:
                checkNumber();
                break;

        }
    }

    @Override
    public void onResume() {
        super.onResume();

    }
    private void checkNumber() {
        hideKeyboard();
        if (editTextPhone.getText().toString().trim().length() == 0) {
            textViewErrorHint.setText(getString(R.string.login_enter_valid_phone));
            textViewErrorHint.setTextColor(getResources().getColor(R.color.colorfireenginered));
            return;
        }
        if (editTextPhone.getText().toString().trim().length() != 11) {
            textViewErrorHint.setText(getString(R.string.login_text_cell_number_error));
            textViewErrorHint.setTextColor(getResources().getColor(R.color.colorfireenginered));
            return;
        }
//

//        getOTP();
        SharedprefrencedUtil.saveShareprefrenced(SharedprefrencedUtil.USER_KEY,new User("09128079650", null, "bearer " ));
//                             myApplication.setCurrentUser();

        startActivity(new Intent(LoginFragment.this.getActivity(), MainActivity.class));
        getActivity().finish();
//

    }

    @Override
    public void onPause() {
        super.onPause();
        hideKeyboard();
    }

    @Override
    public void onStop() {
        super.onStop();
        hideKeyboard();
    }


    private class TextWatcherLisetner implements TextWatcher {

        private View view;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            Log.i("", "onTextChanged: ");
            if (editTextPhone.getText().toString().trim().length() == 0) {
                textViewErrorHint.setText(getString(R.string.login_text_cell_number_));
                textViewErrorHint.setTextColor(getResources().getColor(R.color.colorbattleshipgrey));

            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}
