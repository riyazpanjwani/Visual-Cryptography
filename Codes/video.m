workingDir = pwd;
mkdir(workingDir)
mkdir(workingDir,'images')
shuttleVideo = VideoReader('shuttle.avi');

ii = 1;

while hasFrame(shuttleVideo)
   img = readFrame(shuttleVideo);
   filename = [sprintf('%03d',ii) '.jpg'];
   fullname = fullfile(workingDir,'images',filename);
   imwrite(img,fullname)    % Write out to a JPEG file (img1.jpg, img2.jpg, etc.)
   ii = ii+1;
end

imageNames = dir(fullfile(workingDir,'images','*.jpg'));
imageNames = {imageNames.name}';


images = zeros(288,512,length(imageNames));

for i = 1:length(imageNames)
    images(:,:,i) = Halftoning(rgb2gray(imread(strcat('images/',char(imageNames(i))))));
end

wm = imread('cameraman.tif');
wm = im2bw(wm);

vi_rsz = imresize(images(:,:,1),[256 256]);
vi_rsz = im2bw(vi_rsz);

vi = zeros(256,512);

for i = 1:256
    for j = 1:2:511
        if((vi_rsz(i,floor((j+1)/2)) == 1 && wm(i,floor((j+1)/2)) == 0) )
            vi(i,j) = 1;
            vi(i,j+1) = 0;
        elseif(vi_rsz(i,floor((j+1)/2)) == 0 && wm(i,floor((j+1)/2)) == 1)
            vi(i,j) = 1;
            vi(i,j+1) = 0;
        else 
            vi(i,j) = 0;
            vi(i,j+1) = 1;
        end  
    end
end 


se = zeros(256,512);

for i = 1:256
    for j = 1:2:511
        if(vi_rsz(i,floor((j+1)/2)) == 1)
            se(i,j) = 1;
            se(i,j+1) = 0;
        else 
            se(i,j) = 0;
            se(i,j+1) = 1;
        end 
    end
end 

rec = zeros(256,256);

for i = 1:256
    for j = 1:2:511
        if(se(i,j) == vi(i,j) && se(i,j+1) == vi(i,j+1))
            rec(i,(j+1)/2) = 0;
        else
            rec(i,floor((j+1)/2+1)) = 1;
        end
    end 
end 