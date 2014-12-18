package com.dylan.interpreter;

//TODO: finish all type operations, note: can handle adding characters and strings to concatenate??!!
public class Operations {

	public static Character getCharacter(String quoted) {
		//TODO: handle escapes, especially \'
		Character result = new Character(quoted.replaceAll("'", "").charAt(0));
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
}
