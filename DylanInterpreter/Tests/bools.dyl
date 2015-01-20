function main() returns integer {
	boolean a = 10 > 9;
	boolean b = ((5 <= 7) or (6 > 9)) and (4 > 5);
	bool c = ((true == false) or (false)) xor (true);
	bool d = ((3 != 2) and (3 == 3));
	boolean e = not true;
	boolean f = not (10 > 100);

	print(a + "\n");
	print(b + "\n");
	print(c + "\n");
	print(d + "\n");
	print(e + "\n");
	print(f + "\n");
	return 0;
}
