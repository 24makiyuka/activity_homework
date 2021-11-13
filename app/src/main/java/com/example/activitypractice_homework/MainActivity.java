package com.example.activitypractice_homework;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import com.example.activitypractice_homework.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private Uri imageUri;

    private ActivityResultLauncher<Intent> albumLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        Intent data = result.getData();

                        if (result.getResultCode() == RESULT_OK && null != data) {
                            Uri selectedImage = data.getData();
                            this.imageUri = selectedImage;
                            Bitmap bitmap = Utils.loadBitmap(this,selectedImage);
                            binding.imageView2.setImageBitmap(bitmap);
                            binding.imageView2.setVisibility(View.VISIBLE);
                        }
                    });

    private ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted ->{
                if (isGranted) {
                    Toast.makeText(this,"권한이 설정되었습니다", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this,"권한이 설치되지 않았습니다 권한이 없으므로 앱을 종료합니다", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        binding.button1.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            albumLauncher.launch(intent);

        });
        binding.button2.setOnClickListener(v -> save());
    }


    private void save() {
        Intent intent = new Intent(this,SecondActivity.class);
        String name = binding.edit1.getText().toString();
        String contents = binding.edit2.getText().toString();

        intent.putExtra("EXTRA_NAME", name);
        intent.putExtra("EXTRA_CONTENT", contents);
//        intent.putExtra("EXTRA_TIME", System.currentTimeMillis());
        intent.putExtra("EXTRA_IMAGE", imageUri);

        startActivity(intent);
    }
}





