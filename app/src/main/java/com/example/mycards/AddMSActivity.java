package com.example.mycards;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycards.controller.CONSTANT;
import com.example.mycards.controller.MembershipController;
import com.example.mycards.controller.adapters.CustomDateRecyclerAdapter;
import com.example.mycards.controller.adapters.CustomStringRecyclerAdapter;
import com.example.mycards.controller.adapters.MyListAdapter;
import com.example.mycards.controller.exceptions.StringInputFail;
import com.example.mycards.controller.util.MyDatePicker;
import com.example.mycards.controller.util.MyQRReader;
import com.example.mycards.controller.util.NameValidator;
import com.example.mycards.model.Card;
import com.example.mycards.model.Coupon;
import com.example.mycards.model.MembershipBase;
import com.example.mycards.model.Pair;
import com.example.mycards.model.Subscription;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.journeyapps.barcodescanner.ScanContract;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"FieldCanBeLocal"})
public class AddMSActivity extends AppCompatActivity{

    private RecyclerView customStringList;
    private RecyclerView customDateList;
    private Spinner memTypesSpinner;
    private CustomStringRecyclerAdapter stringAdapter;
    private CustomDateRecyclerAdapter dateAdapter;
    private MyListAdapter memTypesAdapter;

    boolean isOK1, isOK2,isOK3, isOK4;
    private EditText shortName, fullName, id, issuer;
    private final List<Pair<String,String>> stringList = new ArrayList<>();
    private final List<Pair<String, LocalDate>> dateList = new ArrayList<>();
    private ConstraintLayout exclusiveDateCL;
    private LocalDate exclusiveDate = null;
    private Button chooseDateBt;
    private EditText chooseDateEt;
    private CardView frontImgCV, backImgCV;
    private String frontImgPath, backImgPath;

    private final MyQRReader qrReader = new MyQRReader();
    private int membershipType;

    FloatingActionButton addFab;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        assert data != null;
        if (data.getData() == null)
            return;
        if (requestCode == CONSTANT.RESULT_FRONT_PICTURE_CAPTURED)
        {
            frontImgPath = data.getData().getPath();
            ImageView iv = frontImgCV.findViewById(R.id.takePictureIV);
            iv.setImageBitmap(BitmapFactory.decodeFile(frontImgPath));
        }
        else if (requestCode == CONSTANT.RESULT_BACK_PICTURE_CAPTURED) {
            backImgPath = data.getData().getPath();
            ImageView iv = backImgCV.findViewById(R.id.takePictureIV);
            iv.setImageBitmap(BitmapFactory.decodeFile(backImgPath));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_membership);

        //region Bindings
        frontImgCV = findViewById(R.id.front_image);
        backImgCV = findViewById(R.id.back_image);
        TextView error_sn = findViewById(R.id.errorTextView_shortName);
        TextView error_fn = findViewById(R.id.errorTextView_fullName);
        TextView error_id = findViewById(R.id.errorTextView_id);
        TextView error_is = findViewById(R.id.errorTextView_issuer);
        shortName = findViewById(R.id.shortNameEditText_card);
        fullName = findViewById(R.id.fullNameEditText_card);
        id = findViewById(R.id.idEditText_card);
        issuer = findViewById(R.id.issuerEditText_card);
        Button addCustomStringBt = findViewById(R.id.addCustomStringBt);
        Button addCustomDateBt = findViewById(R.id.addCustomDateBt);
        ImageButton qrScannerBt = findViewById(R.id.qrScannerBt);
        addFab = findViewById(R.id.addFab);
        exclusiveDateCL = findViewById(R.id.exclusiveDate);
        chooseDateBt = exclusiveDateCL.findViewById(R.id.chooseDateBT);
        chooseDateEt = exclusiveDateCL.findViewById(R.id.chooseDateET);
        memTypesSpinner = findViewById(R.id.memTypesSpinner);
        customStringList = findViewById(R.id.customStringRecyclerView);
        customDateList = findViewById(R.id.customDateRecyclerView);
        //endregion

        //region Initials
        error_sn.setText("");
        error_fn.setText("");
        error_id.setText("");
        error_is.setText("");
        stringList.add(new Pair<>("", ""));
        dateList.add(new Pair<>("", null));
        chooseDateEt.setInputType(InputType.TYPE_NULL);
        isOK1 = isOK2 = isOK3 = isOK4 = false;
        membershipType = 0;
        ((TextView) backImgCV.findViewById(R.id.takePictureTV)).setText(R.string.addActivity_backImg);
        //endregion

        //region Init Adapters
        memTypesAdapter = new MyListAdapter(this, R.layout.item_spinner, getResources().getStringArray(R.array.membershipTypes));
        memTypesSpinner.setAdapter(memTypesAdapter);

        stringAdapter = new CustomStringRecyclerAdapter(this, stringList);
        customStringList.setLayoutManager(new LinearLayoutManager(this));
        customStringList.setNestedScrollingEnabled(false);
        customStringList.setFocusable(false);
        customStringList.setHasFixedSize(true);
        customStringList.setAdapter(stringAdapter);

        dateAdapter = new CustomDateRecyclerAdapter(this, dateList);
        customDateList.setLayoutManager(new LinearLayoutManager(this));
        customDateList.setNestedScrollingEnabled(false);
        customDateList.setFocusable(false);
        customDateList.setHasFixedSize(true);
        customDateList.setAdapter(dateAdapter);
        //endregion

