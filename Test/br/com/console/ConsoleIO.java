/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.console;

import java.awt.Cursor;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import static java.awt.event.KeyEvent.VK_BACK_SPACE;
import static java.awt.event.KeyEvent.VK_DOWN;
import static java.awt.event.KeyEvent.VK_ENTER;
import static java.awt.event.KeyEvent.VK_LEFT;
import static java.awt.event.KeyEvent.VK_UP;
import java.awt.event.KeyListener;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;

/**
 *
 * @author Andrew
 */
public class ConsoleIO implements KeyListener {
    
    private final ReentrantLock lock = new ReentrantLock();
    
    private final String header = "[Sistema ~] > ";
    private JTextPane console;
    private Document doc;

    //responsavel por pausar o codigo na hora da entrada
    private boolean block = true;
    //posicao minima do caret
    public static int caret_min_position = -1;
    //para permitir apenas numeros
    private boolean write_tipo_int = false;
    private boolean write_tipo_float = false;
    private boolean key_enter = false;

    /*
     *   CLASSE CONSTRUTORA
     */
    public ConsoleIO(JTextPane t) {
        console = t;

        console.setCaret(new CaretConsole());
        
        console.getCaret().setVisible(false);

        doc = console.getDocument();

        console.setHighlighter(null);

        console.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

        console.addKeyListener(this);
    }

    public void close() {
        unlock();
    }

    public void reset() {
        console.setText("");
    }
    /*
     * PRINT
     */

    public void print(Object t) {
        try {
            doc.insertString(doc.getLength(), header + t, null);
        } catch (BadLocationException ex) {
            Logger.getLogger(ConsoleIO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void print(Object t, SimpleAttributeSet style) {
        try {
            doc.insertString(doc.getLength(), (String) t, style);
        } catch (BadLocationException ex) {
            Logger.getLogger(ConsoleIO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void print(int position, Object t) {
        try {
            doc.insertString(position, (String) t, null);
        } catch (BadLocationException ex) {
            Logger.getLogger(ConsoleIO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void print(int position, Object t, SimpleAttributeSet style) {
        try {
            doc.insertString(position, (String) t, style);
        } catch (BadLocationException ex) {
            Logger.getLogger(ConsoleIO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*
     * PRINLN
     */
    public void println(Object t) {
        try {
            doc.insertString(doc.getLength(), header + t + "\n", null);
        } catch (BadLocationException ex) {
            Logger.getLogger(ConsoleIO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void println(Object t, SimpleAttributeSet style) {
        try {
            doc.insertString(doc.getLength(), t + "\n", style);
        } catch (BadLocationException ex) {
            Logger.getLogger(ConsoleIO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void println(int position, Object t) {
        try {
            doc.insertString(position, t + "\n", null);
        } catch (BadLocationException ex) {
            Logger.getLogger(ConsoleIO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void println(int position, Object t, SimpleAttributeSet style) {
        try {
            doc.insertString(position, t + "\n", style);
        } catch (BadLocationException ex) {
            Logger.getLogger(ConsoleIO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*
     * ENTRADA DE STRING
     */
    public synchronized String writeString() {
        String retorno = null;
        write();
        try {
            retorno = doc.getText(caret_min_position, doc.getLength() - caret_min_position);
            print(doc.getLength(), "\n");
            console.repaint();
        } catch (BadLocationException ex) {
            Logger.getLogger(ConsoleIO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            console.getCaret().setVisible(false);
        }
        return retorno;
    }

    /*
     * ENTRADA DE INT
     */
    public int writeInt() {
        int retorno = 0;
        write_tipo_int = true;
        write();
        try {
            String r = doc.getText(caret_min_position, doc.getLength() - caret_min_position);
            if ("".equals(r)) {
                retorno = 0;
            } else {
                retorno = new Integer(r);
            }
            write_tipo_int = false;
            print(doc.getLength(), "\n");
            console.repaint();
        } catch (BadLocationException ex) {
            Logger.getLogger(ConsoleIO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            console.getCaret().setVisible(false);
        }

        return retorno;
    }

    public float writeFloat() {
        float retorno = 0;
        write_tipo_float = true;
        write();
        try {
            String r = doc.getText(caret_min_position, doc.getLength() - caret_min_position).replace(",", ".");
            if ("".equals(r)) {
                retorno = 0;
            } else {
                retorno = new Float(r);
            }

            write_tipo_float = false;
            print(doc.getLength(), "\n");
            console.repaint();
        } catch (BadLocationException ex) {
            Logger.getLogger(ConsoleIO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            console.getCaret().setVisible(false);
        }

        return retorno;
    }

    /*
     * LOOP PARA TRAVAMENTO DE ENTRADA DE DADOS
     */
    private synchronized void write() {
        console.getCaret().setVisible(true);
        console.requestFocus();
        int tmp_length = doc.getLength();
        console.setCaretPosition(tmp_length);
        caret_min_position = tmp_length;
        lock();
    }

    /*
     * RESETA ALGUMAS CONFIGURACOES
     */
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

    /*
     * EVENTOS
     */
    @Override
    public void keyTyped(KeyEvent e) {
        type(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        type(e);
        key_enter = false;
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        key_enter = true;
        type(e);
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
                break;
            default:
                char key = e.getKeyChar();
                if (write_tipo_int) {
                    String caracteres = "0123456789";
                    if (!caracteres.contains(key + "")) {
                        e.consume();
                    }
                } else if (write_tipo_float) {
                    String caracteres = "0123456789,";
                    if (!caracteres.contains(key + "")) {
                        e.consume();
                    }
                }
            break;
        }
    }
}
