package com.dylan.dnode;

import com.dylan.symbolTable.Scope;

public class EmptyNode implements DNode {
	public EmptyNode() {
		
	}
	
	@Override
	public DValue evaluate(Scope currentScope) {
		return new DValue();
	}
}
