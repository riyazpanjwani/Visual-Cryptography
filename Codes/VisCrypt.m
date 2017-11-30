function [share1, share2, share3, share4, share5, share6] = VisCrypt(inImg,l,k,n)

Ln=ceil(n-k/2);
[r,c]=size(inImg);
share1 = zeros(204,204);

share1 = im2bw(share1,0.5);
share2 = zeros(r,c);
share3 = zeros(r,c);
share4 = zeros(r,c);
share5 = zeros(r,c);
share6 = zeros(r,c);

for i=1:r
    for j=1:c
        clear a;
        clear lq;
        clear l;
        clear num;
        clear b;
        b = round(rand(1, n));
     %  b=  mod( reshape(randperm(1*n), 1,n ), 2 );
        num=0;
        
        a=1;
        for k=1:n
             if b(k) == inImg(i,j)
                num=num+1;
             end
            if b(k) ~= inImg(i,j)
                lq(a) = k;
                a=a+1;
            end
        end  
        if num < Ln
           l = randperm(a-1,Ln-num);
          
            for k=1:size(l,2)
                b(lq(l(k)))=1-b(lq(l(k)));
            end
        end
        share1(i,j)=b(1);
        share2(i,j)=b(2);
        share3(i,j)=b(3);
        share4(i,j)=b(4);
        share5(i,j)=b(5);
        share6(i,j)=b(6);
    end
end

imwrite(share1,'share4.bmp');