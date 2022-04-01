package com.example.mycards;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class UserActivity extends AppCompatActivity {

    ImageButton githubButton, googlesheetButton, fbButton, proptitButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        //region Bindings
        githubButton = findViewById(R.id.githubButton);
        googlesheetButton = findViewById(R.id.googleSheetButton);
        fbButton = findViewById(R.id.facebookButton);
        proptitButton = findViewById(R.id.proptitButton);
        //endregion

        //region Listeners
        githubButton.setOnClickListener(view -> goToInternet("https://github.com/inusedname/My-Cards"));
        googlesheetButton.setOnClickListener(view -> {});
        fbButton.setOnClickListener(view -> goToInternet("https://www.facebook.com/two.pijama.bananas"));
        proptitButton.setOnClickListener(view -> goToInternet("https://www.facebook.com/clubproptit/"));
        //endregion
    }
    void goToInternet(String url)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
}