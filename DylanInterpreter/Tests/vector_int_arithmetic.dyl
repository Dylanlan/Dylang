function main() returns integer {
	integer vector a[2] = [1, 2] + [1, 2];
	integer vector b[2] = [3, 4] - [1, 2];
	integer vector c[2] = [5, 6] * [5, 6];
	integer vector d[2] = [10, 8] / [5, 4];
	
	print(a + "\n");
	print(b + "\n");
	print(c + "\n");
	print(d + "\n");
	
	return 0;
}
