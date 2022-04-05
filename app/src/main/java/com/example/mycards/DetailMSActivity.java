package com.example.mycards;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycards.controller.CONSTANT;
import com.example.mycards.controller.MembershipController;
import com.example.mycards.controller.adapters.DateStringViewRecyclerAdapter;
import com.example.mycards.model.Card;
import com.example.mycards.model.Coupon;
import com.example.mycards.model.MembershipBase;
import com.example.mycards.model.Pair;
import com.example.mycards.model.Subscription;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DetailMSActivity extends AppCompatActivity {
    ActivityResultLauncher<Intent> detailLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result ->{
        if (result.getResultCode() == RESULT_OK)
        {
            List<Integer> flags = (List<Integer>) result.getData().getExtras().get("flags");
            for (Integer i: flags)
                switch (i)
                {
                    case CONSTANT.RESULT_EDIT_CARD:
                    case CONSTANT.RESULT_EDIT_COUPON:
                    case CONSTANT.RESULT_EDIT_SUB:
                        this.mBase = (MembershipBase) result.getData().getExtras().get("data");
                        initAgain();
                        Toast.makeText(DetailMSActivity.this, "Sửa thành công", Toast.LENGTH_SHORT).show();
                        this.flags.add(i);
                        break;
                }
        }

    });
    Intent result = new Intent();
    List<Integer> flags = new ArrayList<>();
    MembershipBase mBase;
    int membershipType;
    boolean deleted = false;

    ImageView frontIV, backIV;
    TextView shortNameTV, fullNameTV, idTV, issuerTV;
    RecyclerView customStringRV, customDateRV;
    DateStringViewRecyclerAdapter customStringRA, customDateRA;
    List<Pair<String,String>> stringList;
    List<Pair<String, LocalDate>> dateList;
    LinearLayout qrButton, shareButton, editButton, deleteButton;
    TextView exclusiveDateLabel, exclusiveDateValue;
    SwitchCompat pinSwitch;

    OnBackPressedCallback backPressedCallback = new OnBackPressedCallback(true) {
        @Override
        public void handleOnBackPressed() {
            if (pinSwitch.isChecked() != mBase.isPinned())
            {
                flags.add(CONSTANT.RESULT_PINNED_CHANGE);
                flags.add(CONSTANT.RESULT_EDIT_CARD);
                mBase.setPinned(!mBase.isPinned());
                switch (membershipType)
                {
                    case CONSTANT.CARD:
                        MembershipController.updateCard(DetailMSActivity.this, mBase);
                        flags.add(CONSTANT.RESULT_EDIT_CARD);
                        break;
                    case CONSTANT.COUPON:
                        MembershipController.updateCoupon(DetailMSActivity.this, mBase);
                        flags.add(CONSTANT.RESULT_EDIT_COUPON);
                        break;
                    case CONSTANT.SUB:
                        MembershipController.updateSub(DetailMSActivity.this, mBase);
                        flags.add(CONSTANT.RESULT_EDIT_SUB);
                        break;
                }
                result.putExtra("data", mBase);
            }
            result.putExtra("flags", (Serializable) flags);
            setResult(RESULT_OK, result);
            finish();
        }
    };
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_msactivity);
        getOnBackPressedDispatcher().addCallback(this, backPressedCallback);

        //region Bindings
        frontIV = findViewById(R.id.frontIV);
        backIV = findViewById(R.id.backIV);
        shortNameTV = findViewById(R.id.shortNameTV);
        fullNameTV = findViewById(R.id.fullNameTV);
        idTV = findViewById(R.id.idTV);
        issuerTV = findViewById(R.id.issuerTV);
        customStringRV = findViewById(R.id.customStringRV);
        customDateRV = findViewById(R.id.customDateRV);
        qrButton = findViewById(R.id.qrButton);
        shareButton = findViewById(R.id.shareButton);
        editButton = findViewById(R.id.editButton);
        deleteButton = findViewById(R.id.deleteButton);
        exclusiveDateLabel = findViewById(R.id.exclusiveDateLabel);
        exclusiveDateValue = findViewById(R.id.exclusiveDateValue);
        pinSwitch = findViewById(R.id.pinnedSwitch);
        //endregion

        //region Init
        mBase = (MembershipBase) getIntent().getExtras().get("data");
        mBase.setActiveDate(LocalDateTime.now());
        if (mBase.getClass().equals(Card.class))
            membershipType = CONSTANT.CARD;
        else if (mBase.getClass().equals(Coupon.class))
            membershipType = CONSTANT.COUPON;
        else if (mBase.getClass().equals(Subscription.class))
            membershipType = CONSTANT.SUB;
        if (mBase != null) {
            if (mBase.getFrontImgDir() != null)
                frontIV.setImageBitmap(BitmapFactory.decodeFile(mBase.getFrontImgDir()));
            else
                frontIV.setVisibility(View.GONE);
            if (mBase.getBackImgDir() != null)
                backIV.setImageBitmap(BitmapFactory.decodeFile(mBase.getBackImgDir()));
            else
                backIV.setVisibility(View.GONE);
            shortNameTV.setText(mBase.getShortName());
            fullNameTV.setText(mBase.getFullName());
            idTV.setText(mBase.getMembershipID());
            issuerTV.setText(mBase.getIssuer());
            stringList = mBase.getTextProperties();
            dateList = mBase.getDateProperties();
            pinSwitch.setChecked(mBase.isPinned());
            if (membershipType == CONSTANT.CARD)
                findViewById(R.id.exclusiveDate).setVisibility(View.GONE);
            else if (membershipType == CONSTANT.COUPON)
            {
                exclusiveDateLabel.setText(R.string.expDate);
                LocalDate date = ((Coupon)mBase).getExpDate();
                exclusiveDateValue.setText(date.getDayOfMonth() + "/" + date.getMonthValue() + "/" + date.getYear());
            }
            else if (membershipType == CONSTANT.SUB)
            {
                exclusiveDateLabel.setText(R.string.renewDate);
                LocalDate date = ((Subscription)mBase).getRenewDate();
                exclusiveDateValue.setText(date.getDayOfMonth() + "/" + date.getMonthValue() + "/" + date.getYear());
            }
        }
        //endregion

        //region Adapters
        customStringRA = new DateStringViewRecyclerAdapter(stringList);
        customStringRV.setLayoutManager(new LinearLayoutManager(this));
        customStringRV.setAdapter(customStringRA);

        customDateRA = new DateStringViewRecyclerAdapter(dateList, true);
        customDateRV.setLayoutManager(new LinearLayoutManager(this));
        customDateRV.setAdapter(customDateRA);
        //endregion

        //region Listeners
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(DetailMSActivity.this)
                        .setIcon(R.drawable.ic_baseline_restore_from_trash_24)
                        .setTitle("Xoá thẻ này")
                        .setMessage("Bạn có chắc chắn muốn xoá?")
                        .setPositiveButton("Có", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteMembership();
                            }

                        })
                        .setNegativeButton("Không", null)
                        .show();
            }
        });
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToEditActivity();
            }
        });
        qrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToQRActivity();
            }
        });
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DetailMSActivity.this, getString(R.string.underConstruction), Toast.LENGTH_LONG).show();
            }
        });
        //endregion
    }
    void goToEditActivity() {
        Intent intent = new Intent(DetailMSActivity.this, EditMSActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("mem", mBase);
        intent.putExtras(bundle);
        detailLauncher.launch(intent);
    }
    void goToQRActivity() {
        Intent intent = new Intent(DetailMSActivity.this, ShowQRActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("id", mBase.getMembershipID());
        intent.putExtras(bundle);
        detailLauncher.launch(intent);
    }
    void deleteMembership() {
        switch (membershipType)
        {
            case CONSTANT.CARD:
                flags.clear();
                flags.add(CONSTANT.RESULT_DEL_CARD);
                break;
            case CONSTANT.COUPON:
                flags.clear();
                flags.add(CONSTANT.RESULT_DEL_COUPON);
                break;
            case CONSTANT.SUB:
                flags.clear();
                flags.add(CONSTANT.RESULT_DEL_SUB);
                break;
        }
        deleted = true;
        MembershipController.remove(DetailMSActivity.this, mBase.systemID, membershipType);
        result.putExtra("flags", (Serializable) flags);
        setResult(RESULT_OK, result);
        finish();
    }
    @SuppressLint("SetTextI18n")
    void initAgain() {
        if (mBase != null)
        {
            if (mBase.getFrontImgDir() != null) {
                frontIV.setImageBitmap(BitmapFactory.decodeFile(mBase.getFrontImgDir()));
                frontIV.setVisibility(View.VISIBLE);
            }
            else
                frontIV.setVisibility(View.GONE);
            if (mBase.getBackImgDir() != null) {
                backIV.setImageBitmap(BitmapFactory.decodeFile(mBase.getBackImgDir()));
                backIV.setVisibility(View.VISIBLE);
            }
            else
                backIV.setVisibility(View.GONE);
            shortNameTV.setText(mBase.getShortName());
            fullNameTV.setText(mBase.getFullName());
            idTV.setText(mBase.getMembershipID());
            issuerTV.setText(mBase.getIssuer());
            stringList = mBase.getTextProperties();
            dateList = mBase.getDateProperties();
            customStringRA.refreshList(stringList);
            customDateRA.refreshList(dateList, true);
        }
        if (membershipType == CONSTANT.CARD)
            findViewById(R.id.exclusiveDate).setVisibility(View.GONE);
        else if (membershipType == CONSTANT.COUPON)
        {
            exclusiveDateLabel.setText(R.string.expDate);
            LocalDate date = ((Coupon)mBase).getExpDate();
            exclusiveDateValue.setText(date.getDayOfMonth() + "/" + date.getMonthValue() + "/" + date.getYear());
        }
        else if (membershipType == CONSTANT.SUB)
        {
            exclusiveDateLabel.setText(R.string.renewDate);
            LocalDate date = ((Subscription)mBase).getRenewDate();
            exclusiveDateValue.setText(date.getDayOfMonth() + "/" + date.getMonthValue() + "/" + date.getYear());
        }
    }
}