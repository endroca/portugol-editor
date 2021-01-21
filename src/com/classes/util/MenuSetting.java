/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.classes.util;

import javax.swing.JMenuItem;
import javax.swing.JMenuBar;

/**
 *
 * @author SIMONETO-2
 */
public class MenuSetting {

    //MENU
    JMenuBar MENU;

    //TAB ARQUIVOS
    JMenuItem JMenuItem1;
    JMenuItem JMenuItem2;
    JMenuItem JMenuItem3;
    JMenuItem JMenuItem4;
    JMenuItem JMenuItem5;
    JMenuItem JMenuItem6;
    JMenuItem JMenuItem15;

    //TAB EDITAR
    JMenuItem JMenuItem7;
    JMenuItem JMenuItem8;
    JMenuItem JMenuItem9;
    JMenuItem JMenuItem10;
    JMenuItem JMenuItem11;
    JMenuItem JMenuItem12;
    JMenuItem JMenuItem13;
    JMenuItem JMenuItem14;

    //TAB EXIBIR
    JMenuItem jMenuItem16;

    //TAB ALGORITMO
    //JMenuItem jMenuItem17;
    //JMenuItem jMenuItem18;
    
    
    //TAB AJUDA
    JMenuItem jMenuItem19;

    public MenuSetting(JMenuBar menu) {
        this.MENU = menu;

        JMenuItem1 = MENU.getMenu(0).getItem(0);
        JMenuItem2 = MENU.getMenu(0).getItem(1);
        JMenuItem3 = MENU.getMenu(0).getItem(3);
        JMenuItem4 = MENU.getMenu(0).getItem(4);
        JMenuItem5 = MENU.getMenu(0).getItem(10);
        JMenuItem6 = MENU.getMenu(0).getItem(8);
        JMenuItem15 = MENU.getMenu(0).getItem(6);

        //TAB EDITAR
        JMenuItem7 = MENU.getMenu(1).getItem(0);
        JMenuItem8 = MENU.getMenu(1).getItem(1);
        JMenuItem9 = MENU.getMenu(1).getItem(3);
        JMenuItem10 = MENU.getMenu(1).getItem(4);
        JMenuItem11 = MENU.getMenu(1).getItem(5);
        JMenuItem12 = MENU.getMenu(1).getItem(7);
        JMenuItem13 = MENU.getMenu(1).getItem(9);
        JMenuItem14 = MENU.getMenu(1).getItem(10);

        //TAB EXIBIR
        jMenuItem16 = MENU.getMenu(2).getItem(0);

        //TAB ALGORITMO
        //jMenuItem17 = MENU.getMenu(3).getItem(0);
        jMenuItem19 = MENU.getMenu(3).getItem(1);
    }

    public void setDisabledTop(boolean p) {
        JMenuItem3.setEnabled(p);
        JMenuItem4.setEnabled(p);
        JMenuItem6.setEnabled(p);
        JMenuItem15.setEnabled(p);
        
        //MENU.getMenu(1).setEnabled(p);
        //MENU.getMenu(2).setEnabled(p);
        //MENU.getMenu(3).setEnabled(p);
        
        JMenuItem7.setEnabled(p);
        JMenuItem8.setEnabled(p);
        JMenuItem9.setEnabled(p);
        JMenuItem10.setEnabled(p);
        JMenuItem11.setEnabled(p);
        JMenuItem12.setEnabled(p);
        JMenuItem13.setEnabled(p);
        JMenuItem14.setEnabled(p);
        
        jMenuItem16.setEnabled(p);

        //jMenuItem17.setEnabled(p);
        jMenuItem19.setEnabled(p);
    }

}
