function max(var integer vector x) returns integer {
	integer maxNum = x[1];
	integer size = length(x);
	integer index = 1;
	loop while (index < size) {
		if (x[index] > maxNum) {
			maxNum = x[index];
		}
		index = index + 1;
	
	}

	return maxNum;
}

function min(var integer vector x) returns integer {
	integer minNum = x[1];
	integer size = length(x);
	integer index = 1;
	loop while (index < size) {
		if (x[index] < minNum) {
			minNum = x[index];
		}
		index = index + 1;
	
	}

	return minNum;
}

function main() returns integer {
	integer vector x = [10, 5, 8, 9, 2, 45, 7, 13];
	integer vector y = [42, 94, 2, 9, 100, 3];
	
	print("Max of:\n" + x + " is: " + max(x) + "\n");
	print("Min of:\n" + x + " is: " + min(x) + "\n");
	

	return 0;		
}
