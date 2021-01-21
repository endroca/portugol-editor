/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.console;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JFrame;

import bsh.EvalError;


/**
 * The interpreter class. We delegate to a ready-made bsh interpreter. For
 * details, see http://www.beanshell.org/
 *
 * @author Barak Naveh
 */
public class Interpreter {
    private static final boolean SHOW_GUI = true;

    /**
     * User can pass file names. We run these files from bsh prior to user
     * prompt.
     *
     * @param args
     */
    public static void main( String[] args ) {
        bsh.Interpreter bsh;

        if( SHOW_GUI ) {
            JConsole console = new JConsole(  );
            bsh = new bsh.Interpreter( console );
            createGuiForConsole( console );
        }
        else {
            InputStreamReader reader = new InputStreamReader( System.in );
            bsh = new bsh.Interpreter( reader, System.out, System.err, true );
        }

        try {
            bsh.eval( "import robdd.*;" );
            bsh.eval( "import robdd.operators.*;" );
        }
         catch( EvalError e ) {
            e.printStackTrace(  );
        }

        for( int j = 0; j < args.length; j++ ) {
            String fileName = args[ j ];

            try {
                bsh.source( fileName );
            }
             catch( IOException e1 ) {
                System.err.println( "Could not open file: " + fileName );
            }
             catch( EvalError e2 ) {
                System.err.println( "Evaluation error in file: " + fileName );
            }
        }

        bsh.run(  );
    }


    private static void createGuiForConsole( JConsole console ) {
        JFrame frame = new JFrame( "ROBDD Interpreter" );
        frame.getContentPane(  ).add( console );
        frame.setSize( 640, 400 );
        frame.setLocation( 160, 200 );
        frame.addWindowListener( new WindowAdapter(  ) {
                public void windowClosing( WindowEvent e ) {
                    System.exit( 0 );
                }
            } );
        frame.show(  );
    }
}