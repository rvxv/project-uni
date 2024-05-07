package com.uni.truthdare;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class PhotoUploadActivity extends AppCompatActivity {
    private static final int REQUEST_IMAGE_CAPTURE = 1;

    private ImageView galleryImage;
    private Button uploadButton, saveButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_upload); // Set the layout for this activity

        // Initialize views
        galleryImage = findViewById(R.id.gallery_image);
        uploadButton = findViewById(R.id.upload_button);
        saveButton = findViewById(R.id.save_button);

        // Set click listener for the save button
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savePhotoToGallery();
            }
        });


        // Set click listener for the upload button
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });

        // Initially hide the gallery image view and save button
        galleryImage.setVisibility(View.GONE);
        saveButton.setVisibility(View.GONE);
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            galleryImage.setImageBitmap(imageBitmap);
            galleryImage.setVisibility(View.VISIBLE);
            saveButton.setVisibility(View.VISIBLE); // Show the save button after the image is taken
        }

    }
    // Method to save the photo to the gallery
    private void savePhotoToGallery() {
        // Get the bitmap from the image view
        galleryImage.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(galleryImage.getDrawingCache());
        galleryImage.setDrawingCacheEnabled(false);

        // Save the bitmap to the gallery
        String savedImageURL = MediaStore.Images.Media.insertImage(
                getContentResolver(),
                bitmap,
                "TruthDare_Image_" + System.currentTimeMillis(),
                "Image saved from TruthDare app");

        if (savedImageURL != null) {
            // Photo saved successfully, show a toast or perform any other action
            Toast.makeText(this, "Photo saved to gallery", Toast.LENGTH_SHORT).show();
        } else {
            // Failed to save photo, show an error message
            Toast.makeText(this, "Failed to save photo", Toast.LENGTH_SHORT).show();
        }
    }

}

