function main() returns integer {
	integer i = 1;
	integer p = 1;
	integer isPrime = 1;
	integer maxNum = 1000;

	while (p < maxNum) {
	    i=1;
	    isPrime=1;
	    p=p+1;
	
	    while (i < p/2) {
	        i=i+1;
	
	        if ((p/i) * i == p) {
	            isPrime = 0;
	            i = p;
	        }
	    }
	
	    if (isPrime == 1) {
	        print(p + "\n");
	    }
	}

	return 0;
}