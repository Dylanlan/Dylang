package com.dylan.symbolTable;

public class Symbol {
    public String name;
    public TypeSymbol type;
    
    public Symbol(String name) { this.name = name; }
    
    public Symbol(String name, TypeSymbol type) {
    	this.name = name;
    	this.type = type;
    }
    
    public String getName() { return name; }
    
    public TypeSymbol getType() { return type; }
}
