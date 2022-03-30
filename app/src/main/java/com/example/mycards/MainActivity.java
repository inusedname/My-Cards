package com.example.mycards;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycards.controller.CONSTANT;
import com.example.mycards.controller.MembershipController;
import com.example.mycards.controller.adapters.HomeCardViewRecyclerAdapter;
import com.example.mycards.model.MembershipBase;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@SuppressWarnings({"FieldCanBeLocal"})
public class MainActivity extends AppCompatActivity {


    private HomeCardViewRecyclerAdapter cardRA, couponRA, subRA;
    private RecyclerView cardRV, couponRV, subRV;
    private List<MembershipBase> cardList, couponList, subList;

    private TextView greetingTV, userNameTV, timeTV, dateTV;
    private LinearLayout addButton, clearButton, userButton;

    ActivityResultLauncher<Intent> MainActivityLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        switch (result.getResultCode())
        {
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
        }
    });

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        statistic();

        // region Binding
        timeTV = findViewById(R.id.timeTV);
        dateTV = findViewById(R.id.dateTV);
        greetingTV = findViewById(R.id.greetingsTV);
        userNameTV = findViewById(R.id.userNameTV);
        addButton = findViewById(R.id.addMembershipBT);
        clearButton = findViewById(R.id.clearBT);
        userButton = findViewById(R.id.userSettingBT);
        cardRV = findViewById(R.id.cardRV);
        couponRV = findViewById(R.id.couponRV);
        subRV = findViewById(R.id.subRV);
        // endregion

        //region Initials
        greetingTV.setText(getGreeting());
        userNameTV.setText(String.valueOf(getString(R.string.user_name)));
        cardList = MembershipController.getCardList(this);
        couponList = MembershipController.getCouponList(this);
        subList = MembershipController.getSubscriptionList(this);
        //endregion

        //region Init Adapters
        cardRA = new HomeCardViewRecyclerAdapter(cardList);
        cardRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        cardRV.setAdapter(cardRA);

        couponRA = new HomeCardViewRecyclerAdapter(couponList);
        couponRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        couponRV.setAdapter(couponRA);

        subRA = new HomeCardViewRecyclerAdapter(subList);
        subRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        subRV.setAdapter(subRA);
        //endregion

        //region Listeners
        addButton.setOnClickListener(view -> goToAddActivity());
        clearButton.setOnClickListener(view -> {
            MembershipController.removeAll(MainActivity.this);
            cardRA.updateList(cardList);
            couponRA.updateList(couponList);
            subRA.updateList(subList);
        });
        userButton.setOnClickListener(view -> goToUserActivity());
        //endregion
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private String getGreeting()
    {
        LocalDateTime time = LocalDateTime.now();
        timeTV.setText(time.format(DateTimeFormatter.ofPattern("hh:mm")));
        dateTV.setText(time.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        int hour = time.getHour();
        if (6 <= hour && hour <= 12)
            return getString(R.string.good_morning);
        else if (12 <= hour && hour <= 18)
            return getString(R.string.good_afternoon);
        return getString(R.string.good_night);
    }
    public void goToAddActivity()
    {
        Intent intent = new Intent(MainActivity.this, AddMSActivity.class);
        MainActivityLauncher.launch(intent);
    }
    public void goToUserActivity()
    {
        Intent intent = new Intent(MainActivity.this, UserActivity.class);
        MainActivityLauncher.launch(intent);
    }
    @SuppressWarnings({"UnusedDeclaration"})
    public void statistic()
    {
        Log.i("STATS", "Cards count: " + MembershipController.getCardCount(this));
        Log.i("STATS", "Coupons count: " + MembershipController.getCouponCount(this));
        Log.i("STATS", "Subscription count: " + MembershipController.getSubCount(this));
    }
    public void updateCardRV()
    {
        cardList = MembershipController.getCardList(this.getApplicationContext());
        cardRA.updateList(cardList);
        Log.i("ADAPTER", "Card has been updated. New Quantity is: " + cardList.size());
    }
    public void updateCouponRV()
    {
        couponList = MembershipController.getCouponList(this.getApplicationContext());
        couponRA.updateList(couponList);
        Log.i("ADAPTER", "Coupon has been updated. New Quantity is: " + couponList.size());
    }
    public void updateSubRV()
    {
        subList = MembershipController.getSubscriptionList(this.getApplicationContext());
        subRA.updateList(subList);
        Log.i("ADAPTER", "Sub has been updated. New Quantity is: " + subList.size());
    }
}

