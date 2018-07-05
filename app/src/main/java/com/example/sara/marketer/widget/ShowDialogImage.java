package com.example.sara.marketer.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.ImageView;

import com.example.sara.marketer.R;
import com.example.sara.marketer.utils.DialogUtil;

import java.io.File;

/**
 * Created by sara on 9/17/17.
 */

public class ShowDialogImage implements View.OnClickListener {

    private Dialog mDIalog;


    public static int CLOSE = 1;
    String path;

    private ImageView buttonCLose;
    private ImageView imageView;


    public ShowDialogImage(Context context, String path) {

        this.path = path;
        mDIalog = DialogUtil.showDialog(context, R.layout.dialog_layout_image);
        mDIalog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        initUi();
        buttonCLose.setOnClickListener(this);
        loadImage(path);

    }

    private void loadImage(String path) {
        File imgFile = new File(path);

        if(imgFile.exists()){

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

            imageView.setImageBitmap(myBitmap);

        }
    }

    private void initUi() {

        imageView = (ImageView) mDIalog.findViewById(R.id.image_view);
        buttonCLose = (ImageView) mDIalog.findViewById(R.id.button_close);

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.button_close:
                mDIalog.dismiss();
                break;

        }


    }


}
