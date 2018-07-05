package com.example.sara.marketer.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.TextView;

import com.example.sara.marketer.R;
import com.example.sara.marketer.utils.AppConstant;
import com.example.sara.marketer.utils.DialogUtil;


/**
 * Created by Raad on 9/17/17.
 */

public class ShowDialogFilter implements View.OnClickListener {

    private TextView textViewNoSent;
    private TextView textViewSentNoActive;
    private TextView textViewSentActive;
    private TextView textViewSentReject;
    private TextView textViewAll;
    private Dialog mDIalog;


    private setShowDialogFilterComunication showDialogComunication;




    public interface setShowDialogFilterComunication {
        void showDialogButtonClicked(int position);

    }

    public void setShowDialogFilterComunication(setShowDialogFilterComunication showDialogComunication) {
        this.showDialogComunication = showDialogComunication;
    }


    public ShowDialogFilter(Context context) {

        mDIalog = DialogUtil.showDialogFilter(context, R.layout.dialog_filter_layout);
        mDIalog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        initUi();

        textViewNoSent.setOnClickListener(this);
        textViewSentNoActive.setOnClickListener(this);
        textViewSentActive.setOnClickListener(this);
        textViewSentReject.setOnClickListener(this);
        textViewAll.setOnClickListener(this);

    }


    private void initUi() {

        textViewNoSent = (TextView) mDIalog.findViewById(R.id.text_not_sent);
        textViewSentNoActive = (TextView) mDIalog.findViewById(R.id.text_sent_no_active);
        textViewSentActive = (TextView) mDIalog.findViewById(R.id.text_active);
        textViewSentReject = (TextView) mDIalog.findViewById(R.id.text_sent_reject);
        textViewAll = (TextView) mDIalog.findViewById(R.id.text_all);

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.text_not_sent:
                showDialogComunication.showDialogButtonClicked(AppConstant.NOT_SENT);
                mDIalog.dismiss();
                break;
            case R.id.text_sent_no_active:
                showDialogComunication.showDialogButtonClicked(AppConstant.TEMPACTIVE);
                mDIalog.dismiss();
                break;
            case R.id.text_active:
                showDialogComunication.showDialogButtonClicked(AppConstant.VERIFY);
                mDIalog.dismiss();
                break;
            case R.id.text_sent_reject:
                showDialogComunication.showDialogButtonClicked(AppConstant.DOCERROR);
                mDIalog.dismiss();
                break;
            case R.id.text_all:
                showDialogComunication.showDialogButtonClicked(AppConstant.ALL);
                mDIalog.dismiss();
                break;

        }


    }


}
