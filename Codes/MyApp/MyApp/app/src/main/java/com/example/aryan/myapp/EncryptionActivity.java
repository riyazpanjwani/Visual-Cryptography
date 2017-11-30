package com.example.aryan.myapp;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.scanlibrary.ScanActivity;
import com.scanlibrary.ScanConstants;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;


public class EncryptionActivity extends AppCompatActivity {
    private static int REQUEST_CODE = 99;
    private static int RESULT_LOAD_IMAGE = 1;
    private static Bitmap image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encryption);
    }

    public void getScannerAcitivity(View v){
       Intent intent = null;
        if(v == findViewById(R.id.button4)) {
           intent = new Intent(EncryptionActivity.this, ScanActivity.class);
           intent.putExtra(ScanConstants.OPEN_INTENT_PREFERENCE, ScanConstants.OPEN_CAMERA);
           startActivityForResult(intent, REQUEST_CODE);
       } else if (v == findViewById(R.id.button5)){
           // System.out.println("Button 5 is selected");
            intent = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
           startActivityForResult(intent, RESULT_LOAD_IMAGE);
       }
//       else if(v == findViewById(R.id.button6)){
//            String picturePath = createPicturePath();
//            try {
//                galleryAddPic(picturePath);
//            }catch (NullPointerException e){
//                Toast.makeText(getApplicationContext(),"Cannot Store File !!",
//                        Toast.LENGTH_SHORT).show();
//            }
//
//        }

    }

//    private String createPicturePath() {
//        // Create an image file name
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String imageFileName = "JPEG_" + timeStamp + "_";
//        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//        File image;
//        String picturePath = null;
//        try{
//             image = File.createTempFile(
//                    imageFileName,  /* prefix */
//                    ".jpg",         /* suffix */
//                    storageDir      /* directory */
//            );
//             picturePath = image.getAbsolutePath();
//        }catch (IOException e){
//            e.printStackTrace();
//        }
//        // Save a file: path for use with ACTION_VIEW intents
//        return picturePath;
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        ImageView scannedImageView = (ImageView) findViewById(R.id.imageView);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri uri = data.getExtras().getParcelable(ScanConstants.SCANNED_RESULT);
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                getContentResolver().delete(uri, null, null);
                scannedImageView.setImageBitmap(bitmap);
                View v = findViewById(R.id.button6);
                v.setVisibility(View.VISIBLE);
                image = bitmap;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            //System.out.println("Inside onActivity");
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            //System.out.println("Path: "+picturePath);
            ImageView galleryImage = (ImageView) findViewById(R.id.imageView);
            Bitmap bm = BitmapFactory.decodeFile(picturePath);
            galleryImage.setImageBitmap(bm);
            image = bm;
        }
    }
//    private void galleryAddPic(String picturePath) {
//        File file = new File (picturePath);
//
//        try {
//            FileOutputStream out = new FileOutputStream(file);
//
//            image.compress(Bitmap.CompressFormat.JPEG, 90, out);
//
//            out.flush();out.close();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//        File f = new File(picturePath);
//        Uri contentUri = Uri.fromFile(f);
//        mediaScanIntent.setData(contentUri);
//        this.sendBroadcast(mediaScanIntent);
//
//        Toast.makeText(this,"Files Saved Successfully !! ",Toast.LENGTH_LONG);
//
//        View v = findViewById(R.id.button6);
//        v.setVisibility(View.GONE);
//    }


    public void saveScannedImage(View v){
        if(v == findViewById(R.id.button6)){
            String root = Environment.getExternalStorageDirectory().toString();
            File myDir = new File(root + "/scanned_images");
            myDir.mkdirs();
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";


            File file = new File (myDir, imageFileName);

            try {
                FileOutputStream out = new FileOutputStream(file);

                image.compress(Bitmap.CompressFormat.JPEG, 90, out);
                out.flush();out.close();

                refreshGallery(file.getAbsolutePath());

                Toast.makeText(this,"Files Saved Successfully !! ",Toast.LENGTH_LONG);
                v.setVisibility(View.GONE);

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


    public void encrypt(View view){
        if(view == findViewById(R.id.btnEncrypt)) {
            Intent intent = new Intent(this, EncryptedActivity.class);

            if(image != null) {
                ByteArrayOutputStream bs = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.PNG, 50, bs);
                intent.putExtra("byteArray", bs.toByteArray());
            }else{
                Toast.makeText(getApplicationContext(),"Some Thing",Toast.LENGTH_SHORT);
            }
            startActivity(intent);
        }
    }

}
