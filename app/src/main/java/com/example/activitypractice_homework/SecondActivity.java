package com.example.activitypractice_homework;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import com.example.activitypractice_homework.databinding.ActivityMainBinding;
import com.example.activitypractice_homework.databinding.ActivitySecondBinding;

import java.text.DateFormat;

public class SecondActivity extends AppCompatActivity {
    private ActivitySecondBinding binding;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySecondBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.button.setOnClickListener(v -> finish());

        Intent intent = getIntent();
        String name = intent.getStringExtra("EXTRA_NAME");
        String content = intent.getStringExtra("EXTRA_CONTENT");
//        long timestamp = intent.getLongExtra("EXTRA_TIME",0);
        Uri image = intent.getParcelableExtra("EXTRA_IMAGE");

        binding.textView1.setText(name);
        binding.textView2.setText(content);
        Bitmap bitmap = Utils.loadBitmap(this,image);
        binding.imageView.setImageBitmap(bitmap);
//        binding.textData.setText(getData(timestamp));

    }

//    private String getData(long time) {
//        return DateFormat("yyyy-MM-dd HH:mm:ss", time).toString();
//    }

}