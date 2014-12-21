tree grammar Interpreter;

options {
  language = Java;
  tokenVocab = Syntax;
  output = AST;
  ASTLabelType = CommonTree;
  backtrack = true;
  memoize = true;
}

@header
{
  package com.dylan.interpreter;
  import com.dylan.symbolTable.*;
}

@members
{
	SymbolTable symTab = new SymbolTable();
	Scope currentScope;
	String intType = "int";
	String floatType = "float";
	String charType = "char";
	String boolType = "bool";
	int scopeCounter = 0;
  int numLoop = 0;
  int scopeNumber = 0;
	
	boolean currentlyExecuting = true;
	
	// Need to get this information in an earlier tree grammar, then pass it here
	boolean hasMain = true;
	
	private int getCurrentScopeNum() {
  	return currentScope.scopeNum;
  }
	
}


program
  : ^(PROGRAM 
  {
  	if (hasMain) {
  		currentlyExecuting = false;
  	}
  }
  mainblock)
  ;
  
mainblock
  : globalStatement*
  ;
  
globalStatement
  : statement
  | function
  ;
  
statement
  : assignment
  | declaration
  | print
  | ifstatement
  | loopstatement
  | block
  | callStatement
  | returnStatement
  | Break
  | Continue
  ;

print
  : ^(Print value=expr arg=expr?)
  {
  	if (currentlyExecuting) {
	  	if ($arg.text != null) {
	  		int argument = $arg.result.intResult;
	  		$value.result.print(argument);  	
	  	}
	  	else {
	  		$value.result.print(0);
	  	}
	  }
  }
  ;
  
length returns [String scalarType, Value result]
	: ^(Length expr) {$scalarType = $expr.scalarType; $result = $expr.result;}
	;
	
reverse returns [String scalarType, Value result]
	: ^(Reverse expr) {$scalarType = $expr.scalarType; $result = $expr.result;}
	;

declaration
@init {
	VariableSymbol vs = null;
	int currentScopeNum = getCurrentScopeNum();
	Value result = null;
}
@after {
	Symbol spec;
	if ($s.text == null) {
  	spec = new Symbol("var");
  }
  else {
  	spec = new Symbol($s.text);
  }
  
  vs = new VariableSymbol($id.text, $t.type, spec);
  vs.scopeNum = currentScopeNum;
  
  if (result != null) {
  	vs.setValue(result);
  }
  
  currentScope.define(vs);
}
	: ^(DECL s=specifier? t=type id=Identifier)
	| ^(DECL s=specifier? t=type ^(Assign id=Identifier expr {result = $expr.result;}))
	;

block
  : ^(BLOCK statement*)
  ;
  
function
@init {
	TypeSymbol type = null;
}
@after {
	
	if ($t.text != null) {
		type = $t.type;
	}
	else {
		type = new ScalarTypeSymbol("null");
	}
	
	FunctionSymbol fs = new FunctionSymbol($id.text, type);
	symTab.defineFunction(fs);
	currentScope = currentScope.getEnclosingScope();
}
  : ^(Function id=Identifier paramlist ^(Returns t=type?)
  {
	  if (hasMain && $Identifier.text.equals("main") && !currentlyExecuting) {
	  	currentlyExecuting = true;
	  }
	  else {
	  	currentlyExecuting = false;
	  }
	}
  block)
  {
  	if (hasMain) {
  		currentlyExecuting = false;
  	}
  	else {
  		currentlyExecuting = true;
  	}
  }
  ;
  
paramlist
@after {
	scopeNumber++;
}
  : ^(PARAMLIST {	currentScope = new Scope("paramscope", currentScope, scopeNumber);} p+=parameter*)
  ;
  
