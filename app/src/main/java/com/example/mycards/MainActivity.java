package com.example.mycards;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mycards.controller.MembershipController;
import com.example.mycards.model.Card;
import com.example.mycards.model.Coupon;
import com.example.mycards.model.MembershipBase;
import com.example.mycards.model.Subscription;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MainActivity extends AppCompatActivity {

    private MembershipController membershipController;

    private TextView greetingTV, userNameTV, timeTV, dateTV;
    private LinearLayout addButton, clearButton;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        membershipController = new MembershipController(MainActivity.this);

        addButton = (LinearLayout) findViewById(R.id.addMembershipButton);
        addButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddMSActivity.class);
                receiveFromAddMSActivity.launch(intent);
            }
        });
        clearButton = findViewById(R.id.clearButton);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearAll(view.getContext());
            }
        });
        this.timeTV = findViewById(R.id.timeTextView);
        this.dateTV = findViewById(R.id.dateTextView);
        this.greetingTV = findViewById(R.id.greetingsTextView);
        this.userNameTV = findViewById(R.id.userNameTextView);
        this.greetingTV.setText(getGreeting());
        this.userNameTV.setText(String.valueOf(getString(R.string.user_name)));

    }

        ActivityResultLauncher<Intent> receiveFromAddMSActivity = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == RESULT_OK)
        {
            assert result.getData() != null;
            Bundle bundle = result.getData().getExtras();
            MembershipBase mBase = (MembershipBase) bundle.get("new_membership");
            if (mBase.getClass() == Card.class)
            {
                membershipController.addCard((Card) mBase);
                Log.i("NEW_MEMBERSHIP", "New Card");
            }
            else if (mBase.getClass() == Coupon.class)
            {
                membershipController.addCoupon((Coupon) mBase);
                Log.i("NEW_MEMBERSHIP", "New Coupon");
            }
            else if (mBase.getClass() == Subscription.class)
            {
                membershipController.addSubscription((Subscription) mBase);
                Log.i("NEW_MEMBERSHIP", "New Membership");
            }
        }
    });
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
    private void clearAll(Context context)
    {
        membershipController.clearAll(context);
    }
}

