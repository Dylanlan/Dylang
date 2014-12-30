package com.dylan.dnode;

import com.dylan.interpreter.Helper;
import com.dylan.symbolTable.Scope;

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
	public DValue evaluate(Scope currentScope) {  
		DValue cond = this.condition.evaluate(currentScope);
		
		if (Helper.passedCondition(cond)) {
			return this.ifBlock.evaluate(currentScope);
		}
		else if (this.elseBlock != null) {
			return this.elseBlock.evaluate(currentScope);
		}

		return new DValue();
	}
}
