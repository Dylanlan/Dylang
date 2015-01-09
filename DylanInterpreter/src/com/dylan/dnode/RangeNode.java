package com.dylan.dnode;

import java.util.ArrayList;
import java.util.List;

import com.dylan.interpreter.Helper;
import com.dylan.symbolTable.Scope;

public class RangeNode implements DNode {
	private DNode lower = null;
	private DNode upper = null;
	
	public RangeNode(DNode lower, DNode upper) {
		this.lower = lower;
		this.upper = upper;
	}
	
	@Override
	public DValue evaluate(Scope currentScope) {
		List<DValue> vecResult = new ArrayList<DValue>();
		String vecType = null;
		
		DValue lowerVal = this.lower.evaluate(currentScope);
		DValue upperVal = this.upper.evaluate(currentScope);
		
		vecType = lowerVal.getType();
		
		if (lowerVal.isInt()) {
			for (int i = lowerVal.getInt(); i <= upperVal.getInt(); i++) {
				vecResult.add(new DValue(i));
			}
		}
		else if (lowerVal.isChar()) {
			for (char i = lowerVal.getChar(); i <= upperVal.getChar(); i++) {
				vecResult.add(new DValue(i));
			}
		}
		else {
			throw new RuntimeException("Range operation not defined for type: " + lowerVal.getType());
		}
		
		return new DValue(vecResult, vecType);
	}
}
