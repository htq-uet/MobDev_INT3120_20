package com.example.slide12;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class CameraActivity extends AppCompatActivity {

    static Bitmap bitmap;
    public static ImageView imageView;
    private Button cameraButton, openCameraButton;

    private static final int TAKE_PICTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_layout);
        imageView = findViewById(R.id.image_view);

        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        }

        cameraButton = findViewById(R.id.take_a_pic);
        cameraButton.setOnClickListener(v -> {
            startActivityForResult(
                    new Intent(MediaStore.ACTION_IMAGE_CAPTURE), TAKE_PICTURE);
        });

        openCameraButton = findViewById(R.id.open_camera);
        openCameraButton.setOnClickListener(v -> {
            Intent intent = new Intent(CameraActivity.this, MainCameraActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == TAKE_PICTURE && resultCode == RESULT_OK) {
            // Kết quả hình ảnh sẽ nằm trong Intent data
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            // Hiển thị hình ảnh lên layout

            imageView.setImageBitmap(imageBitmap);
        }
    }


}
