package com.example.budapestquest;
import android.graphics.Bitmap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

public class QRManager {
    public final static int QRcodeWidth = 500 ;
    public final static int COLOR_BLACK = 0xFF000000;
    public final static int COLOR_WHITE = 0xFFFFFFFF;

    public final static String QRCONST = "5vosgame";

    public final static char QR_HARC1   = '0';
    public final static char QR_HARC2   = '1';
    public final static char QR_BOLT    = '2';
    public final static char QR_KONDI   = '3';
    public final static char QR_AUTOMATA= '4';
    public final static char QR_KASZINO = '5';
    public final static char QR_LEPES   = '6';
    public final static char QR_AKCIOK  = '7';
    public final static char QR_MUNKA   = '8';

    /*
    *
    * Formátum:
    * 8 byte: '5vosgame' -> konstans
    * 4 byte: verzió -> TODO: Compile-time assertelni a méretét
    * 1 byte: method
    * x byte: data
    *
    * */

    public static Bitmap TextToImageEncode(char method, String data) {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    QRCONST + GameController.Version + method + data,
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    QRcodeWidth, QRcodeWidth, null
            );
        } catch (Exception e) {
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
