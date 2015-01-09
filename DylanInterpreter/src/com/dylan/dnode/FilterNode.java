package com.dylan.dnode;

import java.util.ArrayList;
import java.util.List;

import com.dylan.interpreter.Helper;
import com.dylan.symbolTable.Scope;

public class FilterNode implements DNode {
	private String varName = null;
	private DNode vector = null;
	private List<DNode> conditions = null;
	
	public FilterNode(String varName, DNode vector, List<DNode> conditions) {
		this.varName = varName;
		this.vector = vector;
		this.conditions = conditions;
	}

	@Override  
	public DValue evaluate(Scope currentScope) {
		List<DValue> passedValues = new ArrayList<DValue>();
		
		String scalarType = null;
		
		Scope newScope = new Scope("filter scope", currentScope);
		
		DValue vec = this.vector.evaluate(newScope);
		scalarType = vec.vectorType;
		
		DeclarationNode decl = new DeclarationNode(this.varName, new EmptyNode());
		decl.evaluate(newScope);
		if (this.vector != null) {
			for (DValue value : vec.vectorResult) {
				AssignmentNode assign = new AssignmentNode(this.varName, new AtomNode(value));
				assign.evaluate(newScope);
				
				boolean passed = true;
				for (DNode cond : this.conditions) {
					passed &= Helper.passedCondition(cond.evaluate(newScope));
				}
				
				if (passed) {
					passedValues.add(value);
				}
			}
		}
		
		return new DValue(passedValues, scalarType);
	}
}
