package com.example.sara.marketer.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.sara.marketer.R;
import com.example.sara.marketer.utils.AppConstant;
import com.example.sara.marketer.utils.DialogUtil;


/**
 * Created by sara on 9/17/17.
 */

public class ShowDialog implements View.OnClickListener {

    private int type;
    private TextView title_TextView;
    private TextView version_TextView;
    private TextView message_TextView;
    private Dialog mDIalog;

    private String title;
    private String version;
    private String message;
    private ShowDialogComunication showDialogComunication;


    public final static int SUBMIT = 0;
    public static int CANCEL = 1;


    private Button buttonSubmit, buttonCancle;

    public interface ShowDialogComunication {
        void showDialogButtonClicked(int position);

    }

    public void setShowDialogComunication(ShowDialogComunication showDialogComunication) {
        this.showDialogComunication = showDialogComunication;
    }


    public ShowDialog(Context context, int type, String title, String detail, String version) {

        this.title = title;
        this.message = detail;
        this.type = type;

        this.version=version;
        mDIalog = DialogUtil.showDialog(context, R.layout.dialog_layout);
        mDIalog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        initUi();
        initValues();

        buttonSubmit.setOnClickListener(this);
        buttonCancle.setOnClickListener(this);

    }


    private void initUi() {

        title_TextView = (TextView) mDIalog.findViewById(R.id.text_title);
        version_TextView = (TextView) mDIalog.findViewById(R.id.text_version);
        message_TextView = (TextView) mDIalog.findViewById(R.id.text_message);
        buttonCancle = (Button) mDIalog.findViewById(R.id.button_cancle);
        buttonSubmit = (Button) mDIalog.findViewById(R.id.button_submit);


    }


    private void initValues() {

        title_TextView.setText(title);
        version_TextView.setText(version);
        message_TextView.setText(message);


        switch (type) {

            case AppConstant.EXIT:


                break;
        }


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.button_submit:
                showDialogComunication.showDialogButtonClicked(SUBMIT);
                mDIalog.dismiss();
                break;
            case R.id.button_cancle:
////                showDialogComunication.showDialogButtonClicked(CANCEL);
                mDIalog.dismiss();
                break;

        }


    }


}
