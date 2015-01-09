package com.dylan.dnode;

import java.util.ArrayList;
import java.util.List;

import com.dylan.interpreter.Helper;
import com.dylan.symbolTable.Scope;

public class GeneratorNode implements DNode {
	private String varName = null;
	private DNode vector = null;
	private DNode eval = null;
	
	public GeneratorNode(String varName, DNode vector, DNode eval) {
		this.varName = varName;
		this.vector = vector;
		this.eval = eval;
	}

	@Override  
	public DValue evaluate(Scope currentScope) {
		List<DValue> vecResult = new ArrayList<DValue>();
		String vecType = null;
		
		Scope newScope = new Scope("generator scope", currentScope);
		
		DeclarationNode decl = new DeclarationNode(this.varName, new EmptyNode());
		decl.evaluate(newScope);
		
		if (this.vector != null) {
			DValue vec = this.vector.evaluate(newScope);
			for (DValue value : vec.vectorResult) {
				AssignmentNode assign = new AssignmentNode(this.varName, new AtomNode(value));
				assign.evaluate(newScope);
				DValue toAdd = this.eval.evaluate(newScope);
				vecResult.add(toAdd);
				vecType = toAdd.getType();
			}
		}
		
		return new DValue(vecResult, vecType);
	}
}
