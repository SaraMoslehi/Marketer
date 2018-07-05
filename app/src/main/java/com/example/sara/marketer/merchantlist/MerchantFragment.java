package com.example.sara.marketer.merchantlist;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.sara.marketer.DB.ContentProviderMerchnat;
import com.example.sara.marketer.DB.DbOpenHelper;
import com.example.sara.marketer.DB.MerchantTable;
import com.example.sara.marketer.DB.ResultCountReceiver;
import com.example.sara.marketer.DB.ResultReceiver;
import com.example.sara.marketer.MyApplication;
import com.example.sara.marketer.R;
import com.example.sara.marketer.SplashActivity;
import com.example.sara.marketer.merchantdetail.AddNewMerchantFragment;
import com.example.sara.marketer.model.Merchant;
import com.example.sara.marketer.model.MerchantAccount;
import com.example.sara.marketer.model.User;
import com.example.sara.marketer.utils.AppConstant;
import com.example.sara.marketer.utils.ChangeFragment;
import com.example.sara.marketer.utils.SharedprefrencedUtil;
import com.example.sara.marketer.widget.ShowDialog;
import com.example.sara.marketer.widget.ShowDialogFilter;
import com.example.sara.marketer.widget.ToolBar;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sara.marketer.DB.MerchantTable._ID;

/**
 * A simple {@link Fragment} subclass.
 */
public class MerchantFragment extends Fragment implements ToolBar.OnToolbarListener, View.OnClickListener {

    final static String TAG = MerchantFragment.class.getSimpleName();

    View view;
    ToolBar appBar;

    List<Merchant> merchantList;
    //    List<Merchant> merchantSyncList;
    MerchantAdapter merchantAdapter;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    MyApplication myApplication;
    //    Boolean nextURL;
    ProgressBar progressBar;

    int merchantListSize = 0;
    int merchantSavedCount = 0;

    public MerchantFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: ");
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_merchant, container, false);
        myApplication = (MyApplication) MerchantFragment.this.getActivity().getApplicationContext();
        Log.i(TAG, "onCreateView: " + User.getInstance().getJwtToken());

        appBar = new ToolBar((AppCompatActivity) getActivity(), view, R.id.toolbar);
        // dari eshteba mizani :)
        appBar.setTitle(getString(R.string.merchant_list));
        appBar.showCustomSetting();
        appBar.showAddButton();
        appBar.showFilterButton();
        appBar.showSyncButton();
        appBar.setOnToolbarListener(this);

        merchantList = new ArrayList<>();
        merchantAdapter = new MerchantAdapter(getActivity(), merchantList);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycle_view);
        mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(merchantAdapter);


