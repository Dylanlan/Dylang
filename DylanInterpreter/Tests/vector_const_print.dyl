function main() returns integer {
	int y = 3;
	realr z = 4.4;
	bool a = true;
	char b = '\'';
	print([1, 2, 3 * 5, y + y] + "\n");
	print([1.1, 2.2, 3.3 * 3, z * 2] + "\n");
	print([true, false, z > y, y == z, a] + "\n");
	print(['a', 'b', 'c', b] + "\n");
	return 0;
}
