package com.example.aryan.myapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

/**
 * Created by Aryan on 21-10-2017.
 */



public class ImageUtilities {

    static final int BLACK = -16777216; // Constant to represent the RGB binary value of black. In binary - 1111111 00000000 00000000 00000000
    static final int WHITE = -1; // Constant to represent the RGB binary value of white. In binary - 1111111 1111111 1111111 1111111


    // Converts original image into a cipher image using the key
    public static Bitmap Create_Cipher(Bitmap original, Bitmap key) {

        Bitmap cipher_image = Bitmap.createBitmap(
                original.getWidth(), original.getHeight(),
                Bitmap.Config.RGB_565);

        // For every pixel in the original image, do the following:
        // 1. If the key pixel is black, flip the color of the original image and store in  cipher at identical location
        // 2. If the key pixel is white, set the cipher pixel to the same color as the original image
        for (int i = 0; i < cipher_image.getHeight(); i++) {
            for (int j = 0; j < cipher_image.getWidth(); j++) {
                if (key.getPixel(j, i) == BLACK) {
                    int temp = Get_and_Flip(original, i, j);
                    cipher_image.setPixel(j, i, temp);
                } else {
                    cipher_image.setPixel(j, i, original.getPixel(j, i));
                }
            }
        }
        return cipher_image;
    }

    // Convert a regular sized image into an image doubled in size.
    // Every original pixel will be converted into a 2x2 square as follows:
    // 1. If the original pixel was black, the 2x2 sqaure will look like:
    //
    //                     |X| |
    //                     -----
    //                     | |X|
    //
    // 2. If the original pixel was white, the 2x2 square will look like:
    //
    //                     | |X|
    //                     -----
    //                     |X| |
    //
    public static Bitmap Magnify(Bitmap img){

        Bitmap magnified_image =  Bitmap.createBitmap(
                img.getWidth()*2, img.getHeight()*2, Bitmap.Config.RGB_565);

        for(int i = 0; i < img.getHeight(); i++){
            for(int j = 0; j < img.getWidth(); j++){
                if(img.getPixel(j, i) == BLACK){
                    //
                    //                     |X| |
                    //                     -----
                    //                     | |X|
                    //
                    magnified_image.setPixel(j*2, i*2, BLACK);
                    magnified_image.setPixel(j*2+1, i*2, WHITE);
                    magnified_image.setPixel(j*2, i*2+1, WHITE);
                    magnified_image.setPixel(j*2+1, i*2+1, BLACK);

                }
                else{
                    //
                    //                     | |X|
                    //                     -----
                    //                     |X| |
                    //
                    magnified_image.setPixel(j*2, i*2, WHITE);
                    magnified_image.setPixel(j*2+1, i*2, BLACK);
                    magnified_image.setPixel(j*2, i*2+1, BLACK);
                    magnified_image.setPixel(j*2+1, i*2+1, WHITE);
                }
            }
        }
        return magnified_image;
    }

//    // Map every block of 4 pixels to a single pixel on the scaled image
//    public static BufferedImage Shrink(BufferedImage img) {
//        BufferedImage shrunk_image = new BufferedImage (
//                img.getWidth()/2, img.getHeight()/2, BufferedImage.TYPE_BYTE_BINARY);
//
//        for(int i = 0; i < img.getHeight(); i += 2) {
//            for (int j = 0; j < img.getWidth(); j += 2) {
//                if (img.getRGB(j, i) == BLACK) {
//                    shrunk_image.setRGB(j/2,  i/2,  BLACK);
//                }
//                else {
//                    shrunk_image.setRGB(j/2, i/2, WHITE);
//                }
//            }
//        }
//
//        return shrunk_image;
//    }

    // Get the color of a particular bit and return the inverse of that color
    public static int Get_and_Flip(Bitmap img, int i, int j) {

        int initial = img.getPixel(j, i);

        if (initial == BLACK) {
            return WHITE;
        } else {
            return BLACK;
        }
    }

    // Combine the two decrypted images together and return the result as output
    public static Bitmap Decrypt(Bitmap image1, Bitmap image2) {

        // Ensure images are the same size
        if (image1.getHeight() != image2.getHeight() || image1.getWidth() != image2.getWidth()) {
            System.out.println("The size's of your selected images are mismatched");
            return null;
        }

        //Create a new buffered image to hold the decryption
        Bitmap output = Bitmap.createBitmap(
                image1.getWidth(), image1.getHeight(), Bitmap.Config.RGB_565);

        for (int i = 0; i < image1.getHeight(); i += 2) {
            for (int j = 0; j < image1.getWidth(); j += 2) {
                if (image1.getPixel(j, i) == BLACK && image2.getPixel(j + 1, i) == BLACK) {
                    output.setPixel(j, i, BLACK);
                    output.setPixel(j + 1, i, BLACK);
                    output.setPixel(j, i + 1, BLACK);
                    output.setPixel(j + 1, i + 1, BLACK);
                } else if (image1.getPixel(j, i) == WHITE && image2.getPixel(j + 1, i) == WHITE) {
                    output.setPixel(j, i, BLACK);
                    output.setPixel(j + 1, i, BLACK);
                    output.setPixel(j, i + 1, BLACK);
                    output.setPixel(j + 1, i + 1, BLACK);
                } else {
                    output.setPixel(j, i, WHITE);
                    output.setPixel(j + 1, i, WHITE);
                    output.setPixel(j, i + 1, WHITE);
                    output.setPixel(j + 1, i + 1, WHITE);
                }

            }
        }
        return output;
    }

    //    // Scale the encrypted images by a constant number of pixels
//    static BufferedImage make_print_friendly(BufferedImage img) {
//
//        int scale = 20; // 20 works well for inkjet printers, dependent on dpi
//
//        BufferedImage print_image = new BufferedImage(
//                img.getWidth()*scale, img.getHeight()*scale, BufferedImage.TYPE_BYTE_BINARY);
//
//        // For each pixel in the original image:
//        for(int i = 0; i < img.getHeight(); i++){
//            for(int j = 0; j < img.getWidth(); j++){
//
//                // If the pixel is black:
//                if(img.getRGB(j, i) == BLACK){
//
//                    // make a square of size 'scale' that is black on the output image
//                    for( int x = 0; x < scale; x++) {
//                        for( int y = 0; y < scale; y++){
//                            print_image.setRGB(j*scale+y,i*scale+x, BLACK);
//                        }
//                    }
//                }
//
//                // If the pixel is white:
//                else{
//
//                    // make a square of size 'scale' that is white on the output image
//                    for( int x = 0; x < scale; x++) {
//                        for( int y = 0; y < scale; y++){
//                            print_image.setRGB(j*scale+y,i*scale+x, WHITE);
//                        }
//                    }
//                }
//            }
//        }
//        return print_image;
//    }
    public static Bitmap Create_Key(Bitmap original) {
        // Create image key
        Bitmap key_image = Bitmap.createBitmap(
                original.getWidth(), original.getHeight(),
                Bitmap.Config.RGB_565);

        // Generate a random key
        Random rand = new Random();
        try {
            SecureRandom secureRandomGenerator = SecureRandom.getInstance("SHA1PRNG");

            for (int i = 0; i < key_image.getHeight(); i++) {
                for (int j = 0; j < key_image.getWidth(); j++) {

                    int result = secureRandomGenerator.nextInt(100);
                    if (result < 50) {
                        key_image.setPixel(j, i, WHITE);
                    } else {
                        key_image.setPixel(j, i, BLACK);
                    }
                }
            }
        } catch (NoSuchAlgorithmException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        return key_image;
    }
}

