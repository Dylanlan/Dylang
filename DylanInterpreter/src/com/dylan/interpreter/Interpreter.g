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
	String charType = "char";
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
  : ^(Print expr) {$expr.result.print();}
  ;
  
length
	: ^(Length expr)
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
  : ^(If expr slist ^(Else slist))
  | ^(If expr slist)
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

expr returns [String exprType, Result result]
  : ^(Plus a=expr b=expr) {$exprType = $a.exprType; $result = Operations.add($a.result, $b.result);}
  | ^(Minus expr expr) {$exprType = $a.exprType; $result = Operations.subtract($a.result, $b.result);}
  | ^(Multiply expr expr) {$exprType = $a.exprType; $result = Operations.multiply($a.result, $b.result);}
  | ^(Divide expr expr) {$exprType = $a.exprType; $result = Operations.divide($a.result, $b.result);}
  | ^(Mod expr expr)
  | ^(Exponent expr expr)
  | ^(Equals expr expr)
  | ^(NEquals expr expr)
  | ^(GThan expr expr)
  | ^(LThan expr expr)
  | ^(GThanE expr expr)
  | ^(LThanE expr expr)
  | ^(Or expr expr)
  | ^(Xor expr expr)
  | ^(And expr expr)
  | ^(Not expr)
  | ^(By expr expr)
  | ^(CALL Identifier ^(ARGLIST expr*))
  | ^(As t=type e=expr)
  | Identifier
  | Number {$exprType = intType; $result = new Result(new Integer($Number.text));}
  | FPNumber
  | True
  | False
  | Null
  | Char {$exprType = charType; $result = new Result(new Character(Operations.getCharacter($Char.text)));}
  | ^(TUPLEEX expr)
  | ^(Dot Identifier)
  | ^(NEG e=expr)
  | ^(POS e=expr)
  | length
  | reverse
  | ^(VCONST expr+)
  | ^(Range a=expr b=expr)
  | ^(Filter Identifier a=expr b=expr) 
  | ^(GENERATOR Identifier a=expr b=expr)
  | ^(GENERATOR ^(ROW Identifier a=expr) ^(COLUMN Identifier b=expr) c=expr)  
  | ^(INDEX vector=expr indx=expr)
  ;
  
vectorconst
  : expr+
  ;

filter
  : ^(Filter Identifier expr expr+)        
  ;
  
generator
  : ^(GENERATOR Identifier expr expr)
  | ^(GENERATOR ^(ROW Identifier expr) ^(COLUMN Identifier expr) expr)    
  ;