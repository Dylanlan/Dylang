procedure test() returns integer interval {
	return 10..20;
}

function main() returns integer {
	integer interval x = test();
	print(x + "\n");

	return 0;
}
