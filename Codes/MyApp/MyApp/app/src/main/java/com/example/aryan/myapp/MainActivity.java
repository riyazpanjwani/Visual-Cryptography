package com.example.aryan.myapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void changeActivity(View view){
        Intent intent = null;
        if(view == findViewById(R.id.button)){
            intent = new Intent(MainActivity.this,EncryptionActivity.class);
        }else if (view == findViewById(R.id.button2)){
            intent = new Intent(MainActivity.this,DecryptionActivity.class);
        }else if (view == findViewById(R.id.button3)){

        }
        startActivity(intent);
    }


}
