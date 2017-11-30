

%Clear Memory & Command Window
clc;
clear all;
close all;

%Read Input Secret Image
inImg = imread('cameraman.jpg');
%Convert image to Binary
inImg = im2bw(inImg,0.5);
[r,c]=size(inImg);
disp (r);
disp(c);
%Show Binary Secret Image
imwrite(inImg,'secret.bmp');
figure;imshow(inImg);title('Secret Image');
l=1;
k=5;
n=6;
%Visual Cryptography
[share1, share2, share3, share4, share5, share6] = VisCrypt(inImg,l,k,n);

%Outputs
figure;imshow(share1);title('Share 1');
figure;imshow(share2);title('Share 2');
figure;imshow(share3);title('Share 3');
figure;imshow(share4);title('Share 4');
figure;imshow(share5);title('Share 5');
figure;imshow(share6);title('Share 6');

%Decryptography
[recovered12]=DeCrypt(share1, share2);
[recovered21]=DeCrypt(share2, share1);
[recovered63]=DeCrypt(share6, share3);
[recovered34]=DeCrypt(share3, share4);

[recovered123]=DeCrypt(share1, share2, share3);
[recovered321]=DeCrypt(share3, share2, share1);
[recovered416]=DeCrypt(share4, share1, share6);
imwrite(recovered123,'Recovered123.bmp');
imwrite(recovered321,'Recovered321.bmp');
imwrite(recovered416,'Recovered416.bmp');

[recovered6123]=DeCrypt(share6, share1, share2, share3);
imwrite(recovered6123,'Recovered6123.bmp');

[recovered46123]=DeCrypt(share4,share6, share1, share2, share3);

imwrite(recovered416,'aa3.bmp');


[recovered461235]=DeCrypt(share4,share6, share1, share2, share3,share5);
[recovered123456]=DeCrypt(share1, share2, share3, share4,share5,share6);
[recovered654321]=DeCrypt(share6, share5, share4, share3,share2,share1);
imwrite(recovered461235,'Recovered461235.bmp');
imwrite(recovered123456,'Recovered123456.bmp');
imwrite(recovered654321,'Recovered654321.bmp');

%figure;imshow(recovered2);title('Recovered Image2');
%figure;imshow(recovered3);title('Recovered Image3');
%figure;imshow(recovered4);title('Recovered Image4');
%figure;imshow(recovered5);title('Recovered Image5');
%figure;imshow(recovered6);title('Recovered Image6');

