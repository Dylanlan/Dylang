package com.dylan.symbolTable;

public class TypeDefSymbol implements TypeSymbol {
	public String name;
	public TypeSymbol type;
	
	public TypeDefSymbol(TypeSymbol builtInType, String name) {
		this.name = name;
		this.type = builtInType;
	}
	
	public TypeSymbol getTypeSymbol() {
		return type;
	}
	
	public String getName() {
		return this.name;
	}
	
	public boolean isScalar() {
		return this.type.isScalar();
	}
}