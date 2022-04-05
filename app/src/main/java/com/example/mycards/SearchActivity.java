package com.example.mycards;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycards.controller.MembershipController;
import com.example.mycards.controller.adapters.SearchRecyclerAdapter;
import com.example.mycards.model.MembershipBase;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("FieldCanBeLocal")
public class SearchActivity extends AppCompatActivity {

    ActivityResultLauncher<Intent> searchActivityLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == RESULT_OK)
        {
            this.result.putExtras(result.getData());
            setResult(RESULT_OK, this.result);
        }
    });
    Intent result = new Intent();

    private RecyclerView searchRV;
    private SearchRecyclerAdapter searchRA;
    private EditText searchET;
    private ImageView deleteBt;

    private final List<MembershipBase> allMembership = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        //region Bindings
        searchRV = findViewById(R.id.searchRV);
        searchET = findViewById(R.id.searchBoxET);
        deleteBt = findViewById(R.id.deleteButton);
        //endregion

        //region Initials
        allMembership.addAll(MembershipController.getCardList(SearchActivity.this));
        allMembership.addAll(MembershipController.getCouponList(SearchActivity.this));
        allMembership.addAll(MembershipController.getSubscriptionList(SearchActivity.this));
        //endregion

        //region Init adapters
        searchRA = new SearchRecyclerAdapter(allMembership, this::goToDetailActivity);
        searchRV.setLayoutManager(new LinearLayoutManager(this));
        searchRV.setAdapter(searchRA);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchET.requestFocus();
        //endregion

        //region Listeners
        searchET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchRA.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        deleteBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchET.setText("");
                searchRA.reset();
            }
        });
        //endregion
    }

    public void goToDetailActivity(MembershipBase mBase) {
        Intent intent = new Intent(SearchActivity.this, DetailMSActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", mBase);
        intent.putExtras(bundle);
        searchActivityLauncher.launch(intent);
    }
}