function main() returns integer {
	integer interval i = 1..5;
	integer interval j = 5..10;
	
	print(i + j + "\n");
	print(i - j + "\n");
	print(i * j + "\n");
	print(j / i + "\n");
	print(i / j + "\n");
	print(-i + "\n");
	print(+j + "\n");
	
	print(i == j + "\n");
	print(i != j + "\n");
	
	return 0;
}
