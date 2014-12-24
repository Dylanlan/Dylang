package com.dylan.dnode;

import java.util.ArrayList;
import java.util.List;

public class GreaterThanEqualNode implements DNode {
	private DNode lhs;  
	private DNode rhs;  
	  
	public GreaterThanEqualNode(DNode lhs, DNode rhs) {  
		this.lhs = lhs;  
		this.rhs = rhs;  
	}  

	@Override  
	public DValue evaluate() {  

		DValue a = lhs.evaluate();  
		DValue b = rhs.evaluate();  
 
		if(a.intResult != null && b.intResult != null) {  
			return new DValue(a.intResult.intValue() >= b.intResult.intValue());  
		}
		
		if(a.floatResult != null && b.floatResult != null) {  
			return new DValue(a.floatResult.floatValue() >= b.floatResult.floatValue());  
		}
		
		throw new RuntimeException("illegal expression: " + this);  
	}  

	@Override  
	public String toString() {  
		return String.format("(%s >= %s)", lhs, rhs);  
	}
}
