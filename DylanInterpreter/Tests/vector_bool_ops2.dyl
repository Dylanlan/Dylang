function main() returns integer {
	boolean vector a[2] = [true, false];
	boolean vector b[2] = [false, true];
	
	boolean vector c[2];
	
	c = not a;
	print(c + "\n");
	
	c = not b;
	print(c + "\n");
	print((a != b) + "\n");
	print((b != b) + "\n");
	print((a == b) + "\n");
	print((b == b) + "\n");
	
	return 0;
}
