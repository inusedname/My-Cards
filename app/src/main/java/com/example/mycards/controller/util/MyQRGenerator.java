package com.example.mycards.controller.util;

import android.graphics.Bitmap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

@SuppressWarnings("UnusedDeclaration")
public class MyQRGenerator {
    /**
     * Creates a new <code>File</code> instance by converting the given
     * pathname string into an abstract pathname.  If the given string is
     * the empty string, then the result is the empty abstract pathname.
     *
     * @param   format  Barcode Format. It can be:
     *                  CODE_39
     *                  CODE_128
     *                  QR_CODE
     *                  EAN_13
     *
     */
    public static Bitmap generateQRCode(String infoString, int width, int height, BarcodeFormat format)
    {
        if (format == BarcodeFormat.CODE_39 || format == BarcodeFormat.CODE_128)
            if (infoString.length() > 80)
                return null;
        if (format == BarcodeFormat.EAN_13)
            if (infoString.length() < 12 || infoString.length() > 13)
                return null;
        MultiFormatWriter writer = new MultiFormatWriter();
        try {
            BitMatrix matrix = writer.encode(infoString, format, width, height);
            BarcodeEncoder encoder = new BarcodeEncoder();
            return encoder.createBitmap(matrix);
        }
        catch (WriterException e){
            e.printStackTrace();

        }
        return null;
    }

}
