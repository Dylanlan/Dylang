package com.dylan.interpreter;

import java.util.List;

public class Result {
	public Integer intResult;
	public Float floatResult;
	public Character charResult;
	public Boolean boolResult;
	public List<Result> vectorResult;
	public String vectorType;
	public Object matrixResult;
	public String matrixType;
	
	public static int VEC_BRACKETS = 1;
	public static int VEC_COMMAS = 2;
	public static int VEC_BRACKETS_COMMAS = 3;
	
	public Result() {
		
	}
	
	public Result(Integer intValue) {
		intResult = intValue;
	}
	
	public Result(Float floatValue) {
		floatResult = floatValue;
	}
	
	public Result(Character charValue) {
		charResult = charValue;
	}
	
	public Result(Boolean boolValue) {
		boolResult = boolValue;
	}
	
	public Result(List<Result> vector) {
		vectorResult = vector;
	}
	
	public int getInt() {
		return this.intResult;
	}
	
	public float getFloat() {
		return this.floatResult;
	}
	
	public char getChar() {
		return this.charResult;
	}
	
	public boolean getBool() {
		return this.boolResult;
	}
	
	public Object getVectorResult() {
		return this.vectorResult;
	}
	
	public Object getMatrixResult() {
		return this.matrixResult;
	}
	
	public String getType() {
		if (intResult != null) {
			return "int";
		}
		else if (floatResult != null) {
			return "float";
		}
		else if (charResult != null) {
			return "char";
		}
		else if (boolResult != null) {
			return "bool";
		}
		else if (vectorResult != null) {
			return "vector";
		}
		else if (matrixResult != null) {
			return "matrix";
		}
		else {
			return "void";
		}
	}
	
	public void print(int argument) {
		if (intResult != null) {
			System.out.print(intResult);
		}
		else if (floatResult != null) {
			System.out.print(floatResult);
		}
		else if (charResult != null) {
			System.out.print(charResult);
		}
		else if (boolResult != null) {
			System.out.print(boolResult);
		}
		else if (vectorResult != null) {
			if (argument == VEC_BRACKETS || argument == VEC_BRACKETS_COMMAS) {
				System.out.print("[");
			}
			int size = vectorResult.size();
			for(int i = 0; i < size; i++) {
				Result element = vectorResult.get(i);
				element.print(argument);
				if (element.charResult == null && i < size - 1) {
					if (argument == VEC_COMMAS || argument == VEC_BRACKETS_COMMAS) {
						System.out.print(",");
					}
					System.out.print(" ");
				}
			}
			if (argument == VEC_BRACKETS || argument == VEC_BRACKETS_COMMAS) {
				System.out.print("]");
			}
		}
		else if (matrixResult != null) {
			System.out.print(matrixResult);
		}
	}
	
}
