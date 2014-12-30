package com.dylan.dnode;

import com.dylan.interpreter.Helper;

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
	public DValue evaluate() {
		DValue eval = this.condition.evaluate();
		if (Helper.passedCondition(eval)) {
			return this.passed.evaluate();
		}
		else {
			return this.failed.evaluate();
		}
	}

	@Override
	public String toString() {
		return "(" + this.condition + " ? " + this.passed + " : " + this.failed + ")";
	}
}
