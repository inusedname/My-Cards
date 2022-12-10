package com.example.mycards;

import android.content.Intent;
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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycards.controller.CONSTANT;
import com.example.mycards.controller.MembershipController;
import com.example.mycards.controller.adapters.CustomDateRecyclerAdapter;
import com.example.mycards.controller.adapters.CustomStringRecyclerAdapter;
import com.example.mycards.controller.adapters.SpinnerArrayAdapter;
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

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"FieldCanBeLocal"})
public class AddMSActivity extends AppCompatActivity{

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null || data.getData() == null)
            return;
        if (requestCode == CONSTANT.RESULT_FRONT_PICTURE_CAPTURED)
        {
            ((ImageView)frontImgCV.findViewById(R.id.takePictureIV)).setImageURI(data.getData());
            frontImgCV.findViewById(R.id.addImageInstruction).setVisibility(View.GONE);
            frontImgPath = data.getData().getPath();
        }
        else if (requestCode == CONSTANT.RESULT_BACK_PICTURE_CAPTURED) {
            ((ImageView)backImgCV.findViewById(R.id.takePictureIV)).setImageURI(data.getData());
            backImgCV.findViewById(R.id.addImageInstruction).setVisibility(View.GONE);
            backImgPath = data.getData().getPath();
        }
    }

    private int membershipType;

    private RecyclerView customStringList;
    private RecyclerView customDateList;
    private Spinner memTypesSpinner;
    private CustomStringRecyclerAdapter stringAdapter;
    private CustomDateRecyclerAdapter dateAdapter;
    private SpinnerArrayAdapter memTypesAdapter;

    boolean isOK1, isOK2,isOK3, isOK4;
    private List<Pair<String,String>> stringList;
    private List<Pair<String, LocalDate>> dateList;
    private LocalDate exclusiveDate;
    private String frontImgPath, backImgPath;

    private EditText shortName, fullName, id, issuer;
    private LinearLayout exclusiveDateCL;
    private Button chooseDateBt;
    private EditText chooseDateEt;
    private CardView frontImgCV, backImgCV;
    private TextView error_sn, error_fn, error_id, error_is;
    private final MyQRReader qrReader = new MyQRReader();
    private ImageButton qrScannerBt;
    private FloatingActionButton addFab;
    private Button addCustomStringBt, addCustomDateBt;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_membership);

        //region Bindings
        frontImgCV = findViewById(R.id.front_image);
        backImgCV = findViewById(R.id.back_image);
        error_sn = findViewById(R.id.shortNameErrorTV);
        error_fn = findViewById(R.id.fullNameErrorTV);
        error_id = findViewById(R.id.idErrorTV);
        error_is = findViewById(R.id.issuerErrorTV);
        shortName = findViewById(R.id.shortNameET);
        fullName = findViewById(R.id.fullNameET);
        id = findViewById(R.id.idET);
        issuer = findViewById(R.id.issuerET);
        addCustomStringBt = findViewById(R.id.addCustomStringBt);
        addCustomDateBt = findViewById(R.id.addCustomDateBt);
        qrScannerBt = findViewById(R.id.qrScannerBt);
        addFab = findViewById(R.id.addFab);
        exclusiveDateCL = findViewById(R.id.exclusiveDate);
        chooseDateBt = exclusiveDateCL.findViewById(R.id.chooseDateBT);
        chooseDateEt = exclusiveDateCL.findViewById(R.id.labelET);
        memTypesSpinner = findViewById(R.id.memTypesSpinner);
        customStringList = findViewById(R.id.customStringRV);
        customDateList = findViewById(R.id.customDateRV);
        //endregion

        //region Init Adapters
        memTypesAdapter = new SpinnerArrayAdapter(this, R.layout.item_spinner, getResources().getStringArray(R.array.membershipTypes));
        memTypesSpinner.setAdapter(memTypesAdapter);

        stringAdapter = new CustomStringRecyclerAdapter();
        customStringList.setLayoutManager(new LinearLayoutManager(this));
        customStringList.setNestedScrollingEnabled(false);
        customStringList.setFocusable(false);
        customStringList.setHasFixedSize(true);
        customStringList.setAdapter(stringAdapter);

        dateAdapter = new CustomDateRecyclerAdapter();
        customDateList.setLayoutManager(new LinearLayoutManager(this));
        customDateList.setNestedScrollingEnabled(false);
        customDateList.setFocusable(false);
        customDateList.setHasFixedSize(true);
        customDateList.setAdapter(dateAdapter);
        //endregion

        //region Initials
        error_sn.setText(R.string.required);
        error_fn.setText(R.string.required);
        error_id.setText(R.string.required);
        error_is.setText(R.string.required);
        stringAdapter.updateList(new Pair<>("",""));
        dateAdapter.updateList(new Pair<>("",null));
        chooseDateEt.setInputType(InputType.TYPE_NULL);
        isOK1 = isOK2 = isOK3 = isOK4 = false;
        membershipType = 0;
        ((TextView) backImgCV.findViewById(R.id.takePictureTV)).setText(R.string.addActivity_backImg);
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
            MyDatePicker myDatePicker = new MyDatePicker(view, chooseDateBt.getId(), exclusiveDate) {
                @Override
                public void whatDoYouWantToDoAfterDateSet() {
                    exclusiveDate = getLocalDate();
                }
            };
            myDatePicker.showTheDialog(view);
        });
        qrScannerBt.setOnClickListener(view -> qrReader.run());
        addFab.setOnClickListener(view -> {
            addFab.requestFocus();
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
        stringList = stringAdapter.getList();
        dateList = dateAdapter.getList();
        for (Pair<String, String> pair: stringList)
        {
            if (pair.getKey().isEmpty() || pair.getValue().isEmpty())
                stringList.remove(pair);
        }
        for (Pair<String, LocalDate> pair: dateList)
        {
            if (pair.getKey().isEmpty() || pair.getValue() == null)
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
                .crop(14f, 9f)
                .compress(1024)
                .maxResultSize(1400, 900)
                .start(CONSTANT.RESULT_FRONT_PICTURE_CAPTURED);
    }
    public void takePictureBack() {
        ImagePicker.with(AddMSActivity.this)
                .crop(14f, 9f)
                .compress(1024)
                .maxResultSize(1400, 900)
                .start(CONSTANT.RESULT_BACK_PICTURE_CAPTURED);
    }
    public void addMembership() {
        cleanEmptyList();
        MembershipBase membershipData;
        Intent result = new Intent();
        List<Integer> flags = new ArrayList<>();
        switch (membershipType) {
            case 1:
                membershipData = new Coupon(frontImgPath, backImgPath, shortName.getText().toString(),fullName.getText().toString(), id.getText().toString(), issuer.getText().toString(), stringList, dateList, exclusiveDate);
                flags.add(CONSTANT.RESULT_ADD_COUPON);
                break;
            case 2:
                membershipData = new Subscription(frontImgPath, backImgPath, shortName.getText().toString(),fullName.getText().toString(), id.getText().toString(), issuer.getText().toString(), stringList, dateList, exclusiveDate);
                flags.add(CONSTANT.RESULT_ADD_SUB);
                break;
            default:
                membershipData = new Card(frontImgPath, backImgPath, shortName.getText().toString(),fullName.getText().toString(), id.getText().toString(), issuer.getText().toString(), stringList, dateList);
                flags.add(CONSTANT.RESULT_ADD_CARD);
                break;
        }
        result.putExtra("flags", (Serializable) flags);
        setResult(RESULT_OK, result);
        membershipData.setActiveDate(LocalDateTime.now());
        MembershipController.addMembership(AddMSActivity.this, membershipData);
    }
}