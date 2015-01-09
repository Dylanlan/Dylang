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
	private int forType;
	
	private String varName = null;
	private DNode vector = null;
	
	public ForNode(List<DNode> declarations, DNode condition, List<DNode> assignments, DNode block) {
		this.declarations = declarations;
		this.assignments = assignments;
		
		this.condition = condition;
		this.block = block;
		this.forType = 0;
	}
	
	public ForNode(String varName, DNode vector, DNode block) {
		this.varName = varName;
		this.vector = vector;
		this.block = block;
		this.forType = 1;
	}

	@Override  
	public DValue evaluate(Scope currentScope) {
		
		
		Scope forScope = new Scope("for scope", currentScope);
		
		if (this.forType == 0) {
			if (this.declarations != null) {
				for (DNode decl : this.declarations) {
					decl.evaluate(forScope);
				}
			}
			
			if (this.condition != null) {
				boolean passed = Helper.passedCondition(this.condition.evaluate(forScope));
				while (passed) {
					this.block.evaluate(forScope);
					
					if (this.assignments != null) {
						for (DNode assign : this.assignments) {
							assign.evaluate(forScope);
						}
					}
					
					passed = Helper.passedCondition(this.condition.evaluate(forScope));
				}
			}
		}
		else if (this.forType == 1) {
			
			DValue vector = this.vector.evaluate(forScope);
			DeclarationNode decl = new DeclarationNode(this.varName, new EmptyNode());
			decl.evaluate(forScope);
			for (DValue node : vector.vectorResult) {
				AssignmentNode assign = new AssignmentNode(this.varName, new AtomNode(node));
				assign.evaluate(forScope);
				this.block.evaluate(forScope);
			}
			
		}
		
		return new DValue();
	}
}
