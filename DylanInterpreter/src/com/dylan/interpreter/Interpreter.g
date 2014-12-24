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
  : assignment {$node = $assignment.node;}
  | declaration {$node = $declaration.node;}
  | print {$node = $print.node;}
  | ifstatement {$node = $ifstatement.node;}
  | loopstatement {$node = $loopstatement.node;}
  | block {$node = $block.node;}
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
	DValue result = null;
}
	: ^(DECL s=specifier? t=type id=Identifier)
	| ^(DECL s=specifier? t=type ^(Assign id=Identifier value=expr)) {$node = new DeclarationNode($id.text, $value.node, currentScope);}
	;

block returns [DNode node]
@init { 
  BlockNode bn = new BlockNode(); 
  $node = bn;
}  
@after { 
  currentScope = currentScope.getEnclosingScope(); 
}
  : ^(BLOCK {	currentScope = new Scope("blockscope", currentScope);} (statement  {bn.addStatement($statement.node);})*)
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
  : ^(PARAMLIST {	currentScope = new Scope("paramscope", currentScope);} p+=parameter*)
  ;
  
parameter returns [DNode node]
@init {
	VariableSymbol vs = null;
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
  : ^(Assign id=Identifier value=expr) {$node = new AssignmentNode($id.text, $value.node, currentScope);}
  | ^(Assign ^(INDEX Identifier index=expr) value=expr)
  ;
  
ifstatement returns [DNode node]
  : ^(If cond=expr ifBlock=slist ^(Else elseBlock=slist)) {$node = new IfNode($cond.node, $ifBlock.node, $elseBlock.node);}
  | ^(If cond=expr ifBlock=slist) {$node = new IfNode($cond.node, $ifBlock.node);}
  ;
  
loopstatement returns [DNode node]
  : ^(While cond1=expr block1=slist) {$node = new WhileNode($cond1.node, $block1.node, 0);}
  | ^(Do block2=slist ^(While cond2=expr)) {$node = new WhileNode($cond2.node, $block2.node, 1);}
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
  | ^(Minus a=expr b=expr) {$node = new SubtractNode($a.node, $b.node);}
  | ^(Multiply a=expr b=expr) {$node = new MultiplyNode($a.node, $b.node);}
  | ^(Divide a=expr b=expr) {$node = new DivideNode($a.node, $b.node);}
  | ^(Mod a=expr b=expr) {$node = new ModNode($a.node, $b.node);}
  | ^(Exponent a=expr b=expr) {$node = new ExponentNode($a.node, $b.node);}
  | ^(Equals a=expr b=expr) {$node = new EqualsNode($a.node, $b.node);}
  | ^(NEquals a=expr b=expr) {$node = new NotEqualsNode($a.node, $b.node);}
  | ^(GThan a=expr b=expr) {$node = new GreaterThanNode($a.node, $b.node);}
  | ^(LThan a=expr b=expr) {$node = new LessThanNode($a.node, $b.node);}
  | ^(GThanE a=expr b=expr) {$node = new GreaterThanEqualNode($a.node, $b.node);}
  | ^(LThanE a=expr b=expr) {$node = new LessThanEqualNode($a.node, $b.node);}
  | ^(Or a=expr b=expr) {$node = new OrNode($a.node, $b.node);}
  | ^(Xor a=expr b=expr) {$node = new XorNode($a.node, $b.node);}
  | ^(And a=expr b=expr) {$node = new AndNode($a.node, $b.node);}
  | ^(Not a=expr) {$node = new NotNode($a.node);}
  | ^(By expr expr)
  | ^(CALL Identifier ^(ARGLIST expr*))
  | ^(As t=type e=expr)
  | Identifier {$node = new VariableNode($Identifier.text, currentScope);}
  | Number {$node = new AtomNode(new DValue(new Integer($Number.text)));}
  | FPNumber {$node = new AtomNode(new DValue(new Float($FPNumber.text)));}
  | True {$node = new AtomNode(new DValue(new Boolean(true)));}
  | False {$node = new AtomNode(new DValue(new Boolean(false)));}
  | Null {$node = new AtomNode(new DValue());}
  | Char {$node = new AtomNode(new DValue(new Character(Helper.getCharacter($Char.text))));}
  | ^(TUPLEEX expr)
  | ^(Dot Identifier)
  | ^(NEG a=expr) {$node = new NegativeNode($a.node);}
  | ^(POS a=expr) {$node = $a.node;}
  | length
  | reverse
  | ^(VCONST (a=expr {vecResult.add($a.node.evaluate());})+) {$node = new AtomNode(new DValue(vecResult, vecResult.get(0).getType()));}
  | ^(Range a=expr b=expr)
  | ^(Filter Identifier a=expr b=expr) 
  | ^(GENERATOR Identifier a=expr b=expr)
  | ^(GENERATOR ^(ROW Identifier a=expr) ^(COLUMN Identifier b=expr) c=expr)  
  | ^(INDEX vector=expr index=expr)
  ;