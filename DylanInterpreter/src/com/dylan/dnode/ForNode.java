package com.dylan.dnode;

import java.util.List;

import org.antlr.runtime.tree.CommonTree;

import com.dylan.interpreter.Helper;
import com.dylan.symbolTable.Scope;

public class ForNode implements DNode {
	private List<DNode> declarations = null;
	private List<DNode> assignments = null;
	private DNode condition = null;
	private DNode block = null;
	
	public ForNode(List<DNode> declarations, DNode condition, List<DNode> assignments, DNode block) {
		this.declarations = declarations;
		this.assignments = assignments;
		
		this.condition = condition;
		this.block = block;
	}

	@Override  
	public DValue evaluate(Scope currentScope) {
		if (this.declarations != null) {
			for (DNode decl : this.declarations) {
				decl.evaluate(currentScope);
			}
		}
		
		if (this.condition != null) {
			boolean passed = Helper.passedCondition(this.condition.evaluate(currentScope));
			while (passed) {
				this.block.evaluate(currentScope);
				
				if (this.assignments != null) {
					for (DNode assign : this.assignments) {
						assign.evaluate(currentScope);
					}
				}
				
				passed = Helper.passedCondition(this.condition.evaluate(currentScope));
			}
		}
		
		return new DValue();
	}
}
