package com.dylan.dnode;

import com.dylan.interpreter.Helper;

public class WhileNode implements DNode {
	public static final int WHILE = 0;
	public static final int DO_WHILE = 1;
	public static final int LOOP = 2;
	
	private DNode condition = null;
	private DNode block = null;
	private int loopType = 0;

	public WhileNode(DNode condition, DNode block, int doWhile) {
		this.condition = condition;
		this.block = block;
		this.loopType = doWhile;
	}

	@Override  
	public DValue evaluate() {
		
		if (this.loopType == LOOP) {
			int loopNum = this.condition.evaluate().intResult;
			while (loopNum > 0) {
				this.block.evaluate();
				loopNum--;
			}
			return new DValue();
		}
		
		if (this.loopType == DO_WHILE) {
			this.block.evaluate();
		}
		
		boolean passed = Helper.passedCondition(this.condition.evaluate());
		
		while (passed) {
			this.block.evaluate();
			passed = Helper.passedCondition(this.condition.evaluate());
		}

		return new DValue();
	}
}
