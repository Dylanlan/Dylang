package com.dylan.dnode;

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
	public DValue evaluate() {  
		DValue value = this.expression.evaluate();
		int argument = 0;
		if (this.arg != null) {
			DValue argValue = this.arg.evaluate();
			argument = argValue.intResult;
		}
		value.print(argument);  
		return new DValue();  
	}
}
