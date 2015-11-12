package com.dylan.dnode;

import com.dylan.symbolTable.NonScalarTypeSymbol;
import com.dylan.symbolTable.ScalarTypeSymbol;
import com.dylan.symbolTable.Scope;
import com.dylan.symbolTable.TypeSymbol;
import com.dylan.symbolTable.VariableSymbol;

public class DeclarationNode implements DNode {
	private String name;
	private TypeSymbol type;
	private DNode value;
	
	public DeclarationNode(String name, TypeSymbol type)
	{
		this.name = name;
		this.type = type;
	}
	
	public DeclarationNode(String name, DNode value) {
		this.name = name;
		this.value = value;
	}
	
	@Override
	public DValue evaluate(Scope currentScope) {
		VariableSymbol vs;
		if (this.value != null)
		{
			DValue result = this.value.evaluate(currentScope);
		
			vs = (VariableSymbol)currentScope.symbols.get(this.name);
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
				else if (result.isVector()) {
					ScalarTypeSymbol scalar = new ScalarTypeSymbol(result.vectorType);
					type = new NonScalarTypeSymbol(result.getType(), scalar);
				}
				else {
					throw new RuntimeException("Can't handle declaration for type: " + result.getType());
				}
				vs = new VariableSymbol(this.name, type);
				vs.setValue(result);
				currentScope.define(vs);
			}
		}
		else
		{
			vs = new VariableSymbol(this.name, this.type);
			vs.setValue(new DValue());
			currentScope.define(vs);
		}
		
		// TODO: should return the value of this declaration??
		return new DValue();
	}
}
