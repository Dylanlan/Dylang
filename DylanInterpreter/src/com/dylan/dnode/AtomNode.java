package com.dylan.dnode;

public class AtomNode implements DNode {
	private DValue value;  
	  
	public AtomNode(DValue value) {  
		this.value = value;  
	}  

	@Override  
	public DValue evaluate() {  
		return value;  
	}  

	@Override  
	public String toString() {  
		return value.toString();  
	}  
}
