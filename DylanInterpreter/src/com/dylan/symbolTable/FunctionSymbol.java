package com.dylan.symbolTable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.CommonTreeNodeStream;

import com.dylan.dnode.DNode;
import com.dylan.dnode.DValue;
import com.dylan.interpreter.Interpreter;


public class FunctionSymbol extends Symbol {
	private List<String> paramNames;
	private CommonTree code;

	//public FunctionSymbol(String name, TypeSymbol type, List<String> params)
	
	public FunctionSymbol(String name, TypeSymbol type, CommonTree params, CommonTree block) {
		super(name, type);
		this.code = block;
		this.paramNames = this.toList(params);
	}

	public DValue invoke(List<DNode> params, Map<String, FunctionSymbol> functions, Scope currentScope) {  

		if(params.size() != this.paramNames.size()) {  
			throw new RuntimeException("illegal function call: " + this.paramNames.size() +  
					" parameters expected for function `" + this.name + "`");  
		}  
		
		Scope functionScope = new Scope("functionScope", currentScope.getGlobalScope());
		// Assign all expression parameters to this function's identifiers 
		for(int i = 0; i < paramNames.size(); i++) {
			DValue value = params.get(i).evaluate(currentScope);
			TypeSymbol type;
			if (value.isScalar()) {
				type = new ScalarTypeSymbol(value.getType());
			}
			else {
				type = new ScalarTypeSymbol("Non scalar type");
			}
			VariableSymbol var = new VariableSymbol(paramNames.get(i), type);
			var.value = value;

			functionScope.define(var);
		}  

		try {  
			// Create a tree walker to evaluate this function's code block  
			CommonTreeNodeStream nodes = new CommonTreeNodeStream(this.code);  
			Interpreter walker = new Interpreter(nodes, functionScope, functions);  
			return walker.block().node.evaluate(functionScope);  
		} catch (RecognitionException e) {  
			// do not recover from this  
			throw new RuntimeException("something went wrong, terminating", e);  
		}
	}

	private List<String> toList(CommonTree tree) {  
		List<String> ids = new ArrayList<String>();  

		// convert the tree to a List of Strings  
		for(int i = 0; i < tree.getChildCount(); i++) {  
			CommonTree child = (CommonTree)tree.getChild(i);  
			ids.add(child.getText());  
		}  
		return ids;  
	}
}
