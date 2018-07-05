package com.example.sara.marketer.merchantdetail;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.sara.marketer.DB.MerchantTable;
import com.example.sara.marketer.DB.ResultCountReceiver;
import com.example.sara.marketer.DB.ResultURIReceiver;
import com.example.sara.marketer.MyApplication;
import com.example.sara.marketer.R;
import com.example.sara.marketer.model.City;
import com.example.sara.marketer.model.Image;
import com.example.sara.marketer.model.Merchant;
import com.example.sara.marketer.model.MerchantAccount;
import com.example.sara.marketer.model.User;
import com.example.sara.marketer.utils.AppConstant;
import com.example.sara.marketer.utils.AutoResizeTextView;
import com.example.sara.marketer.utils.ChangeFragment;
import com.example.sara.marketer.utils.Document;
import com.example.sara.marketer.utils.JalaliCalender;
import com.example.sara.marketer.utils.ResourceHelper;
import com.example.sara.marketer.widget.ShowDateDialog;
import com.example.sara.marketer.widget.ToolBar;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressWarnings("ResourceType")
public class AddNewMerchantFragment extends Fragment implements View.OnClickListener, ToolBar.OnToolbarListener, ImageAdapter.imageAdapterInterface {
    final static String TAG = AddNewMerchantFragment.class.getSimpleName();
    public final static int PICTURE_REQUEST_EXTRA = 300;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 10;


    Image image;
    int position;

    private static final int insert = 0;
    private static final int update = 1;
    private static final int show = 2;

    static int editMode;

    private boolean askedPermission = false;
    private static int cameraPermissionReqCode = 250;

    private String pictureImagePath = "";

    private String selectedItem = null;
    private String selectedValue;

    private static final String ARG_PARAM1 = "param1";
    private View view;
    private ToolBar appBar;
    private Merchant merchant;

    private ProgressBar progressbar;

    private EditText editTextFirstName, editTextLastName, editTextSheba,
            editTextNationaCode, editTextAddress, editTextField,
            editTextCellNo, editTextStoreName, editTextBarcode;

    private EditText editTextFatherName, editTextSerialNo, editTextBankName, editTextAccountType,
            editTextBranchName, editTextBarnchCode, editTextPhoneNo, editTextWebsite, editTextEmail,
            editTextPoetalCode, editTextAccountNo;

    private TextView textBirtDate;
    private Spinner spinnerCity;
    private AutoResizeTextView textFirstName, textLastName, textStoreName, textNationalCode, textViewCell;
    //    private Button addButton, signatureButton, buttonDate;
    private Button addButton, buttonDate;
    private ImageView buttonAddDoc;
    private Button signatureRemoveButton;
//    private ImageView imageViewNatinal, imageViewBussiness, imageViewsign;

    private LinearLayout linearLayout;
    private MyApplication myApplication;

    private Boolean offline = false;
    private List<Image> list;
    private List<Document> documentList;


    private ArrayAdapter<String> cityArrayAdapter;
    private ArrayList<String> cityList;
    private ArrayList<City> cities;

    private Context mContext;

    Geocoder geocoder;

    String bestProvider;
    List<Address> user = null;
    float lat=0;
    float lng=0;

    private ImageAdapter imageAdapter;
    private RecyclerView recyclerView;

    private ImageShowAdapter imageShowAdapter;
    private RecyclerView recyclerViewImage;

    BottomSheetDialog bottomSheetDialog;
    TextView textViewPhote, textViewGallery;

    public AddNewMerchantFragment() {
        // Required empty public constructor
    }

    public static AddNewMerchantFragment newInstance(Merchant merchant) {
        AddNewMerchantFragment fragment = new AddNewMerchantFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, merchant);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        savedInstanceState.putInt("MyInt", 1);
//        if (savedInstanceState == null) {
//            Log.i(TAG, "onViewStateRestored: restore");
//            state = 8;
//        }
        Log.i(TAG, "onCreate: ");
        if (getArguments() != null) {
            merchant = (Merchant) getArguments().getSerializable(ARG_PARAM1);

            editMode = show;
        } else {
            merchant = new Merchant();
            editMode = insert;
        }

        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_new_merchant, container, false);


        myApplication = (MyApplication) AddNewMerchantFragment.this.getActivity().getApplicationContext();

        appBar = new ToolBar((AppCompatActivity) getActivity(), view, R.id.toolbar);
        appBar.setTitle(getString(R.string.add_new_merchant));

        appBar.showBack();

        Log.i(TAG, "onCreateView: " + savedInstanceState);
        init();

        setCityOffLine();

//        if (isNetworkAvailable()) {
//            offline = false;
//        } else {
            offline = true;
//        }


        if (editMode == show) {
            Log.i(TAG, "onCreateView: " + editMode);
            addButton.setVisibility(View.GONE);
            appBar.setTitle(getString(R.string.merchant));
            setEditable(false);

            if (merchant.getState() != AppConstant.NOT_SENT) {
                if (!offline) {
                    getMerchant(merchant.getId());
                } else {
                    getMerchant(merchant);

                }
            } else {
                if (!offline) {
                    getMerchant(merchant);

                } else {
                    getMerchant(merchant);
                }

            }

            appBar.showAddBarcodeButton();
            appBar.showEditButton();
            appBar.setOnToolbarListener(this);
            Log.i(TAG, "onCreateView: " + merchant.toString());

        } else if (editMode == insert) {
            Log.i(TAG, "onCreateView: insert");
            setEditable(true);
        }
