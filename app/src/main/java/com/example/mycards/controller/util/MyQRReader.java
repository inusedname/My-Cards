package com.example.mycards.controller.util;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mycards.MainActivity;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class MyQRReader{
    private ScanOptions options;
    private ActivityResultLauncher<ScanOptions> barcodeLauncher;
    private String result;


    public MyQRReader()
    {
        options = new ScanOptions();
        options.setDesiredBarcodeFormats(ScanOptions.ALL_CODE_TYPES);
        options.setPrompt("Scan a barcode/QR code");
        options.setCameraId(0);  // Use a specific camera of the device
        options.setBeepEnabled(true);
        options.setBarcodeImageEnabled(true);
        options.setOrientationLocked(false);
    }
    public void setLauncher(ActivityResultLauncher<ScanOptions> barcodeLauncher)
    {
        this.barcodeLauncher = barcodeLauncher;
    }
    public void run()
    {
        barcodeLauncher.launch(options);
    }
    public String getResult()
    {
        return result;
    }
}
