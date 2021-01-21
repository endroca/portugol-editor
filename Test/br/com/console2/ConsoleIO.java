/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.console2;

import bsh.util.GUIConsoleInterface;
import bsh.util.NameCompletion;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import javax.swing.Icon;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

/**
 *
 * @author SIMONETO-2
 */
public class ConsoleIO  implements GUIConsoleInterface, Runnable, KeyListener {

    private JTextPane console;

    private OutputStream outPipe;
    private InputStream inPipe;
    private InputStream in;
    private PrintStream out;

    private int cmdStart = 0;
    private String startedLine;
    private int histLine = 0;

    private boolean gotUp = true;

    public InputStream getInputStream() {
        return in;
    }

    @Override
    public Reader getIn() {
        return new InputStreamReader(in);
    }

    @Override
    public PrintStream getOut() {
        return out;
    }

    @Override
    public PrintStream getErr() {
        return out;
    }

    public ConsoleIO(JTextPane text) {
        console = text;
        
        console.addKeyListener(this);
        
        console.setCaret(new CaretConsole());
        /*
         * OUT
         */
        outPipe = new PipedOutputStream();
        try {
            in = new PipedInputStream((PipedOutputStream) outPipe);
        } catch (IOException e) {
        }

        /*
         * IN
         */
        PipedOutputStream pout = new PipedOutputStream();
        out = new PrintStream(pout);
        try {
            inPipe = new BlockingPipedInputStream(pout);
        } catch (IOException e) {
        }

        new Thread(this).start();
        
        console.requestFocus();

    }

    private int textLength() {
        return console.getDocument().getLength();
    }

    private void append(String string) {
        int slen = textLength();
        console.select(slen, slen);
        console.replaceSelection(string);

    }

    private void resetCommandStart() {
        cmdStart = textLength();
    }
    /*
     *
     *   FUNCOES PRINTS
     *
     */

    @Override
    public void println(Object o) {
        print(String.valueOf(o) + "\n");
        console.repaint();
    }

    @Override
    public void print(final Object o) {
        invokeAndWait(new Runnable() {
            @Override
            public void run() {
                append(String.valueOf(o));
                resetCommandStart();
                console.setCaretPosition(cmdStart);
            }
        });

    }

    /**
     * Prints "\\n" (i.e. newline)
     */
    public void println() {
        print("\n");
        console.repaint();
    }

    @Override
    public void error(Object o) {
        print(o, Color.red);
    }

    public void println(Icon icon) {
        print(icon);
        println();
        console.repaint();
    }

    public void print(final Icon icon) {
        if (icon == null) {
            return;
        }

        invokeAndWait(new Runnable() {
            @Override
            public void run() {
                console.insertIcon(icon);
                resetCommandStart();
                console.setCaretPosition(cmdStart);
            }
        });

    }

    public void print(Object s, Font font) {
        print(s, font, null);
    }

    @Override
    public void print(Object s, Color color) {
        print(s, null, color);
    }

    public void print(final Object o, final Font font, final Color color) {
        invokeAndWait(new Runnable() {
            @Override
            public void run() {
                AttributeSet old = getStyle();
                setStyle(font, color);
                append(String.valueOf(o));
                resetCommandStart();
                console.setCaretPosition(cmdStart);
                setStyle(old, true);
            }
        });

    }

    public void print(Object s, String fontFamilyName, int size, Color color) {
        print(s, fontFamilyName, size, color, false, false, false);
    }

    public void print(final Object o, final String fontFamilyName, final int size, final Color color, final boolean bold, final boolean italic, final boolean underline) {
        invokeAndWait(new Runnable() {
            @Override
            public void run() {
                AttributeSet old = getStyle();
                setStyle(fontFamilyName, size, color, bold, italic, underline);
                append(String.valueOf(o));
                resetCommandStart();
                console.setCaretPosition(cmdStart);
                setStyle(old, true);
            }
        });
    }

    private void invokeAndWait(Runnable run) {
        if (!SwingUtilities.isEventDispatchThread()) {
            try {
                SwingUtilities.invokeAndWait(run);
            } catch (InterruptedException | InvocationTargetException e) {
            }
        } else {
            run.run();
        }
    }

