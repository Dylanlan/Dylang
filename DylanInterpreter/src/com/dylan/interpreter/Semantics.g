tree grammar Semantics;

options {
  language = Java;
  output = AST;
  tokenVocab = Syntax;
  ASTLabelType = CommonTree;
}

@header
{
  package com.dylan.interpreter;
}

rule: ;
