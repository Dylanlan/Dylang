package com.dylan.dnode;

import com.dylan.symbolTable.NonScalarTypeSymbol;
import com.dylan.symbolTable.ScalarTypeSymbol;
import com.dylan.symbolTable.Scope;
import com.dylan.symbolTable.TypeSymbol;
import com.dylan.symbolTable.VariableSymbol;

public class DeclarationNode implements DNode {
	private String name;
	private DNode value;
	private Scope scope;
	
	public DeclarationNode(String name, DNode value, Scope scope) {
		this.name = name;
		this.value = value;
		this.scope = scope;
	}
	
	public DValue evaluate() {
		DValue result = this.value.evaluate();
		VariableSymbol vs = (VariableSymbol)this.scope.symbols.get(this.name);
		if (vs != null) {
			throw new RuntimeException("Variable " + this.name + " already exists in this scope");
		}
		else {
			TypeSymbol type;
			if (result.isScalar()) {
				type = new ScalarTypeSymbol(result.getType());
			}
			else {
				throw new RuntimeException("Can't handle non-scalar assignment yet");
			}
			vs = new VariableSymbol(this.name, type);
			vs.setValue(result);
			scope.define(vs);
		}
		
		return new DValue();
	}
}
