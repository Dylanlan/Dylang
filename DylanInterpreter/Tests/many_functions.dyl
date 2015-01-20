function test(integer x) returns integer {
	return x;
}

function test2(const integer x) returns integer {
	return 2 * (x + 3);
}

function test3(real x) returns integer {
	return as<integer>(x);
}

function test4(character x) returns character {
	return x;
}

function test5(boolean x) returns boolean {
	return not x;
}

function test6(integer x) returns integer { return x ^ 2; }

procedure main() returns integer {
	print(test(5+900) + "\n");
	print(test2(10 - 7) + "\n");
	print(test3(3.14) + "\n");
	print(test4('M') + "\n");
	print(test5(true) + "\n");
	print(test6(7) + "\n");
	
	return 0;
}
