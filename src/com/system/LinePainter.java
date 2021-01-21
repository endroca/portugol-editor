/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.system;

/**
 *
 * @author SIMONETO-2
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
import de.sciss.syntaxpane.actions.ActionUtils;

/*
 *  Track the movement of the Caret by painting a background line at the
 *  current caret position.
 */
public class LinePainter implements Highlighter.HighlightPainter {

    private JTextComponent component;

    private Color color;

    private Rectangle lastView;

    /*
     *  The line color will be calculated automatically by attempting
     *  to make the current selection lighter by a factor of 1.2.
     *
     *  @param component  text component that requires background line painting
     */
    public LinePainter(JTextComponent component) {
        this(component, null);
        setLighter(component.getSelectionColor());
    }

    /*
     *  Manually control the line color
     *
     *  @param component  text component that requires background line painting
     *  @param color      the color of the background line
     */
    public LinePainter(JTextComponent component, Color color) {
        this.component = component;
        setColor(color);

        //  Turn highlighting on by adding a dummy highlight
        try {
            component.getHighlighter().addHighlight(0, 0, this);
        } catch (BadLocationException ble) {
        }
    }

    public void marker(int line) {
        setLineNumber(line);
        //resetHighlight();
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setLighter(Color color) {
        int red = Math.min(255, (int) (color.getRed() * 1.2));
        int green = Math.min(255, (int) (color.getGreen() * 1.2));
        int blue = Math.min(255, (int) (color.getBlue() * 1.2));
        setColor(new Color(red, green, blue));
    }

    private void setLineNumber(int line) {
        Element map = component.getDocument().getDefaultRootElement();
        if (line < 0) {
        } else if (line >= map.getElementCount()) {
        } else {
            Element lineElem = map.getElement(line);
            component.setCaretPosition(lineElem.getStartOffset());
        }
    }

    @Override
    public void paint(Graphics g, int p0, int p1, Shape bounds, JTextComponent c) {
        try {
            Rectangle r = c.modelToView(c.getCaretPosition());
            g.setColor(color);
            g.fillRect(0, r.y, c.getWidth(), r.height);

            if (lastView == null) {
                lastView = r;
            }
        } catch (BadLocationException ble) {
            System.out.println(ble);
        }
    }

    /*
     *   Caret position has changed, remove the highlight
     */
    private void resetHighlight() {
        //  Use invokeLater to make sure updates to the Document are completed,
        //  otherwise Undo processing causes the modelToView method to loop.

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    int offset = component.getCaretPosition();
                    Rectangle currentView = component.modelToView(offset);

                    //  Remove the highlighting from the previously highlighted line
                    if (lastView.y != currentView.y) {
                        component.repaint(0, lastView.y, component.getWidth(), lastView.height);
                        lastView = currentView;
                    }
                } catch (BadLocationException ble) {
                }
            }
        });
    }
}