parameter
@init {
	VariableSymbol vs = null;
	int currentScopeNum = getCurrentScopeNum();
	String paramType = null;
}
@after {
	Symbol spec;
	if ($s.text == null) {
  	spec = new Symbol("const");
  }
  else {
  	spec = new Symbol($s.text);
  }
  
  vs = new VariableSymbol($id.text, $t.type, spec);
  vs.scopeNum = currentScopeNum;
  
  currentScope.define(vs);
}
  : ^(id=Identifier s=specifier? t=type)
  ;
  
callStatement
  : ^(CALL Identifier ^(ARGLIST expr*))
  ;
  
returnStatement
  : ^(Return expr?)
  ;
  
assignment
@init {
	VariableSymbol vs = null;
	TypeSymbol variableType = null;
	TypeSymbol scalarType = null;
}
  : ^(Assign Identifier value=expr)
  {
  	vs = (VariableSymbol) currentScope.resolve($Identifier.text);
  	vs.setValue($value.result);
  }
  | ^(Assign ^(INDEX Identifier index=expr) value=expr)
  {
  	vs = (VariableSymbol) currentScope.resolve($Identifier.text);
  	vs.setIndexedValue($value.result, $index.result);
  }
  ;
  
ifstatement
@init {
	boolean haltedExecution = false;
}
@after {
	// If we stopped execution due to failing condition, continue executing after leaving this 'if' block
	if (haltedExecution) {
		currentlyExecuting = true;
	}
}
  : ^(If expr 
  {
  	// If we are currently executing code, and we fail the conditional,
  	// then signify that we've stopped executing this block of code
  	if (currentlyExecuting && 
  			(($expr.result.boolResult != null && !$expr.result.boolResult) ||
  			 ($expr.result.intResult != null && $expr.result.intResult == 0) ||
  			 ($expr.result.floatResult != null && $expr.result.floatResult == 0))) {
  		currentlyExecuting = false;
  		haltedExecution = true;
  	}
  }
  slist ^(Else
  {
  	// If the 'if' statement failed, this 'else' should execute
  	if (haltedExecution) {
  		currentlyExecuting = true;
  	}
  	// Otherwise, the 'if' statement executed, so this 'else' should not
  	else {
  		currentlyExecuting = false;
  		haltedExecution = true;
  	}
  }
  slist))
  | ^(If expr
  {
  	// If we are currently executing code, and we fail the conditional,
  	// then signify that we've stopped executing this block of code
  	if (currentlyExecuting && 
  			(($expr.result.boolResult != null && !$expr.result.boolResult) ||
  			 ($expr.result.intResult != null && $expr.result.intResult == 0) ||
  			 ($expr.result.floatResult != null && $expr.result.floatResult == 0))) {
  		currentlyExecuting = false;
  		haltedExecution = true;
  	}
  }
  slist)
  
  ;
  
loopstatement
  : ^(Loop ^(While e=expr) slist)
  | ^(Loop slist ^(While e=expr))
  | ^(Loop slist)
  ;
  
slist
  : block
  | statement
  | declaration
  ;

type returns [TypeSymbol type]
  : nonScalar {$type = $nonScalar.type;}
  | scalar {$type = $scalar.type;}
  | tuple {System.out.println("Tuples not finished.");}
  | Identifier {System.out.println("Is this a typedef?");}
  ;

nonScalar returns [TypeSymbol type]
  : ^(Vector scalar Number?) {$type = new NonScalarTypeSymbol("vector", $scalar.type);}
  | ^(Matrix scalar Number? Number?) {$type = new NonScalarTypeSymbol("matrix", $scalar.type);}
  ;

scalar returns [TypeSymbol type]
	: Boolean {$type = new ScalarTypeSymbol("boolean");}
  | Integer {$type = new ScalarTypeSymbol("integer");}
  | Float {$type = new ScalarTypeSymbol("float");}
  | Character {$type = new ScalarTypeSymbol("character");}
  ;
  
tuple
  : ^(Tuple (type Identifier?)+)
  ;

specifier
  : Const
  | Var
  ;

