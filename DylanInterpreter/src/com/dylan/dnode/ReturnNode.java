package com.dylan.dnode;

public class ReturnNode implements DNode{
	private DNode expression = null;

	public ReturnNode() {
		
	}
	
	public ReturnNode(DNode expr) {
		this.expression = expr;
	}

	@Override  
	public DValue evaluate() {
		if (this.expression != null) {
			return this.expression.evaluate();
		}
		else {
			return new DValue();
		}
	}
}
