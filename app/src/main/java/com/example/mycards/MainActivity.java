package com.example.mycards;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycards.controller.CONSTANT;
import com.example.mycards.controller.MembershipController;
import com.example.mycards.controller.adapters.HomeCardViewRecyclerAdapter;
import com.example.mycards.controller.util.UIConfig;
import com.example.mycards.controller.util.UserPreferences;
import com.example.mycards.model.Card;
import com.example.mycards.model.Coupon;
import com.example.mycards.model.MembershipBase;
import com.example.mycards.model.Subscription;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"FieldCanBeLocal", "UnusedDeclaration", "unchecked"})

public class MainActivity extends AppCompatActivity {

    final Handler timeHandler = new Handler();
    Runnable myClock = new Runnable() {
        @Override
        public void run() {
            int sec = getGreeting();
            timeHandler.postDelayed(myClock, sec * 1000L);
        }
    };
    ActivityResultLauncher<Intent> MainActivityLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == RESULT_OK) {
            List<Integer> flags = (List<Integer>) result.getData().getExtras().get("flags");
            for (Integer i: flags) {
                Log.i("STATS", "flag code = " + i);
                switch (i) {
                    case CONSTANT.RESULT_DEL_CARD:
                        Toast.makeText(this, "Delete card complete", Toast.LENGTH_SHORT).show();
                        updateCardRV();
                        break;
                    case CONSTANT.RESULT_DEL_COUPON:
                        Toast.makeText(this, "Delete coupon complete", Toast.LENGTH_SHORT).show();
                        updateCouponRV();
                        break;
                    case CONSTANT.RESULT_DEL_SUB:
                        Toast.makeText(this, "Delete sub complete", Toast.LENGTH_SHORT).show();
                        updateSubRV();
                        break;
                    case CONSTANT.RESULT_ADD_CARD:
                        Toast.makeText(this, "Add new card complete", Toast.LENGTH_SHORT).show();
                        updateCardRV();
                        break;
                    case CONSTANT.RESULT_ADD_COUPON:
                        Toast.makeText(this, "Add new coupon complete", Toast.LENGTH_SHORT).show();
                        updateCouponRV();
                        break;
                    case CONSTANT.RESULT_ADD_SUB:
                        Toast.makeText(this, "Add new subscription complete", Toast.LENGTH_SHORT).show();
                        updateSubRV();
                        break;
                    case CONSTANT.RESULT_EDIT_CARD:
                        updateCardRV();
                        break;
                    case CONSTANT.RESULT_EDIT_COUPON:
                        updateCouponRV();
                        break;
                    case CONSTANT.RESULT_EDIT_SUB:
                        updateSubRV();
                        break;
                    case CONSTANT.RESULT_PINNED_CHANGE:
                        MembershipBase data = (MembershipBase) result.getData().getExtras().get("data");
                        updatePinnedRV(data);
                        break;
                }
            }
        }
    });

    private HomeCardViewRecyclerAdapter cardRA, couponRA, subRA, pinnedRA;
    private RecyclerView cardRV, couponRV, subRV, pinnedRV;
    private LinearLayout cardStart, couponStart, subStart, pinnedTab;
    private List<Card> cardList;
    private List<Coupon> couponList;
    private List<Subscription> subList;
    private List<MembershipBase> pinnedList;

    private TextView timeTV, dateTV;
    private LinearLayout addButton, qrScannerBT, userButton, allMSButton;
    private FloatingActionButton searchFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UserPreferences.setSettings(getSharedPreferences("user_data", 0));
        UIConfig.setNightMode(UserPreferences.getThemePreference());
        if (UserPreferences.showGettingStartDialog())
            showGettingStartedDialog();

        // region Binding
        timeTV = findViewById(R.id.timeTV);
        dateTV = findViewById(R.id.dateTV);
        addButton = findViewById(R.id.addMembershipBT);
        qrScannerBT = findViewById(R.id.qrScannerBT);
        userButton = findViewById(R.id.userSettingBT);
        allMSButton = findViewById(R.id.allMembershipBT);
        cardRV = findViewById(R.id.cardRV);
        couponRV = findViewById(R.id.couponRV);
        subRV = findViewById(R.id.subRV);
        searchFab = findViewById(R.id.searchFAB);
        cardStart = findViewById(R.id.cardGettingStarted);
        couponStart = findViewById(R.id.couponGettingStarted);
        subStart = findViewById(R.id.subGettingStarted);
        pinnedTab = findViewById(R.id.pinnedTab);
        pinnedRV = findViewById(R.id.pinnedRV);
        // endregion

        //region Initials
        startTime();
        cardList = MembershipController.getCardList(this);
        couponList = MembershipController.getCouponList(this);
        subList = MembershipController.getSubscriptionList(this);
        pinnedList = new ArrayList<>();
        pinnedList.addAll(MembershipController.getPinnedCardList(this));
        pinnedList.addAll(MembershipController.getPinnedCouponList(this));
        pinnedList.addAll(MembershipController.getPinnedSubList(this));
        if (cardList.size() > 0)
            cardStart.setVisibility(View.GONE);
        else
            cardStart.setVisibility(View.VISIBLE);
        if (couponList.size() > 0)
            couponStart.setVisibility(View.GONE);
        else
            couponStart.setVisibility(View.VISIBLE);
        if (subList.size() > 0)
            subStart.setVisibility(View.GONE);
        else
            subStart.setVisibility(View.VISIBLE);
        if (pinnedList.size() > 0)
            pinnedTab.setVisibility(View.VISIBLE);
        else
            pinnedTab.setVisibility(View.GONE);
        //endregion

        //region Init Adapters
        cardRA = new HomeCardViewRecyclerAdapter((List<MembershipBase>)(List<?>) cardList, this::goToDetailActivity);
        cardRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        cardRV.setAdapter(cardRA);

        couponRA = new HomeCardViewRecyclerAdapter((List<MembershipBase>)(List<?>)couponList, this::goToDetailActivity);
        couponRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        couponRV.setAdapter(couponRA);

        subRA = new HomeCardViewRecyclerAdapter((List<MembershipBase>)(List<?>)subList, this::goToDetailActivity);
        subRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        subRV.setAdapter(subRA);

        pinnedRA = new HomeCardViewRecyclerAdapter(pinnedList, this::goToDetailActivity);
        pinnedRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        pinnedRV.setAdapter(pinnedRA);
        //endregion

        //region Listeners
        addButton.setOnClickListener(view -> goToAddActivity());
        qrScannerBT.setOnClickListener(view -> goToQRActivity());
        userButton.setOnClickListener(view -> goToUserActivity());
        allMSButton.setOnClickListener(view -> Toast.makeText(MainActivity.this, getString(R.string.underConstruction), Toast.LENGTH_SHORT).show());
        searchFab.setOnClickListener(view -> goToSearchActivity());
        cardStart.setOnClickListener(view -> goToAddActivity());
        couponStart.setOnClickListener(view -> goToAddActivity());
        subStart.setOnClickListener(view -> goToAddActivity());
        //endregion
    }

    @Override
    protected void onDestroy() {
        stopTime();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        stopTime();
        super.onPause();
    }

    @Override
    protected void onResume() {
        startTime();
        super.onResume();
    }

    private int getGreeting() {
        LocalDateTime time = LocalDateTime.now();
        timeTV.setText(time.format(DateTimeFormatter.ofPattern("hh:mm")));
        dateTV.setText(time.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        return 60 - time.getSecond();
    }
    private void startTime() {
        timeHandler.post(myClock);
    }
    private void stopTime() {
        timeHandler.removeCallbacks(myClock);
    }
    private void goToQRActivity() {
        Intent intent = new Intent(MainActivity.this, QRScannerActivity.class);
        MainActivityLauncher.launch(intent);
    }
    private void goToAddActivity() {
        Intent intent = new Intent(MainActivity.this, AddMSActivity.class);
        MainActivityLauncher.launch(intent);
    }
    private void goToUserActivity() {
        Intent intent = new Intent(MainActivity.this, UserActivity.class);
        MainActivityLauncher.launch(intent);
    }
    private void goToSearchActivity() {
        Intent intent = new Intent(MainActivity.this, SearchActivity.class);
        MainActivityLauncher.launch(intent);
    }
    private void goToDetailActivity(MembershipBase mBase) {
        Intent intent = new Intent(MainActivity.this, DetailMSActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", mBase);
        intent.putExtras(bundle);
        MainActivityLauncher.launch(intent);
    }
    private void statistic() {
        Log.i("STATS", "Cards count: " + MembershipController.getCardCount(this));
        Log.i("STATS", "Coupons count: " + MembershipController.getCouponCount(this));
        Log.i("STATS", "Subscription count: " + MembershipController.getSubCount(this));
    }
    private void updateCardRV() {
        cardList = MembershipController.getCardList(this.getApplicationContext());
        cardRA.updateList((List<MembershipBase>)(List<?>)cardList);
        if (cardList.size() > 0)
            cardStart.setVisibility(View.GONE);
        else
            cardStart.setVisibility(View.VISIBLE);
    }
    private void updateCouponRV() {
        couponList = MembershipController.getCouponList(this.getApplicationContext());
        couponRA.updateList((List<MembershipBase>)(List<?>)couponList);
        if (couponList.size() > 0)
            couponStart.setVisibility(View.GONE);
        else
            couponStart.setVisibility(View.VISIBLE);
    }
    private void updateSubRV() {
        subList = MembershipController.getSubscriptionList(this.getApplicationContext());
        subRA.updateList((List<MembershipBase>)(List<?>)subList);
        if (subList.size() > 0)
            subStart.setVisibility(View.GONE);
        else
            subStart.setVisibility(View.VISIBLE);
    }
    private void updatePinnedRV(@NonNull MembershipBase data) {
        Log.i("STATS", "Pinned Tab Updated");
        if (data.isPinned())
            pinnedList.add(data);
        else {
            for (MembershipBase i: pinnedList)
                if (i.systemID == data.systemID)
                {
                    pinnedList.remove(i);
                    break;
                }
        }
        if (pinnedList.size() > 0) {
            pinnedRA.updateList(pinnedList);
            pinnedTab.setVisibility(View.VISIBLE);
        }
        else
            pinnedTab.setVisibility(View.GONE);
    }
    private void showGettingStartedDialog() {
        String hello = getString(R.string.getting_started_hello);
        String card = getString(R.string.getting_started_card);
        String coupon = getString(R.string.getting_started_coupon);
        String sub = getString(R.string.getting_started_sub);
        new AlertDialog.Builder(this)
                .setTitle(R.string.getting_started_name)
                .setMessage(hello + "\n\n" + card + "\n\n" + coupon + "\n\n" + sub)
                .setNegativeButton(R.string.no_show_again, (dialogInterface, i) -> UserPreferences.setGettingStartDialog(false))
                .setPositiveButton(R.string.skip, null)
                .show();
    }
}