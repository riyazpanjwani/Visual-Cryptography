package com.example.aryan.myapp;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import static com.example.aryan.myapp.ImageUtilities.Decrypt;
import static com.example.aryan.myapp.VCSActivity.loadImages;


public class DecryptionActivity extends AppCompatActivity {

    private static int RESULT_LOAD_SHARE1 = 1;
    private static int RESULT_LOAD_SHARE2 = 2;
    private static Bitmap share1 ,share2;
    private static String pathToShare1, pathToShare2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decryption);
    }

    public void selectImage(View view){
        Intent intent = new Intent(Intent.ACTION_PICK,
        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, (view == findViewById(R.id.btnKey) )?
                RESULT_LOAD_SHARE1:RESULT_LOAD_SHARE2);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && null != data) {
            //System.out.println("Inside onActivity");
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            //System.out.println("Path: "+picturePath);
            if(requestCode == RESULT_LOAD_SHARE1) {
                share1 = BitmapFactory.decodeFile(picturePath);
                TextView tv1 = (TextView) findViewById(R.id.tvShare1);
                pathToShare1 = picturePath;
                tv1.setText("Share #1 is Loaded!!");
            }
            else if(requestCode == RESULT_LOAD_SHARE2){
                share2 = BitmapFactory.decodeFile(picturePath);
                TextView tv2 = (TextView) findViewById(R.id.tvShare2);
                pathToShare2 = picturePath;
                tv2.setText("Share #2 is Loaded!!");
            }
        }
    }

    public void decrypt(View view){
        Bitmap secret = Decrypt(share1,share2);
        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        secret.compress(Bitmap.CompressFormat.PNG,50,bs);
        Intent intent = new Intent(this,ResultActivity.class);
        intent.putExtra("byteArray",bs.toByteArray());
        startActivity(intent);
    }

//    public void rgDecrypt(View view){
////        Intent intent = new Intent(this,RGDecryptionActivity.class);
////        startActivity(intent);
////        ByteArrayOutputStream bs = new ByteArrayOutputStream();
////        share1.compress(Bitmap.CompressFormat.PNG,50,bs);
////        Intent intent = new Intent(this,ResultActivity.class);
////        intent.putExtra("byteArray",bs.toByteArray());
////        startActivity(intent);
//        Intent intent = new Intent(this,RGDecryptionActivity.class);
//        intent.setAction(Intent.ACTION_SEND_MULTIPLE);
//
//        ArrayList<Bitmap> bms = new ArrayList<>();
//        bms.add(share1); bms.add(share2);
//
//        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, bms);
//
//        startActivity(intent);
//
//    }

    public void rgDecrypt(View view){
        if(view == findViewById(R.id.btnVcs)){
            Intent intent = new Intent(this,VCSActivity.class);
            loadImages(pathToShare1,pathToShare2);
            startActivity(intent);
        }

    }
}
