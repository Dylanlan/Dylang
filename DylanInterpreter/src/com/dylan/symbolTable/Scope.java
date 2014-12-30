package com.dylan.symbolTable;

import java.util.HashMap;
import java.util.Map;

public class Scope {
	String name;
	public Map<String, Symbol> symbols = new HashMap<String, Symbol>();
	Scope enclosingScope;

	public Scope(String name, Scope enclosingScope) {
		this.enclosingScope = enclosingScope;
		this.name = name;
	}

	public String getScopeName() { return this.name; }

	public Scope getEnclosingScope() { return enclosingScope; }

	public void define(Symbol sym) { symbols.put(sym.name, sym); }
	
	public Scope getGlobalScope() {
		Scope global = this;
		while(global.getEnclosingScope() != null) {
			global = global.getEnclosingScope();
		}
		return global;
	}

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