function main() returns integer {
	int y = 3;
	real z = 4.4;
	real x = 3.3;
	bool a = true;
	char b = '\'';
	print([1, 2, 3 * 5, y + y] + "\n");
	print([1.1, 2.2, 3.3 * 3.2, z * 2.1] + "\n");
	print([true, false, z > x, y == 2, a] + "\n");
	print(['a', 'b', 'c', b] + "\n");
	return 0;
}