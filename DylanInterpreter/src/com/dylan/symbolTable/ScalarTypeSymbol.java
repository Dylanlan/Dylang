package com.dylan.symbolTable;

public class ScalarTypeSymbol extends Symbol implements TypeSymbol {
	
	public ScalarTypeSymbol(String name) {
		super(name);
	}
	
	public boolean isScalar() {
		return true;
	}
}
