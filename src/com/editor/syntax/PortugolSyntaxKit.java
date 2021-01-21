/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.editor.syntax;

import com.editor.flex.PortugolFlex;
import de.sciss.syntaxpane.DefaultSyntaxKit;

/**
 *
 * @author Andrew
 */
public class PortugolSyntaxKit extends DefaultSyntaxKit {
    public PortugolSyntaxKit(){
        super(new PortugolFlex());
    }
}
