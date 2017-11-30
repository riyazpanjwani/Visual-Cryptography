clear all;
clc;

im1 = imread('circuit.tif');
im2 = imread('cameraman.tif');
im3 = imread('eight.tif');

im1 = imresize(im1,[128 128]);
im2 = imresize(im2,[128 128]);
im3 = imresize(im3,[128 128]);

%im1 = im2bw(im1);
%im2 = im2bw(im2);
%im3 = im2bw(im3);

im1 = im2bw(im1);
im2 = im2bw(im2);
im3 = im2bw(im3);

figure,imshow(im1);
figure,imshow(im2);
figure,imshow(im3);

[n,m] = size(im1);

share1 = zeros(128,128);
share2 = zeros(128,128);
share3 = zeros(128,128);

for i = 1:n
    for j = 1:floor(m/2)
        x = round(rand()*100);
        x = mod(x,2);
        if(x == 0)
            share1(i,j) = 0;
        else     
            share1(i,j) = 1;
        end 
        if(im1(i,j) == 0)
            share2(i,m-j+1) = share1(i,j);
        else 
            share2(i,m-j+1) =  1-share1(i,j);
        end 
        %share2(i,2*m-2*j) = 1 - share2(i,2*m-2*j-1);
        
        if(im2(i,m-j+1) == 0)
            share3(i,j) = share2(i,m-j+1);
        else 
            share3(i,j) = 1-share2(i,m-j+1);
        end 
            %share3(i,2*j) = 1 - share3(i,2*j-1);
        
         if(im3(i,j) == 0)
            share1(i,m-j+1) = share3(i,j);
        else 
            share1(i,m-j+1) = 1-share3(i,j);
        end 
        %share1(i,2*m-2*j) = 1 - share1(i,2*m-2*j-1);
        
        if(im1(i,m-j+1) == 0)
            share2(i,j) = share1(i,m-j+1);
        else 
            share2(i,j) = 1-share1(i,m-j+1);
        end 
        %share2(i,2*j-1) = 1-share2(i,2*j);
        
        %not clear
        if(im2(i,j) == 0)
            share3(i,m-j+1) = share2(i,j); 
        else 
            share3(i,m-j+1) = 1-share2(i,j);
        end 
        %share3(i,2*m-2*j) = 1 - share3(i,2*m-2*j-1);
    end 
end 

figure,imshow(share1);
figure,imshow(share2);
figure,imshow(share3);

rec1 = xor(share1,flip(share2,2));
rec2 = xor(share2,flip(share3,2));
rec3 = xor(share3,flip(share1,2));


figure,imshow(rec1);
figure,imshow(rec2);
figure,imshow(rec3);


InputImage=im3;
ReconstructedImage=rec3;

ReconstructedImage = imresize(ReconstructedImage,[128,128]);
n=size(InputImage);
M=n(1);
N=n(2);
MSE = sum(sum((InputImage-ReconstructedImage).^2))/(M*N);
PSNR = 10*log10(256*256/MSE);
fprintf('\nMSE: %7.2f ', MSE);
fprintf('\nPSNR: %9.7f dB\n\n', PSNR);
