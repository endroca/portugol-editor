////////////////////////////////////////////////////////////////////////
/*                                                                  ////
* ANALIZADOR DE PORTUGOL (TOKENS).   							    ////
* BY: ANDREW NETO                                                   ////
* 05/07/2014                                                        ////
*/                                                                  ////
////////////////////////////////////////////////////////////////////////

package com.compilador.portugol;

import java_cup.runtime.*;


%%
%class PortugolFlex
%cup
%line
%char
%column


%{

    public PortugolFlex() {
        super();
    }

    Symbol newSym(int tokenId) {
        return new Symbol(tokenId , yyline, yycolumn);
    }

    Symbol newSym(int tokenId, Object value) {
        return new Symbol(tokenId , yyline, yycolumn, value);
    }

%}


newline         = \r|\n|\r\n
full            = .*



%%


"var"						{return newSym(sym.VAR,yytext()); }

"inicio"					{return newSym(sym.INICIO,yytext()); }
"fimalgoritmo"				{return newSym(sym.FIMALGORITMO,yytext()); }

"se"						{return newSym(sym.SE,yytext()); }
"senao"						{return newSym(sym.SENAO,yytext()); }
"fimse"						{return newSym(sym.FIMSE,yytext()); }

"enquanto"					{return newSym(sym.ENQUANTO,yytext()); }
"fimenquanto"				{return newSym(sym.FIMENQUANTO,yytext()); }

"para"						{return newSym(sym.PARA,yytext()); }
"fimpara"					{return newSym(sym.FIMPARA,yytext()); }

"escolha"					{return newSym(sym.ESCOLHA,yytext()); }
"caso"						{return newSym(sym.CASO,yytext()); }
"outrocaso"					{return newSym(sym.OUTROCASO,yytext()); }
"fimescolha"				{return newSym(sym.FIMESCOLHA,yytext()); }

{newline}					{/* IGNORE */}
{whitespace}				{/* IGNORE */}
{full}                      {return newSym(sym.ALL,yytext()); }

<<EOF>>     				{return newSym(sym.EOF);}
.           				{return newSym(sym.error,yytext()); }