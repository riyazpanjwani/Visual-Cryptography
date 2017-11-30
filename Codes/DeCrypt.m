function [recovered] = DeCrypt(varargin)
t=nargin;
Lt=t/2;
[r,c]=size(varargin{1});
recovered = zeros(r,c);
for i=1:r
    for j=1:c
        num2=0;
        for k=1:t
            num2=num2+varargin{k}(i,j);
        end
         
        if num2 < Lt
            recovered(i,j)=0;
        end
        if num2 > Lt
            recovered(i,j)=1;
        end    
        if num2==Lt
             recovered(i,j)=round(rand(1));
        end
     end
end    