clear all
clc
close all

IM=imread('Diagram1.bmp');
IM = im2bw(IM, 0.4);
G=imread('Overlapped12.bmp');
IM= imresize(IM,[256 256]);
G= imresize(G,[256 256]);
% N=rgb2gray(M);
[m n]=size(G);

Tp = 0;
Tn = 0;
Fp = 0;
Fn = 0;
for i=1:m 
 for j=1:n 
     
   if IM(i,j)==1 && G(i,j)==1 %True Positive
    Tp = Tp + 1;
    disp(Tp); 
   
   elseif IM(i,j)==0 && G(i,j)==0  %True Negative

      Tn = Tn + 1;
      disp(Tn);
      
   elseif IM(i,j)==1 && G(i,j)==0   %False Positive
      Fp = Fp + 1;
      disp(Fp);
      
   elseif IM(i,j)==0  && G(i,j)==1   %False Negative
       Fn = Fn + 1;
       disp(Fn);
   end
   end   
 end


precision =Tp/(Fp+Tp);
disp(precision);
recall=Tp/(Fn+Tp);
disp(recall);
specificity=Tn/(Fp+Tn);
disp(specificity);
FM=(2*recall*precision)/(recall+precision);
disp(FM);
BCR=0.5*(specificity+recall);
disp(BCR);
BER=100*(1-BCR);
disp(BER);
NRfn=Fn/(Fn+Tp);
NRfp=Fp/(Fp+Tn);
NRM=(NRfn+NRfp)/2;
disp(NRM);
%psnr

%origImg = double(IM);
%distImg = double(G);

%[M N]=size(origImg);

%N=N/3;
%error = origImg- distImg;
%MSE = sum(sum(error .* error)) / (M * N);

%if(MSE > 0)
 %   PSNR = 10*log(255*255/MSE) / log(10);
%else
 %   PSNR = 99;
%end
