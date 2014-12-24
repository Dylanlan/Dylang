package com.dylan.symbolTable;

import java.util.HashMap;
import java.util.Map;

public class Scope {
	String name;
	public int scopeNum;
	public Map<String, Symbol> symbols = new HashMap<String, Symbol>();
	Scope enclosingScope;

	public Scope(String name, Scope enclosingScope) {
		this.enclosingScope = enclosingScope;
		this.name = name;
		this.scopeNum = 1;
	}

	public Scope(String name, Scope enclosingScope, int scopeNum) {
		this.enclosingScope = enclosingScope;
		this.name = name;
		this.scopeNum = scopeNum;
	}

	public String getScopeName() { return this.name; }

	public Scope getEnclosingScope() { return enclosingScope; }

	public void define(Symbol sym) { symbols.put(sym.name, sym); }

	public Symbol resolve(String name) {
		Symbol sym = symbols.get(name);
		Scope currentScope = this;
		while (sym == null && currentScope.getEnclosingScope() != null) {
			currentScope = currentScope.getEnclosingScope();
			sym = currentScope.resolve(name);
		}

		return sym; 
	}
}