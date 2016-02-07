function main() returns integer {
	integer vector i = 1..5;
	integer vector j = 5..10;
	integer vector k = 30..34;
	
	print(i + j + "\n");
	print(i - j + "\n");
	print(i * j + "\n");
	print(k / i + "\n");
	print(-i + "\n");
	print(+j + "\n");
	
	print((i == j) + "\n");
	print((i == i) + "\n");
	print((i != j) + "\n");
	print((j != j) + "\n");
	
	return 0;
}
