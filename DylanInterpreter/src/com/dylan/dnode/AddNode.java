package com.dylan.dnode;

public class AddNode implements DNode {
	private DNode lhs;  
	private DNode rhs;  
	  
	public AddNode(DNode lhs, DNode rhs) {  
		this.lhs = lhs;  
		this.rhs = rhs;  
	}  

	@Override  
	public DValue evaluate() {  

		DValue a = lhs.evaluate();  
		DValue b = rhs.evaluate();  
 
		if(a.floatResult != null && b.floatResult != null) {  
			return new DValue(a.floatResult + b.floatResult);  
		} 
		
		if(a.intResult != null && b.intResult != null) {  
			return new DValue(a.intResult + b.intResult);  
		}  

		//if(a.isString()) {  
		//	return new TLValue(a.asString() + "" + b.toString());  
		//}  
 
		//if(b.isString()) {  
		//	return new TLValue(a.toString() + "" + b.asString());  
		//}  

		throw new RuntimeException("illegal expression: " + this);  
	}  

	@Override  
	public String toString() {  
		return String.format("(%s + %s)", lhs, rhs);  
	}
}
