package com.example.mycards;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mycards.controller.adapters.CustomDateRecyclerAdapter;
import com.example.mycards.controller.adapters.CustomStringRecyclerAdapter;
import com.example.mycards.controller.adapters.MyListAdapter;
import com.example.mycards.controller.exceptions.StringInputFail;
import com.example.mycards.controller.util.MyDatePicker;
import com.example.mycards.controller.util.NameValidator;
import com.example.mycards.model.Card;
import com.example.mycards.model.Coupon;
import com.example.mycards.model.MembershipBase;
import com.example.mycards.model.Pair;
import com.example.mycards.model.Subscription;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    private LocalDate couponExpirationDate;
    private LocalDate subRenewDate;
    private ConstraintLayout exclusiveDate;
    private Button chooseDateBt;
    private EditText chooseDateEt;

    private int membershipType;


    FloatingActionButton addFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_membership);

        stringList.add(new Pair<>("", ""));
        dateList.add(new Pair<>("", null));

        TextView error_sn = findViewById(R.id.errorTextView_shortName);
        TextView error_fn = findViewById(R.id.errorTextView_fullName);
        TextView error_id = findViewById(R.id.errorTextView_id);
        TextView error_is = findViewById(R.id.errorTextView_issuer);
        error_sn.setText("");
        error_fn.setText("");
        error_id.setText("");
        error_is.setText("");
        isOK1 = isOK2 = isOK3 = isOK4 = false;

        shortName = findViewById(R.id.shortNameEditText_card);
        fullName = findViewById(R.id.fullNameEditText_card);
        id = findViewById(R.id.idEditText_card);
        issuer = findViewById(R.id.issuerEditText_card);
        Button addCustomStringBt = findViewById(R.id.addCustomStringBt);
        Button addCustomDateBt = findViewById(R.id.addCustomDateBt);
        addFab = findViewById(R.id.addFab);
        exclusiveDate = findViewById(R.id.exclusiveDate);
        chooseDateBt = findViewById(R.id.selectDateButton);
        chooseDateEt = findViewById(R.id.labelTextField);
        chooseDateEt.setInputType(InputType.TYPE_NULL);

        membershipType = 0;

        memTypesSpinner = findViewById(R.id.memTypesSpinner);
        memTypesAdapter = new MyListAdapter(this, R.layout.item_spinner, getResources().getStringArray(R.array.membershipTypes));
        memTypesSpinner.setAdapter(memTypesAdapter);

        customStringList = findViewById(R.id.customStringRecyclerView);
        customStringList.setLayoutManager(new LinearLayoutManager(this));
        customStringList.setNestedScrollingEnabled(false);
        customStringList.setFocusable(false);
        customStringList.setHasFixedSize(true);
        stringAdapter = new CustomStringRecyclerAdapter(this, stringList);
        customStringList.setAdapter(stringAdapter);

        customDateList = findViewById(R.id.customDateRecyclerView);
        customDateList.setLayoutManager(new LinearLayoutManager(this));
        customDateList.setNestedScrollingEnabled(false);
        customDateList.setFocusable(false);
        customDateList.setHasFixedSize(true);
        dateAdapter = new CustomDateRecyclerAdapter(this, dateList);
        customDateList.setAdapter(dateAdapter);

        addCustomStringBt.setOnClickListener(view -> stringAdapter.updateList(new Pair<>("","")));
        addCustomDateBt.setOnClickListener(view -> dateAdapter.updateList(new Pair<>("", null)));

        memTypesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                membershipType = i;
                switch (i)
                {
                    case 1:
                        if (exclusiveDate.getVisibility() == View.GONE)
                            exclusiveDate.setVisibility(View.VISIBLE);
                        chooseDateEt.setText("Ngày hết hạn");
                        break;
                    case 2:
                        if (exclusiveDate.getVisibility() == View.GONE)
                            exclusiveDate.setVisibility(View.VISIBLE);
                        chooseDateEt.setText("Ngày gia hạn");
                        break;
                    default:
                        exclusiveDate.setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        chooseDateBt.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                MyDatePicker myDatePicker = new MyDatePicker();
                myDatePicker.setTextID(view, chooseDateBt.getId());
                myDatePicker.setSetListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        myDatePicker.setDay(datePicker.getDayOfMonth());
                        myDatePicker.setMonth(datePicker.getMonth());
                        myDatePicker.setYear(datePicker.getYear());
                        String date = myDatePicker.getDay() + "/" + (myDatePicker.getMonth() + 1) + "/" + myDatePicker.getYear();
                        chooseDateBt.setText(date);
                        if (membershipType == 1)
                        {
                            couponExpirationDate = myDatePicker.getLocalDate();
                        }
                        else if (membershipType == 2)
                        {
                            subRenewDate = myDatePicker.getLocalDate();
                        }
                    }
                });
                myDatePicker.showTheDialog(view);
            }
        });
        addFab.setOnClickListener(view -> {
            cleanEmptyList();
            if (!isOK1 || !isOK2 || !isOK3 || !isOK4)
            {
                Toast.makeText(AddMSActivity.this, "Không tạo được thẻ mới do có lỗi.", Toast.LENGTH_SHORT).show();
            }
            else {
                MembershipBase membershipData;
                switch (membershipType) {
                    case 1:
                        membershipData = new Coupon(shortName.getText().toString(),fullName.getText().toString(), id.getText().toString(), issuer.getText().toString(), stringAdapter.getList(), dateAdapter.getList(), couponExpirationDate);
                        break;
                    case 2:
                        membershipData = new Subscription(shortName.getText().toString(),fullName.getText().toString(), id.getText().toString(), issuer.getText().toString(), stringAdapter.getList(), dateAdapter.getList(), subRenewDate);
                        break;
                    default:
                        membershipData = new Card(shortName.getText().toString(),fullName.getText().toString(), id.getText().toString(), issuer.getText().toString(), stringAdapter.getList(), dateAdapter.getList());
                        break;
                }
                Intent pushIntent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("new_membership", membershipData);
                pushIntent.putExtras(bundle);
                setResult(RESULT_OK, pushIntent);
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
                    NameValidator.checkShortName(charSequence.toString());
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
                    NameValidator.checkFullName(charSequence.toString());
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
                    NameValidator.checkIssuer(charSequence.toString());
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
                    NameValidator.checkStringID(charSequence.toString());
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
    }
    public void cleanEmptyList()
    {
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
}