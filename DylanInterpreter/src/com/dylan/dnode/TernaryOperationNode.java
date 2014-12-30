package com.dylan.dnode;

import com.dylan.interpreter.Helper;
import com.dylan.symbolTable.Scope;

public class TernaryOperationNode implements DNode {
	private DNode condition;
	private DNode passed;
	private DNode failed;
	
	public TernaryOperationNode(DNode condition, DNode passed, DNode failed) {
		this.condition = condition;
		this.passed = passed;
		this.failed = failed;
	}
	
	@Override
	public DValue evaluate(Scope currentScope) {
		DValue eval = this.condition.evaluate(currentScope);
		if (Helper.passedCondition(eval)) {
			return this.passed.evaluate(currentScope);
		}
		else {
			return this.failed.evaluate(currentScope);
		}
	}

	@Override
	public String toString() {
		return "(" + this.condition + " ? " + this.passed + " : " + this.failed + ")";
	}
}
