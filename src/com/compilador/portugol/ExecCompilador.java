/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compilador.portugol;

import static com.forms.Editor.tab;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author SIMONETO-2
 */
public class ExecCompilador extends Compilador {

    public ExecCompilador() {
        PortugolFlex lexer = new PortugolFlex(new StringReader(tab.getEditorPane().getText()));
        
        
        //Symbol sym;
        //for (sym = lexer.next_token(); sym.sym != 0; sym = lexer.next_token()) {
        //System.out.println("Token " + sym.sym + ", with value = " + sym.value + "; at line " + sym.left + ", column " + sym.right);
        //}
        
        parser p = new parser(lexer);
        try {
            p.parse();
        } catch (Exception ex) {
            Logger.getLogger(ExecCompilador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
