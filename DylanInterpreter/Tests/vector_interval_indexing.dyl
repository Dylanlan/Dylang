function main() returns integer {
	integer vector i = [1,2,3,4];
	character vector j = ['a', 'b', 'c', 'd'];
	real vector k = [1.1, 2.2, 3.3, 4.4];
	boolean vector l = [true, false, false, true];
	integer interval m = 1..10;
	
	print(i[2..3] + "\n");
	print(j[3..4] + "\n");
	print(k[1..2] + "\n");
	print(l[1..2] + "\n");
	print(m[3..6] + "\n");
	
	return 0;
}
