function main() returns integer {
	integer vector h = [1, 2, 3, 4, 5];
	real vector i = [1.1, 2.2, 3.3, 4.4, 5.5];
	boolean vector j = [true, false, true, false, false];
	character vector k = ['j', 'e', 'l', 'l', 'o'];
	
	h[2..4] = [6, 7, 8];
	print(h + "\n");
	i[1..2] = [7.7, 9.9];
	print(i + "\n");
	j[4..5] = [true, true];
	print(j + "\n");
	k[1..1] = ['h'];
	print(k + "\n");
	
	return 0;
}
