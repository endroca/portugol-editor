/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.console;


import static br.com.console.Console._console;
import bsh.EvalError;
import bsh.Interpreter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java_cup.runtime.Scanner;

/**
 *
 * @author Andrew
 */
public class InterpreterTestConsole {
    public InterpreterTestConsole(){
            
            Interpreter i = new Interpreter();
        try {
            
            

            i.eval("Scanner t = new Scanner(System.in);");
            //i.eval("System.out.print(\"Digite seu nome: \");");
            //i.eval("String nome = t.next();");
            //i.eval("System.out.print(\"Seu nome e :\"+nome);");
            
            i.eval("public void teste(){System.out.print(\"ttttttttt\");}");
            i.eval("teste();");
            
            
            //i.set("_console", _console);
            
            //i.set("console", console);
            //i.eval("import br.com.console.ConsoleIO");
            //i.set("ConsoleJtextArea", ConsoleJtextArea.class); 
            //i.eval("ConsoleIO _console = new ConsoleIO(console);");
            //i.eval("_console.print(\"Digite seu nome: \");");
            //i.eval("String nome = _console.writeString();");
            //i.eval("_console.print(\"Qual e sua idade: \");");
            //i.eval("int idade = _console.writeInt();");
            //i.eval("_console.print(\"Seu nome é \" + nome);");
            
            //i.eval("int nloop = 0;");
            //i.eval("numero = 0;");
            //i.eval("while(nloop < 10){_console.print(\"Digite \" + nloop + \" de 10: \"); numero = _console.writeFloat(); nloop++; }");
            /*
            i.eval("int nloop;");
            i.eval("float numero,soma;");
            
            
            i.eval("nloop = 0;");
            i.eval("numero = 0;");
            i.eval("soma = 0;");
            
            i.eval("while(nloop < 10){");
                i.eval("_console.print(\"Digite \" + nloop + \" de 10: \");");
                i.eval("numero = _console.writeFloat();");
                i.eval("if((numero % 2 == 0)){");
                    i.eval("soma = soma + numero;");
                i.eval("}");
                i.eval("nloop = nloop + 1;");
            i.eval("}");
            i.eval("_console.println(\"Soma dos numeros: \" + soma);");
            i.eval("_console.println(\"Média dos numeros: \" + (soma / nloop));");
            */
        } catch (EvalError ex) {
            Logger.getLogger(InterpreterTestConsole.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
