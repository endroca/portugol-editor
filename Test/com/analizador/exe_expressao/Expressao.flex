package com.analizador.exe_expressao;

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


/*-*
 * PATTERN DEFINITIONS:
 */
digit           = [0-9]
integer         = {digit}+


%%
{integer}   {return newSym(sym.NUMBER, new Integer(yytext()));}
";"         {return newSym(sym.SEMI);}
"+"         {return newSym(sym.PLUS);}
"-"         {return newSym(sym.MINUS);}
"*"         {return newSym(sym.TIMES);}
"/"         {return newSym(sym.DIVIDE);}
"%"         {return newSym(sym.MOD);}
"("         {return newSym(sym.LPAREN);}
")"         {return newSym(sym.RPAREN);}
<<EOF>>     {return newSym(sym.EOF);}
.           {return newSym(sym.error);}