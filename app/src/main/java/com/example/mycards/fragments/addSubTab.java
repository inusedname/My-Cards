package com.example.mycards.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mycards.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link addSubTab#newInstance} factory method to
 * create an instance of this fragment.
 */
public class addSubTab extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_sub_tab, container,false);
    }
}