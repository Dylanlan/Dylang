package com.dylan.dnode;

import java.util.ArrayList;
import java.util.List;

public class NegativeNode implements DNode {
	private DNode expr;  
	  
	public NegativeNode(DNode expr) {  
		this.expr = expr;   
	}  

	@Override  
	public DValue evaluate() {  

		DValue a = expr.evaluate();  
 
		if (a.intResult != null) {
			return new DValue(-a.intResult);
		}
		else if (a.floatResult != null) {
			return new DValue(-a.floatResult);
		}

		throw new RuntimeException("illegal expression: " + this);  
	}  

	@Override  
	public String toString() {  
		return String.format("(not %s)", expr);  
	}
}
