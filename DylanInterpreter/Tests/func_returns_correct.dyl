function test() returns integer {
	return 42;
}

function test2(integer x) returns integer {
	return x;
}

function test3(const boolean x) returns boolean {
	return x;
}

function test4(const character x) returns character {
	return x;
}

function add(integer x, integer y) returns integer {
	return x + y;
}

function main() returns integer {
	integer a = test();
	int b = test2(35);
	bool c = test3(true);
	char d = test4('a');
	int e = add(3, 4);
	
	print(a + "\n");
	print(b + "\n");
	print(c + "\n");
	print(d + "\n");
	print(e + "\n");

	return 0;
}
