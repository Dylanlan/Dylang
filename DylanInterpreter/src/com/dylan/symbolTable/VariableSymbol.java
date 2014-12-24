package com.dylan.symbolTable;

import com.dylan.dnode.*;

public class VariableSymbol extends Symbol {
	boolean isconst = false;
	boolean isvar = false;
	public int scopeNum;
	public DValue value;
	
	public VariableSymbol(String name, TypeSymbol type) { 
		super(name, type); 
		isconst = false;
		isvar = false;
		scopeNum = 0;
	}
	
	public VariableSymbol(String name, TypeSymbol type, Symbol spec) {
		super(name, type);
		scopeNum = 0;
		if (spec != null && spec.getName().equals("const")) {
			isconst = true;
		}
		else if (spec != null && spec.getName().equals("var")) {
			isvar = true;
		}
	}
	
	public boolean isConst() { return this.isconst; }
	
	public boolean isVar() { return this.isvar; }
	
	public DValue getValue() { return this.value; }
	
	public void setValue(DValue result) { this.value = result; }
	
	public void setIndexedValue(DValue result, DValue index) {
		//TODO: remove this check, handle in a separate tree gramar
		if (value.vectorResult == null) {
			System.out.println("Error: Trying to assign value to index of non-indexable variable");
			System.exit(-1);
		}
		
		int i = index.intResult;
		
		value.vectorResult.set(i, result);
	}
}