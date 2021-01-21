/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.console;

import bsh.EvalError;
import bsh.util.GUIConsoleInterface;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import javax.swing.JFrame;
/**
 *
 * @author Andrew
 */
public class JConsoleExample {

    public static void main(String[] args) throws EvalError {

        //define a frame and add a console to it
        JFrame frame = new JFrame("JConsole example");
        
        //PrintWriter p = new PrintWriter();
        
        JConsole console = new JConsole();
        
        System.setIn(console.getInputStream());
        System.setOut(console.getOut());

        
        
        frame.getContentPane().add(console);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        frame.setVisible(true);

        bsh.Interpreter i = new bsh.Interpreter();
        i.eval("Scanner t = new Scanner(System.in);");
        i.eval("System.out.print(\"Digite seu nome: \");");
        i.eval("String nome = t.next();");
        i.eval("System.out.print(\"Seu nome e \"+nome);");
        
        
        //new Thread( i ).start();
        //System.exit(0);
    }

    /**
     * Print prompt and echos commands entered via the JConsole
     *
     * @param console a GUIConsoleInterface which in addition to basic input and
     * output also provides coloured text output and name completion
     * @param prompt text to display before each input line
     */
    private static void inputLoop(GUIConsoleInterface console, String prompt) {
        Reader input = console.getIn();
        BufferedReader bufInput = new BufferedReader(input);

        String newline = System.getProperty("line.separator");

        console.print(prompt, Color.BLUE);

        String line;
        try {
            while ((line = bufInput.readLine()) != null) {
                console.print("You typed: " + line + newline, Color.ORANGE);

    		 // try to sync up the console
                //System.out.flush();
                //System.err.flush();
                //Thread.yield();  // this helps a little
                if (line.equals("quit")) {
                    break;
                }
                console.print(prompt, Color.BLUE);
            }
            bufInput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
