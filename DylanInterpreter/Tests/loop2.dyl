function main() returns integer {
	integer x = 3;

	while x > 3 {
		x = x - 1;
		print(x + "\n");
	}

	do {
		x = x - 1;
		print(x + "\n");
	} while x > 3

	return 0;
}
