////////////////////////////////////////////////////////////////////
/*                                                        	    ////
* DECLARACAO DE EXPRESSOES EM PORTUGOL (ANALIZADOR LEXICO). 	////
* BY: ANDREW NETO                                               ////
* 20/06/2014                                                    ////
*/                                                              ////
////////////////////////////////////////////////////////////////////


package com.analizador.expressao;

import java_cup.runtime.*;


%%
%cup
%line
%column

%{

    Symbol newSym(int tokenId) {
        return new Symbol(tokenId , yyline, yycolumn);
    }

    Symbol newSym(int tokenId, Object value) {
        return new Symbol(tokenId , yyline, yycolumn, value);
    }

%}

caracters				= [A-Za-z]
numerais				= [0-9]
alphanumeric			= {caracters}|{numerais}
outros					= [_]
ids			      		= {caracters}({alphanumeric}|{outros})*
espaco					= [\n\r\ \t]
nova_linha				= \r|\n|\r\n
inteiro					= {numerais}+


string_content  = (\\\"|[^\n\r\"]|\\{espaco}+\\)*
string_literal  = {double_qoute}{string_content}{double_qoute}
double_qoute    = \"
%%

/*
*	PARA TESTE
*/
"<-"			{ return newSym(sym.ATRIBUIR,yytext());}
":="			{ return newSym(sym.ATRIBUIR,yytext());}



{string_literal}		{ return newSym(sym.STRING, yytext().substring(1, yylength()-1)); }
{inteiro}   			{return newSym(sym.NUMERO,(String) yytext());}
{ids}					{return newSym(sym.ID,yytext());}
{espaco}				{/* IGNORAR */}
{nova_linha}			{/* IGNORAR */}
"+"         			{return newSym(sym.OPERADOR,yytext());}
"-"         			{return newSym(sym.OPERADOR,yytext());}
"*"         			{return newSym(sym.OPERADOR,yytext());}
"/"         			{return newSym(sym.OPERADOR,yytext());}
"%"         			{return newSym(sym.OPERADOR,"%");}
"("         			{return newSym(sym.ABRIR_PARENTESE,yytext());}
")"         			{return newSym(sym.FECHAR_PARENTESE,yytext());}
<<EOF>>     			{return newSym(sym.EOF);}
.           			{return newSym(sym.error,yytext());}