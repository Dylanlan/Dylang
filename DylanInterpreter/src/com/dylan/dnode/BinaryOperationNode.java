package com.dylan.dnode;

import java.util.ArrayList;
import java.util.List;

import com.dylan.symbolTable.Scope;

public class BinaryOperationNode implements DNode {
	public static final int BON_ADD = 0;
	public static final int BON_SUB = 1;
	public static final int BON_MUL = 2;
	public static final int BON_DIV = 3;
	public static final int BON_MOD = 4;
	public static final int BON_POW = 5;
	public static final int BON_AND = 6;
	public static final int BON_EQUAL = 7;
	public static final int BON_NEQUAL = 8;
	public static final int BON_GREATER_E = 9;
	public static final int BON_GREATER = 10;
	public static final int BON_LESS_E = 11;
	public static final int BON_LESS = 12;
	public static final int BON_OR = 13;
	public static final int BON_XOR = 14;
	
	private DNode lhs;  
	private DNode rhs;
	private int operation;
	private Scope scope;
	private String operator;
	
	public BinaryOperationNode(DNode lhs, DNode rhs, int operation) {
		this.rhs = rhs;
		this.lhs = lhs;
		this.operation = operation;
		this.setOperator();
	}  

	@Override  
	public DValue evaluate() {  
		DValue result = null;
		DValue a = lhs.evaluate();  
		DValue b = rhs.evaluate();
 
		switch(this.operation) {
		case BON_ADD:
			result = this.add(a, b);
			break;
		case BON_SUB:
			result = this.sub(a, b);
			break;
		case BON_MUL:
			result = this.mul(a, b);
			break;
		case BON_DIV:
			result = this.div(a, b);
			break;
		case BON_MOD:
			result = this.mod(a, b);
			break;
		case BON_POW:
			result = this.pow(a, b);
			break;
		case BON_AND:
			result = this.and(a, b);
			break;
		case BON_EQUAL:
			result = this.equal(a, b);
			break;
		case BON_NEQUAL:
			result = this.nequal(a, b);
			break;
		case BON_GREATER_E:
			result = this.greater_e(a, b);
			break;
		case BON_GREATER:
			result = this.greater(a, b);
			break;
		case BON_LESS_E:
			result = this.less_e(a, b);
			break;
		case BON_LESS:
			result = this.less(a, b);
			break;
		case BON_OR:
			result = this.or(a, b);
			break;
		case BON_XOR:
			result = this.xor(a, b);
			break;
		default:
			result = new DValue();
			break;
		}
		
		return result;
	}  

	
	private DValue add(DValue a, DValue b) {
		if(a.intResult != null && b.intResult != null) {  
			return new DValue(a.intResult + b.intResult);  
		}
		
		if(a.floatResult != null && b.floatResult != null) {  
			return new DValue(a.floatResult + b.floatResult);  
		}
		
		if (a.charResult != null && b.charResult != null) {
			return new DValue(a.charResult.toString() + b.charResult.toString());
		}
		 
		if(a.isString() || b.isString()) {  
			return new DValue(a.toString() + "" + b.toString());  
		}
		
		if (a.vectorType.equals("int") && b.vectorType.equals("int")) {
			List<DValue> resultVector = new ArrayList<DValue>();
			int maxSize = Math.max(a.vectorResult.size(), b.vectorResult.size());
			for (int i = 0; i < maxSize; i++) {
				int ai = 0;
				if (i < a.vectorResult.size()) {
					ai = a.vectorResult.get(i).intResult;
				}
				int bi = 0;
				if (i < b.vectorResult.size()) {
					bi = b.vectorResult.get(i).intResult;
				}
				resultVector.add(new DValue(new Integer(ai + bi)));
			}
			
			DValue result = new DValue(resultVector, a.vectorType);
			return result;
		}

		throw new RuntimeException("illegal expression: " + this);
	}
	
