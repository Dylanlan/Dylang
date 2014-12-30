package com.dylan.dnode;

import com.dylan.symbolTable.NonScalarTypeSymbol;
import com.dylan.symbolTable.ScalarTypeSymbol;
import com.dylan.symbolTable.Scope;
import com.dylan.symbolTable.TypeSymbol;
import com.dylan.symbolTable.VariableSymbol;

public class PlusAssignNode implements DNode {
	private String name;
	private DNode value;
	
	public PlusAssignNode(String name, DNode value) {
		this.name = name;
		this.value = value;
	}
	
	@Override
	public DValue evaluate(Scope currentScope) {
		VariableSymbol vs = (VariableSymbol)currentScope.resolve(this.name);
		
		if (vs != null) {
			BinaryOperationNode result = new BinaryOperationNode(new AtomNode(vs.getValue()), this.value, BinaryOperationNode.BON_ADD);
			vs.setValue(result.evaluate(currentScope));
		}
		else {
			throw new RuntimeException("Assigning to undefined variable: " + this.name);
		}
		
		return new DValue();
	}
}
