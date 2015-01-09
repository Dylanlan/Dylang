grammar Syntax;

options {
  language = Java;
  output = AST;
  ASTLabelType = CommonTree;
  backtrack = true;
  memoize = true;
}

tokens {
  GENERATOR;
  DECL;
  PROGRAM;
  BLOCK;
  PARAMLIST;
  CALL;
  ARGLIST;
  TUPLEEX;
  POS;
  NEG;
  VCONST;
  ROW;
  COLUMN;
  INDEX;
  SIZE;
  PREINCREMENT;
  POSTINCREMENT;
  PREDECREMENT;
  POSTDECREMENT;
  TERNARY;
  BODY;
}

@header
{
  package com.dylan.interpreter;
}

@lexer::header
{
  package com.dylan.interpreter;
}

//TODO: not necessarily require main
program
  : mainblock EOF -> ^(PROGRAM mainblock)
  ;
  
mainblock
  : globalStatement*
  ;
  
globalStatement
  : statement
  | typedef
  | function
  ;
  
statement
  : assignment SemiColon!
  | declaration SemiColon!
  | print
  | ifstatement
  | loopstatement
  | block
  | callStatement
  | returnStatement
  | Break SemiColon!
  | Continue SemiColon!
  | expr SemiColon!
  ;
  
length
	: Length LParen expr RParen -> ^(Length expr)
	;
	
print
	: Print LParen value=expr (Comma arg=expr)? RParen SemiColon -> ^(Print $value $arg?)
	| Print value=expr (Comma arg=expr)? SemiColon -> ^(Print $value $arg?)
	;
	
reverse
	: Reverse LParen expr RParen -> ^(Reverse expr)
	;

declaration
	: specifier? type? Identifier Assign expr -> ^(DECL specifier? type? ^(Assign Identifier expr)) 
	| specifier? type? Identifier -> ^(DECL specifier? type? Identifier)
	;
  
typedef
  : Typedef type Identifier SemiColon -> ^(Typedef type Identifier)
  ;

block
  : LBrace statement* RBrace -> ^(BLOCK statement*)
  ;
  
function
  : Function Identifier LParen paramlist RParen (Returns type)? block
  -> ^(Function Identifier paramlist ^(Returns["returns"] type?) block)
  ;
  
paramlist
  : parameter? (Comma parameter)* -> ^(PARAMLIST parameter*)
  ;
  
parameter
  : specifier? type Identifier^
  ;
  
callStatement
  : Call Identifier LParen expr? (Comma expr)* RParen SemiColon -> ^(CALL Identifier ^(ARGLIST expr*))
  ;
  
returnStatement
  : Return^ expr? SemiColon!
  ;
  
assignment
  : Identifier Assign expr -> ^(Assign Identifier expr)
  | Identifier index Assign expr -> ^(Assign ^(INDEX Identifier index) expr)
  | Identifier (Plus|Minus|Multiply|Divide|Mod) Assign^ expr
  ;
  
ifstatement
  : If expr slist Else slist -> ^(If expr slist ^(Else slist))
  | If expr slist -> ^(If expr slist)
  ;
  
loopstatement
  : While expr slist -> ^(While expr slist)
  | Do slist While expr -> ^(Do slist ^(While expr))
  | Loop expr slist -> ^(Loop expr slist)
  | For LParen declaration? (Comma declaration)* SemiColon expr? SemiColon
  	assignment? (Comma assignment)* RParen slist -> ^(For declaration* expr? assignment* ^(BODY slist))
  ;
  
slist
  : block
  | statement
  ;

type
  : scalar nonScalar -> ^(nonScalar scalar)
  | scalar
  | tuple
  | String -> ^(Vector["vector"] Character["character"])
  | Identifier
  ;

nonScalar
  : Vector^ (LBracket! Number RBracket!)?
  | Matrix^ (LBracket! Number Comma Number RBracket!)?
  ;  

scalar
	: Boolean
  | Integer
  | Float
  | Character
  ;
  
