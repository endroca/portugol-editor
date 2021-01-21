/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.classes.editor;


import static com.forms.Editor.tab;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JMenuItem;
import javax.swing.JTextPane;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

/**
 *
 * @author Andrew
 */
public class UndoAndRedo {

    private JTextPane campo = null;
    private UndoManager undoManager = new UndoManager();
    private UndoAction undoAction = new UndoAction();
    //private RedoAction redoAction = new RedoAction();

    public UndoAndRedo(JTextPane c) {
        this.campo = c;    
        
        this.campo.getDocument().addUndoableEditListener(new UndoListener());
        
        
        //jMenuItem7.addActionListener(undoAction);
        //jMenuItem8.addActionListener(redoAction);
    }

    class UndoListener implements UndoableEditListener {

        @Override
        public void undoableEditHappened(UndoableEditEvent e) {
            System.out.println("update");
            undoManager.addEdit(e.getEdit());
            //undoAction.update();
            //redoAction.update();
        }
    }

    class UndoAction extends JTextPane {

        public void setUndo() {
            undoManager.undo();
            //undoAction.update();
            //redoAction.update();
        }
    }
}
/*
 class UndoAction extends AbstractAction {

 public UndoAction() {
 super("Desfazer");
 this.putValue(Action.NAME, undoManager.getUndoPresentationName());
 this.setEnabled(false);
 }

 @Override
 public void actionPerformed(ActionEvent e) {
 System.out.println(this.isEnabled());
 if (this.isEnabled()) {
 undoManager.undo();
 undoAction.update();
 redoAction.update();
 }
 }

 public void update() {
 this.putValue(Action.NAME, undoManager.getUndoPresentationName());
 this.setEnabled(undoManager.canUndo());
 }
 }

 class RedoAction extends AbstractAction {

 public RedoAction() {
 this.putValue(Action.NAME, undoManager.getRedoPresentationName());
 this.setEnabled(false);
 }
        
 @Override
 public void actionPerformed(ActionEvent e) {
 if (this.isEnabled()) {
 undoManager.redo();
 undoAction.update();
 redoAction.update();
 }
 }

 public void update() {
 this.putValue(Action.NAME, undoManager.getRedoPresentationName());
 this.setEnabled(undoManager.canRedo());
 }
 }
 */