//        else if (editMode == update) {
//            Log.i(TAG, "setEditable: ");
//            recyclerViewImage.setVisibility(View.VISIBLE);
//
//        }

//        setLoction();

        return view;
    }

    private void setLoction() {
        LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        bestProvider = lm.getBestProvider(criteria, false);
        Location location = lm.getLastKnownLocation(bestProvider);

        if (location == null) {
            Toast.makeText(getActivity(), "Location Not found", Toast.LENGTH_LONG).show();
        } else {
            geocoder = new Geocoder(getActivity());
            try {
                user = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                lat = (float) user.get(0).getLatitude();
                lng = (float) user.get(0).getLongitude();
                System.out.println(" DDD lat: " + lat + ",  longitude: " + lng);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


////////event

    @Override
    public void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= 23) {
            askExternalStoragePermissionForImageCapture();
        }
        if (editMode == insert) {

            showKeyboard();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
//            hideKeyboard();
            Log.i(TAG, "onOptionsItemSelected: ");
            ChangeFragment.removeFragment(getActivity(), AddNewMerchantFragment.this, view);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        Log.i(TAG, "onClick: " + v.getId());

        switch (v.getId()) {

            case R.id.text_take_photo:
                checkedVersion();
                bottomSheetDialog.dismiss();

                break;
            case R.id.text_from_gallery:
                bottomSheetDialog.dismiss();

                break;
            case R.id.button_add_more:

                list.add(new Image(0, "", "", "", null));
                imageAdapter.notifyDataSetChanged();
                break;

            case R.id.button_set_date:
                Log.i(TAG, "onClick: button_set_date");
                ShowDateDialog showDateDialog = new ShowDateDialog(getContext());
                showDateDialog.setShowDialogComunication(new ShowDateDialog.ShowDialogComunication() {
                    @Override
                    public void showDialogButtonClicked(String date) {
                        textBirtDate.setText(date);
                    }
                });
                break;
            case R.id.add_button:

                checkData();


                break;
//            case R.id.button_signature_remove:
//                signatureRemoveButton.setVisibility(View.GONE);
////                signPath = null;
////                merchant.setSignature(null);

//                break;
            case R.id.button_signature:
//                hideKeyboard();
//                SignatureFragment fragment = new SignatureFragment();
//                fragment.setListener(new SignatureListener() {
//                    @Override
//                    public void onSaveClicked(String path) {
////                        signPath = path;
//                        list.add(new Image(position, AppConstant.SIGNATURE, path, "", false));
//                        imageAdapter.notifyDataSetChanged();
//                    }
//                });
//                ChangeFragment.changeFragment(getActivity(), fragment, R.id.container, true);
                break;
//            case R.id.button_barcode:
//                generateBarcode();
//
//                break;


        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        Log.i(TAG, "onRequestPermissionsResult: 222");
        if (requestCode == cameraPermissionReqCode) {
            Log.i(TAG, "onRequestPermissionsResult: ");
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // permission was granted
                takePicture();
            } else {
                Log.i(TAG, "else: ");
                // TODO: display better error message.
//                displayFrameworkBugMessageAndExit();
            }
        } else {
            Log.i(TAG, "onRequestPermissionsResult: 1");
        }
    }

    @Override
    public void onClicked(int position) {
        switch (position) {

            case AppConstant.ADDDOC:
                generateBarcode();

                break;
            case AppConstant.EDIT:
//                hideKeyboard();
                editMode = update;
                setEditable(true);

                appBar.hideEditButton();
                break;

        }
    }

    ////////method

    /////city

    private void setCityOffLine() {
        cityList.clear();
        for (TypedArray item : ResourceHelper.getMultiTypedArray(getContext(), "city")) {
            City city = new City();
            city.setCityId(item.getString(0));
            city.setCityName(item.getString(1));
            cities.add(city);
            cityList.add(item.getString(1));
        }
        setCity();

    }

    private void setCity() {
//

        cityArrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.simple_spinner_item, cityList) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                TextView view = (TextView) super.getView(position, convertView, parent);
                Log.i(TAG, "getView: --------------");
                if (editMode == show) {
                    view.setTextColor(Color.GRAY);
                }

                return view;
            }


        };

        cityArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