//        merchantSyncList = new ArrayList<>();
        progressBar = (ProgressBar) view.findViewById(R.id.progressbar);

        Log.i(TAG, "onCreateView:isSynced " + AppConstant.IS_SYNCED);
        if (!AppConstant.IS_SYNCED) {
            syncMerchant();
            AppConstant.IS_SYNCED = true;
        } else {

            fillMerchant(AppConstant.ALL);
        }

        List<Merchant> m = new ArrayList<>();
        DbOpenHelper dbHelper = new DbOpenHelper(getContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM merchants order by merchantDate desc ", null);
        if (c.moveToFirst()) {
            do {
                Merchant contact = new Merchant();
                contact.setMerchandId(c.getInt(c.getColumnIndex(DbOpenHelper.MERCAHANTID)));
                contact.setId(c.getString(c.getColumnIndex(_ID)));
                contact.setState(c.getInt(c.getColumnIndex("status")));
                contact.setStoreName(c.getString(c.getColumnIndex("storeName")));
                contact.setFirstName(c.getString(c.getColumnIndex("firstName")));
                contact.setModifiedDate(c.getString(c.getColumnIndex("merchantDate")));
                // Adding contact to list
                m.add(contact);
            } while (c.moveToNext());
        }
        return view;
    }

    private void syncMerchant() {
//        if (isNetworkAvailable()) {
//            progressBar.setVisibility(View.VISIBLE);
            // TODO: 10/31/17
            //fetch sqlite nosent
//            fillMerchantSync(AppConstant.NOT_SENT);
            /////no inset since now


//        } else {
            fillMerchant(AppConstant.ALL);

            Toast.makeText(getContext(), "No Internt", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);

//        }

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: ");
//        if (isNetworkAvailable()) {
//
//        }else {
//        }

    }

    @Override
    public void onClicked(final int position) {
        Log.i("", "onClicked: " + position);
        switch (position) {
            case AppConstant.BACK:
                ChangeFragment.removeFragment(getActivity(), MerchantFragment.this, view);

                break;
            case AppConstant.FILTER:
                ShowDialogFilter showDialogFilter = new ShowDialogFilter(getContext());
                showDialogFilter.setShowDialogFilterComunication(new ShowDialogFilter.setShowDialogFilterComunication() {
                    @Override
                    public void showDialogButtonClicked(int position) {
                        Log.i(TAG, "showDialogButtonClicked: " + position);
                        fillMerchant(position);

                    }
                });
                break;
            case AppConstant.ADD:
//                if (isNetworkAvailable()) {

                ChangeFragment.changeFragment(getActivity(), new AddNewMerchantFragment(), R.id.container, true);
//                }else {
//                    Toast.makeText(getContext(), "No Internt", Toast.LENGTH_SHORT).show();
//
//                }

                break;
            case AppConstant.SYNC:

                syncMerchant();

                break;
            case AppConstant.EXIT:
                ShowDialog showDialog = new ShowDialog(getContext(), AppConstant.EXIT, getString(R.string.main_exit), getString(R.string.sure_msg), getString(R.string.menu_version) + " " + getVersion());
                showDialog.setShowDialogComunication(new ShowDialog.ShowDialogComunication() {
                    @Override
                    public void showDialogButtonClicked(int position) {
                        switch (position) {
                            case ShowDialog.SUBMIT:
                                logOut();
                                break;
                        }
                    }
                });
                break;
        }
    }

    Integer page = 1;

    private void updateAccount(final String localDate) {
//        Web.getInstance().getMerchantList(User.getInstance().getJwtToken(), localDate, page.toString()).enqueue(new Callback<MerchantAccount>() {
//            @Override
//            public void onResponse(Call<MerchantAccount> call, Response<MerchantAccount> response) {
//                if (response.isSuccessful()) {
//                    Log.i(TAG, "updateAccount +onResponse: ");
////                    merchantSyncList.clear();
////                    merchantSyncList.addAll(response.body().getResult());
//                    merchantList.addAll(response.body().getResult());
//                    if (response.body().getNextPage()) {
//                        page++;
//                        updateAccount(localDate);
//                    } else {
////                        if (merchantSyncList.size() > 0) {
//                        if (merchantList.size() > 0) {
//
////                            for (int i = 0; i < merchantSyncList.size(); i++) {
//                            for (int i = 0; i < merchantList.size(); i++) {
////                                Log.i(TAG, "onResponse: i ===== " + merchantSyncList.size());
////                                updateSyncMerchant(merchantSyncList.get(i));
//                                updateSyncMerchant(merchantList.get(i));
//
//                            }
//                        } else {
//                            fillMerchant(AppConstant.ALL);
//
//                        }
//
//                        progressBar.setVisibility(View.GONE);
////                        merchantAdapter.notifyDataSetChanged();
//                    }
//                    if (response.body().getResult() == null) {
//
//                        Toast.makeText(getContext(), "No Record to sync", Toast.LENGTH_SHORT).show();
//
//                        progressBar.setVisibility(View.GONE);
//                    }
//
////                    nextURL = response.body().getNextPage();
//                    Log.i(TAG, "onResponse: " + response.body().getResult().size());
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<MerchantAccount> call, Throwable t) {
//                progressBar.setVisibility(View.GONE);
//            }
//        });
    }
