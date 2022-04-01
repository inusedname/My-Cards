package com.example.mycards;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mycards.controller.util.MyQRGenerator;
import com.google.zxing.BarcodeFormat;

public class ShowQRActivity extends AppCompatActivity {

    String id;
    Bitmap qrCodeBitmap, code39Bitmap, code128Bitmap, codeEAN13Bitmap;
    ImageView qrCodeIV, code39IV, code128IV, codeEANIV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_qractivity);

        id = getIntent().getExtras().get("id").toString();

        //region Bindings
        qrCodeIV = findViewById(R.id.qrCodeIV);
        code39IV = findViewById(R.id.code39IV);
        code128IV = findViewById(R.id.code128IV);
        codeEANIV = findViewById(R.id.codeEANIV);
        //endregion

        //region Initials
        qrCodeBitmap = MyQRGenerator.generateQRCode(id, 500, 500, BarcodeFormat.QR_CODE);
        code39Bitmap = MyQRGenerator.generateQRCode(id, 800, 100, BarcodeFormat.CODE_39);
        code128Bitmap = MyQRGenerator.generateQRCode(id, 800, 100, BarcodeFormat.CODE_128);
        codeEAN13Bitmap = MyQRGenerator.generateQRCode(id, 800, 100, BarcodeFormat.EAN_13);

        qrCodeIV.setImageBitmap(qrCodeBitmap);
        code39IV.setImageBitmap(code39Bitmap);
        code128IV.setImageBitmap(code128Bitmap);
        codeEANIV.setImageBitmap(codeEAN13Bitmap);
        //endregion

        //region Listeners
        //endregion
    }
}