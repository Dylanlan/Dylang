package com.dylan.interpreter;

import com.dylan.dnode.DValue;

public class Helper {

	
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
	
	public static boolean passedCondition(DValue cond) {
		
		if (cond.isBool() && cond.boolResult) {
			return true;
		}
		else if (cond.isInt() && cond.intResult != 0) {
			return true;
		}
		else if (cond.isFloat() && cond.floatResult != 0) {
			return true;
		}
		else {
			return false;
		}
	}
}
