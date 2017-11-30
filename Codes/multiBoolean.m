im1 = imread('circuit.tif');
im2 = imread('cameraman.tif');
im3 = imread('eight.tif');

im1 = imresize(im1,[128 128]);
im2 = imresize(im2,[128 128]);
im3 = imresize(im3,[128 128]);

%im1 = im2bw(im1);
%im2 = im2bw(im2);
%im3 = im2bw(im3);

figure,imshow(im1);
figure,imshow(im2);
figure,imshow(im3);

s0 = randi([0 255],128,128);

h = 128;
w = 128;
n = 3;

b2 = zeros(128,128);
b3 = zeros(128,128);

for i=1:h
   for j=1:w
       b2(i,j) = bitxor(s0(i,j),im1(i,j));
       b3(i,j) = bitxor(s0(i,j),im2(i,j));
   end
end 

s1 = zeros(128,128);
s2 = zeros(128,128);
s3 = zeros(128,128);

for i=1:h
   for j=1:w
       s1(i,j) = bitxor(im1(i,j),s0(i,j));
       s2(i,j) = bitxor(im2(i,j),b2(i,j));
       s3(i,j) = bitxor(im3(i,j),b3(i,j)); 
   end
end 

figure,imshow(s0,[0 255]);
figure,imshow(s1,[0 255]);
figure,imshow(s2,[0 255]);
figure,imshow(s3,[0 255]);



