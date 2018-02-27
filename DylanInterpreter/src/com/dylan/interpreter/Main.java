package com.dylan.interpreter;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.TokenStream;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.CommonTreeNodeStream;
import org.antlr.runtime.tree.DOTTreeGenerator;
import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;

import com.dylan.dnode.*;
import com.dylan.symbolTable.FunctionSymbol;
import com.dylan.symbolTable.SymbolTable;

public class Main {
	public static void main(String[] args) throws RecognitionException {
		if (args.length != 1) {
			System.err.print("Usage: Tester <file>");
			System.exit(1);
		}
		String inputfile = null;
		ANTLRFileStream input = null;
		try {
			//inputfile = "test.ds";
			inputfile = args[0];
			input = new ANTLRFileStream(inputfile);
		} catch (IOException e) {
			System.err.print("Invalid program filename: ");
			System.err.println(args[0]);
			System.exit(1);
		}

		//try {
        		SymbolTable symTab = new SymbolTable();
        		SyntaxLexer lexer = new SyntaxLexer(input);
        		TokenStream tokenStream = new CommonTokenStream(lexer);
        		SyntaxParser parser = new SyntaxParser(tokenStream);
        		SyntaxParser.program_return entry = parser.program();
        		CommonTree ast = (CommonTree)entry.getTree();
        		DOTTreeGenerator gen = new DOTTreeGenerator();
        		StringTemplate st = gen.toDOT(ast);
        		//System.out.println(st);
        			
        		CommonTreeNodeStream nodes = new CommonTreeNodeStream(ast);
        		nodes.setTokenStream(tokenStream);

        		
        		nodes = new CommonTreeNodeStream(ast);
        		nodes.setTokenStream(tokenStream);
        		HashMap<String, FunctionSymbol> functions = new HashMap<String, FunctionSymbol>();
        		Interpreter interpret = new Interpreter(nodes, functions);
        		DNode node = interpret.program().node;
        		node.evaluate(symTab.globals);
        		if (functions.containsKey("main")) {
        			functions.get("main").invoke(new ArrayList<DNode>(), functions, symTab.globals);
        		}
		//}
		//catch (RuntimeException e) {
		//    System.out.println("A problem has occured with the dash input file: " + e.getMessage());
        //            System.out.println("Please check the input file for correctness.");
        //            System.exit(1);		    
		//}
	}
	
}