package com.example.bookverse;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class Utils {
    public static Bitmap base64ToBitmap(String base64String) {
        // Remove data URI prefix if present
        if (base64String.contains(",")) {
            base64String = base64String.substring(base64String.indexOf(",") + 1);
        }

        byte[] decodedBytes = Base64.decode(base64String, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }
}
