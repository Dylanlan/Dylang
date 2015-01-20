function main() returns integer {
	print(['I', 'n', 't', 'e', 'g', 'e', 'r', 's'] + "\n");
	print([1,2,3]+[1,2,3] + "\n");
	print([1,2,3]-[1,2,3] + "\n");
	print([1,2,3]*[1,2,3] + "\n");
	print([1,2,3]/[1,2,3] + "\n");
	print([1,2,3]%[1,2,3] + "\n");
	print([1,2,3]^[1,2,3] + "\n");
	print([1,2,3]<[1,3,2] + "\n");
	print([1,2,3]<=[1,3,2] + "\n");
	print([1,2,3]>[1,3,2] + "\n");
	print([1,2,3]>=[1,3,2] + "\n");
	print([1,1]==[1,2] + "\n");
	print([1,1]!=[1,2] + "\n");
	print([1,2]==[1,2] + "\n");
	print([1,2]!=[1,2] + "\n");
	print(-[1,2] + "\n");
	
	print("Reals\n");
	print([1.1,2.2]+[1.1,2.2] + "\n");
	print([1.1,2.2]-[1.1,2.2] + "\n");
	print([1.1,2.2]*[1.1,2.2] + "\n");
	print([1.1,2.2]/[1.1,2.2] + "\n");
	print([1.1,2.2]%[1.1,2.2] + "\n");
	print([1.1,2.2]^[1.1,2.2] + "\n");
	print([1.1,2.2,3.3]<[1.1,3.3,2.2] + "\n");
	print([1.1,2.2,3.3]<=[1.1,3.3,2.2] + "\n");
	print([1.1,2.2,3.3]>[1.1,3.3,2.2] + "\n");
	print([1.1,2.2,3.3]>=[1.1,3.3,2.2] + "\n");
	print([1.1,2.1]==[1.1,2.2] + "\n");
	print([1.1,2.1]!=[1.1,2.2] + "\n");
	print([1.1,2.2]==[1.1,2.2] + "\n");
	print([1.1,2.2]!=[1.1,2.2] + "\n");
	print(-[1.1,2.2] + "\n");
	
	print("Chars\n");
	print(['a','b','c']<['a','c','b'] + "\n");
	print(['a','b','c']<=['a','c','b'] + "\n");
	print(['a','b','c']>['a','c','b'] + "\n");
	print(['a','b','c']>=['a','c','b'] + "\n");
	print(['a','c']==['a','b'] + "\n");
	print(['a','c']!=['a','b'] + "\n");
	print(['a','b']==['a','b'] + "\n");
	print(['a','b']!=['a','b'] + "\n");
	
	
	print(['B', 'o', 'o', 'l', 's'] + "\n");
	print([true,true,false,false]or[true,false,true,false] + "\n");
	print([true,true,false,false]and[true,false,true,false] + "\n");
	print([true,true,false,false]xor[true,false,true,false] + "\n");
	print([true,false]==[true,true] + "\n");
	print([true,false]!=[true,true] + "\n");
	print([true,true]==[true,true] + "\n");
	print([true,true]!=[true,true] + "\n");
	print(not [true, false] + "\n");
	
	print([2]**[3] + "\n");
	print([1]||[1] + "\n");

	print([2.1]**[2.1] + "\n");
	print([1.1]||[1.1] + "\n");

	print(['a']||['a'] + "\n");

	print([true]||[true] + "\n");

	print([3, 2][1] + "\n");
	print([3.3, 2.2][1] + "\n");
	print(['a', 'b'][1] + "\n");
	print([true, false][1] + "\n");
	print("hello"[2] + "\n");
	print((2..10)[4] + "\n");
	
	return 0;
}
