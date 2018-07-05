package com.example.sara.marketer.widget;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import com.example.sara.marketer.R;
import com.example.sara.marketer.utils.DialogUtil;
import com.example.sara.marketer.utils.JalaliCalender;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by sara on 9/17/17.
 */

public class ShowDateDialog implements View.OnClickListener {

    private Dialog mDIalog;

    private ShowDialogComunication showDialogComunication;

    NumberPicker pickerDay;
    NumberPicker pickerMonth;
    NumberPicker pickerYear;

    public final static int SUBMIT = 0;
    public static int CANCEL = 1;
String date;

    private Button buttonSubmit, buttonCancle;

    public interface ShowDialogComunication {
        void showDialogButtonClicked(String date);

    }

    public void setShowDialogComunication(ShowDialogComunication showDialogComunication) {
        this.showDialogComunication = showDialogComunication;
    }



    public ShowDateDialog(Context context) {


        mDIalog = DialogUtil.showDialog(context, R.layout.fragment_date_of_birth);

        initUi();

        buttonSubmit.setOnClickListener(this);
        buttonCancle.setOnClickListener(this);


        pickerDay.setMinValue(1);
        pickerDay.setMaxValue(31);

        pickerMonth.setMinValue(1);
        pickerMonth.setMaxValue(12);


        String gDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        convertGregoianDate(gDate);
    }


    private void initUi() {

        buttonCancle = (Button) mDIalog.findViewById(R.id.button_cancle);
        buttonSubmit = (Button) mDIalog.findViewById(R.id.return_button);

        pickerYear = (NumberPicker) mDIalog.findViewById(R.id.picker_year);
        pickerDay = (NumberPicker) mDIalog.findViewById(R.id.picker_day);
        pickerMonth = (NumberPicker) mDIalog.findViewById(R.id.picker_month);


    }

    private void convertGregoianDate(String dateTime) {

        int year = Integer.valueOf(dateTime.substring(0, 4));
        int month = Integer.valueOf(dateTime.substring(5, 7));
        int day = Integer.valueOf(dateTime.substring(8, 10));
//        String time = dateTime.substring(12, 20);
        Log.i("", "convertDate: " + year + " " + month + " " + day);

        pickerYear.setMinValue(1300);
        pickerYear.setMaxValue(new JalaliCalender().gregorianToJalali(new JalaliCalender.YearMonthDate(year, month - 1, day)).getYear() );

        pickerYear.setValue(new JalaliCalender().gregorianToJalali(new JalaliCalender.YearMonthDate(year, month - 1, day)).getYear() );
        pickerMonth.setValue(new JalaliCalender().gregorianToJalali(new JalaliCalender.YearMonthDate(year, month - 1, day)).getMonth());
        pickerDay.setValue(new JalaliCalender().gregorianToJalali(new JalaliCalender.YearMonthDate(year, month - 1, day)).getDate());

    }

    public String onContinueButtonClicked() {

        String year = String.valueOf(pickerYear.getValue());
        String day = String.valueOf(pickerDay.getValue());
        String month = String.valueOf(pickerMonth.getValue());

        if (day.length() == 1) {
            day = "0" + day;
        }
        if (month.length() == 1) {
            month = "0" + month;
        }

        String jDate = String.valueOf(year + "/" + month + "/" + day);
        return jDate;
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.return_button:

                showDialogComunication.showDialogButtonClicked(onContinueButtonClicked());
                mDIalog.dismiss();
                break;
            case R.id.button_cancle:
////                showDialogComunication.showDialogButtonClicked(CANCEL);
                mDIalog.dismiss();
                break;

        }


    }


}
