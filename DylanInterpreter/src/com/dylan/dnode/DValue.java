package com.dylan.dnode;

import java.util.ArrayList;
import java.util.List;

public class DValue {
	public Integer intResult;
	public Float floatResult;
	public Character charResult;
	public Boolean boolResult;
	public List<DValue> vectorResult;
	public String vectorType;
	public Object matrixResult;
	public String matrixType;
	
	public static int VEC_BRACKETS = 1;
	public static int VEC_COMMAS = 2;
	public static int VEC_BRACKETS_COMMAS = 3;
	
	public DValue() {
		
	}
	
	public DValue(Integer intValue) {
		intResult = intValue;
	}
	
	public DValue(Float floatValue) {
		floatResult = floatValue;
	}
	
	public DValue(Character charValue) {
		charResult = charValue;
	}
	
	public DValue(Boolean boolValue) {
		boolResult = boolValue;
	}
	
	public DValue(List<DValue> vector, String type) {
		vectorResult = vector;
		vectorType = type;
	}
	
	public DValue(String string) {
		List<DValue> vector = new ArrayList<DValue>();
		for (int i = 0; i < string.length(); i++) {
	        Character charac = new Character(string.charAt(i));
	        vector.add(new DValue(charac));
	    }
		this.vectorResult = vector;
		this.vectorType = "character";
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
	
	public List<DValue> getVector() {
		return this.vectorResult;
	}
	
	public Object getMatrix() {
		return this.matrixResult;
	}
	
	public boolean isInt() {
		return this.intResult != null;
	}
	
	public boolean isFloat() {
		return this.intResult != null;
	}
	
	public boolean isChar() {
		return this.intResult != null;
	}
	
	public boolean isBool() {
		return this.intResult != null;
	}
	
	public boolean isVector() {
		return this.vectorResult != null;
	}
	
	public boolean isNull() {
		if (!this.isInt() && !this.isFloat() && !this.isChar() &&
			!this.isBool() && !this.isVector()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean isScalar() {
		if (this.isInt() || this.isFloat() || this.isChar() || this.isBool()) {
			return true;
		}
		else {
			return false;
		}
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
			return "null";
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
				DValue element = vectorResult.get(i);
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
	
	
	@Override  
	public String toString() {  
		if (this.intResult != null) {
			return this.intResult.toString();
		}
		else if (this.floatResult != null) {
			return this.floatResult.toString();
		}
		else if (this.boolResult != null) {
			return this.boolResult.toString();
		}
		else if (this.charResult != null) {
			return this.charResult.toString();
		}
		else if (this.vectorResult != null) {
			return "VECTOR";
		}
		else {
			return "null";
		}
	}
}
