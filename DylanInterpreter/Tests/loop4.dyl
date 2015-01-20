procedure main() returns integer {
	integer x = 5;

	loop while x > 0 {
		x = x - 1;
		if (x == 2) {
			continue;
		}
		
		print(x + "\n");
	}


	return 0;
}