////

        spinnerCity.setAdapter(cityArrayAdapter);


        spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedItem = cities.get(position).getCityId();
                Log.i(TAG, "onItemSelected: " + selectedItem);
                selectedValue = parent.getItemAtPosition(position).toString();
                Log.i(TAG, "onItemSelected: " + selectedValue);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.i(TAG, "onNothingSelected: " + selectedItem);

            }
        });


    }


    /////show merchant
    private void getMerchant(String id) {
        progressbar.setVisibility(View.VISIBLE);
//        Web.getInstance().getMerchant(User.getInstance().getJwtToken(), id).enqueue(new Callback<MerchantAccount>() {
//            @Override
//            public void onResponse(Call<MerchantAccount> call, Response<MerchantAccount> response) {
//                if (response.isSuccessful()) {
//
//                    getMerchant(response.body().getResult().get(0));
//                }
//                progressbar.setVisibility(View.GONE);
//
//            }
//
//            @Override
//            public void onFailure(Call<MerchantAccount> call, Throwable t) {
//                Toast.makeText(mContext, R.string.network_error, Toast.LENGTH_SHORT).show();
//
//                progressbar.setVisibility(View.GONE);
//
//            }
//        });
    }

    /////

    private void init() {
        Log.i(TAG, "init: ");
        editTextFirstName = (EditText) view.findViewById(R.id.editext_first_name);
        editTextLastName = (EditText) view.findViewById(R.id.editext_last_name);
        editTextFatherName = (EditText) view.findViewById(R.id.editext_father_name);
        editTextSheba = (EditText) view.findViewById(R.id.editext_sheba);
        editTextAddress = (EditText) view.findViewById(R.id.editext_address);
        editTextField = (EditText) view.findViewById(R.id.editext_field);
        editTextNationaCode = (EditText) view.findViewById(R.id.editext_national_code);
        editTextCellNo = (EditText) view.findViewById(R.id.editext_cell);
        editTextStoreName = (EditText) view.findViewById(R.id.editext_store);
        editTextBarcode = (EditText) view.findViewById(R.id.editext_barcode);

        addButton = (Button) view.findViewById(R.id.add_button);
//        signatureButton = (Button) view.findViewById(R.id.button_signature);
//        signatureRemoveButton = (Button) view.findViewById(R.id.button_signature_remove);

//        linearLayout = (LinearLayout) view.findViewById(R.id.linear_barcode);

        editTextBankName = (EditText) view.findViewById(R.id.editext_bank_name);
        editTextBarnchCode = (EditText) view.findViewById(R.id.editext_bank_code);
        editTextBranchName = (EditText) view.findViewById(R.id.editext_branch_name);

        textBirtDate = (TextView) view.findViewById(R.id.editext_birth_date);

        cityList = new ArrayList<>();
        cities = new ArrayList<>();
        spinnerCity = (Spinner) view.findViewById(R.id.spinner_birth_city);

        editTextFatherName = (EditText) view.findViewById(R.id.editext_phone);
        editTextPhoneNo = (EditText) view.findViewById(R.id.editext_zip_code);
        editTextPoetalCode = (EditText) view.findViewById(R.id.edit_text_account_no);
        editTextAccountNo = (EditText) view.findViewById(R.id.edit_text_account_no);
        editTextAccountType = (EditText) view.findViewById(R.id.editext_account_type);
        editTextWebsite = (EditText) view.findViewById(R.id.editext_web_site);
        editTextEmail = (EditText) view.findViewById(R.id.editext_email);
        editTextSerialNo = (EditText) view.findViewById(R.id.editext_shenasname);

        buttonDate = (Button) view.findViewById(R.id.button_set_date);
        buttonAddDoc = (ImageView) view.findViewById(R.id.button_add_more);

        addButton.setOnClickListener(this);
//        signatureButton.setOnClickListener(this);

        buttonDate.setOnClickListener(this);
        buttonAddDoc.setOnClickListener(this);


        textFirstName = (AutoResizeTextView) view.findViewById(R.id.text_view_first_name);
        textLastName = (AutoResizeTextView) view.findViewById(R.id.text_view_last_name);
        textNationalCode = (AutoResizeTextView) view.findViewById(R.id.text_view_national_code);
        textStoreName = (AutoResizeTextView) view.findViewById(R.id.text_view_store_name);
        textViewCell = (AutoResizeTextView) view.findViewById(R.id.text_view_cell);


//        imageViewNatinal = (ImageView) view.findViewById(R.id.image_view_natinal_card);
//        imageViewBussiness = (ImageView) view.findViewById(R.id.image_view_licence);
//        imageViewsign = (ImageView) view.findViewById(R.id.image_view_sign);
        bottomSheetDialog = new BottomSheetDialog(getContext());
        bottomSheetDialog.setContentView(R.layout.fragment_sheet_image);
        textViewGallery = (TextView) bottomSheetDialog.findViewById(R.id.text_from_gallery);
        textViewPhote = (TextView) bottomSheetDialog.findViewById(R.id.text_take_photo);
        textViewGallery.setOnClickListener(this);
        textViewPhote.setOnClickListener(this);

        bottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                bottomSheetDialog.dismiss();
            }
        });

        progressbar = (ProgressBar) view.findViewById(R.id.progressbar);
        Log.i(TAG, "init: " + editMode);

        if (list == null) {
            list = new ArrayList<>();
            list.add(new Image(0, "", "", "", null));

        }
        imageAdapter = new ImageAdapter(getActivity(), list);
        imageAdapter.setImageAdapterInterface(this);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycle_view_image);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());


        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(imageAdapter);

        if (documentList == null) {
            documentList = new ArrayList<>();

        }

        imageShowAdapter = new ImageShowAdapter(getActivity(), documentList);
        recyclerViewImage = (RecyclerView) view.findViewById(R.id.recycle_view_image_view);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getActivity().getApplicationContext());

        recyclerViewImage.setLayoutManager(linearLayoutManager1);
        recyclerViewImage.setAdapter(imageShowAdapter);


    }

    //////permisssion
    private void askExternalStoragePermissionForImageCapture() {
        // Here, thisActivity is the current activity
        Log.i(TAG, "askExternalStoragePermissionForImageCapture: ");
        if (ContextCompat.checkSelfPermission(AddNewMerchantFragment.this.getActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(AddNewMerchantFragment.this.getActivity(),
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);


        } else {
            Log.i(TAG, "askExternalStoragePermissionForImageCapture: mCamera.startPreview()");

        }
    }

    ///////save
    private void saveMerchant(Merchant merchant) {
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date());
        merchant.setModifiedDate(timeStamp);
        MerchantTable.setMerchantsToTable(mContext, merchant, new ResultURIReceiver() {
            @Override
            public void onResult(Uri i) {
                progressbar.setVisibility(View.GONE);
                changeFragment();

            }
        });

    }


    private void fillNewMerchant() {

        merchant.setFirstName(editTextFirstName.getText().toString().trim());
        merchant.setLastName(editTextLastName.getText().toString().trim());
//        merchant.setFatherName(editTextFatherName.getText().toString().trim());
        merchant.setNationalCode(editTextNationaCode.getText().toString().trim());
        merchant.setShebaNumber(editTextSheba.getText().toString().trim());
        merchant.setAddress(editTextAddress.getText().toString().trim());
        merchant.setCellNo(editTextCellNo.getText().toString().trim());
//        merchant.setShenasnameNo(editTextShenasname.getText().toString().trim());
        merchant.setStoreName(editTextStoreName.getText().toString().trim());

        merchant.setFathername(editTextFatherName.getText().toString().trim());
        merchant.setBankName(editTextBankName.getText().toString().trim());
        merchant.setBranchCode(editTextBranchName.getText().toString().trim());
        merchant.setBranchName(editTextBranchName.getText().toString().trim());
        merchant.setWebSite(editTextWebsite.getText().toString().trim());
        merchant.setEmail(editTextEmail.getText().toString().trim());
        merchant.setAccountNo(editTextAccountNo.getText().toString().trim());
        merchant.setAccountType(editTextAccountType.getText().toString().trim());
        merchant.setCityOfBirthCode(selectedItem);
//        merchant.setCityOfBirth1(selectedValue);

        merchant.setBirthdate(textBirtDate.getText().toString().trim());

        merchant.setLongitude(lng);
        merchant.setLatitude(lat);
        Log.i(TAG, "fillNewMerchant: " + editTextBarcode.getText().toString().trim());
        merchant.setBarcode(editTextBarcode.getText().toString().trim());

    }


    private void getMerchant(Merchant merchant) {
        editTextFirstName.setText(merchant.getFirstName());
        editTextLastName.setText(merchant.getLastName());
        editTextNationaCode.setText(merchant.getNationalCode());
        editTextSheba.setText(merchant.getShebaNumber());
        editTextAddress.setText(merchant.getAddress());
        editTextCellNo.setText(merchant.getCellNo());
        editTextStoreName.setText(merchant.getStoreName());

        editTextWebsite.setText(merchant.getWebSite());
        editTextEmail.setText(merchant.getEmail());
        editTextBankName.setText(merchant.getBankName());
        editTextBarnchCode.setText(merchant.getBranchCode());
        editTextBranchName.setText(merchant.getBranchName());
        editTextAccountNo.setText(merchant.getAccountNo());
        editTextAccountType.setText(merchant.getAccountType());
        editTextFatherName.setText(merchant.getFathername());
        editTextSerialNo.setText(merchant.getIdentiricationId());
        editTextBarcode.setText(merchant.getBarcode());
        if (!TextUtils.isEmpty(merchant.getBirthdate())) {

            if (offline) {
                textBirtDate.setText(merchant.getBirthdate().substring(0, 10));
            } else if (merchant.getBirthdate().length() > 0) {
                if (merchant.getState() != AppConstant.NOT_SENT) {
                    textBirtDate.setText(convertGregoianDate(merchant.getBirthdate()));
                } else {
                    textBirtDate.setText(merchant.getBirthdate().substring(0, 10));
                }
            }

        }


        setEditable(false);

        checkImage(merchant);

        if (!TextUtils.isEmpty(merchant.getCityOfBirthCode())) {
//
//            }
            for (City d : cities) {
                if (d.getCityId().equals(merchant.getCityOfBirthCode())) {
                    spinnerCity.setSelection(cityList.indexOf(d.getCityName()));
                    return;
                }
            }
        }


    }

    private String convertGregoianDate(String dateTime) {

        int year = Integer.valueOf(dateTime.substring(0, 4));
        int month = Integer.valueOf(dateTime.substring(5, 7));
        int day = Integer.valueOf(dateTime.substring(8, 10));
//        String time = dateTime.substring(12, 20);
        Log.i("", "convertDate: " + year + " " + month + " " + day);
        String jbDate;

        jbDate = String.valueOf(new JalaliCalender().gregorianToJalali(new JalaliCalender.YearMonthDate(year, month - 1, day)).getYear());
        jbDate += "/" + String.valueOf(new JalaliCalender().gregorianToJalali(new JalaliCalender.YearMonthDate(year, month - 1, day)).getMonth());
        jbDate += "/" + String.valueOf(new JalaliCalender().gregorianToJalali(new JalaliCalender.YearMonthDate(year, month - 1, day)).getDate());

        return jbDate;
    }


    private void generateBarcode() {

        if (!TextUtils.isEmpty(merchant.getId())) {

            Log.d(TAG, "onClicked: " + merchant.getId());
//            ChangeFragment.changeFragment(getActivity(), FinalBarcodeFragment.newInstance(editTextStoreName.getText().toString(), merchant.getId()), R.id.container, true);
        }

    }


    private void checkData() {
        hideKeyboard();

        if (!checkIsValid()) {
            return;
        }

        fillNewMerchant();

        checkNetwork();


    }

    private void checkFile(final int index) {
        Log.i(TAG, "checkFile: " + index);
        uploadFiles(list.get(index).getImagePath(), new UploadFileListener() {
            @Override
            public void onRsponseResult(String pathURL) {
                Log.i(TAG, "onRsponseResult: " + index);
                laodlist(pathURL, index);
            }
        });
    }


    private void checkNetwork() {
        progressbar.setVisibility(View.VISIBLE);
//
//        if (isNetworkAvailable()) {
//
//            if (editMode == update) {
//                //todo 1/11
////                merchant.setState(AppConstant.TEMPACTIVE);
//
//                if (list.size() > 0) {
//                    loadAgain(0);
//
//
//                } else {
//
//                    editAccount();
//                }
//
//            } else if (editMode == insert) {
//                //todo 1/11
////                merchant.setState(AppConstant.TEMPACTIVE);
//
//                if (list.size() > 0) {
//                    loadAgain(0);
//
//
//                } else {
//
//                    saveAccount();
//                }
//            }
//
//
//        } else {
            //todo
            if (editMode == update) {

                sendOfflineEdit();
            } else if (editMode == insert) {


                sendOffline();

            }


//        }
    }

    private void laodlist(String pathURL, int index) {

        if (!TextUtils.isEmpty(pathURL)) {

//            if (list.get(index).getImageTitle().equals(AppConstant.SIGNATURE)) {
//                merchant.setSignature(pathURL);
//                list.get(index).setLinkPath(pathURL);
//            } else {
            list.get(index).setLinkPath(pathURL);
//            }
            Log.i(TAG, "laodlist: " + index);
            list.get(index).setSent(true);

            loadAgain(index + 1);

        } else

        {
            list.get(index).setSent(false);
            Toast.makeText(getActivity(), getString(R.string.file_error), Toast.LENGTH_SHORT).show();
            return;
        }

    }

    private void loadAgain(int index) {
        if (list.size() > index) {
            Log.i(TAG, "loadAgain: index" + index);
            if (TextUtils.isEmpty(list.get(index).getImagePath())) {
                index++;
                loadAgain(index);
            } else {
                checkFile(index);
            }

        } else {
            checkToSaveAccount();
        }
    }

    private void checkToSaveAccount() {
        for (Image f : list) {
            Log.i(TAG, "checkToSaveAccount: " + f.getImageId());
            if (!TextUtils.isEmpty(f.getImagePath())) {
                if (!f.getSent()) {
                    Toast.makeText(getActivity(), getString(R.string.file_error), Toast.LENGTH_SHORT).show();
                    progressbar.setVisibility(View.GONE);
                    return;
                }
            }
        }
        if (editMode == insert) {
            saveAccount();
//
        } else {
            editAccount();
        }

    }

