package com.dylan.dnode;

import java.util.ArrayList;
import java.util.List;

public class NotNode implements DNode {
	private DNode expr;  
	  
	public NotNode(DNode expr) {  
		this.expr = expr;   
	}  

	@Override  
	public DValue evaluate() {  

		DValue a = expr.evaluate();  
 
		if (a.boolResult != null) {
			return new DValue(!a.boolResult);
		}

		throw new RuntimeException("illegal expression: " + this);  
	}  

	@Override  
	public String toString() {  
		return String.format("(not %s)", expr);  
	}
}
