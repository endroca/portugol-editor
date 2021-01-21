/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compilador.portugol;

import static com.forms.Editor.tab;
import java.io.IOException;
import java.io.StringReader;
import java.util.Scanner;
import java.util.concurrent.locks.ReentrantLock;
import java_cup.runtime.Symbol;

/**
 *
 * @author Andrew
 */
public class Inicializador {

    private final ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) throws IOException, Exception {
        //Yylex lexer = new Yylex(new StringReader("2+9"));
        //PortugolFlex lexer = new PortugolFlex(Inicializador.class.getResourceAsStream("Exemplo.txt"));

        //Symbol sym;
        //for (sym = lexer.next_token(); sym.sym != 0; sym = lexer.next_token()) {
        //System.out.println("Token " + sym.sym + ", with value = " + sym.value + "; at line " + sym.left + ", column " + sym.right);
        //}
        //parser p = new parser(lexer);
        //p.parse();
        //p.debug_parse();
        //System.out.print(Math.sin(90*Math.PI/180));
        if(System.getProperty("os.name").toLowerCase().contains("win")){
            System.out.print("windows");
        }
    }

}
