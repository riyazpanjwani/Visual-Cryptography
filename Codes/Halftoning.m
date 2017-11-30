%Jarvis Halftoning
function outImg = Halftoning(inImg)
inImg = double(inImg);

[M,N] = size(inImg);
T = 127.5;
y = inImg;
error = 0;

y= [127.5*ones(M,2) y 127.5*ones(M,2) ; 127.5*ones(2,N+4)];
z = y;

for rows = 1:M  
  for cols = 3:N+2
    z(rows,cols) =255*(y(rows,cols)>=T);
    error = -z(rows,cols) + y(rows,cols);

    y(rows,cols+2) = 5/48 * error + y(rows,cols+2);
    y(rows,cols+1) = 7/48 * error + y(rows,cols+1);

    y(rows+1,cols+2) = 3/48 * error + y(rows+1,cols+2);
    y(rows+1,cols+1) = 5/48 * error + y(rows+1,cols+1);
    y(rows+1,cols+0) = 7/48 * error + y(rows+1,cols+0);
    y(rows+1,cols-1) = 5/48 * error + y(rows+1,cols-1);
    y(rows+1,cols-2) = 3/48 * error + y(rows+1,cols-2);
 
    y(rows+2,cols+2) = 1/48 * error + y(rows+2,cols+2);
    y(rows+2,cols+1) = 3/48 * error + y(rows+2,cols+1);
    y(rows+2,cols+0) = 5/48 * error + y(rows+2,cols+0);
    y(rows+2,cols-1) = 3/48 * error + y(rows+2,cols-1);
    y(rows+2,cols-2) = 1/48 * error + y(rows+2,cols-2);
  end
end

outImg = z(1:M,3:N+2);
outImg = im2bw(uint8(outImg));
