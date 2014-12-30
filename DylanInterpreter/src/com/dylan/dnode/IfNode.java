package com.dylan.dnode;

import com.dylan.interpreter.Helper;

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
		
		if (Helper.passedCondition(cond)) {
			return this.ifBlock.evaluate();
		}
		else if (this.elseBlock != null) {
			return this.elseBlock.evaluate();
		}

		return new DValue();
	}
}
