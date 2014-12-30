package com.dylan.dnode;

import com.dylan.symbolTable.NonScalarTypeSymbol;
import com.dylan.symbolTable.ScalarTypeSymbol;
import com.dylan.symbolTable.Scope;
import com.dylan.symbolTable.TypeSymbol;
import com.dylan.symbolTable.VariableSymbol;

public class VariableNode implements DNode {
	private String name;
	
	public VariableNode(String name) {
		this.name = name;
	}
	
	public DValue evaluate(Scope currentScope) {
		VariableSymbol vs = (VariableSymbol)currentScope.resolve(this.name);
		
		if (vs != null) {
			return vs.getValue();
		}

		return new DValue();
	}
	
	@Override  
	public String toString() {  
		return String.format("(" + this.name + ")");  
	}
}
