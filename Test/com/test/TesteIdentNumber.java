/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.test;

/**
 *
 * @author SIMONETO-2
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.text.rtf.*;

class Test extends JFrame
{
    public Test()
    {
        JEditorPane edit = new JEditorPane();
        edit.setEditorKit(new MyEditorKit());
        JScrollPane scroll=new JScrollPane(edit);
        getContentPane().add(scroll);
        setSize(300,300);
        setVisible(true);
    }

    public static void main(String a[])
    {
        new Test();
    }

}

class MyEditorKit extends StyledEditorKit
{
    public ViewFactory getViewFactory()
    {
        return new MyRTFViewFactory();
    }
}

class MyRTFViewFactory implements ViewFactory
{
    public View create(Element elem)
    {
        String kind = elem.getName();
        if (kind != null)
            if (kind.equals(AbstractDocument.ContentElementName)) {
        return new LabelView(elem);
            } else if (kind.equals(AbstractDocument.ParagraphElementName)) {
//              return new ParagraphView(elem);
                return new MyParagraphView(elem);
            } else if (kind.equals(AbstractDocument.SectionElementName)) {
//              return new BoxView(elem, View.Y_AXIS);
                return new MySectionView(elem, View.Y_AXIS);
            } else if (kind.equals(StyleConstants.ComponentElementName)) {
                return new ComponentView(elem);
            } else if (kind.equals(StyleConstants.IconElementName)) {
                return new IconView(elem);
            }
            // default to text display
            return new LabelView(elem);
    }
}

class MySectionView extends BoxView {
    public MySectionView(Element e, int axis)
    {
        super(e,axis);
    }

    public void paintChild(Graphics g,Rectangle r,int n) {
        if (n>0) {
            MyParagraphView child=(MyParagraphView)this.getView(n-1);
            int shift=child.shift+child.childCount;

            MyParagraphView current=(MyParagraphView)this.getView(n);
            current.shift=shift;
        }
        super.paintChild(g,r,n);
    }
}

class MyParagraphView extends javax.swing.text.ParagraphView
{
    public int childCount;
    public int shift=0;
    public MyParagraphView(Element e)
    {
        super(e);
        short top=0;
        short left=20;
        short bottom=0;
        short right=0;
        this.setInsets(top,left,bottom,right);

    }

    public void paint(Graphics g, Shape a)
    {
        childCount=this.getViewCount();
        super.paint (g,a);
        int rowCountInThisParagraph=this.getViewCount(); //<----- YOU HAVE REAL ROW COUNT FOR ONE PARAGRAPH}
        System.err.println(rowCountInThisParagraph);
    }
    public void paintChild(Graphics g,Rectangle r,int n) {
        super.paintChild(g,r,n);
        g.drawString(Integer.toString(shift+n+1),r.x-20,r.y+r.height-3);
    }
}