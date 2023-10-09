package com.example.slide12;

import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainCameraActivity extends AppCompatActivity {
    private Camera camera;
    private int cameraId = -1;
    private FloatingActionButton captureButton;
    private SurfaceView preview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera);

        captureButton = findViewById(R.id.image_capture_button);
        preview = findViewById(R.id.surface_view);

        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            Toast.makeText(this, "No camera on this device", Toast.LENGTH_LONG).show();
        } else {
            cameraId = findFrontFacingCamera();
            if (cameraId >= 0) {

                camera = Camera.open(cameraId);
                prepareCameraPreview();
            } else {
                Toast.makeText(this, "No front-facing camera found.", Toast.LENGTH_LONG).show();
            }
        }

        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (camera != null) {
                    camera.takePicture(null, null, new PhotoHandler(getApplicationContext()));
                }
            }
        });
    }

    private void prepareCameraPreview() {
        SurfaceHolder holder = preview.getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    camera.setPreviewDisplay(holder);

                    // Set the camera display orientation to portrait
                    camera.setDisplayOrientation(90); // 90 degrees for portrait

                    camera.startPreview();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                // Handle surface changes if needed
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                // Release the camera when the surface is destroyed
                if (camera != null) {
                    camera.stopPreview();
                    camera.release();
                    camera = null;
                }
            }
        });
    }

    private int findFrontFacingCamera() {
        int cameraId = -1;
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                cameraId = i;
                break;
            }
        }
        return cameraId;
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Release the camera in onStop to ensure it's released when the activity is no longer visible
        if (camera != null) {
            camera.stopPreview();
            camera.release();
            camera = null;
        }
    }
}