tuple
  : Tuple^ LParen! type Identifier? (Comma! type Identifier?)+ RParen!
  ;

specifier
  : Const
  | Var
  ;

expr
  : ternaryExpr
  ;
  
ternaryExpr
  : a=xorExpr Question b=xorExpr Colon c=xorExpr -> ^(TERNARY $a $b $c)
  | xorExpr
  ;
  
xorExpr
  : andExpr ((Xor | Or)^ andExpr)*
  ;
  
andExpr
  : equExpr (And^ equExpr)*
  ;
  
equExpr
  : relExpr ((Equals | NEquals)^ relExpr)*
  ;

relExpr
  : byExpr ((LThan | GThan | LThanE | GThanE)^ byExpr)* 
  ;
  
byExpr
  : addExpr (By^ addExpr)*
  ;
  
addExpr
  : mulExpr ((Plus | Minus)^ mulExpr)*
  ;
  
mulExpr
  : powExpr ((Product | Multiply | Divide | Mod)^ powExpr)*
  ;

powExpr
  : (unaryExpr -> unaryExpr) ((Exponent) y=unaryExpr -> ^(Exponent $powExpr $y))*
  ;
  
unaryExpr
  : Plus x=unaryExpr -> ^(POS $x)
  | Minus x=unaryExpr -> ^(NEG $x)
  | Not x=unaryExpr -> ^(Not $x)
  | rangeExpr
  ;
  
rangeExpr
  : incrExpr (Range^ incrExpr)? 
  ;
  
incrExpr
  : indexExpr Increment -> ^(POSTINCREMENT indexExpr)
  | indexExpr Decrement -> ^(POSTDECREMENT indexExpr)
  | Increment indexExpr -> ^(PREINCREMENT indexExpr)
  | Decrement indexExpr -> ^(PREDECREMENT indexExpr)
  | indexExpr
  ;
  
indexExpr
  : atom (index) -> ^(INDEX atom index)
  | atom
  ;

index
  : (LBracket expr)=> LBracket! expr RBracket!
  | LBracket! atom RBracket!
  ;   
  
atom
@init
{
  CommonTree toVConst = null;
}
  : Number
  | FPNumber
  | True
  | False
  | Null
  | Char
  | s=StringLiteral 
  {
    toVConst = (CommonTree) adaptor.nil();
    String temp = $s.text.substring(1, $s.text.length()-1);
    if (temp.length() > 0) 
    {
      String[] tokens = temp.split("(?!^)");
 
      for (int i = 0; i < tokens.length; i++) {
        if (tokens[i].equals("\\") && ((i + 1) < tokens.length)) 
        {
          toVConst.addChild((CommonTree) adaptor.create(Char, tokens[i] + tokens[i+1]));
          i++;
          } 
          else 
          toVConst.addChild((CommonTree) adaptor.create(Char, '\'' + tokens[i] + '\''));
       }
    }
  }
  -> ^(VCONST {toVConst})
  | Identifier Dot^ (Identifier|Number)
  | LParen (a=expr -> expr) (Comma b=expr -> ^(TUPLEEX $a $b))+ RParen
  | length
  | reverse
  | Identifier LParen expr? (Comma expr)* RParen -> ^(CALL Identifier ^(ARGLIST expr*))
  | Identifier
  | filter
  | generator
  | LParen expr RParen -> expr
  | As LThan type GThan LParen expr RParen -> ^(As type expr)
  | vectorconst -> ^(VCONST vectorconst)
  ;
  
vectorconst
  : LBracket! expr (Comma! expr)* RBracket!
  ;

filter
  : Filter LParen Identifier In vector=expr Bar condition=expr (Comma condition2+=expr)* RParen 
    -> ^(Filter Identifier $vector $condition $condition2*)        
  ;
  
generator
  : LBracket Identifier In vector=expr Bar apply=expr RBracket -> ^(GENERATOR Identifier $vector $apply)
  | LBracket id1=Identifier In e1=expr Comma id2=Identifier In e2=expr Bar apply=expr RBracket 
    -> ^(GENERATOR ^(ROW $id1 $e1) ^(COLUMN $id2 $e2) $apply)    
  ;
  
