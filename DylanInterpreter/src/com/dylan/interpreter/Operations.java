package com.dylan.interpreter;

import java.util.List;
import java.util.ArrayList;

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
	
	public static Result add(Result a, Result b) {
		Result result = new Result();
		
		if (a.intResult != null && b.intResult != null) {
			result.intResult = a.intResult + b.intResult;
		}
		else if (a.floatResult != null && b.floatResult != null) {
			result.floatResult = a.floatResult + b.floatResult;
		}
		
		else if (a.vectorType != null && b.vectorType != null) {
			if (a.vectorType.equals("char") && b.vectorType.equals("char")) {
				result.vectorType = "char";
				List<Result> concat = new ArrayList<Result>();
				concat.addAll(a.vectorResult);
				concat.addAll(b.vectorResult);
				result.vectorResult = concat;
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
	
	public static Result subtract(Result a, Result b) {
		Result result = new Result();
		
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
	
	public static Result multiply(Result a, Result b) {
		Result result = new Result();
		
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
	
	public static Result divide(Result a, Result b) {
		Result result = new Result();
		
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
	
	public static Result mod(Result a, Result b) {
		Result result = new Result();
		
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
	
	public static Result exponent(Result a, Result b) {
		Result result = new Result();
		
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
	
	public static Result equals(Result a, Result b) {
		Result result = new Result();
		
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
	
	public static Result notEquals(Result a, Result b) {
		Result result = new Result();
		
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
	
	public static Result lessThan(Result a, Result b) {
		Result result = new Result();
		
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
	
	public static Result greaterThan(Result a, Result b) {
		Result result = new Result();
		
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
	
	public static Result lessThanEqual(Result a, Result b) {
		Result result = new Result();
		
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
	
	public static Result greaterThanEqual(Result a, Result b) {
		Result result = new Result();
		
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
	
	public static Result or(Result a, Result b) {
		Result result = new Result();
		
		if (a.boolResult != null && b.boolResult != null) {
			result.boolResult = a.boolResult || b.boolResult;
		}
		else {
			System.out.println("Or not implemented for these types");
		}
		
		return result;
	}
	
	public static Result xor(Result a, Result b) {
		Result result = new Result();
		
		if (a.boolResult != null && b.boolResult != null) {
			result.boolResult = a.boolResult ^ b.boolResult;
		}
		else {
			System.out.println("Xor not implemented for these types");
		}
		
		return result;
	}
	
	public static Result and(Result a, Result b) {
		Result result = new Result();
		
		if (a.boolResult != null && b.boolResult != null) {
			result.boolResult = a.boolResult && b.boolResult;
		}
		else {
			System.out.println("And not implemented for these types");
		}
		
		return result;
	}
	
	public static Result not(Result a) {
		Result result = new Result();
		
		if (a.boolResult != null) {
			result.boolResult = !a.boolResult;
		}
		else {
			System.out.println("Not not implemented for this type");
		}
		
		return result;
	}
	
	public static Result negative(Result a) {
		Result result = new Result();
		
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
