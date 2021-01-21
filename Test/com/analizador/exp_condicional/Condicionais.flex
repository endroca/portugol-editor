////////////////////////////////////////////////////////////////////
/*																////
*	DECLARACAO DE VARIAVEIS EM PORTUGOL (ANALIZADOR LEXICO).	////
*	BY: ANDREW NETO												////
*	20/06/2014													////
*/																////
////////////////////////////////////////////////////////////////////

package com.analizador.exp_condicional;

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
inteiro                                 = {numerais}+
string_content  = (\\\"|[^\n\r\"]|\\{espaco}+\\)*
string_literal  = {double_qoute}{string_content}{double_qoute}
double_qoute    = \"


%%
/* PARA TESTE LIMITADOR */

(se|SE)						{ return newSym(sym.SE,yytext());}
(entao|ENTAO)				{ return newSym(sym.ENTAO,yytext());}

(e|E)						{ return newSym(sym.CONDICIONAL_E,"&&");}
(ou|OU)						{ return newSym(sym.CONDICIONAL_OU,"||");}


"<"							{ return newSym(sym.CONDICIONAL,yytext());}
"<="						{ return newSym(sym.CONDICIONAL,yytext());}
">"							{ return newSym(sym.CONDICIONAL,yytext());}
">="						{ return newSym(sym.CONDICIONAL,yytext());}



"<>"						{ return newSym(sym.CONDICIONAL_ESPECIAL,"!=");}
"="         				{return newSym(sym.CONDICIONAL_ESPECIAL,"==");}



"+"         				{return newSym(sym.OPERADOR,yytext());}
"-"         				{return newSym(sym.OPERADOR,yytext());}
"*"         				{return newSym(sym.OPERADOR,yytext());}
"/"         				{return newSym(sym.OPERADOR,yytext());}
"%"         				{return newSym(sym.OPERADOR,"%");}



"("         				{return newSym(sym.ABRIR_PARENTESE);}
")"         				{return newSym(sym.FECHAR_PARENTESE);}



{string_literal}			{ return newSym(sym.STRING, (String) yytext().substring(1, yylength()-1)); }
{inteiro}   				{return newSym(sym.NUMERO,(String) yytext());}
{ids}						{return newSym(sym.ID,yytext());}
{espaco}					{/* IGNORAR */}
{nova_linha}				{/* IGNORAR */}


<<EOF>>     				{ return newSym(sym.EOF);}
.           				{ return newSym(sym.error,yytext()); }