//    private boolean isNetworkAvailable() {
//        ConnectivityManager connectivityManager
//                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
//        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
//    }

    private void saveAccount() {
//        Log.i(TAG, "saveMerchant: " + myApplication.getCurrentUser().getJwtToken());
//            setLoading(true);
        Log.i(TAG, "saveAccount string: " + merchant.toString());
//        Web.getInstance().saveMerchant(User.getInstance().getJwtToken(), merchant, list).
//                enqueue(new Callback<Result>() {
//                            @Override
//                            public void onResponse(Call<Result> call, Response<Result> response) {
//
//                                if (response.isSuccessful()) {
//                                    // TODO: 10/31/17 get id from server
//                                    Log.i(TAG, "onResponse: " + response.body().getId());
//                                    merchant.setId(response.body().getId());
//
//                                    merchant.setState(AppConstant.VERIFY);
//
//                                    //todo 1/11
//                                    //merchant.setState(AppConstant.TEMPACTIVE);
//                                    ////////todo database
//
//                                    saveMerchant(merchant);
//
//                                } else {
//                                    Toast.makeText(mContext, R.string.server_error + "" + R.string.not_save, Toast.LENGTH_SHORT).show();
//                                    progressbar.setVisibility(View.GONE);
//                                }
//
////                                    setLoading(false);
//                            }
//
//                            @Override
//                            public void onFailure(Call<Result> call, Throwable t) {
//                                Toast.makeText(mContext, R.string.network_error_offline, Toast.LENGTH_SHORT).show();
//                                merchant.setState(AppConstant.NOT_SENT);
//                                saveMerchant(merchant);
////                                    setLoading(false);
//                                progressbar.setVisibility(View.GONE);
//
//                            }
//                        }
//
//
//                );
    }

    //offline
    private void sendOfflineEdit() {
        merchant.setState(AppConstant.NOT_SENT);

//        if (!TextUtils.isEmpty(signPath)) {
//            Log.i(TAG, "checkData: 1");
//            File imgFile = new File(signPath);
//
//            if (imgFile.exists()) {
////                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
//                merchant.setSignature(signPath);
//            }
//        }
//        if (!TextUtils.isEmpty(natinalCardImage)) {
//            Log.i(TAG, "checkData: 1");
//            File imgFile = new File(natinalCardImage);
//
//            if (imgFile.exists()) {
////                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
//                merchant.setNationalCardImage(natinalCardImage);
//            }
//        }
//        if (!TextUtils.isEmpty(businessLicenceImage)) {
//            Log.i(TAG, "checkData: 2");
//            File imgFile = new File(businessLicenceImage);
//
//            if (imgFile.exists()) {
//
//                merchant.setBusinessLicenceImage(businessLicenceImage);
//            }
//        }
////////todo upload
        editMerchant(merchant);
    }

    private void sendOffline() {
        merchant.setState(AppConstant.NOT_SENT);
//        if (!TextUtils.isEmpty(signPath)) {
//            Log.i(TAG, "checkData: 1");
//            File imgFile = new File(signPath);
//
//            if (imgFile.exists()) {
////                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
//                merchant.setSignature(signPath);
//            }
//        }
//        if (!TextUtils.isEmpty(natinalCardImage)) {
//            Log.i(TAG, "checkData: 1");
//            File imgFile = new File(natinalCardImage);
//
//            if (imgFile.exists()) {
////                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
//                merchant.setNationalCardImage(natinalCardImage);
//            }
//        }
//        if (!TextUtils.isEmpty(businessLicenceImage)) {
//            Log.i(TAG, "checkData: 2");
//            File imgFile = new File(businessLicenceImage);
//
//            if (imgFile.exists()) {
//
//                merchant.setBusinessLicenceImage(businessLicenceImage);
//            }
//        }
////////todo upload

        saveMerchant(merchant);
    }

