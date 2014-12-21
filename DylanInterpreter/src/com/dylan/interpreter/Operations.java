package com.dylan.interpreter;

import java.util.List;
import java.util.ArrayList;

import com.dylan.symbolTable.Value;

//TODO: figure out and finish all type operations, note: can handle adding characters and strings to concatenate??!!
public class Operations {

	public static Character getCharacter(String quoted) {
		//TODO: clean up the copy/paste code, and handle more escapes 
		Character result = null;
		if (quoted.length() > 2 && quoted.charAt(0) == '\'' && quoted.charAt(1) == '\\') {
			char escaped = quoted.charAt(2);
			if (escaped == 'n') {
				result = new Character('\n');
			}
			else if (escaped == 't') {
				result = new Character('\t');
			}
			else if (escaped == '\\') {
				result = new Character('\\');
			}
			else if (escaped == '\'') {
				result = new Character('\'');
			}
			else if (escaped == '\"') {
				result = new Character('\"');
			}
			else {
				result = new Character(escaped);
			}
		}
		else if (quoted.length() > 1 && quoted.charAt(0) == '\\') {
			char escaped = quoted.charAt(1);
			if (escaped == 'n') {
				result = new Character('\n');
			}
			else if (escaped == 't') {
				result = new Character('\t');
			}
			else if (escaped == '\\') {
				result = new Character('\\');
			}
			else if (escaped == '\'') {
				result = new Character('\'');
			}
			else if (escaped == '\"') {
				result = new Character('\"');
			}
			else {
				result = new Character(escaped);
			}
		}
		else {
			result = new Character(quoted.replaceAll("'", "").charAt(0));
		}
		return result;
	}
	
	public static Value add(Value a, Value b) {
		Value result = new Value();
		
		if (a.intResult != null && b.intResult != null) {
			result.intResult = a.intResult + b.intResult;
		}
		else if (a.floatResult != null && b.floatResult != null) {
			result.floatResult = a.floatResult + b.floatResult;
		}
		
		else if (a.vectorType != null && b.vectorType != null) {
			if (a.vectorType.equals("char") && b.vectorType.equals("char")) {
				List<Value> concat = new ArrayList<Value>();
				concat.addAll(a.vectorResult);
				concat.addAll(b.vectorResult);
				result.vectorResult = concat;
				result.vectorType = a.vectorType;
			}
			else if (a.vectorType.equals("int") && b.vectorType.equals("int")) {
				List<Value> resultVector = new ArrayList<Value>();
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
					resultVector.add(new Value(new Integer(ai + bi)));
				}
				result.vectorResult = resultVector;
				result.vectorType = a.vectorType;
			}
			else {
				System.out.println("Addition not implemented for these vector types");
			}
		}
		else {
			System.out.println("Addition not implemented for these types");
		}
		
