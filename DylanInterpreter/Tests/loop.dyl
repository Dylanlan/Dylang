function main() returns integer {	
	integer x = 3;

	loop while x > 0 {
		x = x - 1;
		print(x + "\n");
	}

	print('\n');

	x = 13;
	loop {
		x = x - 1;
		print(x + "\n");
	} while x > 10
	
	x = 10;
	loop 3 {
		x = x - 1;
		print(x + "\n");
	}

	return 0;
}
