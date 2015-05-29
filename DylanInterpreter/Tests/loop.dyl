function main() returns integer {	
	integer x = 3;

	while x > 0 {
		x = x - 1;
		print(x + "\n");
	}

	print('\n');

	x = 13;
	do {
		x = x - 1;
		print(x + "\n");
	} while x > 10
	
	print('\n');
	
	x = 10;
	loop 3 {
		x = x - 1;
		print(x + "\n");
	}

	return 0;
}
