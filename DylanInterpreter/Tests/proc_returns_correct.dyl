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

function increment(var integer x) {
	x = x + 1;
	return;
}

procedure main() returns integer {
	int a = test();
	int b = test2(35);
	bool c = test3(true);
	char d = test4('a');
	int e = add(3, 4);
	int x = 10;
	increment(x);
	
	print(a + "\n");
	print(b + "\n");
	print(c + "\n");
	print(d + "\n");
	print(e + "\n");
	print(x + "\n");

	return 0;
}
