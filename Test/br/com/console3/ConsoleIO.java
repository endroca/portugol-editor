/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.console3;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import static java.awt.event.KeyEvent.VK_BACK_SPACE;
import static java.awt.event.KeyEvent.VK_DOWN;
import static java.awt.event.KeyEvent.VK_LEFT;
import static java.awt.event.KeyEvent.VK_UP;
import java.awt.event.KeyListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;

/**
 *
 * @author SIMONETO-2
 */
public class ConsoleIO implements KeyListener {

    private final String header = "[Sistema ~] > ";
    private JTextPane console;
    private Document doc;

    public static int caret_min_position = -1;

    private boolean key_enter = false;
    private boolean block = true;
    private boolean write = false;
    private boolean write_tipo_int = false;
    private boolean write_tipo_float = false;
    private boolean write_tipo_char = false;
    private int write_tipo_char_count = 0;

    public ConsoleIO(JTextPane console) {
        this.console = console;
        doc = this.console.getDocument();
        console.addKeyListener(this);
        console.setCaretColor(Color.decode("#999999"));
        console.setCaret(new CaretConsole());
    }

    private int textLength() {
        return doc.getLength();
    }

    public void print(int position, Object t, SimpleAttributeSet style) {
        try {
            doc.insertString(position, ""+t, style);
        } catch (BadLocationException ex) {
            Logger.getLogger(ConsoleIO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void print(Object t) {
        print(textLength(), t, null);
    }

    public void print(Object t, SimpleAttributeSet style) {
        print(textLength(), t + "\n", style);
    }

    public void println(Object t) {
        print(textLength(), t + "\n", null);
    }

    public void println(Object t, SimpleAttributeSet style) {
        print(textLength(), t + "\n", style);
    }

    public String writeString() {

        write();
        String retorno = null;

        lock();

        try {
            retorno = doc.getText(caret_min_position, textLength() - caret_min_position);
        } catch (BadLocationException ex) {
            Logger.getLogger(ConsoleIO.class.getName()).log(Level.SEVERE, null, ex);
        }

        write = false;
        print("\n");

        return retorno;
    }

    public int writeInt() {

        write();

        write_tipo_int = true;
        int retorno = 0;
        String r = "";

        lock();

        try {
            r = doc.getText(caret_min_position, textLength() - caret_min_position);
        } catch (BadLocationException ex) {
            Logger.getLogger(ConsoleIO.class.getName()).log(Level.SEVERE, null, ex);
        }

        retorno = new Integer(r);

        write = false;
        write_tipo_int = false;
        print("\n");

        return retorno;
    }

    public float writeFloat() {

        write();

        write_tipo_int = true;
        float retorno = 0;
        String r = "";

        lock();

        try {
            r = doc.getText(caret_min_position, textLength() - caret_min_position);
        } catch (BadLocationException ex) {
            Logger.getLogger(ConsoleIO.class.getName()).log(Level.SEVERE, null, ex);
        }

        retorno = new Float(r);

        write = false;
        write_tipo_int = false;
        print("\n");

        return retorno;
    }

    public char writeChar() {

        write();

        write_tipo_char = true;
        char retorno;
        String r = "";

        lock();

        try {
            r = doc.getText(caret_min_position, textLength() - caret_min_position);
        } catch (BadLocationException ex) {
            Logger.getLogger(ConsoleIO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        retorno = r.charAt(0);
        
        write = false;
        write_tipo_char = false;
        write_tipo_char_count = 0;
        print("\n");

        return retorno;
    }

    private void write() {
        key_enter = false;
        write = true;
        console.requestFocus();
        int tmp_length = textLength();
        console.setCaretPosition(tmp_length);
        caret_min_position = tmp_length;
    }

    public void finalizacao() {
        console.setCaretPosition(textLength());
    }

    public boolean isLocked() {
        return block;
    }

    public synchronized void lock() {
        while (block) {
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(ConsoleIO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        block = true;
    }

    public synchronized void unlock() {
        block = false;
        notify();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (write) {
            type(e);
        } else {
            e.consume();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (write) {
            type(e);
            key_enter = false;
        } else {
            e.consume();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (write) {
            key_enter = true;
            type(e);
        } else {
            e.consume();
        }
    }

    private synchronized void type(KeyEvent e) {
        switch (e.getKeyCode()) {
            case (KeyEvent.VK_ENTER):
                if (e.getID() == KeyEvent.KEY_PRESSED) {
                    if (key_enter) {
                        unlock();
                        e.consume();
                    }
                }
                break;
            case (VK_DOWN):
            case (VK_UP):
                break;
            case (VK_LEFT):
            case (VK_BACK_SPACE):
                if (caret_min_position == console.getCaretPosition()) {
                    e.consume();
                    Toolkit.getDefaultToolkit().beep();
                }

                if (write_tipo_char) {
                    write_tipo_char_count = 0;
                }
                break;
            default:
                char key = e.getKeyChar();
                if (write_tipo_int) {
                    String caracteres = "-0123456789";
                    if (!caracteres.contains(key + "")) {
                        e.consume();
                    }
                } else if (write_tipo_float) {
                    String caracteres = "-0123456789.";
                    if (!caracteres.contains(key + "")) {
                        e.consume();
                    }
                } else if (write_tipo_char) {
                    if (write_tipo_char_count < 2) {
                        write_tipo_char_count++;
                    } else {
                        e.consume();
                    }

                }
                break;
        }
    }
}
