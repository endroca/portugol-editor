/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.analizador.expressao;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 *
 * @author Andrew
 */
public class Inicializador {

    public static void main(String[] args) throws IOException, Exception {
        //Yylex lexer = new Yylex(new StringReader("2+9"));
        Yylex lexer = new Yylex(Inicializador.class.getResourceAsStream("Exemplo.txt"));
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