//TODO: add a Type class, and set the return type to $result.type, calculated in the Operations class
expr returns [String exprType, Value result, String scalarType]
@init {
	List<Value> vecResult = new ArrayList<Value>();
}
  : ^(Plus a=expr b=expr) {$exprType = $a.exprType; $result = Operations.add($a.result, $b.result);}
  | ^(Minus a=expr b=expr) {$exprType = $a.exprType; $result = Operations.subtract($a.result, $b.result);}
  | ^(Multiply a=expr b=expr) {$exprType = $a.exprType; $result = Operations.multiply($a.result, $b.result);}
  | ^(Divide a=expr b=expr) {$exprType = $a.exprType; $result = Operations.divide($a.result, $b.result);}
  | ^(Mod a=expr b=expr) {$exprType = $a.exprType; $result = Operations.mod($a.result, $b.result);}
  | ^(Exponent a=expr b=expr) {$exprType = $a.exprType; $result = Operations.exponent($a.result, $b.result);}
  | ^(Equals a=expr b=expr) {$exprType = boolType; $result = Operations.equals($a.result, $b.result);}
  | ^(NEquals a=expr b=expr) {$exprType = boolType; $result = Operations.notEquals($a.result, $b.result);}
  | ^(GThan a=expr b=expr) {$exprType = boolType; $result = Operations.greaterThan($a.result, $b.result);}
  | ^(LThan a=expr b=expr) {$exprType = boolType; $result = Operations.lessThan($a.result, $b.result);}
  | ^(GThanE a=expr b=expr) {$exprType = boolType; $result = Operations.greaterThanEqual($a.result, $b.result);}
  | ^(LThanE a=expr b=expr) {$exprType = boolType; $result = Operations.lessThanEqual($a.result, $b.result);}
  | ^(Or a=expr b=expr) {$exprType = $a.exprType; $result = Operations.or($a.result, $b.result);}
  | ^(Xor a=expr b=expr) {$exprType = $a.exprType; $result = Operations.xor($a.result, $b.result);}
  | ^(And a=expr b=expr) {$exprType = $a.exprType; $result = Operations.and($a.result, $b.result);}
  | ^(Not a=expr) {$exprType = boolType; $result = Operations.not($a.result);}
  | ^(By expr expr)
  | ^(CALL Identifier ^(ARGLIST expr*))
  | ^(As t=type e=expr)
  | Identifier
  {
  	VariableSymbol vs = (VariableSymbol) currentScope.resolve($Identifier.text);
  	$exprType = vs.getType().getName();
  	$result = vs.getValue();
  }
  | Number {$exprType = intType; $result = new Value(new Integer($Number.text));}
  | FPNumber {$exprType = floatType; $result = new Value(new Float($FPNumber.text));}
  | True {$exprType = boolType; $result = new Value(new Boolean(true));}
  | False {$exprType = boolType; $result = new Value(new Boolean(false));}
  | Null
  | Char {$exprType = charType; $result = new Value(new Character(Operations.getCharacter($Char.text)));}
  | ^(TUPLEEX expr)
  | ^(Dot Identifier)
  | ^(NEG a=expr) {$exprType = $a.exprType; $result = Operations.negative($a.result);}
  | ^(POS a=expr) {$exprType = $a.exprType; $result = $a.result;}
  | length {$exprType = intType; $result = new Value(new Integer($length.result.vectorResult.size()));}
  | reverse
  | ^(VCONST (a=expr {vecResult.add($a.result); $scalarType = $a.exprType;})+) {$exprType = "vector"; $result = new Value(vecResult, $scalarType);}
  | ^(Range a=expr b=expr)
  | ^(Filter Identifier a=expr b=expr) 
  | ^(GENERATOR Identifier a=expr b=expr)
  | ^(GENERATOR ^(ROW Identifier a=expr) ^(COLUMN Identifier b=expr) c=expr)  
  | ^(INDEX vector=expr index=expr)
  ;