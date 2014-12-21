package com.dylan.symbolTable;

import java.util.*;

public class SymbolTable {
	
    public Scope globals;
    Map<String, TypeSymbol> types;
    Map<String, TypeDefSymbol> userDefinedTypes;
    //Map<String, SpecSymbol> specs;
    Map<String, FunctionSymbol> functionSymbols;
    
    public SymbolTable() { 
    	this.globals = new Scope("global", null, 0);
    	this.types = new HashMap<String, TypeSymbol>();
    	this.functionSymbols = new HashMap<String, FunctionSymbol>();
    	//this.specs = new HashMap<String, BuiltInTypeSymbol>();
    	this.userDefinedTypes = new HashMap<String, TypeDefSymbol>();
    	initTypeSystem(); 
    }
    
    protected void initTypeSystem() {
    	defineType(new ScalarTypeSymbol("boolean"));
    	defineType(new ScalarTypeSymbol("character"));
        defineType(new ScalarTypeSymbol("integer"));
        defineType(new ScalarTypeSymbol("float"));
        //defineType(new ScalarTypeSymbol("tuple"));
        defineType(new NonScalarTypeSymbol("vector"));
        //defineType(new ScalarTypeSymbol("matrix"));
        defineType(new NonScalarTypeSymbol("string", new ScalarTypeSymbol("character")));
        defineType(new ScalarTypeSymbol("null"));
        
        ArrayList<Symbol> params = new ArrayList<Symbol>();
        params.add(new VariableSymbol("vectorParam", new NonScalarTypeSymbol("vector")));
        FunctionSymbol funcSym = new FunctionSymbol("length", new ScalarTypeSymbol("integer"), params);
        defineFunction(funcSym);
        
        //defineSpec(new SpecifierSymbol("var"));
        //defineSpec(new SpecifierSymbol("const"));
    }
    
    Boolean[][] promotelookup = {
			{false, null, null, null, null, null, true, null}, //boolean
			{null, false, true, null, null, null, true, null}, //integer
			{null, null, false, null, null, null, true, null}, //real
			{null, null, null, false, null, null, true, null}, //interval
			{null, null, null, null, false, null, true, null}, //character
			{null, null, null, null, null, false, null, null}, //tuple
			{null, null, null, null, null, null, false, null}, //vector
			{null, null, null, null, null, null, null, false}  //matrix
	};
    
    Boolean[][] expromotelookup = {
			{false, true, true, null, true, null, true, null},
			{true, false, true, null, true, null, true, null},
			{null, true, false, null, null, null, true, null},
			{null, null, null, false, null, null, true, null},
			{true, true, true, null, false, null, true, null},
			{null, null, null, null, null, false, null, null},
			{null, null, null, null, null, null, false, null},
			{null, null, null, null, null, null, null, false}
	};
    
    public TypeSymbol getTypeSymbol(String name) {
        String bitname = name;
        String oldname;
        do {
          oldname = bitname;
          bitname = resolveTDType(bitname).getTypeSymbol().getName();
        } while (!bitname.equals("null"));
        
        return resolveType(oldname);
    }
    
    public Boolean logicValidity(TypeSymbol t1, TypeSymbol t2) {
    	String basetype1 = "";
    	String basetype2 = "";
    	if (t1.getName().equals("vector")) {
    		NonScalarTypeSymbol nonScalar = (NonScalarTypeSymbol) t1;
    		basetype1 = nonScalar.getScalarName();
    	} else {
    		basetype1 = t1.getName();
    	}
    	
    	if (t2.getName().equals("vector")) {
    		NonScalarTypeSymbol nonScalar = (NonScalarTypeSymbol) t2;
    		basetype2 = nonScalar.getScalarName();
    	} else {
    		basetype2 = t2.getName();
    	}
    	
    	if (basetype1.equals("boolean") && basetype2.equals("boolean")) {
    		return true;
    	}
    	else {
    		return false;
    	}
    }
    
    public Boolean arithmeticValidity(TypeSymbol t1, TypeSymbol t2) {
    	String basetype1 = "";
    	String basetype2 = "";
    	if (t1.getName().equals("vector")) {
    		NonScalarTypeSymbol nonScalar = (NonScalarTypeSymbol) t1;
    		basetype1 = nonScalar.getScalarName();
    	} else {
    		basetype1 = t1.getName();
    	}
    	
    	if (t2.getName().equals("vector")) {
    		NonScalarTypeSymbol nonScalar = (NonScalarTypeSymbol) t2;
    		basetype2 = nonScalar.getScalarName();
    	} else {
    		basetype2 = t2.getName();
    	}
    	
    	if ((basetype1.equals("integer") || basetype1.equals("float")) &&
    			(basetype2.equals("integer") || basetype2.equals("float"))) {
    		return true;
    	}
    	else {
    		return false;
    	}
    }
    
    public Boolean compareValidity(TypeSymbol t1, TypeSymbol t2) {
    	String basetype1 = "";
    	String basetype2 = "";
    	if (t1.getName().equals("vector")) {
    		NonScalarTypeSymbol nonScalar = (NonScalarTypeSymbol) t1;
    		basetype1 = nonScalar.getScalarName();
    	} else {
    		basetype1 = t1.getName();
    	}
    	
    	if (t2.getName().equals("vector")) {
    		NonScalarTypeSymbol nonScalar = (NonScalarTypeSymbol) t2;
    		basetype2 = nonScalar.getScalarName();
    	} else {
    		basetype2 = t2.getName();
    	}
    	
    	if ((basetype1.equals("integer") || basetype1.equals("real") || basetype1.equals("character")) &&
    			(basetype2.equals("integer") || basetype2.equals("real") || basetype2.equals("character"))) {
    		return true;
    	}
    	else {
    		return false;
    	}
    }
    
