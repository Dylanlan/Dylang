package com.dylan.dnode;

public class WhileNode implements DNode {
	private DNode condition = null;
	private DNode block = null;
	private int isDoWhile = 0;

	public WhileNode(DNode condition, DNode block, int doWhile) {
		this.condition = condition;
		this.block = block;
		this.isDoWhile = doWhile;
	}

	@Override  
	public DValue evaluate() {
		
		if (isDoWhile == 1) {
			this.block.evaluate();
		}
		
		boolean passed = this.passedCondition(this.condition.evaluate());
		
		while (passed) {
			this.block.evaluate();
			passed = this.passedCondition(this.condition.evaluate());
		}

		return new DValue();
	}
	
	public boolean passedCondition(DValue cond) {
		
		if (cond.isBool() && cond.boolResult) {
			return true;
		}
		else if (cond.isInt() && cond.intResult != 0) {
			return true;
		}
		else if (cond.isFloat() && cond.floatResult != 0) {
			return true;
		}
		else {
			return false;
		}
	}
}
