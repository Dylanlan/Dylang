function main() returns integer {
	integer vector x = [1,2,3,4,5,6,7,8,9] by 4;
	boolean vector y = [true, false, true, true] by 2;
	character vector z = ['y', 'o', 'l', 'o'] by 3;
	character vector v  = "swag\n" by 2;
	real vector q = [1.1,2.2,3.3,4.4,5.5,6.6] by 5;  
	
	print(x + "\n");
	print(y + "\n");
	print(z + "\n");
	print(v + "\n");
	print(q + "\n");
	
	return 0;
}
