package com.dylan.dnode;

import com.dylan.symbolTable.Scope;
import java.util.ArrayList;
import java.util.List;

public class BuiltInFunctionNode implements DNode{
	public static final int BIF_REVERSE = 0;
	public static final int BIF_LENGTH = 1;
	
	
	private DNode expression;
	private int function;

	public BuiltInFunctionNode(DNode expr, int function) {
		this.expression = expr;
		this.function = function;
	}

	@Override  
	public DValue evaluate(Scope currentScope) {
		DValue value = this.expression.evaluate(currentScope);
		DValue result = new DValue();
		
		switch(this.function) {
		case BIF_REVERSE:
			result = this.reverse(value);
			break;
		case BIF_LENGTH:
			result = this.length(value);
			break;
		}
		
		return result;
	}
	
	private DValue reverse(DValue value)
	{
		ArrayList<DValue> reversed = new ArrayList<DValue>();
		if (value.isVector())
		{
			List<DValue> list = value.getVector();
			
			for (int i = list.size() - 1; i >= 0; i--) {
				reversed.add(list.get(i));
				
			}
		}
		else
		{
			throw new RuntimeException("Can't call reverse() on non-vector type: " + value.getType());
		}
 
		return new DValue(reversed, value.vectorType); 
	}
	
	private DValue length(DValue value)
	{
		int length = 0;
		if (value.isVector())
		{
			List<DValue> list = value.getVector();
			
			length = list.size();
		}
		else
		{
			throw new RuntimeException("Can't call length() on non-vector type: " + value.getType());
		}
 
		return new DValue(length); 
	}
}
