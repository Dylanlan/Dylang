tree grammar Templater;

options {
  language = Java;
  output = template;
  tokenVocab = Syntax;
  ASTLabelType = CommonTree;
}

@header
{
  package com.dylan.interpreter;
}

rule: ;