/////edit


    private void editAccount() {
//        Web.getInstance().editMerchant(User.getInstance().getJwtToken(), merchant.getId(), merchant, list).enqueue(new Callback<Result>() {
//            @Override
//            public void onResponse(Call<Result> call, Response<Result> response) {
//                if (response.isSuccessful()) {
//                    // TODO: 10/31/17 get id from server
////                    Log.i(TAG, "onResponse: " + response.body().getId());
////                    merchant.setId(response.body().getId());
//
//                    ///todo 1/11
////                    merchant.setState(AppConstant.TEMPACTIVE);
//
//
////                    merchant.setNationalCardImage(natinalCardImage);
////                    merchant.setBusinessLicenceImage(businessLicenceImage);
//                    ////////todo database
//
//                    editMerchant(merchant);
//
//                } else {
//                    Toast.makeText(mContext, R.string.server_error + "" + R.string.not_save, Toast.LENGTH_SHORT).show();
//
////                    progressbar.setVisibility(View.GONE);
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<Result> call, Throwable t) {
//
//            }
//        });
    }
//    private void editAccount() {
//        Web.getInstance().editMerchant(myApplication.getCurrentUser().getJwtToken(), merchant.getId(), merchant).enqueue(new Callback<Result>() {
//            @Override
//            public void onResponse(Call<Result> call, Response<Result> response) {
//                if (response.isSuccessful()) {
//                    // TODO: 10/31/17 get id from server
////                    Log.i(TAG, "onResponse: " + response.body().getId());
////                    merchant.setId(response.body().getId());
//                    merchant.setState(AppConstant.TEMPACTIVE);
////                    merchant.setNationalCardImage(natinalCardImage);
////                    merchant.setBusinessLicenceImage(businessLicenceImage);
//                    ////////todo database
//
//                    editMerchant(merchant);
//
//                } else {
//                    Toast.makeText(mContext, R.string.server_error + "" + R.string.not_save, Toast.LENGTH_SHORT).show();
//
////                    progressbar.setVisibility(View.GONE);
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<Result> call, Throwable t) {
//
//            }
//        });
//    }

    private void editMerchant(Merchant merchant) {
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date());
        merchant.setModifiedDate(timeStamp);
        MerchantTable.setEditMerchantsToTable(mContext, merchant, new ResultCountReceiver() {
            @Override
            public void onResult(int i) {
                progressbar.setVisibility(View.GONE);

                changeFragment();


            }
        });
    }


    //////validity

    private void changeFragment() {
//        progressbar.setVisibility(View.GONE);
        Toast.makeText(mContext, R.string.saved, Toast.LENGTH_SHORT).show();
        ChangeFragment.removeFragment(getActivity(), AddNewMerchantFragment.this, view);
        generateBarcode();

    }

    private boolean checkIsValid() {
        Log.i(TAG, "checkIsValid: " + merchant.toString());
        if (editTextFirstName.getText().toString().trim().length() == 0) {
            textFirstName.setTextColor(getResources().getColor(R.color.colorfireenginered));
            editTextFirstName.requestFocus();
            return false;
        }
        if (editTextLastName.getText().toString().trim().length() == 0) {
            textLastName.setTextColor(getResources().getColor(R.color.colorfireenginered));
            editTextLastName.requestFocus();
            return false;
        }
        if (editTextStoreName.getText().toString().trim().length() == 0) {
            textStoreName.setTextColor(getResources().getColor(R.color.colorfireenginered));
            editTextStoreName.requestFocus();
            return false;
        }
        if (editTextNationaCode.getText().toString().trim().length() == 0) {
            textNationalCode.setTextColor(getResources().getColor(R.color.colorfireenginered));
            editTextNationaCode.requestFocus();
            return false;
        }
//        if (editTextCellNo.getText().toString().trim().length() != 0)
        if (editTextCellNo.getText().toString().trim().length() != 11) {
//                textViewCell.setText(getString(R.string.login_text_cell_number_error));
            textViewCell.setTextColor(getResources().getColor(R.color.colorfireenginered));
            editTextCellNo.requestFocus();
            return false;
        }
        String barcode = editTextNationaCode.getText().toString().trim();
        if (barcode.length() != 10) {
            hideKeyboard();
            Toast.makeText(getActivity(), getString(R.string.national_code_len_error), Toast.LENGTH_SHORT).show();
            return false;
        } else if (!checkCodeMeli(barcode)) {
            Toast.makeText(getActivity(), getString(R.string.national_code_error), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (editTextEmail.getText().toString().trim().length() != 0)
            if (!isEmailValid(editTextEmail.getText().toString().trim())) {
                editTextEmail.setTextColor(getResources().getColor(R.color.colorfireenginered));
                return false;
            }


        return true;
    }

    private boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /////facility
    private void uploadFiles(String path, final UploadFileListener uploadFileListener) {


//        Web.getInstance().uploadFile(User.getInstance().getJwtToken(), compressFile(path), path).enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Call<String> call, Response<String> response) {
//                if (response.isSuccessful()) {
//                    uploadFileListener.onRsponseResult(response.body());
//                } else {
////                    progressbar.setVisibility(View.GONE);
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
////                progressbar.setVisibility(View.GONE);
//                Toast.makeText(mContext, R.string.network_error_file, Toast.LENGTH_SHORT).show();
//
//            }
//        });
    }

    private byte[] compressFile(String imageUrl) {
//        int compressionRatio = 2; //1 == originalImage, 2 = 50% compression, 4=25% compress
        ByteArrayOutputStream bos;
        File file = new File(imageUrl);
        try {
            Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
            bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 10, bos);
        } catch (Throwable t) {
            bos = null;
            Log.e("ERROR", "Error compressing file." + t.toString());
            t.printStackTrace();
        }

        return bos.toByteArray();
    }


    private void hideKeyboard() {
        if (getActivity().getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }
    }


    private void showKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    private void takePicture() {
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        pictureImagePath = getVideoFilePath();

        Log.i(TAG, "takePicture: " + pictureImagePath);
        takePicture.putExtra(MediaStore.EXTRA_OUTPUT, getOutputMediaFileUri(pictureImagePath));
        startActivityForResult(takePicture, PICTURE_REQUEST_EXTRA);
    }

    private Uri getOutputMediaFileUri(String path) {
        //return Uri.fromFile(new File(path));
        if (Build.VERSION.SDK_INT > 21) { //use this if Lollipop_Mr1 (API 22) or above
            return FileProvider.getUriForFile(getActivity().getApplicationContext(), getActivity().getPackageName() + ".provider", new File(path));
        } else {
            return Uri.fromFile(new File(path));
        }
    }


    private String getVideoFilePath() {
//        Log.i(TAG, "getVideoFilePath: " + type);
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MarketerApp");


        // Create the storage directory(MyCameraVideo) if it does not exist
        if (!mediaStorageDir.exists()) {

            if (!mediaStorageDir.mkdirs()) {

//                output.setText("Failed to create directory MyCameraVideo.");

                Toast.makeText(this.getActivity(), "failed_to_create_directory",
                        Toast.LENGTH_LONG).show();

                Log.d("MyCameraVideo", "Failed to create directory MyCameraVideo.");
                return null;
            }
        }


        Date date = new Date();
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(date.getTime());

        String path;

        path = mediaStorageDir.getPath() + File.separator +
                "IMG_E_" + timeStamp + ".jpg";


        return path;

    }


    private void openCameraWithPermission(int type) {
        if (ContextCompat.checkSelfPermission(this.getActivity(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "openCameraWithPermission: ");
            takePicture();
        } else if (!askedPermission) {
            Log.i(TAG, "askedPermission: ");
            requestPermissions(
                    new String[]{Manifest.permission.CAMERA},
                    cameraPermissionReqCode);
            askedPermission = true;

        } else {
            Log.i(TAG, "openCameraWithPermission: else");
            takePicture();

        }
    }


    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        File imgFile;
        Log.i(TAG, "onActivityResult: " + pictureImagePath);
//
        switch (requestCode) {
            case PICTURE_REQUEST_EXTRA:
                imgFile = new File(pictureImagePath);
                if (imgFile.exists()) {
                    image.setImageId(position);
                    image.setImagePath(pictureImagePath);
//                    image.setSent(false);
                    image.setImageTitle(String.valueOf(position));
                    imageAdapter.updateAdapter(position, image);
                }
                break;
        }
//
    }

    /////////
    private void setEditable(boolean flag) {
//        if (!isNew) {
//            editMode = flag;
//        }
        addButton.setVisibility(flag ? View.VISIBLE : View.GONE);

        editTextFirstName.setEnabled(flag);
        editTextLastName.setEnabled(flag);
        editTextNationaCode.setEnabled(flag);
        editTextSheba.setEnabled(flag);
        editTextAddress.setEnabled(flag);
        editTextCellNo.setEnabled(flag);
        editTextStoreName.setEnabled(flag);

        editTextBankName.setEnabled(flag);
        editTextBranchName.setEnabled(flag);
        editTextAccountType.setEnabled(flag);
        editTextAccountNo.setEnabled(flag);
        editTextWebsite.setEnabled(flag);
        editTextEmail.setEnabled(flag);
        editTextBarnchCode.setEnabled(flag);

        editTextSerialNo.setEnabled(flag);
        editTextPhoneNo.setEnabled(flag);
        editTextFatherName.setEnabled(flag);
        buttonDate.setEnabled(flag);
        buttonDate.setBackgroundResource(flag ? R.drawable.gradient_background_date : R.drawable.gradient_background_date_deactive);
        spinnerCity.setEnabled(flag);
        editTextField.setEnabled(flag);
        editTextBarcode.setEnabled(flag);

        addButton.setEnabled(flag);

//        buttonAddDoc.setVisibility(flag ? View.VISIBLE : View.GONE);

//        recyclerView.setVisibility(flag ? View.VISIBLE : View.GONE);
//        recyclerViewImage.setVisibility(flag ? View.GONE : View.VISIBLE);


        setImage();


//        signatureButton.setVisibility(flag ? View.VISIBLE : View.GONE);
//        signatureButton.setVisibility(flag ? View.VISIBLE : View.GONE);
//        imageViewNatinal.setVisibility(!flag ? View.VISIBLE : View.GONE);
//        imageViewBussiness.setVisibility(!flag ? View.VISIBLE : View.GONE);
//        imageViewsign.setVisibility(!flag ? View.VISIBLE : View.GONE);
//        Log.i(TAG, "setEditable: " + documentList.size());
//

    }

    private void setImage() {

        if (offline) {
            Log.i(TAG, "setEditable: ");
            recyclerView.setVisibility(View.GONE);
            recyclerViewImage.setVisibility(View.GONE);
            buttonAddDoc.setVisibility(View.GONE);
        } else {

            buttonAddDoc.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
            if (editMode == update) {
                recyclerViewImage.setVisibility(View.VISIBLE);
            } else if (editMode == insert) {
                recyclerViewImage.setVisibility(View.GONE);
            }

        }


    }


    private void checkImage(Merchant merchant) {
        //////online mode only
        if (offline) {
            //check if cache
            recyclerViewImage.setVisibility(View.GONE);
        } else {
            if (merchant.getDocumentList() != null) {
                recyclerViewImage.setVisibility(View.VISIBLE);
                documentList.addAll(merchant.getDocumentList());
                imageShowAdapter.notifyDataSetChanged();
                return;
            }

        }
    }


    private boolean checkCodeMeli(String code) {

        int L = code.length();

        if (L < 8 || !TextUtils.isDigitsOnly(code))
            return false;

        code = ("0000" + code).substring(L + 4 - 10);

        if (Integer.valueOf(code.substring(3, 9)) == 0)
            return false;

        int c = Integer.valueOf(code.substring(9, 10));
        int s = 0;
        for (int i = 0; i < 9; i++)
            s += Integer.valueOf(code.substring(i, i + 1)) * (10 - i);
        s = s % 11;

        return (s < 2 && c == s) || (s >= 2 && c == (11 - s));

    }


    @Override
    public void onAddClicked(int position, Image image) {
        this.image = image;
        this.position = position;
        bottomSheetDialog.show();
        bottomSheetDialog.setCancelable(true);
        bottomSheetDialog.setCanceledOnTouchOutside(true);


    }


    private void checkedVersion() {

        if (Build.VERSION.SDK_INT >= 23) {
            openCameraWithPermission(PICTURE_REQUEST_EXTRA);
        } else {
            takePicture();
//
        }
    }

    int state;


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("MyInt", 1);
    }

    //////////
    public class ImageShowAdapter extends RecyclerView.Adapter<ImageShowAdapter.MyViewHolder> {


        @Override
        public int getItemCount() {
            return list.size();
        }

        List<Document> list;
        FragmentActivity context;


        public ImageShowAdapter(FragmentActivity context, List<Document> list) {
            this.list = list;
            this.context = context;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_image_show, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {

            loadImage(holder.imageView, list.get(position));
//            Glide.with(mContext).load("https://api.raad.cloud/files/v3/docs/2017/12/04/0c3d5bd1433644409c565b202e1faa5f20171204.jpg").
//                                       https://api.raad.cloud/files/v3/docs/2018/01/03/3976c70991d148a3bcf6544d6a52c33b20180103.jpg
//                    apply(ImageUtil.DefaultOption()).into(holder.imageView);

        }

        private void loadImage(ImageView imageView, Document img) {

//            String path = WebService.IMAGE_URL.substring(0, WebService.IMAGE_URL.length()) + img.getDocURL();
//            Log.i(TAG, "loadImage: " + path);
////            path="https://api.raad.cloud/files/v3/docs/2017/12/04/0c3d5bd1433644409c565b202e1faa5f20171204.jpg";
////            path="https://api.raad.cloud/files/v3/docs/2018/01/03/71035d3458434fdd8aff02ed1efc114220180103.jpg";
////            Glide.with(mContext).load(path).into(imageView);
//            Glide.with(mContext).load(path).
//                    apply(ImageUtil.DefaultOption()).into(imageView);


        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            ImageView imageView;

            public MyViewHolder(View itemView) {

                super(itemView);
                imageView = (ImageView) itemView.findViewById(R.id.image_view);


            }
        }

    }

}
