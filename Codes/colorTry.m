img = imread('001.jpg');

%img = imresize(img,0.2);

figure,imshow(img);

r = img(:,:,1);
g = img(:,:,2);
b = img(:,:,3);

a = zeros(size(img, 1), size(img, 2));

c = 255-r;
m = 255-g;
y = 255-b;

%figure,imshow(cat(3,c,a,a));
%figure,imshow(cat(3,a,m,a));
%figure,imshow(cat(3,a,a,y));

hc = Halftoning(r);
hm = Halftoning(m);
hy = Halftoning(y);

figure,imshow(cat(3,hc,a,a));
figure,imshow(cat(3,a,hm,a));
figure,imshow(cat(3,a,a,hy));

h = size(img,1);
w = size(img,2);

stack = zeros(2*h,2*w);

for i = 1:2*h
    for j = 1:2*w
        if(mod(j,2) == 1)
            stack(i,j) = 1;
        else    
            stack(i,j) = 0;
        end
    end 
end 

share1 = zeros(2*h,2*w);
share2 = zeros(2*h,2*w);
share3 = zeros(2*h,2*w);

for i = 1:2:2*h
    for j = 1:2:2*w
       if((hc(ceil(i/2),ceil(j/2)) == 1))
        share1(i,j) = 0;
        share1(i,j+1) = 1;
        share1(i+1,j+1) = 0;
        share1(i+1,j+1) =1;
       else 
        share1(i,j) = 1;
        share1(i,j+1) = 0;
        share1(i+1,j) = 1;
        share1(i+1,j+1) = 0;
       end 
       if((hm(ceil(i/2),ceil(j/2)) == 1))
        share2(i,j) = 0;
        share2(i,j+1) = 1;
        share2(i+1,j+1) = 0;
        share2(i+1,j+1) =1;
       else 
        share2(i,j) = 1;
        share2(i,j+1) = 0;
        share2(i+1,j) = 1;
        share2(i+1,j+1) = 0;
       end 
       if((hy(ceil(i/2),ceil(j/2)) == 1))
        share3(i,j) = 0;
        share3(i,j+1) = 1;
        share3(i+1,j+1) = 0;
        share3(i+1,j+1) =1;
       else 
        share3(i,j) = 1;
        share3(i,j+1) = 0;
        share3(i+1,j) = 1;
        share3(i+1,j+1) = 0;
       end 
    end 
end 

a = zeros(size(share1, 1), size(share1, 2));

figure,imshow(cat(3,share1,a,a));
figure,imshow(cat(3,a,share2,a));
figure,imshow(cat(3,a,a,share3));
