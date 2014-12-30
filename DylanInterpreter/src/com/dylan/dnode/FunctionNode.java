package com.dylan.dnode;

import java.util.List;
import java.util.Map;

import com.dylan.symbolTable.FunctionSymbol;
import com.dylan.symbolTable.Scope;

public class FunctionNode implements DNode {
	private String name;
	private List<DNode> params;
	private Map<String, FunctionSymbol> functions;
	
	public FunctionNode(String name, List<DNode> params, Map<String, FunctionSymbol> functions) {
		this.name = name;
		this.params = params;
		this.functions = functions;
	}
	
	
	@Override
	public DValue evaluate(Scope currentScope) {
		FunctionSymbol symbol = this.functions.get(this.name);
		return symbol.invoke(this.params, this.functions, currentScope);
	}

	@Override
	public String toString() {
		String paramString = "";
		for (int i = 0; i < this.params.size(); i++) {
			paramString += this.params.get(i).toString();
			if (i < this.params.size() - 1) {
				paramString += ", ";
			}
		}
		return this.name + "(" + paramString + ")";
	}
}
