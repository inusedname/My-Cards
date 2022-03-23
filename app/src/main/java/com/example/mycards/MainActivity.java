package com.example.mycards;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mycards.controller.MembershipController;
import com.example.mycards.controller.util.FileLoader;
import com.example.mycards.model.Card;
import com.example.mycards.model.Coupon;
import com.example.mycards.model.MembershipBase;
import com.example.mycards.model.Subscription;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MembershipController membershipController;

    private Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        membershipController = new MembershipController(MainActivity.this);

        this.addButton = (Button) findViewById(R.id.newMembershipButton);
        this.addButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddMSActivity.class);
                receiveFromAddMSActivity.launch(intent);
            }
        });

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
                System.out.println("New Card!");
            }
            else if (mBase.getClass() == Coupon.class)
            {
                membershipController.addCoupon((Coupon) mBase);
                System.out.println("New Coupon!");
            }
            else if (mBase.getClass() == Subscription.class)
            {
                membershipController.addSubscription((Subscription) mBase);
                System.out.println("New Subscription!");
            }
        }
    });
}