//
//    private boolean isNetworkAvailable() {
//        ConnectivityManager connectivityManager
//                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
//        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
//    }

    String lastDate = null;

    private void getMerchantToSync() {
        lastDate = null;
        Log.i(TAG, "getMerchantToSync: ");
        MerchantTable.getLastDate(getContext(), new ResultReceiver() {
            @Override
            public void onResult(List<Merchant> merchants) {
                Log.i(TAG, "onResult: " + merchants);
//                if (merchants != null) {
                if (merchants == null) {
                    merchants = new ArrayList<Merchant>();
                    lastDate = "0";

                } else {
                    Log.i(TAG, "onResult: getMerchantToSync" + merchants.size());
                    if (merchants.get(0).getModifiedDate() == null) {
                        lastDate = "0";
                    } else {
                        lastDate = merchants.get(0).getModifiedDate().substring(0, 10);
                        Log.i(TAG, "onResult: lastdate" + lastDate);
                    }
//
                }
                Log.i(TAG, "onResult: " + merchants.size() + "  " + lastDate);
                updateAccount(lastDate);

            }
        });

    }

    private String getVersion() {
        String version = "1.0";
        try {
            PackageInfo pInfo = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
            version = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return version;
    }

    Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    private void saveAccount(final Merchant merchant) {
//        Log.i(TAG, "saveAccount: " + myApplication.getCurrentUser().getJwtToken());
//            setLoading(true);
//        Web.getInstance().saveMerchant(User.getInstance().getJwtToken(), merchant).
//                enqueue(new Callback<Result>() {
//                            @Override
//                            public void onResponse(Call<Result> call, Response<Result> response) {
//
//                                if (response.isSuccessful()) {
//                                    merchantSavedCount++;
//                                    // TODO: 10/31/17 get id from server
//                                    merchant.setId(response.body().getId());
//                                    merchant.setState(AppConstant.TEMPACTIVE);
//                                    Log.i(TAG, "onResponse: ");
//
//                                    updateMerchant(merchant);
////                                    Toast.makeText(mContext, R.string.saved, Toast.LENGTH_SHORT).show();
//                                    if (merchantSavedCount == merchantListSize) {
//                                        getMerchantToSync();
//                                        merchantSavedCount = 0;
//                                        merchantListSize = 0;
//                                    }
//
//                                }
////                                    setLoading(false);
//                            }
//
//                            @Override
//                            public void onFailure(Call<Result> call, Throwable t) {
//                                Toast.makeText(mContext, R.string.network_error, Toast.LENGTH_SHORT).show();
//
//                            }
//                        }
//
//
//                );
    }

    private void updateMerchant(Merchant merchant) {
        MerchantTable.updateMerchantsSyncToTable(mContext, merchant);
    }

//    int plus = 0;

    private void updateSyncMerchant(final Merchant merchant) {
        Log.i(TAG, "updateSyncMerchant: 111");
//        merchant.setState(AppConstant.TEMPACTIVE);
        MerchantTable.updateMerchantsToTable(mContext, merchant, new ResultCountReceiver() {
            @Override
            public void onResult(int i) {

//                Log.i(TAG, "onResult: merchantSyncList" + merchantSyncList.size() + " plus " + plus);
                Log.i(TAG, "onResult: merchantSyncList" + merchantList.size());
//                if (merchantSyncList.size() == plus) {
//                if (merchantList.size() == plus) {
                Log.i(TAG, "onResult: true--------");
                fillMerchant(AppConstant.ALL);
//                    plus = 0;
//
//                }


            }
        });
        //// TODO: 11/4/17 timeserver
        progressBar.setVisibility(View.GONE);

    }


    private void fillMerchant(int position) {

        MerchantTable.getMerchants(getContext(), position, new ResultReceiver() {
            @Override
            public void onResult(List<Merchant> merchants) {

                merchantList.clear();
                if (merchants != null) {
                    merchantList.addAll(merchants);

                } else {
                    Log.i(TAG, "onResult: null");
                }
                merchantAdapter.notifyDataSetChanged();

            }
        });
    }

    private void fillMerchantSync(int position) {
//        merchantSyncList.clear();
        merchantList.clear();
        page = 1;
        MerchantTable.getMerchants(getContext(), position, new ResultReceiver() {
            @Override
            public void onResult(List<Merchant> merchants) {

                if (merchants != null) {
                    merchantListSize = merchants.size();
                    Log.i(TAG, "onResult: " + merchants.size());
                    for (int i = 0; i < merchantListSize; i++) {
                        saveAccount(merchants.get(i));
                    }

                } else {
                    getMerchantToSync();
                    Log.i(TAG, "onResult: null");
                }

            }
        });
    }


    private ByteArrayOutputStream compressFile(String imageUrl) {
        int compressionRatio = 2; //1 == originalImage, 2 = 50% compression, 4=25% compress
        ByteArrayOutputStream bos;
        File file = new File(imageUrl);
        try {
            Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
            bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, compressionRatio, bos);
        } catch (Throwable t) {
            bos = null;
            Log.e("ERROR", "Error compressing file." + t.toString());
            t.printStackTrace();
        }

        return bos;
    }


    private void logOut() {
        SharedprefrencedUtil.deleteUser();
        SharedprefrencedUtil.deleteDeviceToken();
        getContext().getContentResolver().delete(ContentProviderMerchnat.CONTENT_URI, null, null);
        User.getInstance().setUser(null);
        AppConstant.IS_SYNCED = false;

        Intent intent = new Intent(getActivity(), SplashActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        getActivity().finish();


    }

    @Override
    public void onClick(View v) {

    }


}
