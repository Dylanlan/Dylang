package com.dylan.symbolTable;

public class NonScalarTypeSymbol extends Symbol implements TypeSymbol {
	public TypeSymbol scalarType;
	
	public NonScalarTypeSymbol(String name) {
		super(name);
		this.scalarType = new ScalarTypeSymbol("null");
	}
	
	public NonScalarTypeSymbol(String name, TypeSymbol scalarType) {
		super(name);
		this.scalarType = scalarType;
	}
	
	public String getName() {
		return this.name;
	}
	
	public TypeSymbol getScalarType() {
		return this.scalarType;
	}
	
	public String getScalarName() {
		return this.scalarType.getName();
	}
	
	public boolean isScalar() {
		return false;
	}
}