	private DValue sub(DValue a, DValue b) {
		if(a.intResult != null && b.intResult != null) {  
			return new DValue(a.intResult - b.intResult);  
		}
		
		if(a.floatResult != null && b.floatResult != null) {  
			return new DValue(a.floatResult - b.floatResult);  
		}
		
		if (a.vectorType.equals("int") && b.vectorType.equals("int")) {
			List<DValue> resultVector = new ArrayList<DValue>();
			int maxSize = Math.max(a.vectorResult.size(), b.vectorResult.size());
			for (int i = 0; i < maxSize; i++) {
				int ai = 0;
				if (i < a.vectorResult.size()) {
					ai = a.vectorResult.get(i).intResult;
				}
				int bi = 0;
				if (i < b.vectorResult.size()) {
					bi = b.vectorResult.get(i).intResult;
				}
				resultVector.add(new DValue(new Integer(ai - bi)));
			}
			
			DValue result = new DValue(resultVector, a.vectorType);
			return result;
		}

		throw new RuntimeException("illegal expression: " + this);
	}
	
	private DValue mul(DValue a, DValue b) {
		if(a.intResult != null && b.intResult != null) {  
			return new DValue(a.intResult * b.intResult);  
		}
		
		if(a.floatResult != null && b.floatResult != null) {  
			return new DValue(a.floatResult * b.floatResult);  
		}
		
		if (a.vectorType.equals("int") && b.vectorType.equals("int")) {
			List<DValue> resultVector = new ArrayList<DValue>();
			int maxSize = Math.max(a.vectorResult.size(), b.vectorResult.size());
			for (int i = 0; i < maxSize; i++) {
				int ai = 1;
				if (i < a.vectorResult.size()) {
					ai = a.vectorResult.get(i).intResult;
				}
				int bi = 1;
				if (i < b.vectorResult.size()) {
					bi = b.vectorResult.get(i).intResult;
				}
				resultVector.add(new DValue(new Integer(ai * bi)));
			}
			
			DValue result = new DValue(resultVector, a.vectorType);
			return result;
		}

		throw new RuntimeException("illegal expression: " + this); 
	}
	
	private DValue div(DValue a, DValue b) {
		if(a.intResult != null && b.intResult != null) {
			if (b.intResult == 0) {
				System.out.println("Error: divide by 0");
			}
			return new DValue(a.intResult / b.intResult);  
		}
		
		if(a.floatResult != null && b.floatResult != null) {
			if (b.floatResult == 0) {
				System.out.println("Error: divide by 0");
			}
			return new DValue(a.floatResult / b.floatResult);  
		}
		
		throw new RuntimeException("illegal expression: " + this);
	}
	
	private DValue mod(DValue a, DValue b) {
		if(a.intResult != null && b.intResult != null) {
			if (b.intResult == 0) {
				System.out.println("Error: mod by 0");
			}
			return new DValue(a.intResult % b.intResult);  
		}
		
		if(a.floatResult != null && b.floatResult != null) {
			if (b.floatResult == 0) {
				System.out.println("Error: mod by 0");
			}
			return new DValue(a.floatResult % b.floatResult);  
		}
		
		throw new RuntimeException("illegal expression: " + this);
	}
	
	private DValue pow(DValue a, DValue b) {
		if(a.intResult != null && b.intResult != null) {  
			return new DValue((int)Math.pow(a.intResult, b.intResult));  
		}
		
		if(a.floatResult != null && b.floatResult != null) {  
			return new DValue((float)Math.pow(a.floatResult, b.floatResult));  
		}
		
		if (a.vectorType.equals("int") && b.vectorType.equals("int")) {
			List<DValue> resultVector = new ArrayList<DValue>();
			int maxSize = Math.max(a.vectorResult.size(), b.vectorResult.size());
			for (int i = 0; i < maxSize; i++) {
				int ai = 1;
				if (i < a.vectorResult.size()) {
					ai = a.vectorResult.get(i).intResult;
				}
				int bi = 1;
				if (i < b.vectorResult.size()) {
					bi = b.vectorResult.get(i).intResult;
				}
				resultVector.add(new DValue(new Integer((int)Math.pow(ai, bi))));
			}
			
			DValue result = new DValue(resultVector, a.vectorType);
			return result;
		}

		throw new RuntimeException("illegal expression: " + this);
	}
	
	private DValue and(DValue a, DValue b) {
		if (a.boolResult != null && b.boolResult != null) {
			return new DValue(a.boolResult && b.boolResult);
		}

		throw new RuntimeException("illegal expression: " + this);
	}
	
	private DValue equal(DValue a, DValue b) {
		if(a.intResult != null && b.intResult != null) {  
			return new DValue(a.intResult.intValue() == b.intResult.intValue());  
		}
		
		if(a.floatResult != null && b.floatResult != null) {  
			return new DValue(a.floatResult.floatValue() == b.floatResult.floatValue());  
		}
		
		if (a.charResult != null && b.charResult != null) {
			return new DValue(a.charResult.toString().equals(b.charResult.toString()));
		}
		 
		if(a.isString() || b.isString()) {  
			return new DValue(a.toString().equals(b.toString()));  
		}
		
		throw new RuntimeException("illegal expression: " + this);
	}
	
