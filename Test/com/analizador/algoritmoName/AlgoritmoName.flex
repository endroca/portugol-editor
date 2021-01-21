package com.analizador.algoritmoName;

import java_cup.runtime.*;


%%
/*
* LEXICAL FUNCTIONS:
*/
%cup
%line
%char
%column

%{

    Symbol newSym(int tokenId) {
        return new Symbol(tokenId , yyline, yycolumn);
    }

    Symbol newSym(int tokenId, Object value) {
        return new Symbol(tokenId , yyline, yycolumn, value);
    }

%}


letras          = [A-Za-z]
numeros         = [0-9]
alphanumeric    = {letras}|{numeros}
underline   	= [_]
identifier      = {letras}({alphanumeric}|{underline})*

whitespace      = [\n\r\ \t]
string_content  = (\\\"|[^\n\r\"]|\\{whitespace}+\\)*
string_literal  = {double_qoute}{string_content}{double_qoute}
double_qoute    = \"
newline         = \r|\n|\r\n


%%
(algoritmo|ALGORITMO)	{return newSym(sym.ALGORITMO,yytext());}

{identifier}			{return newSym(sym.ID,yytext());}
{string_literal}		{ return newSym(sym.STRING, yytext().substring(1, yylength()-1)); }

{newline}				{/* IGNORE */}
{whitespace}			{/* IGNORE */}



<<EOF>>     {return newSym(sym.EOF);}
.           {return newSym(sym.error,yytext());}