function main() returns integer {
	integer vector a = [1, 2] + [1, 2];
	integer vector b = [3, 4] - [1, 2];
	integer vector c = [5, 6] * [5, 6];
	integer vector d = [10, 8] / [5, 4];
	
	print(a + "\n");
	print(b + "\n");
	print(c + "\n");
	print(d + "\n");
	
	return 0;
}
