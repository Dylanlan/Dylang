tree grammar Interpreter;

options {
  language = Java;
  tokenVocab = Syntax;
  ASTLabelType = CommonTree;
  backtrack = true;
  memoize = true;
}

@header
{
  package com.dylan.interpreter;
}

@members
{
	String intType = "int";
	String floatType = "float";
	String charType = "char";
	String boolType = "bool";
	
	boolean currentlyExecuting = true;
}


program
  : ^(PROGRAM mainblock)
  ;
  
mainblock
  : globalStatement*
  ;
  
globalStatement
  : declaration
  | typedef
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
  
length returns [String scalarType, Result result]
	: ^(Length expr) {$scalarType = $expr.scalarType; $result = $expr.result;}
	;
	
reverse
	: ^(Reverse expr)
	;

declaration
	: ^(DECL specifier? type Identifier)
	| ^(DECL specifier? type ^(Assign Identifier expr))
	;
  
typedef
  : ^(Typedef type Identifier)
  ;

block
  : ^(BLOCK statement*)
  ;
  
function
  : ^(Function Identifier paramlist ^(Returns type?) block?)
  ;
  
paramlist
  : ^(PARAMLIST parameter*)
  ;
  
parameter
  : ^(Identifier specifier? type)
  ;
  
callStatement
  : ^(CALL Identifier ^(ARGLIST expr*))
  ;
  
returnStatement
  : ^(Return expr?)
  ;
  
assignment
  : ^(Assign Identifier value=expr)
  | ^(Assign ^(INDEX Identifier index=expr) value=expr)
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

type
  : ^(nonScalar scalar)
  | scalar
  | tuple
  | Identifier
  ;

nonScalar
  : ^(Vector Number?)
  | ^(Matrix Number? Number?)
  ;  

scalar
	: Boolean
  | Integer
  | Real
  | Character
  ;
  
tuple
  : ^(Tuple (type Identifier?)+)
  ;

specifier
  : Const
  | Var
  ;

//TODO: add a Type class, and set the return type to $result.type, calculated in the Operations class
expr returns [String exprType, Result result, String scalarType]
@init {
	List<Result> vecResult = new ArrayList<Result>();
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
  | Number {$exprType = intType; $result = new Result(new Integer($Number.text));}
  | FPNumber {$exprType = floatType; $result = new Result(new Float($FPNumber.text));}
  | True {$exprType = boolType; $result = new Result(new Boolean(true));}
  | False {$exprType = boolType; $result = new Result(new Boolean(false));}
  | Null
  | Char {$exprType = charType; $result = new Result(new Character(Operations.getCharacter($Char.text)));}
  | ^(TUPLEEX expr)
  | ^(Dot Identifier)
  | ^(NEG a=expr) {$exprType = $a.exprType; $result = Operations.negative($a.result);}
  | ^(POS a=expr) {$exprType = $a.exprType; $result = $a.result;}
  | length {$exprType = intType; $result = new Result(new Integer($length.result.vectorResult.size()));}
  | reverse
  | ^(VCONST (a=expr {vecResult.add($a.result); $scalarType = $a.exprType;})+) {$exprType = "vector"; $result = new Result(vecResult, $scalarType);}
  | ^(Range a=expr b=expr)
  | ^(Filter Identifier a=expr b=expr) 
  | ^(GENERATOR Identifier a=expr b=expr)
  | ^(GENERATOR ^(ROW Identifier a=expr) ^(COLUMN Identifier b=expr) c=expr)  
  | ^(INDEX vector=expr index=expr)
  ;