function main() returns integer {
	integer vector x = [1,2,3,4];
	real vector y = [1.1,2.2,3.3,4.4];
	boolean vector z = [true];
	character vector q = ['f','o','o'];
	
	x[3] = 10;
	y[3] = 5.5;
	z[1] = false;
	q[2] = 'a';
	
	print(x + "\n");
	print(y + "\n");
	print(z + "\n");
	print(q + "\n");
	
	return 0;
}