		return result;
	}
	
	public static Value subtract(Value a, Value b) {
		Value result = new Value();
		
		if (a.intResult != null && b.intResult != null) {
			result.intResult = a.intResult - b.intResult;
		}
		else if (a.floatResult != null && b.floatResult != null) {
			result.floatResult = a.floatResult - b.floatResult;
		}
		else {
			System.out.println("Subtraction not implemented for these types");
		}
		
		return result;
	}
	
	public static Value multiply(Value a, Value b) {
		Value result = new Value();
		
		if (a.intResult != null && b.intResult != null) {
			result.intResult = a.intResult * b.intResult;
		}
		else if (a.floatResult != null && b.floatResult != null) {
			result.floatResult = a.floatResult * b.floatResult;
		}
		else {
			System.out.println("Multiplication not implemented for these types");
		}
		
		return result;
	}
	
	public static Value divide(Value a, Value b) {
		Value result = new Value();
		
		if (a.intResult != null && b.intResult != null) {
			if (b.intResult == 0) {
				System.out.println("Error: divide by 0");
			}
			result.intResult = a.intResult / b.intResult;
		}
		else if (a.floatResult != null && b.floatResult != null) {
			if (b.floatResult == 0) {
				System.out.println("Error: divide by 0");
			}
			result.floatResult = a.floatResult / b.floatResult;
		}
		else {
			System.out.println("Division not implemented for these types");
		}
		
		return result;
	}
	
	public static Value mod(Value a, Value b) {
		Value result = new Value();
		
		if (a.intResult != null && b.intResult != null) {
			if (b.intResult == 0) {
				System.out.println("Error: mod by 0");
			}
			result.intResult = a.intResult % b.intResult;
		}
		else if (a.floatResult != null && b.floatResult != null) {
			if (b.floatResult == 0) {
				System.out.println("Error: divide by 0");
			}
			result.floatResult = a.floatResult % b.floatResult;
		}
		else {
			System.out.println("Mod not implemented for these types");
		}
		
		return result;
	}
	
	public static Value exponent(Value a, Value b) {
		Value result = new Value();
		
		if (a.intResult != null && b.intResult != null) {
			result.intResult = (int)Math.pow(a.intResult, b.intResult);
		}
		else if (a.floatResult != null && b.floatResult != null) {
			result.floatResult = (float)Math.pow(a.floatResult, b.floatResult);
		}
		else {
			System.out.println("Exponent not implemented for these types");
		}
		
		return result;
	}
	
	public static Value equals(Value a, Value b) {
		Value result = new Value();
		
		if (a.intResult != null && b.intResult != null) {
			result.boolResult = a.intResult.intValue() == b.intResult.intValue();
		}
		else if (a.floatResult != null && b.floatResult != null) {
			result.boolResult = a.floatResult.floatValue() == b.floatResult.floatValue();
		}
		else {
			System.out.println("Equals not implemented for these types");
		}
		
		return result;
	}
	
	public static Value notEquals(Value a, Value b) {
		Value result = new Value();
		
		if (a.intResult != null && b.intResult != null) {
			result.boolResult = a.intResult.intValue() != b.intResult.intValue();
		}
		else if (a.floatResult != null && b.floatResult != null) {
			result.boolResult = a.floatResult.floatValue() != b.floatResult.floatValue();
		}
		else {
			System.out.println("Not Equals not implemented for these types");
		}
		
		return result;
	}
	
	public static Value lessThan(Value a, Value b) {
		Value result = new Value();
		
		if (a.intResult != null && b.intResult != null) {
			result.boolResult = a.intResult < b.intResult;
		}
		else if (a.floatResult != null && b.floatResult != null) {
			result.boolResult = a.floatResult < b.floatResult;
		}
		else {
			System.out.println("Less Than not implemented for these types");
		}
		
		return result;
	}
	
	public static Value greaterThan(Value a, Value b) {
		Value result = new Value();
		
		if (a.intResult != null && b.intResult != null) {
			result.boolResult = a.intResult > b.intResult;
		}
		else if (a.floatResult != null && b.floatResult != null) {
			result.boolResult = a.floatResult > b.floatResult;
		}
		else {
			System.out.println("Greater Than not implemented for these types");
		}
		
		return result;
	}
	
	public static Value lessThanEqual(Value a, Value b) {
		Value result = new Value();
		
		if (a.intResult != null && b.intResult != null) {
			result.boolResult = a.intResult <= b.intResult;
		}
		else if (a.floatResult != null && b.floatResult != null) {
			result.boolResult = a.floatResult <= b.floatResult;
		}
		else {
			System.out.println("Less Than Equal not implemented for these types");
		}
		
		return result;
	}
	
	public static Value greaterThanEqual(Value a, Value b) {
		Value result = new Value();
		
		if (a.intResult != null && b.intResult != null) {
			result.boolResult = a.intResult >= b.intResult;
		}
		else if (a.floatResult != null && b.floatResult != null) {
			result.boolResult = a.floatResult >= b.floatResult;
		}
		else {
			System.out.println("Greater Than Equal not implemented for these types");
		}
		
		return result;
	}
	
	public static Value or(Value a, Value b) {
		Value result = new Value();
		
		if (a.boolResult != null && b.boolResult != null) {
			result.boolResult = a.boolResult || b.boolResult;
		}
		else {
			System.out.println("Or not implemented for these types");
		}
		
		return result;
	}
	
	public static Value xor(Value a, Value b) {
		Value result = new Value();
		
		if (a.boolResult != null && b.boolResult != null) {
			result.boolResult = a.boolResult ^ b.boolResult;
		}
		else {
			System.out.println("Xor not implemented for these types");
		}
		
		return result;
	}
	
	public static Value and(Value a, Value b) {
		Value result = new Value();
		
		if (a.boolResult != null && b.boolResult != null) {
			result.boolResult = a.boolResult && b.boolResult;
		}
		else {
			System.out.println("And not implemented for these types");
		}
		
		return result;
	}
	
	public static Value not(Value a) {
		Value result = new Value();
		
		if (a.boolResult != null) {
			result.boolResult = !a.boolResult;
		}
		else {
			System.out.println("Not not implemented for this type");
		}
		
		return result;
	}
	
	public static Value negative(Value a) {
		Value result = new Value();
		
		if (a.intResult != null) {
			result.intResult = -a.intResult;
		}
		else if (a.floatResult != null) {
			result.floatResult = -a.floatResult;
		}
		else {
			System.out.println("Negative not implemented for this type");
		}
		
		return result;
	}
}