In        : 'in';
By        : 'by';
As        : 'as';
Var       : 'var';
Const     : 'const';
Matrix    : 'matrix';
Vector    : 'vector';
Integer   : 'integer' | 'int';
Boolean   : 'boolean' | 'bool';
True      : 'true';
False     : 'false';
Float     : 'float' | 'real';
String    : 'string';
Function  : 'function';
Returns   : 'returns';
Typedef   : 'typedef';
If        : 'if';
Else      : 'else';
While     : 'while';
Do				: 'do';
Loop      : 'loop';
For				: 'for';
Break     : 'break';
Continue  : 'continue';
Return    : 'return';
Filter    : 'filter';
Not       : 'not' || '!';
And       : 'and' || '&&';
Or        : 'or' || '||';
Xor       : 'xor';
Rows      : 'rows';
Columns   : 'columns';
Length    : 'length';
Tuple     : 'tuple';
Stream    : 'stream_state';
Reverse   : 'reverse';
Call      : 'call';
Print 		: 'print';
Null      : 'null' || 'NULL';
Character : 'character' || 'char';


Comment   : '//';
LBComment : '/*';
RBComment : '*/';
LArrow    : '<-';
RArrow    : '->';
Increment : '++';
Plus      : '+';
Decrement : '--';
Minus     : '-';
Product   : '**';
Multiply  : '*';
Divide    : '/';
Mod       : '%';
Exponent  : '^';
Equals    : '==';
NEquals   : '!=';
GThan     : '>';
GThanE    : '>=';
LThan     : '<';
LThanE    : '<=';
LParen    : '(';
RParen    : ')';
LBracket  : '[';
RBracket  : ']';
LBrace    : '{';
RBrace    : '}';
Assign    : '=';
SemiColon : ';';
Colon     : ':';
Comma     : ',';
Range     : '..';
Bar       : '|';
Question  : '?';

fragment Dot       
  : '.'
  ;
fragment Digit
  : '0'..'9'
  ;
fragment Exp
  : ('e' | 'E') '_'* Digit+ '_'*
  | ('e' | 'E') '_'* (Minus|Plus) '_'* Digit+ '_'*
  ;

Identifier
  : ('A'..'Z' | 'a'..'z' | '_') ('A'..'Z'| 'a'..'z'| '_' | Digit)*
  ;

Number 
  : Digit (Digit|'_')*
  ;
  
FPNumber
  : (Digit|'_')* 
       (  Exp
       | {input.LA(1) == '.' && input.LA(2) != '.'}? => Dot (Digit|'_')* Exp?  
       | (Range)=>      {$type=Number;}  
       | 
       )
  ;

fragment SingleChar
  :  ('A'..'Z'|'a'..'z'|'0'..'9'|' '|'!'|'#'|'$'|'%'|'&'|'('|')'|
          '*'|'+'|','|'-'|'.'|'/'|':'|';'|'<'|'='|'>'|'?'|'@'|'['|']'|
          '^'|'_'|'`'|'{'|'|'|'}'|'~'|
          '\\'  ('A'..'Z'|'a'..'z'|'0'..'9'|' '|'!'|'#'|'$'|'%'|'&'|'('|')'|
          '*'|'+'|','|'-'|'.'|'/'|'\\'|'\''|'\"'|':'|';'|'<'|'='|'>'|'?'|'@'|'['|']'|
          '^'|'_'|'`'|'{'|'|'|'}'|'~'))
  ;

Char
  : '\'' SingleChar '\''
  ;
  
StringLiteral
  : '"' SingleChar* '"'
  ;
 
MULTILINE_COMMENT : '/*' .* '*/' {$channel = HIDDEN;} ;
COMMENT : '//' .* ('\n'|'\r') {$channel = HIDDEN;};

Space
  : (' ' 
  | '\t' 
  | '\n'
  | '\r') {$channel = HIDDEN;}
  ;