	private DValue nequal(DValue a, DValue b) {
		if(a.intResult != null && b.intResult != null) {  
			return new DValue(a.intResult.intValue() != b.intResult.intValue());  
		}
		
		if(a.floatResult != null && b.floatResult != null) {  
			return new DValue(a.floatResult.floatValue() != b.floatResult.floatValue());  
		}
		
		if (a.charResult != null && b.charResult != null) {
			return new DValue(!a.charResult.toString().equals(b.charResult.toString()));
		}
		 
		if(a.isString() || b.isString()) {  
			return new DValue(!a.toString().equals(b.toString()));  
		}
		
		throw new RuntimeException("illegal expression: " + this);
	}
	
	private DValue greater_e(DValue a, DValue b) {
		if(a.intResult != null && b.intResult != null) {  
			return new DValue(a.intResult.intValue() >= b.intResult.intValue());  
		}
		
		if(a.floatResult != null && b.floatResult != null) {  
			return new DValue(a.floatResult.floatValue() >= b.floatResult.floatValue());  
		}
		
		throw new RuntimeException("illegal expression: " + this);
	}
	
	private DValue greater(DValue a, DValue b) {
		if(a.intResult != null && b.intResult != null) {  
			return new DValue(a.intResult.intValue() > b.intResult.intValue());  
		}
		
		if(a.floatResult != null && b.floatResult != null) {  
			return new DValue(a.floatResult.floatValue() > b.floatResult.floatValue());  
		}
		
		throw new RuntimeException("illegal expression: " + this);
	}
	
	private DValue less_e(DValue a, DValue b) {
		if(a.intResult != null && b.intResult != null) {  
			return new DValue(a.intResult.intValue() <= b.intResult.intValue());  
		}
		
		if(a.floatResult != null && b.floatResult != null) {  
			return new DValue(a.floatResult.floatValue() <= b.floatResult.floatValue());  
		}
		
		throw new RuntimeException("illegal expression: " + this);
	}
	
	private DValue less(DValue a, DValue b) {
		if(a.intResult != null && b.intResult != null) {  
			return new DValue(a.intResult.intValue() < b.intResult.intValue());  
		}
		
		if(a.floatResult != null && b.floatResult != null) {  
			return new DValue(a.floatResult.floatValue() < b.floatResult.floatValue());  
		}
		
		throw new RuntimeException("illegal expression: " + this);
	}
	
	private DValue or(DValue a, DValue b) {
		if (a.boolResult != null && b.boolResult != null) {
			return new DValue(a.boolResult || b.boolResult);
		}

		throw new RuntimeException("illegal expression: " + this);
	}
	
	private DValue xor(DValue a, DValue b) {
		if (a.boolResult != null && b.boolResult != null) {
			return new DValue(a.boolResult ^ b.boolResult);
		}

		throw new RuntimeException("illegal expression: " + this);
	}
	
	private void setOperator() {
		switch (this.operation) {
		case BON_ADD:
			this.operator = "+";
			break;
		case BON_SUB:
			this.operator = "-";
			break;
		case BON_MUL:
			this.operator = "*";
			break;
		case BON_DIV:
			this.operator = "/";
			break;
		case BON_MOD:
			this.operator = "%";
			break;
		case BON_POW:
			this.operator = "^";
			break;
		case BON_AND:
			this.operator = "and";
			break;
		case BON_EQUAL:
			this.operator = "==";
			break;
		case BON_NEQUAL:
			this.operator = "!=";
			break;
		case BON_GREATER_E:
			this.operator = ">=";
			break;
		case BON_GREATER:
			this.operator = ">";
			break;
		case BON_LESS_E:
			this.operator = "<=";
			break;
		case BON_LESS:
			this.operator = "<";
			break;
		case BON_OR:
			this.operator = "or";
			break;
		case BON_XOR:
			this.operator = "xor";
			break;
		default:
			this.operator = "??";
			break;
		}
	}
	
	@Override  
	public String toString() {  
		return String.format("(%s %s %s)", this.lhs, this.operator, this.rhs);  
	}
}
