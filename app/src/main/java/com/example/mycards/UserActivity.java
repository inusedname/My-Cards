package com.example.mycards;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.example.mycards.controller.adapters.SpinnerArrayAdapter;
import com.example.mycards.controller.util.UIConfig;
import com.example.mycards.controller.util.UserPreferences;

@SuppressWarnings({"FieldCanBeLocal"})
public class UserActivity extends AppCompatActivity {

    private ImageButton githubButton, googlesheetButton, fbButton, proptitButton;
    private Spinner dayNightSpinner;
    private SpinnerArrayAdapter dayNightAdapter;
    private SwitchCompat showGettingStartedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        //region Bindings
        githubButton = findViewById(R.id.githubButton);
        googlesheetButton = findViewById(R.id.googleSheetButton);
        fbButton = findViewById(R.id.facebookButton);
        proptitButton = findViewById(R.id.proptitButton);
        dayNightSpinner = findViewById(R.id.dayNightSpinner);
        showGettingStartedButton = findViewById(R.id.showGettingStartedButton);
        //endregion

        //region Initials
        showGettingStartedButton.setChecked(UserPreferences.showGettingStartDialog());
        //endregion

        //region Init adapters
        dayNightAdapter = new SpinnerArrayAdapter(this, R.layout.item_spinner, getResources().getStringArray(R.array.dayNightTypes));
        dayNightAdapter.setFormat(true);
        dayNightSpinner.setAdapter(dayNightAdapter);
        dayNightSpinner.setSelection(UIConfig.getNightMode(this));
        //endregion

        //region Listeners
        githubButton.setOnClickListener(view -> goToInternet("https://github.com/inusedname/My-Cards"));
        googlesheetButton.setOnClickListener(view -> goToInternet("https://forms.gle/MKQxhYKaApXZxvTf8"));
        fbButton.setOnClickListener(view -> goToInternet("https://www.facebook.com/two.pijama.bananas"));
        proptitButton.setOnClickListener(view -> goToInternet("https://www.facebook.com/clubproptit/"));
        dayNightSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                UIConfig.setNightMode(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        showGettingStartedButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                UserPreferences.setGettingStartDialog(b);
            }
        });
        //endregion
    }
    void goToInternet(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
}