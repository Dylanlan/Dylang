package com.dylan.dnode;

import java.util.ArrayList;
import java.util.List;

public class MultiplyNode implements DNode {
	private DNode lhs;  
	private DNode rhs;  
	  
	public MultiplyNode(DNode lhs, DNode rhs) {  
		this.lhs = lhs;  
		this.rhs = rhs;  
	}  

	@Override  
	public DValue evaluate() {  

		DValue a = lhs.evaluate();  
		DValue b = rhs.evaluate();  
 
		if(a.intResult != null && b.intResult != null) {  
			return new DValue(a.intResult * b.intResult);  
		}
		
		if(a.floatResult != null && b.floatResult != null) {  
			return new DValue(a.floatResult * b.floatResult);  
		}
		
		if (a.vectorType.equals("int") && b.vectorType.equals("int")) {
			List<DValue> resultVector = new ArrayList<DValue>();
			int maxSize = Math.max(a.vectorResult.size(), b.vectorResult.size());
			for (int i = 0; i < maxSize; i++) {
				int ai = 1;
				if (i < a.vectorResult.size()) {
					ai = a.vectorResult.get(i).intResult;
				}
				int bi = 1;
				if (i < b.vectorResult.size()) {
					bi = b.vectorResult.get(i).intResult;
				}
				resultVector.add(new DValue(new Integer(ai * bi)));
			}
			
			DValue result = new DValue(resultVector, a.vectorType);
			return result;
		}

		throw new RuntimeException("illegal expression: " + this);  
	}  

	@Override  
	public String toString() {  
		return String.format("(%s * %s)", lhs, rhs);  
	}
}
