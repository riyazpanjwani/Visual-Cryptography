package com.example.aryan.myapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        if(getIntent().hasExtra("byteArray")){
            ImageView imageView = (ImageView) findViewById(R.id.result);
            Bitmap bm = BitmapFactory.decodeByteArray(
                    getIntent().getByteArrayExtra("byteArray"),0,
                    getIntent().getByteArrayExtra("byteArray").length
            );
            imageView.setImageBitmap(bm);
        }
    }

}
