function main() returns integer {
	boolean vector a[4] = [true, true, false, false];
	boolean vector b[4] = [true, false, true, false];

	boolean vector c[4];
	c = a or b;
	print(c + "\n");
	c = a xor b;
	print(c + "\n");
	c = a and b;
	print(c + "\n");
	
	return 0;
}
