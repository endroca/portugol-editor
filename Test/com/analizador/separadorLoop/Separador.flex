////////////////////////////////////////////////////////////////////
/*																////
*	DECLARACAO DE VARIAVEIS EM PORTUGOL (ANALIZADOR LEXICO).	////
*	BY: ANDREW NETO												////
*	19/06/2014													////
*/																////
////////////////////////////////////////////////////////////////////

package com.analizador.separadorLoop;

import java_cup.runtime.*;


%%
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

caracters				= [A-Za-z]
numerais				= [0-9]
alphanumeric			= {caracters}|{numerais}
outros					= [_]
ids			      		= {caracters}({alphanumeric}|{outros})*
espaco					= [\n\r\ \t]
nova_linha				= \r|\n|\r\n

inteiro					= {numerais}+

string_content  		= (\\\"|[^\n\r\"]|\\{espaco}+\\)*
string_literal  		= {double_qoute}{string_content}{double_qoute}
double_qoute    		= \"



%%

(escreva|ESCREVA)		{ return newSym(sym.ESCREVA,yytext());}


","						{ return newSym(sym.SEPARADOR,yytext());}


"("						{return newSym(sym.ABRIR_PARENTESE);}
")"						{return newSym(sym.FECHAR_PARENTESE);}

"+"         			{return newSym(sym.OPERADOR,yytext());}
"-"         			{return newSym(sym.OPERADOR,yytext());}
"*"         			{return newSym(sym.OPERADOR,yytext());}
"/"         			{return newSym(sym.OPERADOR,yytext());}
"%"         			{return newSym(sym.OPERADOR,"%");}

{inteiro}   			{return newSym(sym.NUMERO,yytext());}
{string_literal}		{ return newSym(sym.STRING,yytext().substring(1, yylength()-1)); }
{ids}					{ return newSym(sym.ID,yytext());}
{espaco}				{/*IGNORAR*/}
{nova_linha}			{/*IGNORAR*/}



<<EOF>>     			{ return newSym(sym.EOF);}
.           			{ return newSym(sym.error,yytext()); }