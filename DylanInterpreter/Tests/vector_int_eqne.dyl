function main() returns integer {
	integer vector a = [1,2,3,4];
	integer vector b = [1,2,3,4];
	integer vector c = [4,3,2,1];

	print(a == b + "\n");
	
	print(a != b + "\n");
	
	print(a == c + "\n");
	
	print(a != c + "\n");
	
	return 0;
}
