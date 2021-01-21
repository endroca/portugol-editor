/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.classes.util;

import static com.forms.Editor.files_opens;
import static com.forms.Editor.msg;
import static com.forms.Editor.tab;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author SIMONETO-2
 */
public class SaveSaveAs {

    private JMenuItem botao_save = null;
    private JMenuItem botao_save_as = null;
    private JTextPane editor_txt = null;

    public SaveSaveAs(JMenuItem btn_save, JMenuItem btn_saveAs) {
        //ARMAZENANDO VARIAVEIS (BOTOES)
        this.botao_save = btn_save;
        this.botao_save_as = btn_saveAs;

        //ADICIONANDO EVENTOS
        botao_save_as.addActionListener(action_save_As);
        botao_save.addActionListener(action_save);
    }

    //EVENTO PARA SAVA(AS)
    private final ActionListener action_save_As = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (e.getSource() == botao_save_as) {
                    SaveAs();
                }
            } catch (IOException ex) {
                Logger.getLogger(SaveSaveAs.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };

    //EVENTO PARA SALVAMENTO PADRAO
    private final ActionListener action_save = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getSource() == botao_save) {
                //VERIFICANDO SE EXISTE UM LOCAL SALVO
                                
                if (!"".equals(files_opens.getInfo(tab.getEditorPane().getName())[2])) {
                    try {
                        //SALVA O ARQUIVO
                        Save(files_opens.getInfo(tab.getEditorPane().getName())[2]);
                        msg.write("Arquivo " + files_opens.getInfo(tab.getEditorPane().getName())[2] + " foi modificado com sucesso.");
                        System.out.println("Arquivo " + files_opens.getInfo(tab.getEditorPane().getName())[2] + " foi modificado com sucesso.");
                    } catch (IOException ex) {
                        JOptionPane.showConfirmDialog(null, "Erro ao salver o arquivo, tente novamente", "Alerta", JOptionPane.ERROR_MESSAGE);                        
                        //Logger.getLogger(SaveSaveAs.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    try {
                        //ABRE JANELA DE SAVE(AS)
                        SaveAs();
                    } catch (IOException ex) {
                        JOptionPane.showConfirmDialog(null, "Erro ao salver o arquivo, tente novamente", "Alerta", JOptionPane.ERROR_MESSAGE);
                        //Logger.getLogger(SaveSaveAs.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    };

    public void Save(String diretory) throws IOException {
        //ARMAZENA VARIAVEIS
        String tmp = diretory;
        File salvar_arquivo = new File(tmp);

        //APAGAR CONTEU DO ARQUIVO
        PrintWriter clean = new PrintWriter(salvar_arquivo);
        clean.print("");
        clean.close();

        //PREPARA PARA SALVAR O ARQUIVO
        PrintWriter writer = new PrintWriter(diretory);
        String[] alg = tab.getEditorPane().getText().split("\n");
        for (int i = 0; i < alg.length; i++) {
            if (i == alg.length - 1) {
                writer.print(alg[i]);
            } else {
                writer.println(alg[i]);
            }
        }
        writer.close();
    }

    public void SaveAs() throws FileNotFoundException, IOException {
        //ABRINDO CHOOSE PARA SALVAR ARQUIVO
        JFileChooser SavedFile = new JFileChooser();
        SavedFile.setAcceptAllFileFilterUsed(false);
        FileFilter ft = new FileNameExtensionFilter("Salvar como arquivo algoritmo *.alg", "alg");
        SavedFile.addChoosableFileFilter(ft);
        SavedFile.setDialogTitle("Aonde você deseja salvar o seu algoritmo ?");

        //VARIAVEL DE RETORNO
        int returnVal = SavedFile.showSaveDialog(botao_save_as);

        //ABRINDO DIALOGO PARA SALVAR ARQUIVO CRIPTOGRAFADO
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File salvar_arquivo = SavedFile.getSelectedFile();

            //VERIFICANDO SE O ARQUIVO EXISTE PARA O REPLACE
            if (salvar_arquivo.exists()) {
                int result = JOptionPane.showConfirmDialog(null, "O arquivo que você está tentando salvar já existe. \nDeseja substituir esse arquivo?", "Alerta", JOptionPane.YES_NO_CANCEL_OPTION);
                if (result == JOptionPane.NO_OPTION) {
                    SaveAs();
                    return;
                }
            }

            //TRATANDO DO NOME
            String nome = salvar_arquivo.toString();

            //VERIFICA SE JA EXISTE O .ALG NO NOME
            if (!nome.endsWith(".alg")) {
                nome = nome + ".alg";
            }

            //MODIFICANDO NOME
            salvar_arquivo = new File(nome);

            //SALVANDO O ARQUIVO
            Save(salvar_arquivo.toString());

            //VERIFICANDO SE O ARQUIVO JA ESTA SALVO
            if (!"".equals(files_opens.getInfo(tab.getEditorPane().getName())[2])) {
                //PARA O SAVAS_aS
                tab.addTab(salvar_arquivo.getName(), nome, tab.getEditorPane().getText());
            } else {
                //PARA O SAVE
                files_opens.updateDir(tab.getEditorPane().getName(), nome);
                tab.renameTab(tab.getIndexTab(), salvar_arquivo.getName());
            }
            
            
            msg.write("Arquivo " + salvar_arquivo.toString() + " foi salvo com sucesso.");
            System.out.println("Arquivo " + salvar_arquivo.toString() + " foi salvo com sucesso.");

        }

    }

}
