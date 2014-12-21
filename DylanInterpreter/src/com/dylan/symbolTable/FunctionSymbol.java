package com.dylan.symbolTable;

import java.util.ArrayList;
import java.util.List;


public class FunctionSymbol extends Symbol {
	public ArrayList<Symbol> params = new ArrayList<Symbol>();
	
	public FunctionSymbol(String name, TypeSymbol type) {
		super(name, type);
	}
	
	public FunctionSymbol(String name, TypeSymbol type, List<Symbol> params) {
		super(name, type);
		if (params != null) {
			this.params = (ArrayList<Symbol>) params;
		}
	}
	
	public ArrayList<Symbol> getParamList() { return this.params; }
}
