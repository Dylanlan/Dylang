package com.dylan.dnode;

import java.util.ArrayList;
import java.util.List;

public class ModNode implements DNode {
	private DNode lhs;  
	private DNode rhs;  
	  
	public ModNode(DNode lhs, DNode rhs) {  
		this.lhs = lhs;  
		this.rhs = rhs;  
	}  

	@Override  
	public DValue evaluate() {  

		DValue a = lhs.evaluate();  
		DValue b = rhs.evaluate();  
 
		if(a.intResult != null && b.intResult != null) {
			if (b.intResult == 0) {
				System.out.println("Error: mod by 0");
			}
			return new DValue(a.intResult % b.intResult);  
		}
		
		if(a.floatResult != null && b.floatResult != null) {
			if (b.floatResult == 0) {
				System.out.println("Error: mod by 0");
			}
			return new DValue(a.floatResult % b.floatResult);  
		}
		
		throw new RuntimeException("illegal expression: " + this);  
	}  

	@Override  
	public String toString() {  
		return String.format("(%s % %s)", lhs, rhs);  
	}
}
