/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.console;

/**
 *
 * @author SIMONETO-2
 */
// CornerCaret.java
// A custom caret class.
//
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.JTextComponent;

public class CaretConsole extends DefaultCaret {

    //private String mark = "<";
    public CaretConsole() {
        setBlinkRate(500);
    }

    @Override
    protected synchronized void damage(Rectangle r) {
        if (r == null) {
            return;
        }

        JTextComponent comp = getComponent();
        FontMetrics fm = comp.getFontMetrics(comp.getFont());
        int textWidth = fm.stringWidth(">");
        int textHeight = fm.getHeight();
        x = r.x;
        y = r.y;
        width = textWidth;
        height = textHeight;
        repaint(); // calls getComponent().repaint(x, y, width, height)
    }

    @Override
    public void paint(Graphics g) {
        JTextComponent comp = getComponent();
        if (comp == null) {
            return;
        }

        int dot = getDot();
        Rectangle r = null;
        try {
            r = comp.modelToView(dot);
        } catch (BadLocationException e) {
            return;
        }
        if (r == null) {
            return;
        }

        if ((x != r.x) || (y != r.y)) {
            repaint(); // erase previous location of caret
            damage(r);
        }

        if (isVisible()) {
            FontMetrics fm = comp.getFontMetrics(comp.getFont());
            int textWidth = fm.stringWidth(">");
            int textHeight = fm.getHeight();

            g.setColor(comp.getCaretColor());
            g.fillRect(r.x, r.y, 8, r.height);
        }
    }

    @Override
    public void mousePressed(MouseEvent evt) {
        evt.consume();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        e.consume();
    }

}
