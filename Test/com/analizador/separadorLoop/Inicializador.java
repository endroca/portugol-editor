/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.analizador.separadorLoop;

import java.io.IOException;
import java_cup.runtime.Symbol;

/**
 *
 * @author Andrew
 */
public class Inicializador {

    public static void main(String[] args) throws IOException, Exception {
        Yylex lexer = new Yylex(Inicializador.class.getResourceAsStream("Exemplo.txt"));
        
        //Yylex lexer = new Yylex(new StringReader("teste1,teste2,teste3,testeN : caracter"));
        /*
         Symbol sym;
         for (sym = lexer.next_token(); sym.sym != 0; sym = lexer.next_token()) {
         System.out.println("Token " + sym.sym + ", with value = " + sym.value + "; at line " + sym.left + ", column " + sym.right);
         }
        */
        
        
        parser p = new parser(lexer);
        p.parse();

    }

}
