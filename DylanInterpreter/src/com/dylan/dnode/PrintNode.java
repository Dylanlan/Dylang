package com.dylan.dnode;

import com.dylan.symbolTable.Scope;

public class PrintNode implements DNode{
	private DNode expression; 
	private DNode arg = null;

	public PrintNode(DNode expr) {
		this.expression = expr;
	}
	
	public PrintNode (DNode expr, DNode arg) {
		this.expression = expr;
		this.arg = arg;
	}

	@Override  
	public DValue evaluate(Scope currentScope) {
		DValue value = this.expression.evaluate(currentScope);
		int argument = 0;
		if (this.arg != null) {
			DValue argValue = this.arg.evaluate(currentScope);
			argument = argValue.intResult;
		}
		value.print(argument);  
		return new DValue();  
	}
}
