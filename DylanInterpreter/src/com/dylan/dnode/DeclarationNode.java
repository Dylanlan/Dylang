package com.dylan.dnode;

import com.dylan.symbolTable.NonScalarTypeSymbol;
import com.dylan.symbolTable.ScalarTypeSymbol;
import com.dylan.symbolTable.Scope;
import com.dylan.symbolTable.TypeSymbol;
import com.dylan.symbolTable.VariableSymbol;

public class DeclarationNode implements DNode {
	private String name;
	private DNode value;
	
	public DeclarationNode(String name, DNode value) {
		this.name = name;
		this.value = value;
	}
	
	@Override
	public DValue evaluate(Scope currentScope) {
		DValue result = this.value.evaluate(currentScope);
		VariableSymbol vs = (VariableSymbol)currentScope.symbols.get(this.name);
		if (vs != null) {
			throw new RuntimeException("Variable " + this.name + " already exists in this scope");
		}
		else {
			TypeSymbol type;
			if (result.isScalar()) {
				type = new ScalarTypeSymbol(result.getType());
			}
			else if (result.isNull()) {
				type = new ScalarTypeSymbol("null");
			}
			else {
				throw new RuntimeException("Can't handle non-scalar declaration for type: " + result.getType());
			}
			vs = new VariableSymbol(this.name, type);
			vs.setValue(result);
			currentScope.define(vs);
		}
		
		return new DValue();
	}
}
