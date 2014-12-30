package com.dylan.dnode;

import com.dylan.symbolTable.Scope;

public interface DNode {
	DValue evaluate(Scope currentScope);
}