    private void inPipeWatcher() throws IOException {
        byte[] ba = new byte[256]; //	arbitrary blocking factor
        int read;
        while ((read = inPipe.read(ba)) != -1) {
            print(new String(ba, 0, read));
            //text.repaint();
        }

        println("Console: Input	closed...");
    }

    @Override
    public void run() {
        try {
            inPipeWatcher();
        } catch (IOException e) {
            print("Console: I/O Error: " + e + "\n", Color.red);
        }
    }

    private void enter() {
        String s = getCmd();

        if (s.length() == 0) // special hack	for empty return!
        {
            s = ";\n";
        } else {
            s = s + "\n";
        }

        append("\n");
        histLine = 0;
        acceptLine(s);
        console.repaint();
    }
    
    private String getCmd() {
        String s = "";
        try {
            s = console.getText(cmdStart, textLength() - cmdStart);
        } catch (BadLocationException e) {
            // should not happen
            System.out.println("Internal JConsole Error: " + e);
        }
        return s;
    }
    
    String ZEROS = "000";
    
    private void acceptLine(String line) {
		// Patch to handle Unicode characters
        // Submitted by Daniel Leuck
        StringBuilder buf = new StringBuilder();
        int lineLength = line.length();
        for (int i = 0; i < lineLength; i++) {
            char c = line.charAt(i);
            if (c > 127) {
                String val = Integer.toString(c, 16);
                val = ZEROS.substring(0, 4 - val.length()) + val;
                buf.append("\\u").append(val);
            } else {
                buf.append(c);
            }
        }
        line = buf.toString();
		// End unicode patch

        if (outPipe == null) {
            print("Console internal	error: cannot output ...", Color.red);
        } else {
            try {
                outPipe.write(line.getBytes());
                outPipe.flush();
            } catch (IOException e) {
                outPipe = null;
                throw new RuntimeException("Console pipe broken...");
            }
        }
        //text.repaint();
    }
    
    
    @Override
    public void setNameCompletion(NameCompletion nc) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setWaitFeedback(boolean bln) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyTyped(KeyEvent e) {
        type(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        type(e);
        gotUp = false;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        gotUp = true;
        type(e);
    }

    private synchronized void type(KeyEvent e) {
        switch (e.getKeyCode()) {
            case (KeyEvent.VK_ENTER):
                if (e.getID() == KeyEvent.KEY_PRESSED) {
                    if (gotUp) {
                        enter();
                        resetCommandStart();
                        console.setCaretPosition(cmdStart);
                    }
                }
                e.consume();
                console.repaint();
                break;

            case (KeyEvent.VK_UP):
                e.consume();
                break;

            case (KeyEvent.VK_DOWN):
                e.consume();
                break;

            case (KeyEvent.VK_LEFT):
            case (KeyEvent.VK_BACK_SPACE):
            case (KeyEvent.VK_DELETE):
                if (console.getCaretPosition() <= cmdStart) {
                    e.consume();
                }
                break;

            case (KeyEvent.VK_RIGHT):
                forceCaretMoveToStart();
                break;

            case (KeyEvent.VK_HOME):
                console.setCaretPosition(cmdStart);
                e.consume();
                break;

            case (KeyEvent.VK_ALT):
            case (KeyEvent.VK_CAPS_LOCK):
            case (KeyEvent.VK_CONTROL):
            case (KeyEvent.VK_META):
            case (KeyEvent.VK_SHIFT):
            case (KeyEvent.VK_PRINTSCREEN):
            case (KeyEvent.VK_SCROLL_LOCK):
            case (KeyEvent.VK_PAUSE):
            case (KeyEvent.VK_INSERT):
            case (KeyEvent.VK_F1):
            case (KeyEvent.VK_F2):
            case (KeyEvent.VK_F3):
            case (KeyEvent.VK_F4):
            case (KeyEvent.VK_F5):
            case (KeyEvent.VK_F6):
            case (KeyEvent.VK_F7):
            case (KeyEvent.VK_F8):
            case (KeyEvent.VK_F9):
            case (KeyEvent.VK_F10):
            case (KeyEvent.VK_F11):
            case (KeyEvent.VK_F12):
            case (KeyEvent.VK_ESCAPE):

                // only	modifier pressed
                break;

            default:
                if ((e.getModifiers() & (InputEvent.CTRL_MASK | InputEvent.ALT_MASK | InputEvent.META_MASK)) == 0) {
                    // plain character
                    forceCaretMoveToEnd();
                }

                /*
                 The getKeyCode function always returns VK_UNDEFINED for
                 keyTyped events, so backspace is not fully consumed.
                 */
                if (e.paramString().contains("Backspace")) {
                    if (console.getCaretPosition() <= cmdStart) {
                        e.consume();
                        break;
                    }
                }

                break;
        }
    }

    private void forceCaretMoveToEnd() {
        if (console.getCaretPosition() < cmdStart) {
            // move caret first!
            console.setCaretPosition(textLength());
        }
        console.repaint();
    }

    private void forceCaretMoveToStart() {
        if (console.getCaretPosition() < cmdStart) {
            // move caret first!
        }
        console.repaint();
    }

    /**
     * The overridden read method in this class will not throw "Broken pipe"
     * IOExceptions; It will simply wait for new writers and data. This is used
     * by the JConsole internal read thread to allow writers in different (and
     * in particular ephemeral) threads to write to the pipe.
     *
     * It also checks a little more frequently than the original read().
     *
     * Warning: read() will not even error on a read to an explicitly closed
     * pipe (override closed to for that).
     */
    public static class BlockingPipedInputStream extends PipedInputStream {

        boolean closed;

        public BlockingPipedInputStream(PipedOutputStream pout)
                throws IOException {
            super(pout);
        }

        @Override
        public synchronized int read() throws IOException {
            if (closed) {
                throw new IOException("stream closed");
            }

            while (super.in < 0) {	// While no data */
                notifyAll();	// Notify any writers to wake up
                try {
                    wait(750);
                } catch (InterruptedException e) {
                    throw new InterruptedIOException();
                }
            }
            // This is what the superclass does.
            int ret = buffer[super.out++] & 0xFF;
            if (super.out >= buffer.length) {
                super.out = 0;
            }
            if (super.in == super.out) {
                super.in = -1;  /* now empty */

            }
            return ret;
        }

        @Override
        public void close() throws IOException {
            closed = true;
            super.close();
        }
    }

    /*
     *
     *  SET STYLES E GET SYLES
     *
     */
    private AttributeSet getStyle() {
        return console.getCharacterAttributes();
    }

    private AttributeSet setStyle(Font font) {
        return setStyle(font, null);
    }

    private AttributeSet setStyle(Color color) {
        return setStyle(null, color);
    }

    private AttributeSet setStyle(Font font, Color color) {
        if (font != null) {
            return setStyle(font.getFamily(), font.getSize(), color,
                    font.isBold(), font.isItalic(),
                    StyleConstants.isUnderline(getStyle()));
        } else {
            return setStyle(null, -1, color);
        }
    }

    private AttributeSet setStyle(String fontFamilyName, int size, Color color) {
        MutableAttributeSet attr = new SimpleAttributeSet();
        if (color != null) {
            StyleConstants.setForeground(attr, color);
        }
        if (fontFamilyName != null) {
            StyleConstants.setFontFamily(attr, fontFamilyName);
        }
        if (size != -1) {
            StyleConstants.setFontSize(attr, size);
        }

        setStyle(attr);

        return getStyle();
    }

    private AttributeSet setStyle(String fontFamilyName, int size, Color color, boolean bold, boolean italic, boolean underline) {
        MutableAttributeSet attr = new SimpleAttributeSet();
        if (color != null) {
            StyleConstants.setForeground(attr, color);
        }
        if (fontFamilyName != null) {
            StyleConstants.setFontFamily(attr, fontFamilyName);
        }
        if (size != -1) {
            StyleConstants.setFontSize(attr, size);
        }
        StyleConstants.setBold(attr, bold);
        StyleConstants.setItalic(attr, italic);
        StyleConstants.setUnderline(attr, underline);

        setStyle(attr);

        return getStyle();
    }

    private void setStyle(AttributeSet attributes) {
        setStyle(attributes, false);
    }

    private void setStyle(AttributeSet attributes, boolean overWrite) {
        console.setCharacterAttributes(attributes, overWrite);
    }
}
