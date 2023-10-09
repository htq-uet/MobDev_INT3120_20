package com.example.slide12;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.widget.ImageView;

public class PhotoHandler implements Camera.PictureCallback {

    private final Context context;

    public PhotoHandler(Context applicationContext) {
        this.context = applicationContext;
    }

    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
        // Decode the byte array into a Bitmap
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);

        //Set vertical orientation
        bitmap = RotateBitmap(bitmap, -90);

        CameraActivity.bitmap = bitmap;

        // Start the CameraActivity
        Intent intent = new Intent(context, CameraActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static Bitmap RotateBitmap(Bitmap source, float angle)
    {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }
}