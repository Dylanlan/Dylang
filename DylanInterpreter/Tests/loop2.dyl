function main() returns integer {
	integer x = 3;

	loop while x > 3 {
		x = x - 1;
		print(x + "\n");
	}

	loop {
		x = x - 1;
		print(x + "\n");
	} while x > 3

	return 0;
}
