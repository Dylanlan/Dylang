package com.dylan.interpreter;

//TODO: finish all type operations, note: can handle adding characters and strings to concatenate??!!
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
		else {
			System.out.println("THIS SHOULDN'T HAPPEN!!");
		}
		
		return result;
	}
	
	public static Result subtract(Result a, Result b) {
		Result result = new Result();
		
		if (a.intResult != null && b.intResult != null) {
			result.intResult = a.intResult - b.intResult;
		}
		else {
			System.out.println("THIS SHOULDN'T HAPPEN!!");
		}
		
		return result;
	}
	
	public static Result multiply(Result a, Result b) {
		Result result = new Result();
		
		if (a.intResult != null && b.intResult != null) {
			result.intResult = a.intResult * b.intResult;
		}
		else {
			System.out.println("THIS SHOULDN'T HAPPEN!!");
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
		else {
			System.out.println("THIS SHOULDN'T HAPPEN!!");
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
		else {
			System.out.println("THIS SHOULDN'T HAPPEN!!");
		}
		
		return result;
	}
	
	public static Result exponent(Result a, Result b) {
		Result result = new Result();
		
		if (a.intResult != null && b.intResult != null) {
			result.intResult = (int)Math.pow(a.intResult, b.intResult);
		}
		else {
			System.out.println("THIS SHOULDN'T HAPPEN!!");
		}
		
		return result;
	}
	
	public static Result equals(Result a, Result b) {
		Result result = new Result();
		
		if (a.intResult != null && b.intResult != null) {
			result.boolResult = a.intResult.intValue() == b.intResult.intValue();
		}
		else {
			System.out.println("THIS SHOULDN'T HAPPEN!!");
		}
		
		return result;
	}
	
	public static Result notEquals(Result a, Result b) {
		Result result = new Result();
		
		if (a.intResult != null && b.intResult != null) {
			result.boolResult = a.intResult.intValue() != b.intResult.intValue();
		}
		else {
			System.out.println("THIS SHOULDN'T HAPPEN!!");
		}
		
		return result;
	}
	
	public static Result lessThan(Result a, Result b) {
		Result result = new Result();
		
		if (a.intResult != null && b.intResult != null) {
			result.boolResult = a.intResult < b.intResult;
		}
		else {
			System.out.println("THIS SHOULDN'T HAPPEN!!");
		}
		
		return result;
	}
	
	public static Result greaterThan(Result a, Result b) {
		Result result = new Result();
		
		if (a.intResult != null && b.intResult != null) {
			result.boolResult = a.intResult > b.intResult;
		}
		else {
			System.out.println("THIS SHOULDN'T HAPPEN!!");
		}
		
		return result;
	}
	
	public static Result lessThanEqual(Result a, Result b) {
		Result result = new Result();
		
		if (a.intResult != null && b.intResult != null) {
			result.boolResult = a.intResult <= b.intResult;
		}
		else {
			System.out.println("THIS SHOULDN'T HAPPEN!!");
		}
		
		return result;
	}
	
	public static Result greaterThanEqual(Result a, Result b) {
		Result result = new Result();
		
		if (a.intResult != null && b.intResult != null) {
			result.boolResult = a.intResult >= b.intResult;
		}
		else {
			System.out.println("THIS SHOULDN'T HAPPEN!!");
		}
		
		return result;
	}
	
	public static Result or(Result a, Result b) {
		Result result = new Result();
		
		if (a.boolResult != null && b.boolResult != null) {
			result.boolResult = a.boolResult || b.boolResult;
		}
		else {
			System.out.println("THIS SHOULDN'T HAPPEN!!");
		}
		
		return result;
	}
	
	public static Result xor(Result a, Result b) {
		Result result = new Result();
		
		if (a.boolResult != null && b.boolResult != null) {
			result.boolResult = a.boolResult ^ b.boolResult;
		}
		else {
			System.out.println("THIS SHOULDN'T HAPPEN!!");
		}
		
		return result;
	}
	
	public static Result and(Result a, Result b) {
		Result result = new Result();
		
		if (a.boolResult != null && b.boolResult != null) {
			result.boolResult = a.boolResult && b.boolResult;
		}
		else {
			System.out.println("THIS SHOULDN'T HAPPEN!!");
		}
		
		return result;
	}
}
