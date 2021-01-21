/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.classes.util;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Andrew
 */
public class Menssage extends JPanel {

    private JLabel label = null;
    
    private Thread thread = null;

    public Menssage(JLabel label) {
        this.label = label;
        this.label.setText("");
    }

    public void write(final String msg) {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                label.setText(msg);
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Menssage.class.getName()).log(Level.SEVERE, null, ex);
                } finally{
                    label.setText("");
                }
            }
        });
        if(thread.isAlive()){
            thread.interrupt();
        }
        thread.start();
    }

}
