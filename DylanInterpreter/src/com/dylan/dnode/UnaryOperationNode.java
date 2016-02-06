package com.dylan.dnode;

import java.util.ArrayList;
import java.util.List;

import com.dylan.symbolTable.Scope;

public class UnaryOperationNode implements DNode{
	public static final int UON_NEG = 0;
	public static final int UON_NOT = 1;
	public static final int UON_PRE_INCR = 2;
	public static final int UON_PRE_DECR = 3;
	public static final int UON_POST_INCR = 4;
	public static final int UON_POST_DECR = 5;
	
	private DNode expr;
	private int operation;
	private String operator;
	private String name;
	
	public UnaryOperationNode(DNode expression, int operation) {
		this.expr = expression;
		this.operation = operation;
		this.setOperator();
	}
	
	public UnaryOperationNode(String name, int operation) {
		this.expr = new VariableNode(name);
		this.operation = operation;
		this.setOperator();
		this.name = name;
	}
	
	@Override  
	public DValue evaluate(Scope currentScope) {  
		DValue result = null;
		DValue a = expr.evaluate(currentScope);
 
		switch(this.operation) {
		case UON_NEG:
			result = this.neg(a);
			break;
		case UON_NOT:
			result = this.not(a);
			break;
		case UON_PRE_INCR:
			result = this.preIncrement(a, currentScope);
			break;
		case UON_PRE_DECR:
			result = this.preDecrement(a, currentScope);
			break;
		case UON_POST_INCR:
			result = this.postIncrement(a, currentScope);
			break;
		case UON_POST_DECR:
			result = this.postDecrement(a, currentScope);
			break;
		default:
			result = new DValue();
			break;
		}
		
		return result;
	}
	
	
	private DValue neg(DValue a) {   
		if (a.intResult != null) {
			return new DValue(-a.intResult);
		}
		else if (a.floatResult != null) {
			return new DValue(-a.floatResult);
		}

		throw new RuntimeException("illegal expression: " + this);  
	}
	
	private DValue not(DValue a) {
		if (a.boolResult != null) {
			return new DValue(!a.boolResult);
		}
		
		if (a.isVector()) {
			List<DValue> resultVector = new ArrayList<DValue>();
			int size = a.vectorResult.size();
			for (int i = 0; i < size; i++) {
				DValue ai = a.vectorResult.get(i);
				resultVector.add(this.not(ai));
			}
			DValue result = new DValue(resultVector, a.vectorType);
			return result;
		} 

		throw new RuntimeException("illegal expression: " + this);
	}
	
	private DValue preIncrement(DValue a, Scope currentScope) {
		BinaryOperationNode node = new BinaryOperationNode(new AtomNode(a), new AtomNode(new DValue(1)), BinaryOperationNode.BON_ADD);
		DValue result = node.evaluate(currentScope);
		AssignmentNode assign = new AssignmentNode(this.name, node);
		assign.evaluate(currentScope);
		return result;
	}
	
	private DValue preDecrement(DValue a, Scope currentScope) {
		BinaryOperationNode node = new BinaryOperationNode(new AtomNode(a), new AtomNode(new DValue(1)), BinaryOperationNode.BON_SUB);
		DValue result = node.evaluate(currentScope);
		AssignmentNode assign = new AssignmentNode(this.name, node);
		assign.evaluate(currentScope);
		return result;
	}
	
	private DValue postIncrement(DValue a, Scope currentScope) {
		BinaryOperationNode node = new BinaryOperationNode(new AtomNode(a), new AtomNode(new DValue(1)), BinaryOperationNode.BON_ADD);
		AssignmentNode assign = new AssignmentNode(this.name, node);
		assign.evaluate(currentScope);
		return a;
	}
	
	private DValue postDecrement(DValue a, Scope currentScope) {
		BinaryOperationNode node = new BinaryOperationNode(new AtomNode(a), new AtomNode(new DValue(1)), BinaryOperationNode.BON_SUB);
		AssignmentNode assign = new AssignmentNode(this.name, node);
		assign.evaluate(currentScope);
		return a;
	}
	
	private void setOperator() {
		switch (this.operation) {
		case UON_NEG:
			this.operator = "-";
			break;
		case UON_NOT:
			this.operator = "not";
			break;
		default:
			this.operator = "?";
			break;
		}
	}
	
	@Override  
	public String toString() {  
		return String.format("(%s %s)", this.operator, this.expr);  
	}
}
