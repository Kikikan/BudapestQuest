package com.example.budapestquest;
import android.graphics.Bitmap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class QRManager {
    public final static int QRcodeWidth = 500 ;
    public final static int COLOR_BLACK = 0xFF000000;
    public final static int COLOR_WHITE = 0xFFFFFFFF;

    public static Bitmap TextToImageEncode(String Value) throws WriterException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    Value,
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    QRcodeWidth, QRcodeWidth, null
            );
        } catch (IllegalArgumentException Illegalargumentexception) {
            return null;
        }

        int bitMatrixWidth = bitMatrix.getWidth();
        int bitMatrixHeight = bitMatrix.getHeight();
        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0, offset = 0; y < bitMatrixHeight; y++) {
            for (int x = 0; x < bitMatrixWidth; x++, offset++) {
                pixels[offset] = bitMatrix.get(x, y) ? COLOR_BLACK : COLOR_WHITE;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, QRcodeWidth, 0, 0, bitMatrixWidth, bitMatrixHeight);

        return bitmap;
    }
}
