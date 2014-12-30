package com.dylan.dnode;

import com.dylan.symbolTable.Scope;

public class ReturnNode implements DNode{
	private DNode expression = null;

	public ReturnNode() {
		
	}
	
	public ReturnNode(DNode expr) {
		this.expression = expr;
	}

	@Override  
	public DValue evaluate(Scope currentScope) {
		if (this.expression != null) {
			return this.expression.evaluate(currentScope);
		}
		else {
			return new DValue();
		}
	}
}
