package com.example.mycards.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mycards.R;
import com.example.mycards.controller.CustomDateRecyclerAdapter;
import com.example.mycards.controller.CustomStringRecyclerAdapter;
import com.example.mycards.controller.MembershipController;
import com.example.mycards.controller.exceptions.StringInputFail;
import com.example.mycards.model.Pair;

import java.util.ArrayList;
import java.util.List;

public class addCardTab extends Fragment {

    boolean isOK1, isOK2,isOK3, isOK4;
    private RecyclerView customStringList;
    private RecyclerView customDateList;
    private CustomStringRecyclerAdapter stringAdapter;
    private CustomDateRecyclerAdapter dateAdapter;
    private List<Pair<String,String>> stringList = new ArrayList<>();
    private TextView error_sn, error_fn, error_id, error_is;
    private EditText shortName, fullName, id, issuer;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stringList.add(new Pair<String,String>("",""));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_card_tab, container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        customStringList = view.findViewById(R.id.customStringRecycler_card);
        error_sn = view.findViewById(R.id.errorTextView_shortName);
        error_fn = view.findViewById(R.id.errorTextView_fullName);
        error_id = view.findViewById(R.id.errorTextView_id);
        error_is = view.findViewById(R.id.errorTextView_issuer);

        error_sn.setText("");
        error_fn.setText("");
        error_id.setText("");
        error_is.setText("");
        shortName = view.findViewById(R.id.shortNameEditText_card);
        fullName = view.findViewById(R.id.fullNameEditText_card);
        id = view.findViewById(R.id.idEditText_card);
        issuer = view.findViewById(R.id.issuerEditText_card);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        customStringList.setLayoutManager(layoutManager);
        customStringList.setHasFixedSize(true);
        stringAdapter = new CustomStringRecyclerAdapter(getContext(),stringList);
        customStringList.setAdapter(stringAdapter);

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
                }
                catch (StringInputFail stringInputFail) {
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
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

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
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}