    public Boolean lookup(TypeSymbol tst1, TypeSymbol tst2) {
    	if (!tst1.getName().equals("vector") && tst2.getName().equals("vector")) {
    		NonScalarTypeSymbol nonScalar = (NonScalarTypeSymbol) tst2;
    		return lookup(tst1, nonScalar.getScalarType());
    	} else if (tst1.getName().equals("vector") && tst2.getName().equals("vector")) {
    		NonScalarTypeSymbol nonScalar1 = (NonScalarTypeSymbol) tst1;
    		NonScalarTypeSymbol nonScalar2 = (NonScalarTypeSymbol) tst2;
    		return lookup(nonScalar1.getScalarType(), nonScalar2.getScalarType());
    	}
    	
    	Integer i1 = null;
    	Integer i2 = null;
    	String st1 = "";
    	String st2 = "";
    	try {
    		st1 = getTypeSymbol(tst1.getName()).getName();
    		st2 = getTypeSymbol(tst2.getName()).getName();
    	} catch (NullPointerException npe) {
    		throw new RuntimeException("undefined type error, use typedef to define user types");
    	}
    	
    	if (st1.equals("boolean")) {
    	    i1 = 0;
    	}
    	else if (st1.equals("integer")) {
    		i1 = 1;
    	}
    	else if (st1.equals("real")) {
    		i1 = 2;
    	}
    	else if (st1.equals("interval")) {
    		i1 = 3;
    	}
    	else if (st1.equals("character")) {
    		i1 = 4;
    	}
    	else if (st1.equals("tuple")) {
    		i1 = 5;
    	}
    	else if (st1.equals("vector")) {
    		i1 = 6;
    	}
    	else if (st1.equals("matrix")) {
    		i1 = 7;
    	}
    	
    	if (st2.equals("boolean")) {
            i2 = 0;
        }
    	else if (st2.equals("integer")) {
    		i2 = 1;
    	}
    	else if (st2.equals("real")) {
    		i2 = 2;
    	}
    	else if (st2.equals("interval")) {
    		i2 = 3;
    	}
    	else if (st2.equals("character")) {
    		i2 = 4;
    	}
    	else if (st2.equals("tuple")) {
    		i2 = 5;
    	}
    	else if (st2.equals("vector")) {
    		i2 = 6;
    	}
    	else if (st2.equals("matrix")) {
    		i2 = 7;
    	}
    	
    	return promotelookup[i1][i2];
    }
	public Boolean exLookup(TypeSymbol tst1, TypeSymbol tst2) {
		Integer i1 = null;
    	Integer i2 = null;
    	String st1 = getTypeSymbol(tst1.getName()).getName();
    	String st2 = getTypeSymbol(tst2.getName()).getName();
    	if (st1.equals("boolean")) {
            i1 = 0;
        }
        else if (st1.equals("integer")) {
                i1 = 1;
        }
        else if (st1.equals("real")) {
                i1 = 2;
        }
        else if (st1.equals("interval")) {
                i1 = 3;
        }
        else if (st1.equals("character")) {
                i1 = 4;
        }
        else if (st1.equals("tuple")) {
            i1 = 5;
        }
        else if (st1.equals("vector")) {
    		i1 = 6;
    	}
        else if (st1.equals("matrix")) {
    		i1 = 7;
    	}

    	if (st2.equals("boolean")) {
            i2 = 0;
        }
        else if (st2.equals("integer")) {
                i2 = 1;
        }
        else if (st2.equals("real")) {
                i2 = 2;
        }
        else if (st2.equals("interval")) {
                i2 = 3;
        }
        else if (st2.equals("character")) {
                i2 = 4;
        }
        else if (st2.equals("tuple")) {
                i2 = 5;
        }
        else if (st2.equals("vector")) {
    		i2 = 6;
    	}
        else if (st2.equals("matrix")) {
    		i2 = 7;
    	}
    	
    	return expromotelookup[i2][i1];
	}
	
    public String getScopeName() { return "global"; }
    
    public void define(Symbol sym) { globals.define(sym); }
    
    public void defineType(TypeDefSymbol sym) {
    	types.put(sym.getName(), sym);
    	userDefinedTypes.put(sym.getName(), sym);
    }
    
    protected void defineType(TypeSymbol sym) {
    	types.put(sym.getName(), sym); 
    }
    
    //protected void defineSpec(BuiltInTypeSymbol sym) {
    //	specs.put(sym.getName(), sym);
    //}
    
    public void defineFunction(FunctionSymbol sym) {
    	functionSymbols.put(sym.getName(), sym);
    }
    
    public Symbol resolve(String name) { return globals.resolve(name); }
    
    //public BuiltInTypeSymbol resolveSpec(String name) { return specs.get(name); }
    
    public TypeSymbol resolveType(String name) { return types.get(name); }
    
    public TypeDefSymbol resolveTDType(String name) {
    	TypeDefSymbol tds = userDefinedTypes.get(name); 
    	if (tds != null) {
    		return tds;
    	}
    	
    	return new TypeDefSymbol(new ScalarTypeSymbol("null"), "null");
    }
    
    public FunctionSymbol resolveFunction(String name) { return functionSymbols.get(name); }
}