function main() returns integer {
	integer vector a = [1, 2, 3];
	integer vector b = [4, 5, 6];
	integer vector c;
	print(a + "\n");
	print(b + "\n");
	c = b;
	b = a;
	a = c;
	print(a + "\n");
	print(b + "\n");
	return 0;
}
