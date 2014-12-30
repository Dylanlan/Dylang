package com.dylan.dnode;

import java.util.ArrayList;
import java.util.List;

import com.dylan.symbolTable.Scope;

public class AtomNode implements DNode {
	private DValue value = null; 
	private DNode node = null;
	private List<DNode> nodes = null;
	  
	public AtomNode(DValue value) {
		this.value = value;  
	}
	
	public AtomNode(DNode node) {
		this.node = node;
	}
	
	public AtomNode(List<DNode> nodes) {
		this.nodes = nodes;
	}

	@Override  
	public DValue evaluate(Scope currentScope) {  
		if (this.value != null) {
			return this.value;
		}
		else if (this.node != null){
			return this.node.evaluate(currentScope);
		}
		else if (this.nodes != null) {
			DValue result = new DValue();
			result.vectorResult = new ArrayList<DValue>();
			String type = null;
			for (DNode singleNode : this.nodes) {
				DValue scalar = singleNode.evaluate(currentScope);
				type = scalar.getType();
				result.vectorResult.add(scalar);
			}
			result.vectorType = type;
			return result;
		}
		else {
			return new DValue();
		}
	}  

	@Override  
	public String toString() {
		if (this.value != null) {
			return value.toString();
		}
		else if (this.node != null){
			return "atom node";
		}
		else if (this.nodes != null) {
			return "atom nodes";
		}
		else {
			return "null";
		}
	}  
}