        //region Listeners
        qrReader.setBarcodeLauncher(registerForActivityResult(new ScanContract(),
                result -> {
                    if(result.getContents() != null)
                        id.setText(result.getContents());
                }));

        frontImgCV.setOnClickListener(view -> takePictureFront());
        backImgCV.setOnClickListener(view -> takePictureBack());
        addCustomStringBt.setOnClickListener(view -> stringAdapter.updateList(new Pair<>("","")));
        addCustomDateBt.setOnClickListener(view -> dateAdapter.updateList(new Pair<>("", null)));
        memTypesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                membershipType = i;
                changeMemType();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        chooseDateBt.setOnClickListener(view -> {
            MyDatePicker myDatePicker = new MyDatePicker(view, chooseDateBt.getId()) {
                @Override
                public void whatDoYouWantToDoAfterDateSet() {
                    exclusiveDate = getLocalDate();
                }
            };
            myDatePicker.showTheDialog(view);
        });
        qrScannerBt.setOnClickListener(view -> qrReader.run());
        addFab.setOnClickListener(view -> {
            if (!isOK1 || !isOK2 || !isOK3 || !isOK4 || (membershipType > 0 && exclusiveDate == null))
                Toast.makeText(AddMSActivity.this, R.string.unableToCreateCard, Toast.LENGTH_SHORT).show();
            else {
                addMembership();
                finish();
            }
        });

        shortName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                isOK1 = false;
                try {
                    NameValidator.checkShortName(charSequence.toString(), AddMSActivity.this );
                    error_sn.setText("");
                    isOK1 = true;
                } catch (StringInputFail stringInputFail) {
                    error_sn.setText(stringInputFail.getMsg());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        fullName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                isOK2 = false;
                try {
                    NameValidator.checkFullName(charSequence.toString(),AddMSActivity.this);
                    error_fn.setText("");
                    isOK2 = true;
                }
                catch (StringInputFail stringInputFail) {
                    error_fn.setText(stringInputFail.getMsg());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        issuer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                isOK3 = false;
                try {
                    NameValidator.checkIssuer(charSequence.toString(), AddMSActivity.this);
                    error_is.setText("");
                    isOK3 = true;
                }
                catch (StringInputFail stringInputFail) {
                    error_is.setText(stringInputFail.getMsg());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2){}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                isOK4 = false;
                try {
                    NameValidator.checkStringID(charSequence.toString(), AddMSActivity.this);
                    error_id.setText("");
                    isOK4 = true;
                }
                catch (StringInputFail stringInputFail) {
                    error_id.setText(stringInputFail.getMsg());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
        //endregion
    }
    public void cleanEmptyList() {
        for (Pair<String, String> pair: stringList)
        {
            if (pair.getKey().isEmpty())
                stringList.remove(pair);
        }
        for (Pair<String, LocalDate> pair: dateList)
        {
            if (pair.getKey().isEmpty())
                dateList.remove(pair);
        }
    }
    public void changeMemType() {
        switch (membershipType)
        {
            case 1:
                exclusiveDateCL.setVisibility(View.VISIBLE);
                chooseDateEt.setText(R.string.expDate);
                break;
            case 2:
                exclusiveDateCL.setVisibility(View.VISIBLE);
                chooseDateEt.setText(R.string.renewDate);
                break;
            default:
                exclusiveDateCL.setVisibility(View.GONE);
                break;
        }
    }
    public void takePictureFront() {
        ImagePicker.with(AddMSActivity.this)
                .compress(512)
                .maxResultSize(1920, 1080)
                .crop(14f, 9f)
                .start(CONSTANT.RESULT_FRONT_PICTURE_CAPTURED);
    }
    public void takePictureBack() {
        ImagePicker.with(AddMSActivity.this)
                .compress(512)
                .maxResultSize(1920, 1080)
                .crop(14f, 9f)
                .start(CONSTANT.RESULT_BACK_PICTURE_CAPTURED);
    }
    public void addMembership() {
        cleanEmptyList();
        MembershipBase membershipData;
        Intent result = new Intent();
        switch (membershipType) {
            case 1:
                membershipData = new Coupon(frontImgPath, backImgPath, shortName.getText().toString(),fullName.getText().toString(), id.getText().toString(), issuer.getText().toString(), stringAdapter.getList(), dateAdapter.getList(), exclusiveDate);
                setResult(CONSTANT.RESULT_ADD_COUPON, result);
                break;
            case 2:
                membershipData = new Subscription(frontImgPath, backImgPath, shortName.getText().toString(),fullName.getText().toString(), id.getText().toString(), issuer.getText().toString(), stringAdapter.getList(), dateAdapter.getList(), exclusiveDate);
                setResult(CONSTANT.RESULT_ADD_SUB, result);
                break;
            default:
                membershipData = new Card(frontImgPath, backImgPath, shortName.getText().toString(),fullName.getText().toString(), id.getText().toString(), issuer.getText().toString(), stringAdapter.getList(), dateAdapter.getList());
                setResult(CONSTANT.RESULT_ADD_CARD, result);
                break;
        }
        MembershipController.addMembership(AddMSActivity.this, membershipData);
    }
}