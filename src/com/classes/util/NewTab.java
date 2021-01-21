/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.classes.util;

import static com.forms.Editor.enabled_menu;
import static com.forms.Editor.files_opens;
import static com.forms.Editor.line_number;
import static com.forms.Editor.linha_coluna_txt;
import de.sciss.syntaxpane.DefaultSyntaxKit;
import java.awt.Color;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import de.sciss.syntaxpane.actions.CaretMonitor;
import de.sciss.syntaxpane.components.LineNumbersRuler;
import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

/**
 *
 * @author SIMONETO-2
 */
public class NewTab {

    private final JScrollPane[] scrollpane = new JScrollPane[500];
    private final JEditorPane[] jtextpane = new JEditorPane[500];

    private JTabbedPane tab = null;

    public int tabs_criadas = 0;

    public NewTab(JTabbedPane jTab) {
        //ADICIONANDO O JTAB
        this.tab = jTab;
    }

    public void addTab(String tabname, String diretory, String alg) {
        //CRIANDO A SCROLBAR
        scrollpane[tabs_criadas] = new javax.swing.JScrollPane();
        scrollpane[tabs_criadas].setBorder(null);
        scrollpane[tabs_criadas].setBackground(Color.decode("#FFFFFF"));
        scrollpane[tabs_criadas].setName("scrollpane_" + tabs_criadas);

        //CRIANDO JTEXTPANE
        jtextpane[tabs_criadas] = new javax.swing.JEditorPane() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                
                Graphics2D g2d = (Graphics2D) g;
                
               AlphaComposite alphaChannel = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f);
                URL urlicon = NewTab.class.getClassLoader().getResource("com/images/logo.png");
                Image icon = Toolkit.getDefaultToolkit().getImage(urlicon);
                g2d.setComposite(alphaChannel);
                g2d.drawImage(icon, this.getWidth() - 128, this.getHeight() - 128, this);
                g2d.dispose();
            }
        };
        jtextpane[tabs_criadas].setBackground(Color.decode("#FFFFFF"));
        jtextpane[tabs_criadas].setBorder(null);
        //jtextpane[tabs_criadas].setMargin(new java.awt.Insets(5, 5, 5, 5));
        jtextpane[tabs_criadas].setToolTipText(null);
        jtextpane[tabs_criadas].setName("jtextpane_" + tabs_criadas);
        jtextpane[tabs_criadas].setSelectionColor(Color.GRAY);
        //SETANDO A SCROLL NO TEXTPANE
        scrollpane[tabs_criadas].setViewportView(jtextpane[tabs_criadas]);

        //INICIANDO O EDITOR
        jtextpane[tabs_criadas].setContentType("text/portugol");
        jtextpane[tabs_criadas].setText(alg);
        ((de.sciss.syntaxpane.SyntaxDocument) jtextpane[tabs_criadas].getDocument()).clearUndos();

        //ADICIOANDO A TAB
        //"<html><body topmargin=5 marginheight=3>"+tabname+"</body></html>"
        tab.addTab("<html><body topmargin=2 marginheight=2>" + tabname + "</body></html>", scrollpane[tabs_criadas]);
        jtextpane[tabs_criadas].setCaretPosition(0);

        //SETANDO CARET_MONITOR
        CaretMonitor m = new CaretMonitor(jtextpane[tabs_criadas], linha_coluna_txt);
        m.setNoSelectionFormat("Linha %d, Coluna %d (Total de caracteres ate o cursor %d)");
        m.setSelectionFormat("Linha %d, Coluna %d até Linha %d, Coluna %d (Total de caracteres selecionados %d)");

        //VERIFICANDO SE E PARA REMOVER O LINE_NUMBER
        if (!line_number) {
            DefaultSyntaxKit kit = (DefaultSyntaxKit) jtextpane[tabs_criadas].getEditorKit();
            if (kit.isComponentInstalled(jtextpane[tabs_criadas], LineNumbersRuler.class.getName())) {
                kit.deinstallComponent(jtextpane[tabs_criadas], LineNumbersRuler.class.getName());
            }
        }

        //PEGANDO A POSICAO DA TAB
        int tabIndex = tab.getTabCount() - 1;

        //ADICIONANDO NOME A TAB
        tab.getComponentAt(tabIndex).setName("" + tabs_criadas);

        //CONFIGURANDO A ARRAY DE INFORMACOES DO ARQUIVO
        files_opens.addFileOpen(tabs_criadas, "jtextpane_" + tabs_criadas, diretory);

        //SELECIONANDO A TAB ABERTA
        tab.setSelectedIndex(tabIndex);

        //VERIFICANDO SE O MENU ESTA DESABILITADO
        if (enabled_menu == false) {
            enabled_menu = true;
        }
        //setting_menu.setDisabledTop(enabled_menu);

        //APLICANDO FOCUS NO TEXT
        jtextpane[tabs_criadas].requestFocus();

        System.out.println("Aba criada com sucesso. ID:" + tabs_criadas);

        //INCREMENTANDO CONTATADOR
        tabs_criadas++;
    }

    //RETORNAR TOTAL DE TABS
    public int getTabCount() {
        return tab.getTabCount();
    }

    //VAI PARA A TAB SELECIONADA
    public void setSelectedIndex(int index) {
        tab.setSelectedIndex(index);
    }

    //FUNCAO PARA RENOMEAR A TAB
    public void renameTab(int index, String txt) {
        tab.setTitleAt(index, txt);
    }

    public String getTitleTab(int index) {
        return tab.getTitleAt(index);
    }

    //PEGA A TAB QUE ESTA ABERTA
    public int getIndexTab() {
        return tab.getSelectedIndex();
    }

    //DELETAR TAB
    public void unistallTab(int id) {
        scrollpane[id] = null;
        jtextpane[id] = null;
        files_opens.unistall(id);
    }

    //RETORNAR O JTEXTPANE DA TAB ABERTA
    public JEditorPane getEditorPane() {
        JScrollPane scroll_atual = (JScrollPane) tab.getSelectedComponent();
        return (JEditorPane) scroll_atual.getViewport().getComponent(0);
    }

    //RETORNAR O JTEXTPANE DE QUALQUER TAB
    public JEditorPane getJEditorPaneAt(int index) {
        JScrollPane scroll_atual = (JScrollPane) tab.getComponentAt(index);
        return (JEditorPane) scroll_atual.getViewport().getComponent(0);
    }

    public int getIdTab() {
        String[] Split = getEditorPane().getName().split("_");
        return Integer.parseInt(Split[1]);
    }

    public int getIdTab(int index) {
        String[] Split = getJEditorPaneAt(index).getName().split("_");
        return Integer.parseInt(Split[1]);
    }
}
