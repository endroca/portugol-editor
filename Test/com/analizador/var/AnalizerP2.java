/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.analizador.var;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 *
 * @author Andrew
 */
public class AnalizerP2 {
    public AnalizerP2() throws FileNotFoundException, Exception{
        File f = new File("C:\\Users\\Andrew\\Documents\\NetBeansProjects\\portugol-projeto\\Test\\com\\analizador\\var\\Exemplo.txt");
        Yylex lexer = new Yylex(new FileInputStream(f));
        parser p = new parser(lexer);
        p.parse();
    }
}
