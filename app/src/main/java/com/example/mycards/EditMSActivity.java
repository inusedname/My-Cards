package com.example.mycards;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycards.controller.CONSTANT;
import com.example.mycards.controller.MembershipController;
import com.example.mycards.controller.adapters.CustomDateRecyclerAdapter;
import com.example.mycards.controller.adapters.CustomStringRecyclerAdapter;
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
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"FieldCanBeLocal"})
public class EditMSActivity extends AppCompatActivity {

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

    private MembershipBase mBase;
    private int membershipType;

    private RecyclerView customStringList;
    private RecyclerView customDateList;
    private CustomStringRecyclerAdapter stringAdapter;
    private CustomDateRecyclerAdapter dateAdapter;

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
    private FloatingActionButton editFab;
    private Button addCustomStringBt, addCustomDateBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_msactivity);

        mBase = (MembershipBase) getIntent().getExtras().get("mem");
        if (mBase.getClass().equals(Card.class))
            membershipType = CONSTANT.CARD;
        else if (mBase.getClass().equals(Coupon.class))
            membershipType = CONSTANT.COUPON;
        else if (mBase.getClass().equals(Subscription.class))
            membershipType = CONSTANT.SUB;

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
        editFab = findViewById(R.id.editFab);
        exclusiveDateCL = findViewById(R.id.exclusiveDate);
        chooseDateBt = exclusiveDateCL.findViewById(R.id.chooseDateBT);
        chooseDateEt = exclusiveDateCL.findViewById(R.id.labelET);
        customStringList = findViewById(R.id.customStringRV);
        customDateList = findViewById(R.id.customDateRV);
        //endregion

        //region Initials
        error_sn.setText("");
        error_fn.setText("");
        error_id.setText("");
        error_is.setText("");
        isOK1 = isOK2 = isOK3 = isOK4 = true;

        shortName.setText(mBase.getShortName());
        fullName.setText(mBase.getFullName());
        id.setText(mBase.getMembershipID());
        issuer.setText(mBase.getIssuer());
        stringList = mBase.getTextProperties();
        dateList = mBase.getDateProperties();
        frontImgPath = mBase.getFrontImgDir();
        backImgPath = mBase.getBackImgDir();
        ((TextView) backImgCV.findViewById(R.id.takePictureTV)).setText(R.string.addActivity_backImg);

        if (frontImgPath != null && frontImgPath.length() > 0)
        {
            ((ImageView)frontImgCV.findViewById(R.id.takePictureIV)).setImageBitmap(BitmapFactory.decodeFile(frontImgPath));
            frontImgCV.findViewById(R.id.addImageInstruction).setVisibility(View.GONE);
        }
        if (backImgPath != null && backImgPath.length() > 0)
        {
            ((ImageView)backImgCV.findViewById(R.id.takePictureIV)).setImageBitmap(BitmapFactory.decodeFile(backImgPath));
            backImgCV.findViewById(R.id.addImageInstruction).setVisibility(View.GONE);
        }
        String dateToString;
        switch (membershipType)
        {
            case CONSTANT.COUPON:
                exclusiveDateCL.setVisibility(View.VISIBLE);
                exclusiveDate = ((Coupon)mBase).getExpDate();
                dateToString = exclusiveDate.getDayOfMonth() + "/" + exclusiveDate.getMonthValue() + "/" + exclusiveDate.getYear();
                chooseDateEt.setText(R.string.expDate);
                ((Button)exclusiveDateCL.findViewById(R.id.chooseDateBT)).setText(dateToString);
                setExclusiveDateButtonListener();
                break;
            case CONSTANT.SUB:
                exclusiveDateCL.setVisibility(View.VISIBLE);
                exclusiveDate = ((Subscription)mBase).getRenewDate();
                dateToString = exclusiveDate.getDayOfMonth() + "/" + exclusiveDate.getMonthValue() + "/" + exclusiveDate.getYear();
                chooseDateEt.setText(R.string.renewDate);
                ((Button)exclusiveDateCL.findViewById(R.id.chooseDateBT)).setText(dateToString);
                setExclusiveDateButtonListener();
                break;
            default:
                exclusiveDateCL.setVisibility(View.GONE);
                break;
        }
        //endregion

        //region Init Adapters
        stringAdapter = new CustomStringRecyclerAdapter();
        stringAdapter.updateList(stringList);
        customStringList.setLayoutManager(new LinearLayoutManager(this));
        customStringList.setNestedScrollingEnabled(false);
        customStringList.setFocusable(false);
        customStringList.setHasFixedSize(true);
        customStringList.setAdapter(stringAdapter);

        dateAdapter = new CustomDateRecyclerAdapter();
        dateAdapter.updateList(dateList);
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
        qrScannerBt.setOnClickListener(view -> qrReader.run());
        editFab.setOnClickListener(view -> {
            editFab.requestFocus();
            if (!isOK1 || !isOK2 || !isOK3 || !isOK4)
                Toast.makeText(EditMSActivity.this, R.string.unableToEditCard, Toast.LENGTH_SHORT).show();
            else {
                editMembership();
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
                    NameValidator.checkShortName(charSequence.toString(), EditMSActivity.this );
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
                    NameValidator.checkFullName(charSequence.toString(),EditMSActivity.this);
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
                    NameValidator.checkIssuer(charSequence.toString(), EditMSActivity.this);
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
                    NameValidator.checkStringID(charSequence.toString(), EditMSActivity.this);
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
    private void cleanEmptyList() {
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
    private void takePictureFront() {
        ImagePicker.with(EditMSActivity.this)
                .crop(14f, 9f)
                .compress(1024)
                .maxResultSize(1400, 900)
                .start(CONSTANT.RESULT_FRONT_PICTURE_CAPTURED);
    }
    private void takePictureBack() {
        ImagePicker.with(EditMSActivity.this)
                .crop(14f, 9f)
                .compress(1024)
                .maxResultSize(1400, 900)
                .start(CONSTANT.RESULT_BACK_PICTURE_CAPTURED);
    }
    private void editMembership() {
        cleanEmptyList();
        Intent result = new Intent();
        List<Integer> flags = new ArrayList<>();
        mBase.setFrontImgDir(frontImgPath);
        mBase.setBackImgDir(backImgPath);
        mBase.setMembershipID(id.getText().toString());
        mBase.setShortName(shortName.getText().toString());
        mBase.setFullName(fullName.getText().toString());
        mBase.setIssuer(issuer.getText().toString());
        mBase.setTextProperties(stringAdapter.getList());
        mBase.setDateProperties(dateAdapter.getList());
        switch (membershipType)
        {
            case CONSTANT.CARD:
                MembershipController.updateCard(EditMSActivity.this, mBase);
                flags.add(CONSTANT.RESULT_EDIT_CARD);
                break;
            case CONSTANT.COUPON:
                ((Coupon)mBase).setExpDate(exclusiveDate);
                MembershipController.updateCoupon(EditMSActivity.this, mBase);
                flags.add(CONSTANT.RESULT_EDIT_COUPON);
                break;
            case CONSTANT.SUB:
                ((Subscription)mBase).setRenewDate(exclusiveDate);
                MembershipController.updateSub(EditMSActivity.this, mBase);
                flags.add(CONSTANT.RESULT_EDIT_SUB);
                break;
        }
        result.putExtra("flags", (Serializable) flags);
        result.putExtra("data", mBase);
        setResult(RESULT_OK, result);
        finish();
    }
    private void setExclusiveDateButtonListener() {
        chooseDateBt.setOnClickListener(view -> {
            MyDatePicker myDatePicker = new MyDatePicker(view, chooseDateBt.getId(), exclusiveDate) {
                @Override
                public void whatDoYouWantToDoAfterDateSet() {
                    exclusiveDate = getLocalDate();
                }
            };
            myDatePicker.showTheDialog(view);
        });
    }

}