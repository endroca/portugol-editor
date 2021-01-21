/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.analizador.algoritmoName;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 *
 * @author Andrew
 */
public class AnalizerP1 {
    
    public AnalizerP1() throws FileNotFoundException, Exception{
        File f = new File("C:\\Users\\Andrew\\Documents\\NetBeansProjects\\portugol-projeto\\Test\\com\\analizador\\algoritmoName\\Exemplo.txt");
        Yylex lexer = new Yylex(new FileInputStream(f));
        parser p = new parser(lexer);
        p.parse();
    }
    
}
