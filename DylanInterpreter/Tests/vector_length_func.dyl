function main() returns integer {
	integer vector x = [1, 2, 3];
	integer vector y = [1, 2, 3, 4, 5];
	integer z = [1, 2, 3, 4];
	integer a = [1, 2];
	print(length(x) + "\n");
	print(length(y) + "\n");
	print(length(z) + "\n");
	print(length(a) * length(a+a) + length(a) + "\n");
	return 0;
}
