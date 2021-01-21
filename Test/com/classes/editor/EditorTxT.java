/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.classes.editor;

import com.classes.editor.HighlightingStyledDocument;
import com.classes.editor.LinePainter;
import com.classes.editor.TextLineNumber;
import com.classes.editor.UndoAndRedo;
import static com.forms.Editor.tab;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.TabSet;
import javax.swing.text.TabStop;
import javax.swing.undo.UndoManager;

/**
 *
 * @author SIMONETO-2
 */
public class EditorTxT {

    /*
     *   VARIAVEIS
     */
    private JTextPane campo = null;
    private final int tabSize = 5;
    private TextLineNumber tln = null;
    private final HighlightingStyledDocument highlight = new HighlightingStyledDocument();
    private LinePainter painter = null;
    private UndoAndRedo undoRedo = null;

    private UndoManager undoManager = new UndoManager();
    /*
     *   FUNCAO PRINCIPAL
     */

    public EditorTxT(final JTextPane campo, JScrollPane scroll) {
        //SETANDO CAMPO VARIAVEL
        this.campo = campo;

        //MUDANDO COR DO CURSOR
        this.campo.setCaretColor(Color.WHITE);

        //APLICANDO A NUMERCAO DE LINHAS
        tln = new TextLineNumber(this.campo, 2);
        scroll.setRowHeaderView(tln);

        //APLICANDO O FUCUS DE LINHA
        painter = new LinePainter(this.campo);
        painter.setColor(Color.decode("#353535"));
       
        
        //CONFIGURACOES DE COR E ESTILO DO EDTITOR
        SimpleAttributeSet syle_comentario = new SimpleAttributeSet();
        syle_comentario.addAttribute(StyleConstants.Foreground, Color.decode("#8f908a"));
        //syle_comentario.addAttribute(StyleConstants.CharacterConstants.Italic, Boolean.TRUE);

        SimpleAttributeSet style_key_principais = new SimpleAttributeSet();
        style_key_principais.addAttribute(StyleConstants.Foreground, Color.decode("#66d9ef"));
        style_key_principais.addAttribute(StyleConstants.CharacterConstants.Bold, Boolean.TRUE);

        SimpleAttributeSet style_funcoes = new SimpleAttributeSet();
        style_funcoes.addAttribute(StyleConstants.Foreground, Color.decode("#f92672"));
        //style_funcoes.addAttribute(StyleConstants.CharacterConstants.Bold, Boolean.TRUE);

        SimpleAttributeSet syle_string = new SimpleAttributeSet();
        syle_string.addAttribute(StyleConstants.Foreground, Color.decode("#e6db74"));

        SimpleAttributeSet style_default = new SimpleAttributeSet();
        style_default.addAttribute(StyleConstants.Foreground, Color.WHITE);

        highlight.setCommentStyle(syle_comentario);
        highlight.setDefaultStyle(style_default);
        highlight.setKeywordStyle(style_key_principais);
        highlight.setEnvironmentWordStyle(style_funcoes);
        highlight.setStringStyle(syle_string);
        
        
        this.campo.setDocument(highlight);
        
        //SETANDO TAMANHO DA TAB
        setTabSize(this.campo, tabSize);

        //SETANDO O UNDO MANAGE
        this.campo.getDocument().addUndoableEditListener(new UndoableEditListener() {
            @Override
            public void undoableEditHappened(UndoableEditEvent e) {
                undoManager.addEdit(e.getEdit());
                updateUndoRedo();
            }
        });

        /*
         //EVENTO DE MODIFICAO DO TXT
         this.campo.getDocument().addDocumentListener(new DocumentListener() {

         @Override
         public void removeUpdate(DocumentEvent e) {
         //System.out.println("nao modificado "+campo.getName());
         }

         @Override
         public void insertUpdate(DocumentEvent e) {
         //System.out.println("modificado "+campo.getName());
         }

         @Override
         public void changedUpdate(DocumentEvent arg0) {
         //System.out.println("modificado "+campo.getName());
         }
         });
         */
    }
    
    /*
    *   FUNCOES PARA SETAR O CTRL+Z E CTRL+V
    */
    public void setUndo(){
        if(undoManager.canUndo()) undoManager.undo();
    }
    public void setRedo(){
        if(undoManager.canRedo()) undoManager.redo();
    }
    
    public boolean canUndo(){
        return undoManager.canUndo();
    }
    
    public boolean canRedo(){
        return undoManager.canRedo();
    }
    public void updateUndoRedo(){
        //System.out.println(canUndo());
        //jMenuItem8.setEnabled(canRedo());
        //jMenuItem7.setEnabled(canUndo());
    }
    
    
    /*
    *   FUNCAO PARA SETAR O TABSIZE
    */
    public void setTabSize(JTextPane pane, int size) {
        String tab = "";
        for (int i = 0; i < size; i++) {
            tab += " ";
        }
        float f = (float) pane.getFontMetrics(pane.getFont()).stringWidth(tab);
        TabStop[] tabs = new TabStop[500]; // this sucks

        for (int i = 0; i < tabs.length; i++) {
            tabs[i] = new TabStop(f * (i + 1), TabStop.ALIGN_LEFT, TabStop.LEAD_NONE);
        }

        TabSet tabset = new TabSet(tabs);
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.TabSet, tabset);
        pane.setParagraphAttributes(aset, false);
    }
}
