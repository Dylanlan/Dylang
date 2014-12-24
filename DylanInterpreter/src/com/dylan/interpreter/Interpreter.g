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
  import com.dylan.dnode.*;
}

@members
{
	SymbolTable symTab = new SymbolTable();
	Scope currentScope = null;
	Map<String, FunctionSymbol> functions = null;
	String intType = "int";
	String floatType = "float";
	String charType = "char";
	String boolType = "bool";
  int scopeNumber = 0;
	
	private int getCurrentScopeNum() {
  	return currentScope.scopeNum;
  }
  
  public Interpreter(CommonTreeNodeStream nodes, Map<String, FunctionSymbol> fns) {
    this(nodes);
    currentScope = this.symTab.globals;
    functions = fns;
  }
  
  public Interpreter(CommonTreeNodeStream nds, Scope sc, Map<String, FunctionSymbol> fns) {
    this(nds);
    currentScope = sc;
    functions = fns;
  }
	
}


program returns [DNode node]
  : ^(PROGRAM mainblock) {$node = $mainblock.node;}
  ;
  
mainblock returns [DNode node]
@init {
	BlockNode bn = new BlockNode();
	$node = bn;
}
  : (globalStatement {bn.addStatement($globalStatement.node);})*
  ;
  
globalStatement returns [DNode node]
  : statement {$node = $statement.node;}
  | function {$node = new EmptyNode();}
  ;
  
statement returns [DNode node]
  : assignment
  | declaration {$node = $declaration.node;}
  | print {$node = $print.node;}
  | ifstatement
  | loopstatement
  | block
  | callStatement
  | returnStatement {$node = $returnStatement.node;}
  | Break
  | Continue
  ;

print returns [DNode node]
  : ^(Print value=expr arg=expr) {$node = new PrintNode($value.node, $arg.node);}
  | ^(Print value=expr) {$node = new PrintNode($value.node);}
  ;
  
length returns [DNode node]
	: ^(Length expr)
	;
	
reverse returns [DNode node]
	: ^(Reverse expr)
	;

declaration returns [DNode node]
@init {
	VariableSymbol vs = null;
	int currentScopeNum = getCurrentScopeNum();
	DValue result = null;
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
  $node = new EmptyNode();
}
	: ^(DECL s=specifier? t=type id=Identifier)
	| ^(DECL s=specifier? t=type ^(Assign id=Identifier expr {result = $expr.node.evaluate();}))
	;

block returns [DNode node]
@init { 
  BlockNode bn = new BlockNode(); 
  $node = bn; 
  //Scope scope = new Scope(currentScope); 
  //currentScope = scope; 
}  
@after { 
  //currentScope = currentScope.parent(); 
}
  : ^(BLOCK (statement  {bn.addStatement($statement.node);})*)
  ;
  
function returns [DNode node]
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
	
	FunctionSymbol fs = new FunctionSymbol($id.text, type, $p.tree, $b.tree);
	symTab.defineFunction(fs);
	functions.put($id.text, fs);
	currentScope = currentScope.getEnclosingScope();
}
  : ^(Function id=Identifier p=paramlist ^(Returns t=type?) b=block)
  ;
  
paramlist returns [DNode node]
@after {
	scopeNumber++;
}
  : ^(PARAMLIST {	currentScope = new Scope("paramscope", currentScope, scopeNumber);} p+=parameter*)
  ;
  
parameter returns [DNode node]
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
  
callStatement returns [DNode node]
  : ^(CALL Identifier ^(ARGLIST expr*))
  ;
  
returnStatement returns [DNode node]
  : ^(Return expr) {$node = $expr.node;}
  | Return {$node = new EmptyNode();}
  ;
  
assignment returns [DNode node]
@init {
	VariableSymbol vs = null;
	TypeSymbol variableType = null;
	TypeSymbol scalarType = null;
}
  : ^(Assign Identifier value=expr)
  {
  	vs = (VariableSymbol) currentScope.resolve($Identifier.text);
  }
  | ^(Assign ^(INDEX Identifier index=expr) value=expr)
  {
  	vs = (VariableSymbol) currentScope.resolve($Identifier.text);
  }
  ;
  
ifstatement returns [DNode node]
  : ^(If expr slist ^(Else slist))
  | ^(If expr slist)
  ;
  
loopstatement returns [DNode node]
  : ^(Loop ^(While e=expr) slist)
  | ^(Loop slist ^(While e=expr))
  | ^(Loop slist)
  ;
  
slist returns [DNode node]
  : block {$node = $block.node;}
  | statement {BlockNode bn = new BlockNode(); bn.addStatement($statement.node); $node = bn;}
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

expr returns [DNode node]
@init {
	List<DValue> vecResult = new ArrayList<DValue>();
}
  : ^(Plus a=expr b=expr) {$node = new AddNode($a.node, $b.node);}
  | ^(Minus a=expr b=expr)
  | ^(Multiply a=expr b=expr)
  | ^(Divide a=expr b=expr)
  | ^(Mod a=expr b=expr)
  | ^(Exponent a=expr b=expr)
  | ^(Equals a=expr b=expr)
  | ^(NEquals a=expr b=expr)
  | ^(GThan a=expr b=expr)
  | ^(LThan a=expr b=expr)
  | ^(GThanE a=expr b=expr)
  | ^(LThanE a=expr b=expr)
  | ^(Or a=expr b=expr)
  | ^(Xor a=expr b=expr)
  | ^(And a=expr b=expr)
  | ^(Not a=expr)
  | ^(By expr expr)
  | ^(CALL Identifier ^(ARGLIST expr*))
  | ^(As t=type e=expr)
  | Identifier
  {
  	VariableSymbol vs = (VariableSymbol) currentScope.resolve($Identifier.text);
  	$node = new AtomNode(vs.value);
  }
  | Number {$node = new AtomNode(new DValue(new Integer($Number.text)));}
  | FPNumber {$node = new AtomNode(new DValue(new Float($FPNumber.text)));}
  | True {$node = new AtomNode(new DValue(new Boolean(true)));}
  | False {$node = new AtomNode(new DValue(new Boolean(false)));}
  | Null {$node = new AtomNode(new DValue());}
  | Char {$node = new AtomNode(new DValue(new Character(Operations.getCharacter($Char.text))));}
  | ^(TUPLEEX expr)
  | ^(Dot Identifier)
  | ^(NEG a=expr)
  | ^(POS a=expr)
  | length
  | reverse
  | ^(VCONST (a=expr {vecResult.add($a.node.evaluate());})+) {$node = new AtomNode(new DValue(vecResult, vecResult.get(0).getType()));}
  | ^(Range a=expr b=expr)
  | ^(Filter Identifier a=expr b=expr) 
  | ^(GENERATOR Identifier a=expr b=expr)
  | ^(GENERATOR ^(ROW Identifier a=expr) ^(COLUMN Identifier b=expr) c=expr)  
  | ^(INDEX vector=expr index=expr)
  ;