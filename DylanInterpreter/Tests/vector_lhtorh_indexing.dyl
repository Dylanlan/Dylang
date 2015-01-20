function main() returns integer {
	integer vector x = [1,2,3,4];
	real vector y = [1.1,2.2,3.3,4.4];
	boolean vector z = [true, false];
	character vector q = ['f','o','o'];
	
	x[3] = x[4];
	y[3] = y[2];
	z[1] = z[2];
	q[3] = q[1];
	
	print(x + "\n");
	print(y + "\n");
	print(z + "\n");
	print(q + "\n");
	
	return 0;
}
