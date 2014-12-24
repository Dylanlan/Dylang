package com.dylan.dnode;

public class IfNode implements DNode {
	private DNode condition = null;
	private DNode ifBlock = null;
	private DNode elseBlock = null;

	public IfNode(DNode condition, DNode ifBlock) {
		this.condition = condition;
		this.ifBlock = ifBlock;
	}

	public IfNode(DNode condition, DNode ifBlock, DNode elseBlock) {
		this.condition = condition;
		this.ifBlock = ifBlock;
		this.elseBlock = elseBlock;
	}

	@Override  
	public DValue evaluate() {  
		DValue cond = this.condition.evaluate();
		
		if (this.passedCondition(cond)) {
			return this.ifBlock.evaluate();
		}
		else if (this.elseBlock != null) {
			return this.elseBlock.evaluate();
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
