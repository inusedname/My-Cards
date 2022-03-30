package com.example.mycards.controller.util;

import androidx.activity.result.ActivityResultLauncher;

import com.journeyapps.barcodescanner.ScanOptions;

/*  Use this on your activity
    MyQRReader scanner = new MyQRReader();
    scanner.setBarcodeLauncher(registerForActivityResult(new ScanContract(),
                        result -> {
                            id.setText(result.getContents());
                        }));
    scanner.run();
    // You can't use this inside a clickOnListener. Use it inside onCreate() method.
    */
public class MyQRReader{
    private final ScanOptions options;
    private ActivityResultLauncher<ScanOptions> barcodeLauncher;


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
    public void setBarcodeLauncher(ActivityResultLauncher<ScanOptions> launcher)
    {
        this.barcodeLauncher = launcher;
    }
    public void run()
    {
        barcodeLauncher.launch(options);
    }
    @SuppressWarnings("UnusedDeclaration")
    public String getResult()
    {
        return barcodeLauncher.toString();
    }
}
