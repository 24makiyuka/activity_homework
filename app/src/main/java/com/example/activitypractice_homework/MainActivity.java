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
import android.widget.Toast;

import com.example.activitypractice_homework.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    ActivityResultLauncher<Intent> resultLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        Intent data = result.getData();

                        if (result.getResultCode() == RESULT_OK && null != data) {
                            Uri selectedImage = data.getData();
                            Bitmap bitmap = loadBitmap(selectedImage);
                            binding.imageView2.setImageBitmap(bitmap);
                        }
                    });


    private Bitmap loadBitmap(Uri uri) {
        String[] filePathColumn = {MediaStore.Images.Media.DATA};

        Cursor cursor = getContentResolver().query(uri,
                filePathColumn,null,null,null);
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();

        return BitmapFactory.decodeFile(picturePath);
    }

    private ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted ->{
                if (isGranted) {
                    Toast.makeText(this,"권한이 설정되었습니다", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this,"권한이 설치되지 않았습니다 권한이 없으므로 앱을 종료합니다", Toast.LENGTH_SHORT).show();;
                    finish();
                }
            });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.button2.setOnClickListener(v -> startSecondActivity());
        binding.button1.setOnClickListener(v -> getPhoto());

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }

    private void getPhoto(){
    Intent intent = new Intent(Intent.ACTION_PICK);
    intent.setType("image/*");
    resultLauncher.launch(intent);
    }

    public void startSecondActivity() {
        Intent intent = new Intent(this,SecondActivity.class);
        String message = binding.edit1.getText().toString();
        intent.putExtra("EXTRA_MESSAGE", message);


        startActivity(intent);
    }
}





