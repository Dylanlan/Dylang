package com.dylan.dnode;

public class EmptyNode implements DNode {
	public EmptyNode() {
		
	}
	
	public DValue evaluate() {
		return new DValue();
	}
}
