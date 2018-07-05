package com.example.sara.marketer.widget;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sara.marketer.R;
import com.example.sara.marketer.utils.AppConstant;


/**
 * Created by Raad on 5/25/17.
 */

public class ToolBar implements View.OnClickListener {


    final static String TAG = Toolbar.class.getSimpleName();

    Toolbar toolbar;

    TextView toolbarTitle;

    ImageView toolbarAdd;

    ImageView toolbarBarcode;

    ImageView toolbarFilter;

    ImageView toolbarEdit;

    ImageView toolbarClose;

    ImageView toolbarShare;

    ImageView toolbarSave;

    ImageView toolbarClear;

    TextView toolbarAddDoc;

    ImageView toolbarSetting;

    ImageView toolbarSync;

    AppCompatActivity mActivity;

    ActionBar actionBar;


    @Override
    public void onClick(View v) {
        Log.i(TAG, "onClick:tool bar ");
        switch (v.getId()) {

            case R.id.image_view_filter:
                onToolbarListener.onClicked(AppConstant.FILTER);
                break;
            case R.id.relative_share:
                onToolbarListener.onClicked(AppConstant.SHARE);
                break;
            case R.id.relative_edit:
                onToolbarListener.onClicked(AppConstant.EDIT);
                break;
            case R.id.relative_barcode:
                onToolbarListener.onClicked(AppConstant.ADDDOC);
                break;
            case R.id.image_view_add_account:
                onToolbarListener.onClicked(AppConstant.ADD);
                break;
            case R.id.image_view_setting_toolbar:
                onToolbarListener.onClicked(AppConstant.EXIT);
                break;
            case R.id.image_view_sync_toolbar:
                onToolbarListener.onClicked(AppConstant.SYNC);
                break;
            case R.id.image_view_close_toolbar:
                onToolbarListener.onClicked(AppConstant.CLOSE);
                break;
            case R.id.image_view_clear_toolbar:
                onToolbarListener.onClicked(AppConstant.CLEAR);
                break;
            case R.id.image_view_save_toolbar:
                onToolbarListener.onClicked(AppConstant.SAVE);
                break;

        }
    }


    public interface OnToolbarListener {
        void onClicked(int position);
    }

    private OnToolbarListener onToolbarListener;

    public void setOnToolbarListener(OnToolbarListener onToolbarListener) {
        this.onToolbarListener = onToolbarListener;
    }

    public ToolBar(AppCompatActivity activity, View view, int toolbarId) {
        mActivity = activity;
        toolbar = (Toolbar) view.findViewById(toolbarId);
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbarTitle = (TextView) view.findViewById(R.id.toolbar_title);
        toolbarAdd = (ImageView) view.findViewById(R.id.image_view_add_account);
        toolbarAddDoc = (TextView) view.findViewById(R.id.text_view_add_doc);
        toolbarBarcode = (ImageView) view.findViewById(R.id.relative_barcode);
        toolbarEdit = (ImageView) view.findViewById(R.id.relative_edit);
        toolbarShare = (ImageView) view.findViewById(R.id.relative_share);
        toolbarSave = (ImageView) view.findViewById(R.id.image_view_save_toolbar);
        toolbarClear = (ImageView) view.findViewById(R.id.image_view_clear_toolbar);
        toolbarFilter = (ImageView) view.findViewById(R.id.image_view_filter);
        toolbarSetting = (ImageView) view.findViewById(R.id.image_view_setting_toolbar);
        toolbarSync = (ImageView) view.findViewById(R.id.image_view_sync_toolbar);
        toolbarClose = (ImageView) view.findViewById(R.id.image_view_close_toolbar);

        toolbar.setTitleTextColor(ContextCompat.getColor(activity, R.color.colorWhite));

        toolbarAdd.setOnClickListener(this);
        toolbarBarcode.setOnClickListener(this);
        toolbarSetting.setOnClickListener(this);
        toolbarFilter.setOnClickListener(this);
        toolbarEdit.setOnClickListener(this);
        toolbarSync.setOnClickListener(this);
        toolbarShare.setOnClickListener(this);
        toolbarClose.setOnClickListener(this);
        toolbarClear.setOnClickListener(this);
        toolbarSave.setOnClickListener(this);

    }

    public void setTitle(CharSequence title) {
        toolbarTitle.setText(title);
//        toolbarTitle.setTypeface(Typeface.SERIF);
//        toolbar.setTitle(title);
    }

    public void hideBack() {
        actionBar = mActivity.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.show();
    }

    public void showBack() {
        actionBar = mActivity.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.show();
    }

    public void showCloseButton() {
        toolbarClose.setVisibility(View.VISIBLE);
    }
    public void showSyncButton() {
        toolbarSync.setVisibility(View.VISIBLE);
    }

    public void showCustomSetting() {
        toolbarSetting.setVisibility(View.VISIBLE);
    }

    public void showAddButton() {
        toolbarAdd.setVisibility(View.VISIBLE);
    }

    public void showEditButton() {
        toolbarEdit.setVisibility(View.VISIBLE);
    }

    public void showShareButton() {
        toolbarShare.setVisibility(View.VISIBLE);
    }

    public void showClearButton() {
        toolbarClear.setVisibility(View.VISIBLE);
    }
    public void showSaveButton() {
        toolbarSave.setVisibility(View.VISIBLE);
    }

    public void showFilterButton() {
        toolbarFilter.setVisibility(View.VISIBLE);
    }

    public void hideEditButton() {
        toolbarEdit.setVisibility(View.GONE);
    }

    public void hideAddButton() {
        toolbarAdd.setVisibility(View.GONE);
    }


    public void showAddDocButton() {
        toolbarAddDoc.setVisibility(View.VISIBLE);
    }

    public void hideAddDocButton() {
        toolbarAddDoc.setVisibility(View.GONE);
    }

    public void showAddBarcodeButton() {
        toolbarBarcode.setVisibility(View.VISIBLE);
    }

    public void hideAddBarcodeButton() {
        toolbarBarcode.setVisibility(View.GONE);
    }
}
