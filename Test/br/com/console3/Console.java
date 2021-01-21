/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.console3;

/**
 *
 * @author SIMONETO-2
 */
public class Console extends javax.swing.JFrame {

    public Console() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        _console = new javax.swing.JTextPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        _console.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        jScrollPane1.setViewportView(_console);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {

        new Console().setVisible(true);

        ConsoleIO ConsoleIO = new ConsoleIO(_console);

        char sexo;

        ConsoleIO.println(("Informe O SEXO M OU F:"));
        sexo = ConsoleIO.writeChar();
        while (!(sexo == 'M') && !(sexo == 'F')) {
            ConsoleIO.print(("ENTRADA INVALIDA!"));
            sexo = ConsoleIO.writeChar();
        }
        if (sexo == 'M') {
            ConsoleIO.print(("MASCULINO!"));
        } else {
            ConsoleIO.print(("FEMININO!"));

        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private static javax.swing.JTextPane _console;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
