////////////////////////////////////////////////////////////////////
/*																////
*	DECLARACAO DE VARIAVEIS EM PORTUGOL (ANALIZADOR LEXICO).	////
*	BY: ANDREW NETO												////
*	19/06/2014													////
*/																////
////////////////////////////////////////////////////////////////////

package com.analizador.var;

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


%%

(var|VAR)				{ return newSym(sym.VAR,yytext());}

(inteiro|INTEIRO)		{ return newSym(sym.TIPO_VARIAVEL,"int");}
(real|REAL)				{ return newSym(sym.TIPO_VARIAVEL,"float");}
(caracter|CARACTER)		{ return newSym(sym.TIPO_VARIAVEL,"String");}

":"						{ return newSym(sym.ADD_ATTR_VAR,yytext());}
","						{ return newSym(sym.SEPARADOR,yytext());}

{ids}					{ return newSym(sym.ID,(String) yytext());}
{espaco}				{/*IGNORAR*/}
{nova_linha}			{/*IGNORAR*/}

<<EOF>>     			{ return newSym(sym.EOF);}
.           			{ return newSym(sym.error,yytext()); }