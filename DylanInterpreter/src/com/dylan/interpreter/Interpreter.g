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
	//Scope currentScope = null;
	Map<String, FunctionSymbol> functions = null;
	String intType = "int";
	String floatType = "float";
	String charType = "char";
	String boolType = "bool";
  
  public Interpreter(CommonTreeNodeStream nodes, Map<String, FunctionSymbol> fns) {
    this(nodes);
    functions = fns;
  }
  
  public Interpreter(CommonTreeNodeStream nds, Scope sc, Map<String, FunctionSymbol> fns) {
    this(nds);
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
  | expr {$node = $expr.node;}
  ;

print returns [DNode node]
  : ^(Print value=expr arg=expr) {$node = new PrintNode($value.node, $arg.node);}
  | ^(Print value=expr) {$node = new PrintNode($value.node);}
  ;
  
length returns [DNode node]
	: ^(Length expr) {$node = new BuiltInFunctionNode($expr.node, BuiltInFunctionNode.BIF_LENGTH);}
	;
	
reverse returns [DNode node]
	: ^(Reverse expr) {$node = new BuiltInFunctionNode($expr.node, BuiltInFunctionNode.BIF_REVERSE);}
	;

declaration returns [DNode node]
@init {
	VariableSymbol vs = null;
	DValue result = null;
}
	: ^(DECL s=specifier? t=type id=Identifier)
	| ^(DECL s=specifier? t=type ^(Assign id=Identifier value=expr)) {$node = new DeclarationNode($id.text, $value.node);}
	;

block returns [DNode node]
@init { 
  BlockNode bn = new BlockNode(); 
  $node = bn;
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
	functions.put($id.text, fs);
}
  : ^(Function id=Identifier p=paramlist ^(Returns t=type?) b=block)
  ;
  
paramlist returns [DNode node]
  : ^(PARAMLIST p+=parameter*)
  ;
  
parameter returns [DNode node]
  : ^(id=Identifier s=specifier? t=type)
  ;
  
callStatement returns [DNode node]
  : ^(CALL Identifier ^(ARGLIST expr*))
  ;
  
returnStatement returns [DNode node]
  : ^(Return expr) {$node = new ReturnNode($expr.node);}
  | Return {$node = new ReturnNode();}
  ;
  
assignment returns [DNode node]
  : ^(Assign id=Identifier value=expr) {$node = new AssignmentNode($id.text, $value.node);}
  | ^(Assign ^(INDEX id=Identifier index=expr) value=expr)
  | ^(Assign id=Identifier Plus value=expr) {$node = new PlusAssignNode($id.text, $value.node);}
  | ^(Assign id=Identifier Minus value=expr) {$node = new MinusAssignNode($id.text, $value.node);}
  | ^(Assign id=Identifier Multiply value=expr) {$node = new MultiplyAssignNode($id.text, $value.node);}
  | ^(Assign id=Identifier Divide value=expr) {$node = new DivideAssignNode($id.text, $value.node);}
  | ^(Assign id=Identifier Mod value=expr) {$node = new ModAssignNode($id.text, $value.node);}
  ;
  
ifstatement returns [DNode node]
  : ^(If cond=expr ifBlock=slist ^(Else elseBlock=slist)) {$node = new IfNode($cond.node, $ifBlock.node, $elseBlock.node);}
  | ^(If cond=expr ifBlock=slist) {$node = new IfNode($cond.node, $ifBlock.node);}
  ;
  
loopstatement returns [DNode node]
@init {
	List<DNode> decls = new ArrayList<DNode>();
	List<DNode> assigns = new ArrayList<DNode>();
}
  : ^(While cond1=expr block1=slist) {$node = new WhileNode($cond1.node, $block1.node, WhileNode.WHILE);}
  | ^(Do block2=slist ^(While cond2=expr)) {$node = new WhileNode($cond2.node, $block2.node, WhileNode.DO_WHILE);}
  | ^(Loop num=expr exec=slist) {$node = new WhileNode($num.node, $exec.node, WhileNode.LOOP);}
  | ^(For (declaration {decls.add($declaration.node);})* cond=expr? (assignment {assigns.add($assignment.node);})* ^(BODY block3=slist)) {$node = new ForNode(decls, $cond.node, assigns, $block3.node);}
  | ^(For type id=Identifier expr ^(BODY slist)) {$node = new ForNode($id.text, $expr.node, $slist.node);}
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
	List<DNode> nodeList = new ArrayList<DNode>();
}
  : ^(Plus a=expr b=expr) {$node = new BinaryOperationNode($a.node, $b.node, BinaryOperationNode.BON_ADD);}
  | ^(Minus a=expr b=expr) {$node = new BinaryOperationNode($a.node, $b.node, BinaryOperationNode.BON_SUB);}
  | ^(Multiply a=expr b=expr) {$node = new BinaryOperationNode($a.node, $b.node, BinaryOperationNode.BON_MUL);}
  | ^(Divide a=expr b=expr) {$node = new BinaryOperationNode($a.node, $b.node, BinaryOperationNode.BON_DIV);}
  | ^(Mod a=expr b=expr) {$node = new BinaryOperationNode($a.node, $b.node, BinaryOperationNode.BON_MOD);}
  | ^(Exponent a=expr b=expr) {$node = new BinaryOperationNode($a.node, $b.node, BinaryOperationNode.BON_POW);}
  | ^(Equals a=expr b=expr) {$node = new BinaryOperationNode($a.node, $b.node, BinaryOperationNode.BON_EQUAL);}
  | ^(NEquals a=expr b=expr) {$node = new BinaryOperationNode($a.node, $b.node, BinaryOperationNode.BON_NEQUAL);}
  | ^(GThan a=expr b=expr) {$node = new BinaryOperationNode($a.node, $b.node, BinaryOperationNode.BON_GREATER);}
  | ^(LThan a=expr b=expr) {$node = new BinaryOperationNode($a.node, $b.node, BinaryOperationNode.BON_LESS);}
  | ^(GThanE a=expr b=expr) {$node = new BinaryOperationNode($a.node, $b.node, BinaryOperationNode.BON_GREATER_E);}
  | ^(LThanE a=expr b=expr) {$node = new BinaryOperationNode($a.node, $b.node, BinaryOperationNode.BON_LESS_E);}
  | ^(Or a=expr b=expr) {$node = new BinaryOperationNode($a.node, $b.node, BinaryOperationNode.BON_OR);}
  | ^(Xor a=expr b=expr) {$node = new BinaryOperationNode($a.node, $b.node, BinaryOperationNode.BON_XOR);}
  | ^(And a=expr b=expr) {$node = new BinaryOperationNode($a.node, $b.node, BinaryOperationNode.BON_AND);}
  | ^(Not a=expr) {$node = new UnaryOperationNode($a.node, UnaryOperationNode.UON_NOT);}
  | ^(By expr expr)
  | ^(CALL id=Identifier ^(ARGLIST (a=expr {nodeList.add($a.node);})*)) {$node = new FunctionNode($id.text, nodeList, functions);}
  | ^(As t=type e=expr)
  | Identifier {$node = new VariableNode($Identifier.text);}
  | Number {$node = new AtomNode(new DValue(new Integer($Number.text)));}
  | FPNumber {$node = new AtomNode(new DValue(new Float($FPNumber.text)));}
  | True {$node = new AtomNode(new DValue(new Boolean(true)));}
  | False {$node = new AtomNode(new DValue(new Boolean(false)));}
  | Null {$node = new AtomNode(new DValue());}
  | Char {$node = new AtomNode(new DValue(new Character(Helper.getCharacter($Char.text))));}
  | ^(TUPLEEX expr)
  | ^(Dot Identifier)
  | ^(NEG a=expr) {$node = new UnaryOperationNode($a.node, UnaryOperationNode.UON_NEG);}
  | ^(POS a=expr) {$node = $a.node;}
  | length {$node = $length.node;}
  | reverse {$node = $reverse.node;}
  | ^(VCONST (a=expr {nodeList.add($a.node);})+) {$node = new AtomNode(nodeList);}
  | ^(Range a=expr b=expr) {$node = new RangeNode($a.node, $b.node);}
  | ^(Filter Identifier a=expr b=expr) 
  | ^(GENERATOR type id=Identifier a=expr b=expr) {$node = new GeneratorNode($id.text, $a.node, $b.node);}
  | ^(INDEX vector=expr index=expr)
  | ^(PREINCREMENT id=Identifier) {$node = new UnaryOperationNode($id.text, UnaryOperationNode.UON_PRE_INCR);}
  | ^(PREDECREMENT id=Identifier) {$node = new UnaryOperationNode($id.text, UnaryOperationNode.UON_PRE_DECR);}
  | ^(POSTINCREMENT id=Identifier) {$node = new UnaryOperationNode($id.text, UnaryOperationNode.UON_POST_INCR);}
  | ^(POSTDECREMENT id=Identifier) {$node = new UnaryOperationNode($id.text, UnaryOperationNode.UON_POST_DECR);}
  | ^(TERNARY a=expr b=expr c=expr) {$node = new TernaryOperationNode($a.node, $b.node, $c.node);}
  | ^(Filter type id=Identifier vector=expr (cond=expr {nodeList.add($cond.node);})+) {$node = new FilterNode($id.text, $vector.node, nodeList);}   
  ;