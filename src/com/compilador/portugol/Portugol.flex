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

/*%caseless*/
/*%ignorecase*/

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


letras          = [A-Za-z]
numeros         = [0-9]
alphanumeric    = {letras}|{numeros}
underline   	= [_]
identifier      = {letras}({alphanumeric}|{underline})*

inteiro			= {numeros}+|\-{numeros}+
fracao			= \.{numeros}*
exponent        = (E|e)(\+|\-)?{numeros}*
float			= ((({numeros}* {fracao} {exponent}?))|({numeros}+\.)|({numeros}+\.? {exponent})) |
				  \-((({numeros}* {fracao} {exponent}?))|({numeros}+\.)|({numeros}+\.? {exponent}))


whitespace      = [\n\r\ \t]

string_content  = (\\\"|[^\n\r\"]|\\{whitespace}+\\)*
string_literal  = {double_qoute}{string_content}{double_qoute}
double_qoute    = \"


string_content_single  = (\\\'|[^\n\r\']|\\{whitespace}+\\){0,1}
string_literal_single  = {double_qoute_single}{string_content_single}{double_qoute_single}
double_qoute_single    = \'

/*
string = {string_literal}
char = {string_literal_single}
*/

string = (\" ( [^\"\n\\] | \\[^\n] )* (\n | \\\n | \")) 
char = (\' ( [^\'\n\\] | \\[^\n] ){0,1} (\n | \\\n | \'))


newline         = \r|\n|\r\n
InputCharacter 	= [^\r\n]

Comment 			= {TraditionalComment} | {EndOfLineComment} 
TraditionalComment 	= "/*" [^*] ~"*/" | "/*" "*"+ "/"
EndOfLineComment 	= "//" {InputCharacter}* {newline}?


%%

{Comment}					{/* IGNORAR COMENTARIOS */}

/*
* ALGORITMO NAME
*/

"algoritmo"					{return newSym(sym.ALGORITMO,yytext()); }


/*
* VARIAVEIS
*/

"var"						{return newSym(sym.VAR,yytext()); }
"inteiro"					{return newSym(sym.TIPO_VARIAVEL,yytext()); }
"real"						{return newSym(sym.TIPO_VARIAVEL,yytext()); }
"caracter"					{return newSym(sym.TIPO_VARIAVEL,yytext()); }
"string"					{return newSym(sym.TIPO_VARIAVEL,yytext()); }
"logico"					{return newSym(sym.TIPO_VARIAVEL,yytext()); }
"vetor"						{return newSym(sym.TIPO_VARIAVEL_VETOR,yytext()); }


/*
* ESTRUTURA
*/

"inicio"					{return newSym(sym.INICIO,yytext()); }
"fimalgoritmo"				{return newSym(sym.FIMALGORITMO,yytext()); }


/*
* CONDICIONAL
*/
"se"						{return newSym(sym.SE,yytext()); }
"entao"						{return newSym(sym.ENTAO,yytext()); }
"senao"						{return newSym(sym.SENAO,yytext()); }
"fimse"						{return newSym(sym.FIMSE,yytext()); }

/*
* FUNCAO REPETICAO
*/
"enquanto"						{return newSym(sym.ENQUANTO,yytext()); }
"faca"							{return newSym(sym.FACA,yytext()); }
"fimenquanto"					{return newSym(sym.FIMENQUANTO,yytext()); }

"para"							{return newSym(sym.PARA,yytext()); }
"fimpara"						{return newSym(sym.FIMPARA,yytext()); }



/*
* FUNCOES
*/
"escreva"						{return newSym(sym.ESCREVA,yytext()); }
"escreval"						{return newSym(sym.ESCREVAL,yytext()); }
"leia"							{return newSym(sym.LEIA,yytext()); }

"escolha"						{return newSym(sym.ESCOLHA,yytext()); }
"caso"							{return newSym(sym.CASO,yytext()); }
"outrocaso"						{return newSym(sym.OUTROCASO,yytext()); }
"fimescolha"					{return newSym(sym.FIMESCOLHA,yytext()); }

"interrompa"					{return newSym(sym.INTERROMPA,yytext()); }
"espere"					{return newSym(sym.ESPERAR,yytext()); }

"funcao"						{return newSym(sym.FUNCAO,yytext()); }
"fimfuncao"						{return newSym(sym.FIMFUNCAO,yytext()); }
"vazio"							{return newSym(sym.VOID,yytext()); }

/*
* AUXILIARES
*/

"de"						{return newSym(sym.DE,yytext()); }
"e"							{return newSym(sym.E,yytext()); }
"ate"						{return newSym(sym.ATE,yytext()); }
"passo"						{return newSym(sym.PASSO,yytext()); }
"ou"						{return newSym(sym.OU,yytext()); }
"nao"						{return newSym(sym.NAO,yytext()); }


/*
* VARIAVEL LOGICA
*/
"verdadeiro"				{return newSym(sym.VALOR_LOGICO,yytext());}
"falso"						{return newSym(sym.VALOR_LOGICO,yytext());}


/*
* OPERADORES
*/

"["							{return newSym(sym.ABRIR_COLCHETE,yytext());}
"]"							{return newSym(sym.FECHAR_COLCHETE,yytext());}
"("         				{return newSym(sym.ABRIR_PARENTESE,yytext());}
")"         				{return newSym(sym.FECHAR_PARENTESE,yytext());}


".."						{return newSym(sym.DOIS_PONTOS,yytext());}
":"							{return newSym(sym.ADD_ATTR_VAR,yytext());}
","							{return newSym(sym.SEPARADOR,yytext());}


"+"         				{return newSym(sym.OPERADOR_SOMA,yytext());}
"-"         				{return newSym(sym.OPERADOR,yytext());}
"*"         				{return newSym(sym.OPERADOR,yytext());}
"/"         				{return newSym(sym.OPERADOR,yytext());}
"mod"         				{return newSym(sym.OPERADOR,yytext());}
"div"         				{return newSym(sym.OPERADOR,yytext());}



"pot"						{return newSym(sym.POTENCIA,yytext());}
"modulo"					{return newSym(sym.MODULO,yytext());}
"sen"						{return newSym(sym.SENO,yytext());}
"cos"						{return newSym(sym.COSSENO,yytext());}
"tan"						{return newSym(sym.TANGENTE,yytext());}
"raiz"						{return newSym(sym.RAIZ,yytext());}

"PI"						{return newSym(sym.PI,yytext());}

"<"							{return newSym(sym.CONDICIONAL_NUMERICA,yytext());}
"<="						{return newSym(sym.CONDICIONAL_NUMERICA,yytext());}
">"							{return newSym(sym.CONDICIONAL_NUMERICA,yytext());}
">="						{return newSym(sym.CONDICIONAL_NUMERICA,yytext());}

"<>"						{return newSym(sym.CONDICIONAL_COMPARATIVA,yytext());}
"="         				{return newSym(sym.CONDICIONAL_COMPARATIVA,yytext());}


"<-"						{return newSym(sym.ATRIBUIR,yytext());}
":="						{return newSym(sym.ATRIBUIR,yytext());}




/*
* REGIDOS POR REGEXP
*/
{inteiro}					{return newSym(sym.NUMERO_INT, yytext()); }
{float}   					{return newSym(sym.NUMERO_FLOAT, yytext()); }
{identifier}				{return newSym(sym.ID,yytext()); }
{string}					{return newSym(sym.STRING, yytext()); }
{char}                      {return newSym(sym.CHAR, yytext()); }
{newline}					{/* IGNORE */}
{whitespace}				{/* IGNORE */}


<<EOF>>     				{return newSym(sym.EOF);}
.           				{return newSym(sym.error,yytext()); }