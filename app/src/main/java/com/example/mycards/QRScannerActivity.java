package com.example.mycards;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mycards.controller.util.MyQRReader;
import com.journeyapps.barcodescanner.ScanContract;

public class QRScannerActivity extends AppCompatActivity {

    private TextView valueTV;
    private Button scanButton, copyButton;
    private final MyQRReader myQRReader = new MyQRReader();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrscanner);

        //region Bindings
        valueTV = findViewById(R.id.valueTV);
        scanButton = findViewById(R.id.scanButton);
        copyButton = findViewById(R.id.copyButton);
        //endregion

        //region Initials
        valueTV.setTextIsSelectable(true);
        //endregion

        //region Init Adapters

        //endregion

        //region Listener
        myQRReader.setBarcodeLauncher(registerForActivityResult(new ScanContract(),
                result -> {
                    if(result.getContents() != null)
                        valueTV.setText(result.getContents());
                }));
        scanButton.setOnClickListener(view -> myQRReader.run());
        copyButton.setOnClickListener(view -> {
            ClipboardManager manager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText("text", valueTV.getText());
            manager.setPrimaryClip(clipData);
            Toast.makeText(QRScannerActivity.this, getString(R.string.copyToClipboard), Toast.LENGTH_SHORT).show();
        });
        //endregion
    }
}