clear all;
clc;
close all;
img = imread('C:\Users\Aryan\Desktop\logo-MNNIT-ConvertImage.png');
figure,imshow(img);
[height,width] =  size(img);
k = 4;
n = 6;
cnt = 1;
level = 1;
tmp = zeros(k,int64(height*width/k));
for i = 1:height
  for j = 1:width
    tmp(cnt,level) = img(i,j);
    cnt = cnt + 1;
    if(cnt > k)
       cnt = 1;
       level = level + 1;
    end 
  end 
end 
%size(tmp);
shares = zeros(height,int64(width/k),n);
cnt = 1;
h = 1;
w = 1;
for i = 1:height*width/k
    for j = 1:n
        shares(h,w,j) = mod(polyval(tmp(:,i),j),251);
    end 
 w = w+1;
 if(w>width/k)
    w = 1;
    h = h+1;
 end 
end 

figure,imshow(shares(:,:,1),[0 255])
figure,imshow(shares(:,:,2),[0 255])
figure,imshow(shares(:,:,3),[0 255])
figure,imshow(shares(:,:,4),[0 255])
figure,imshow(shares(:,:,5),[0 255])
figure,imshow(shares(:,:,6),[0 255])

clc
cnt=0;
x=0;
xx = sym('x');
h = 1;
w = 1;
recimg = zeros(256,256);
for i=1:256
    for j=1:64
         temp1=shares(i,j,1);
         temp2=shares(i,j,2);
         temp3=shares(i,j,3);
         temp4=shares(i,j,4);
         fi=[1 2 3 4];
         fo=[temp1 temp2 temp3 temp4];
         Poly = test(fi,fo,xx);
         vec = sym2poly(Poly);
         for k = 0:3
            recimg(h,w+k) = mod(vec(k+1),251);
         end 
          w = w+ 4;
          if (w >= 256) 
              w = 1;
              h = h+1;
          end 
    end 
end 
figure
imshow(recimg,[0 255]);
figure
imshow(img,[0 255]);