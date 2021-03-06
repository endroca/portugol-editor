package com.forms;

import static com.forms.Editor.tab;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author SIMONETO-2
 */
public class LocalizarSubstituir extends javax.swing.JFrame {

    private int pos = 0;
    private int pos_reg = 0;
    private String tmp_search = "";
    private boolean search = false;
    private boolean replaceAllResult = true;

    /**
     * Creates new form LocalizarSubstituir
     */
    public LocalizarSubstituir() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        find_txt = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        replace_txt = new javax.swing.JTextArea();
        btn_buscar = new javax.swing.JButton();
        btn_replace = new javax.swing.JButton();
        btn_replaceAll = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Localizar e substituir");
        setResizable(false);

        jLabel1.setText("Encontrar:");

        find_txt.setColumns(20);
        find_txt.setRows(2);
        find_txt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TabFocus1(evt);
            }
        });
        jScrollPane1.setViewportView(find_txt);

        jLabel2.setText("Substituir:");

        replace_txt.setColumns(20);
        replace_txt.setRows(2);
        replace_txt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TabFocus2(evt);
            }
        });
        jScrollPane2.setViewportView(replace_txt);

        btn_buscar.setText("Buscar");
        btn_buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FindAction(evt);
            }
        });

        btn_replace.setText("Substituir");
        btn_replace.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReplaceAction(evt);
            }
        });

        btn_replaceAll.setText("Substituir todos");
        btn_replaceAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                replaceAllAction(evt);
            }
        });

        jLabel3.setText("Opções:");

        jCheckBox1.setSelected(true);
        jCheckBox1.setText("Usar expressão regular");

        jCheckBox2.setText("Diferenciar maiuscula de minuscula");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jCheckBox2)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jCheckBox1)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE)
                                    .addComponent(jScrollPane2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btn_replaceAll, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                                    .addComponent(btn_replace, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btn_buscar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btn_buscar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_replace)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_replaceAll)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox1)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TabFocus1(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TabFocus1
        // TODO add your handling code here:
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_TAB) {
            replace_txt.requestFocus();
            evt.consume();
        }
    }//GEN-LAST:event_TabFocus1

    private void TabFocus2(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TabFocus2
        // TODO add your handling code here:
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_TAB) {
            btn_buscar.requestFocus();
            evt.consume();
        }
    }//GEN-LAST:event_TabFocus2

    private void FindAction(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FindAction
        // TODO add your handling code here:
        find_text(find_txt.getText());
    }//GEN-LAST:event_FindAction

    private void ReplaceAction(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReplaceAction
        // TODO add your handling code here:
        if (search == false) {
            find_text(find_txt.getText());
        }
        replace_text(replace_txt.getText());
    }//GEN-LAST:event_ReplaceAction

    private void replaceAllAction(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_replaceAllAction
        // TODO add your handling code here:
        while(replaceAllResult){
            if (search == false) {
                find_text(find_txt.getText());
            }
            replace_text(replace_txt.getText());            
        }
    }//GEN-LAST:event_replaceAllAction

    public void replace_text(String replace) {
        if (replace != null && replace.length() > 0) {
            if (search != false) {
                System.out.println("[Sistema~] > Texto \"" + find_txt.getText() + "\" foi substituído por \"" + replace + "\".");
                tab.getEditorPane().replaceSelection(replace);
                search = false;
            }
        }
    }

    public void find_text(String find) {

        search = false;

        String texto = find;

        if (jCheckBox2.isSelected() == false) {
            texto = texto.toLowerCase();
        }

        if (texto != null && texto.length() > 0) {
            Document document = tab.getEditorPane().getDocument();
            int findLength = texto.length();

            if (!tmp_search.equals(texto)) {
                pos = 0;
                tmp_search = "";
            }

            try {
                boolean found = false;
                // Rest the search position if we're at the end of the document
                tmp_search = texto;

                if (!jCheckBox1.isSelected()) {
                    if (pos + findLength > document.getLength()) {
                        pos = 0;
                    }
                    // While we haven't reached the end...
                    // "<=" Correction
                    while (pos + findLength <= document.getLength()) {
                        // Extract the text from teh docuemnt
                        String match = null;
                        if (jCheckBox2.isSelected() == false) {
                            match = document.getText(pos, findLength).toLowerCase();

                        } else {
                            match = document.getText(pos, findLength);
                        }

                        // Check to see if it matches or request
                        if (match.equals(texto)) {
                            found = true;
                            break;
                        }
                        pos++;
                    }
                } else {

                    String match = document.getText(0, document.getLength());

                    Pattern p = null;
                    if (!jCheckBox2.isSelected()) {
                        p = Pattern.compile(find, Pattern.CASE_INSENSITIVE);
                    } else {
                        p = Pattern.compile(find);
                    }

                    Matcher m = p.matcher(match);

                    int count = 0;
                    while (m.find()) {
                        count++;
                    }

                    if (pos_reg >= count) {
                        pos = 0;
                        pos_reg = 0;
                    }

                    if (m.find(pos)) {
                        pos = m.start(0);
                        findLength = m.group(0).length();
                        pos_reg++;
                        found = true;
                    }
                }

                // Did we find something...
                if (found) {
                    // Get the rectangle of the where the text would be visible...
                    Rectangle viewRect = tab.getEditorPane().modelToView(pos);
                    // Scroll to make the rectangle visible
                    tab.getEditorPane().scrollRectToVisible(viewRect);
                    // Highlight the text
                    tab.getEditorPane().setCaretPosition(pos + findLength);
                    tab.getEditorPane().moveCaretPosition(pos);
                    // Move the search position beyond the current match

                    pos += findLength;

                    search = true;
                } else {
                    //JOptionPane.showMessageDialog(null, "Nenhuma ocorrência foi encontrada", "Alerta", JOptionPane.WARNING_MESSAGE);
                    replaceAllResult = false;
                }

            } catch (BadLocationException | HeadlessException exp) {
            }
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_buscar;
    private javax.swing.JButton btn_replace;
    private javax.swing.JButton btn_replaceAll;
    private javax.swing.JTextArea find_txt;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea replace_txt;
    // End of variables declaration//GEN-END:variables
}
