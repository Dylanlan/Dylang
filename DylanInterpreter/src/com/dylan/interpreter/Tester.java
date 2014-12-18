package com.dylan.interpreter;

import java.io.FileNotFoundException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.TokenStream;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.CommonTreeNodeStream;
import org.antlr.runtime.tree.DOTTreeGenerator;
import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;

public class Tester {
	public static void main(String[] args) throws RecognitionException {
		if (args.length != 1) {
			System.err.print("Usage: Tester <dash_file>");
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
        		//SymbolTable symtab = new SymbolTable();
        		SyntaxLexer lexer = new SyntaxLexer(input);
        		TokenStream tokenStream = new CommonTokenStream(lexer);
        		SyntaxParser parser = new SyntaxParser(tokenStream);
        		SyntaxParser.program_return entry = parser.program();
        		CommonTree ast = (CommonTree)entry.getTree();
        		DOTTreeGenerator gen = new DOTTreeGenerator();
        		StringTemplate st = gen.toDOT(ast);
        		System.out.println(st);
        			
        		CommonTreeNodeStream nodes = new CommonTreeNodeStream(ast);
        		nodes.setTokenStream(tokenStream);
        		
        			
        		st = gen.toDOT(ast);
        		//System.out.println(st);
        		
        		nodes = new CommonTreeNodeStream(ast);
        		nodes.setTokenStream(tokenStream);
        		Interpreter interpret = new Interpreter(nodes);
        		interpret.program();
		//}
		//catch (RuntimeException e) {
		//    System.out.println("A problem has occured with the dash input file: " + e.getMessage());
        //            System.out.println("Please check the input file for correctness.");
        //            System.exit(1);		    
		//}
	}
	
}