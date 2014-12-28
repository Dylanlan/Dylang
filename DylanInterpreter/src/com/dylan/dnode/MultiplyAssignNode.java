package com.dylan.dnode;

import com.dylan.symbolTable.NonScalarTypeSymbol;
import com.dylan.symbolTable.ScalarTypeSymbol;
import com.dylan.symbolTable.Scope;
import com.dylan.symbolTable.TypeSymbol;
import com.dylan.symbolTable.VariableSymbol;

public class MultiplyAssignNode implements DNode {
	private String name;
	private DNode value;
	private Scope scope;
	
	public MultiplyAssignNode(String name, DNode value, Scope scope) {
		this.name = name;
		this.value = value;
		this.scope = scope;
	}
	
	public DValue evaluate() {
		VariableSymbol vs = (VariableSymbol)scope.resolve(this.name);
		
		if (vs != null) {
			BinaryOperationNode result = new BinaryOperationNode(new AtomNode(vs.getValue()), this.value, BinaryOperationNode.BON_MUL);
			vs.setValue(result.evaluate());
		}
		else {
			throw new RuntimeException("Assigning to undefined variable: " + this.name);
		}
		
		return new DValue();
	}
}
