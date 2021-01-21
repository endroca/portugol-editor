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

%caseless
%ignorecase

%{

    public PortugolFlex() {
        super();
    }

    private static final byte PARAN     = 1;
    private static final byte BRACKET   = 2;
    private static final byte CURLY     = 3;

    @Override
    public int yychar() {
        return yychar;
    }
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

ConstantIdentifier = {SimpleConstantIdentifier}
SimpleConstantIdentifier = [#A-Z0-9_]+

Identifier = [:jletter:][:jletterdigit:]*

TypeIdentifier = {SimpleTypeIdentifier}
SimpleTypeIdentifier = [A-Z][:jletterdigit:]*

/* int literals */

DecLiteral = 0 | [1-9][0-9]* {IntegerSuffix}

HexLiteral    = 0 [xX] 0* {HexDigit}* {IntegerSuffix}
HexDigit      = [0-9a-fA-F]

OctLiteral    = 0+ {OctDigit}* {IntegerSuffix}
OctDigit          = [0-7]

IntegerSuffix = [uU]? [lL]? [uU]?

/* float literals */

FloatLiteral  = ({FLit1}|{FLit2}|{FLit3}|{FLit4}) ([fF]|[dD])?

FLit1 = [0-9]+ \. [0-9]* {Exponent}?
FLit2 = \. [0-9]+ {Exponent}?
FLit3 = [0-9]+ {Exponent}
FLit4 = [0-9]+ {Exponent}?

Exponent = [eE] [+\-]? [0-9]+

%%

<YYINITIAL> {

  /* keywords */
  "algoritmo" | "var"  { return token(TokenType.KEYWORD); }

  "escreva" |"escreval" | "leia" | "entao" | "faca" | "e" | "ou" { return token(TokenType.KEYWORD2); }

  /* Type */
  "inteiro" | "caracter" | "real" { return token(TokenType.TYPE); }


  /* literals */
  (\" ( [^\"\n\\] | \\[^\n] )* (\n | \\\n | \")) | (\' ( [^\'\n\\] | \\[^\n] )* (\n | \\\n | \'))  { return token(TokenType.STRING); }
 


  {DecLiteral} | {OctLiteral} | {HexLiteral} | {FloatLiteral} { return token(TokenType.NUMBER); }

     



  
  /* separators */
  "("                            { return token(TokenType.OPERATOR,  PARAN); }
  ")"                            { return token(TokenType.OPERATOR, -PARAN); }

  "{"                            { return token(TokenType.OPERATOR,  CURLY); }
  "}"                            { return token(TokenType.OPERATOR, -CURLY); }

  "inicio"						 { return token(TokenType.KEYWORD,  CURLY); }
  "fimalgoritmo"				 { return token(TokenType.KEYWORD,  -CURLY); }

  "se" | "enquanto" | "senao"	 { return token(TokenType.KEYWORD2,  CURLY); }
  "fimse" | "fimenquanto"		 { return token(TokenType.KEYWORD2,  -CURLY); }




  "["                            { return token(TokenType.OPERATOR,  BRACKET); }
  "]"                            { return token(TokenType.OPERATOR, -BRACKET); }



  /* operators */
  "mod" |
  "div" |
  "=" |
  "," |
  ">" |
  "<" |
  ":" |
  "+" |
  "-" |
  "*" |
  "/" |
  "^" |
  "-" { return token(TokenType.OPERATOR); }



  {ConstantIdentifier} |{TypeIdentifier} |  {Identifier}	{ return token(TokenType.IDENTIFIER); }


{WhiteSpace}						{/* IGNORAR */}

\n 									{/* IGNORAR */}



  {Comment}   	{ return token(TokenType.COMMENT); }

}



/* error fallback */
.|\n                             {  }
<<EOF>>                          { return null; }