function main() returns integer {
	integer vector a = [1, 2, 3];
	integer vector c = [4, 5, 6];
	integer vector b = [4, 5, 6, 7];
	
	integer vector d = a || b;
	
	print(d + "\n");
	
	print(a ** c + "\n");
	return 0;
}
