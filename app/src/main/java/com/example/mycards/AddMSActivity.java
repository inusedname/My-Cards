package com.example.mycards;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mycards.controller.adapters.CustomDateRecyclerAdapter;
import com.example.mycards.controller.adapters.CustomStringRecyclerAdapter;
import com.example.mycards.controller.MembershipController;
import com.example.mycards.controller.exceptions.StringInputFail;
import com.example.mycards.model.Pair;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AddMSActivity extends AppCompatActivity{

    private RecyclerView customStringList;
    private RecyclerView customDateList;
    private CustomStringRecyclerAdapter stringAdapter;
    private CustomDateRecyclerAdapter dateAdapter;

    boolean isOK1, isOK2,isOK3, isOK4;
    private EditText shortName, fullName, id, issuer;
    private List<Pair<String,String>> stringList = new ArrayList<>();
    private List<Pair<String, LocalDate>> dateList = new ArrayList<>();

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

        shortName = findViewById(R.id.shortNameEditText_card);
        fullName = findViewById(R.id.fullNameEditText_card);
        id = findViewById(R.id.idEditText_card);
        issuer = findViewById(R.id.issuerEditText_card);
        Button addCustomStringBt = findViewById(R.id.addCustomStringBt);
        Button addCustomDateBt = findViewById(R.id.addCustomDateBt);

        customStringList = findViewById(R.id.customStringRecyclerView);
        customStringList.setLayoutManager(new LinearLayoutManager(this));
        customStringList.setNestedScrollingEnabled(false);
        customStringList.setFocusable(false);
        customStringList.setHasFixedSize(true);
        stringAdapter = new CustomStringRecyclerAdapter(this,stringList);
        customStringList.setAdapter(stringAdapter);

        customDateList = findViewById(R.id.customDateRecyclerView);
        customDateList.setLayoutManager(new LinearLayoutManager(this));
        customDateList.setNestedScrollingEnabled(false);
        customDateList.setFocusable(false);
        customDateList.setHasFixedSize(true);
        dateAdapter = new CustomDateRecyclerAdapter(this,dateList);
        customDateList.setAdapter(dateAdapter);

        addCustomStringBt.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                stringAdapter.updateList(new Pair<>("",""));
            }
        });

        addCustomDateBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateAdapter.updateList(new Pair<>("", null));
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
                    MembershipController.checkShortName(charSequence.toString());
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
                    MembershipController.checkFullName(charSequence.toString());
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
                    MembershipController.checkIssuer(charSequence.toString());
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
                    MembershipController.checkID(charSequence.toString());
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
}