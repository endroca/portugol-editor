package com.editor.flex;


import jsyntaxpane.Token;
import jsyntaxpane.TokenType;
import jsyntaxpane.lexers.DefaultJFlexLexer;


%%

%public
%class PortugolFlex
%extends DefaultJFlexLexer
%final
%unicode
%char
%type Token

/*%caseless*/
/*%ignorecase*/

%{
    /**
     * Create an empty lexer, yyrset will be called later to reset and assign
     * the reader
     */
    public PortugolFlex() {
        super();
    }

    @Override
    public int yychar() {
        return yychar;
    }

    private static final byte PARAN     = 1;
    private static final byte BRACKET   = 2;
    private static final byte CURLY     = 3;
    private static final byte WORD      = 4;

%}

/* main character classes */
LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]

WhiteSpace = {LineTerminator} | [ \t\f]+

/* comments */
Comment = {TraditionalComment} | {EndOfLineComment} 

TraditionalComment = "/*" [^*] ~"*/" | "/*" "*"+ "/"
EndOfLineComment = "//" {InputCharacter}* {LineTerminator}?

/* identifiers */
Identifier = [a-zA-Z][a-zA-Z0-9_]*

/* integer literals */
DecIntegerLiteral = 0 | [1-9][0-9]*
DecLongLiteral    = {DecIntegerLiteral} [lL]

HexIntegerLiteral = 0 [xX] 0* {HexDigit} {1,8}
HexLongLiteral    = 0 [xX] 0* {HexDigit} {1,16} [lL]
HexDigit          = [0-9a-fA-F]

OctIntegerLiteral = 0+ [1-3]? {OctDigit} {1,15}
OctLongLiteral    = 0+ 1? {OctDigit} {1,21} [lL]
OctDigit          = [0-7]
    
/* floating point literals */        
FloatLiteral  = ({FLit1}|{FLit2}|{FLit3}) {Exponent}? [fF]
DoubleLiteral = ({FLit1}|{FLit2}|{FLit3}) {Exponent}?

FLit1    = [0-9]+ \. [0-9]* 
FLit2    = \. [0-9]+ 
FLit3    = [0-9]+ 
Exponent = [eE] [+-]? [0-9]+

/* string and character literals */
StringCharacter = [^\r\n\"\\]

%state STRING, ML_STRING

%%

<YYINITIAL> {
  
  /* key principais */
  "algoritmo"                     |
  "var"                           { return token(TokenType.KEYWORD); }
  

  "inicio"                        { return token(TokenType.KEYWORD, WORD); }
  "fimalgoritmo"                  { return token(TokenType.KEYWORD, -WORD); }




  /* keys secundarias */
  "leia"                          |
  "escreva"                       |
  "escreval"                      |
  "entao"                         |
  "vetor"                         |
  "de"                            |
  "ate"                           |
  "passo"                         |
  "e"                             |
  "ou"                            |
  "senao"                         |
  "caso"                          |
  "outrocaso"                     |
  "interrompa"                    |
  "funcao"                        |
  "fimfuncao"                     |
  "espere"                        |
  "faca"                          { return token(TokenType.KEYWORD2); }


  /* key secundarias com abertura de identacao */
  "enquanto"                     |
  "escolha"                      |
  "para"                         |
  "se"                           { return token(TokenType.KEYWORD2,  WORD); }

  "fimenquanto"                   |
  "fimescolha"                    |
  "fimpara"                       |
  "fimse"                         { return token(TokenType.KEYWORD2, -WORD); }


  "string"                       |
  "inteiro"                      |
  "caracter"                     |
  "logico"                       |
  "real"                         {  return token(TokenType.TYPE);  }


  
  /* operators */

  "("                            { return token(TokenType.OPERATOR,  PARAN); }
  ")"                            { return token(TokenType.OPERATOR, -PARAN); }
  /* "{"                            { return token(TokenType.OPERATOR,  CURLY); } */
  /* "}"                            { return token(TokenType.OPERATOR, -CURLY); } */
  "["                            { return token(TokenType.OPERATOR,  BRACKET); }
  "]"                            { return token(TokenType.OPERATOR, -BRACKET); }
  


  "+"                            |
  "-"                            |
  "*"                            |
  "/"                            |
  "mod"                          |
  "div"                          |
  "nao"                          |
  "pot"                          |
  "modulo"                       |
  "sen"                          |
  "cos"                          |
  "tan"                          |
  "raiz"                         |
  "PI"                           |
  "<"                            |
  ">"                            |
  "<="                           |
  ">="                           |
  "=="                           |
  "<>"                           |
  ","                            |
  ":"                            |
  ":="                           |
  "<-"                           |
  "="                            { return token(TokenType.OPERATOR); }
  

  "verdadeiro"|
  "falso"     { return token(TokenType.NUMBER); }


  /* string literal */
  \"{3}                          {
                                    yybegin(ML_STRING);
                                    tokenStart = yychar;
                                    tokenLength = 3;
                                 }

  \"                             {
                                    yybegin(STRING);
                                    tokenStart = yychar;
                                    tokenLength = 1;
                                 }


  /* numeric literals */

  {DecIntegerLiteral}            |
  {DecLongLiteral}               |
  
  {HexIntegerLiteral}            |
  {HexLongLiteral}               |
 
  {OctIntegerLiteral}            |
  {OctLongLiteral}               |

  {FloatLiteral}                 |
  {DoubleLiteral}                |
  {FloatLiteral}[jJ]             { return token(TokenType.NUMBER); }
  
  /* comments */
  {Comment}                      { return token(TokenType.COMMENT); }

  /* whitespace */
  {WhiteSpace}                   { }


  /* identifiers */ 
  {Identifier}"?"                { return token(TokenType.TYPE2); }
  {Identifier}                   { return token(TokenType.IDENTIFIER); }
}

  (\" ( [^\"\n\\] | \\[^\n] )* (\n | \\\n | \"))  { return token(TokenType.STRING); }

  (\' ( [^\'\n\\] | \\[^\n] ){0,1} (\n | \\\n | \'))  { return token(TokenType.STRING); }

/* error fallback */
\n                             {  }
.								{return token(TokenType.ERROR); }
<<EOF>>                          { return null; }
