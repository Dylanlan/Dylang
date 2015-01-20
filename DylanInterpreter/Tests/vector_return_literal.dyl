function test() returns integer vector {
	return [1,2,3,4,5];
}


function main() returns integer {
	integer vector x[5];
	x = test();
	print(x + "\n");
	return 0;
}
