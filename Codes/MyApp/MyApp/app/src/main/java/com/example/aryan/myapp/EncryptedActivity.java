package com.example.aryan.myapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

import static com.example.aryan.myapp.ImageUtilities.Create_Cipher;
import static com.example.aryan.myapp.ImageUtilities.Create_Key;
import static com.example.aryan.myapp.ImageUtilities.Magnify;


public class EncryptedActivity extends AppCompatActivity {

//    private ImageView ivKey = (ImageView) findViewById(R.id.ivKey);
//    private ImageView ivCipher = (ImageView) findViewById(R.id.ivCipher);
//
    private Bitmap bm1 ,bm2, bm1_mag,bm2_mag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encrypted);
        if(getIntent().hasExtra("byteArray")) {
            Bitmap originalImage = BitmapFactory.decodeByteArray(
                    getIntent().getByteArrayExtra("byteArray"), 0,
                    getIntent().getByteArrayExtra("byteArray").length
            );

            ImageView imv1 = (ImageView) findViewById(R.id.ivKey);
            ImageView imv2 = (ImageView) findViewById(R.id.ivCipher);

            bm1 = Create_Key(originalImage);
            bm2 = Create_Cipher(originalImage, bm1);

            bm1_mag = Magnify(bm1);
            bm2_mag = Magnify(bm2);

            imv1.setImageBitmap(bm1);
            imv2.setImageBitmap(bm2);
        }

    }

    public void saveImages(View v){
        if(v == findViewById(R.id.btnSave)){
                String root = Environment.getExternalStorageDirectory().toString();
                File myDir = new File(root + "/saved_images");
                myDir.mkdirs();
                Random generator = new Random();
                int n = 10000;
                n = generator.nextInt(n);
                String keyName = "Image-Key-mag"+ n +".jpg";
                String cipherName = "Image-Cipher-mag"+ n +".jpg";
                File file1 = new File (myDir, keyName);
                File file2 =  new File(myDir,cipherName);
                if (file1.exists ()) file1.delete ();
                if (file2.exists ()) file2.delete ();

            try {
                    FileOutputStream out1 = new FileOutputStream(file1);
                    FileOutputStream out2 = new FileOutputStream(file2);

                    bm1_mag.compress(Bitmap.CompressFormat.JPEG, 90, out1);
                    bm2_mag.compress(Bitmap.CompressFormat.JPEG, 90, out2);

                    out1.flush(); out2.flush();
                    out1.close(); out2.close();

                refreshGallery(file1.getAbsolutePath());
                refreshGallery(file2.getAbsolutePath());

                Toast.makeText(this,"Files Saved Successfully !! ",Toast.LENGTH_LONG);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    public void refreshGallery(String fileUrl) {
        Intent mediaScanIntent = new Intent(
                Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(fileUrl);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }
    }

