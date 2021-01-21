/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.test;

/**
 *
 * @author Andrew
 */
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.UndoManager;

public class UndoEditor extends JFrame {
  private UndoManager undoManager = new UndoManager();
  private JMenuBar menuBar = new JMenuBar();
  private JMenu editMenu = new JMenu("Edit");
  private UndoAction undoAction = new UndoAction();
  private RedoAction redoAction = new RedoAction();

  public UndoEditor() {
    setLayout(new BorderLayout());
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    JTextPane editor = new JTextPane();
    editor.getDocument().addUndoableEditListener(new UndoListener());

    menuBar.add(editMenu);
    editMenu.add(undoAction);
    editMenu.add(redoAction);
    this.setJMenuBar(menuBar);
    add(new JScrollPane(editor));
    setSize(400, 300);
    setVisible(true);
  }

  public static void main(String[] args) {
    UndoEditor e = new UndoEditor();
  }

  class UndoListener implements UndoableEditListener {
    public void undoableEditHappened(UndoableEditEvent e) {
      undoManager.addEdit(e.getEdit());
      undoAction.update();
      redoAction.update();
    }
  }

  class UndoAction extends AbstractAction {
    public UndoAction() {
      this.putValue(Action.NAME, undoManager.getUndoPresentationName());
      this.setEnabled(false);
    }

    public void actionPerformed(ActionEvent e) {
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
}
