package com.dylan.dnode;

import com.dylan.symbolTable.NonScalarTypeSymbol;
import com.dylan.symbolTable.ScalarTypeSymbol;
import com.dylan.symbolTable.Scope;
import com.dylan.symbolTable.TypeSymbol;
import com.dylan.symbolTable.VariableSymbol;

public class VariableNode implements DNode {
	private String name;
	private Scope scope;
	
	public VariableNode(String name, Scope scope) {
		this.name = name;
		this.scope = scope;
	}
	
	public DValue evaluate() {
		VariableSymbol vs = (VariableSymbol)scope.resolve(this.name);
		
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
