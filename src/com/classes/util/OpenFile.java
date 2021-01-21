/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.classes.util;

import static com.forms.Editor.files_opens;
import static com.forms.Editor.tab;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author SIMONETO-2
 */
public class OpenFile {

    private JMenuItem menu = null;

    public OpenFile(JMenuItem botao) {
        //ARMAZENANDO BOTAO
        this.menu = botao;
        //ADICIONANDO EVENTO
        menu.addActionListener(actions);
    }
    
    //RESPONSAVEL PELA LEITURA DO ARQUIVO
    public String readFile(String path, Charset encoding) throws IOException {
        /*byte[] encoded = Files.readAllBytes(Paths.get(path));
         return new String(encoded, encoding);*/
        String retorno = "";
        List<String> lines = Files.readAllLines(Paths.get(path), encoding);
        for (int i = 0; i < lines.size(); i++) {
            if (i == lines.size() - 1) {
                retorno += lines.get(i);
            } else {
                retorno += lines.get(i) + "\n";
            }
        }
        return retorno;
    }
    
    //ACAO DE ABERTURA DO ARQUIVO FILE CHOOSER
    private final ActionListener actions = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == menu) {
                JFileChooser fc = new JFileChooser();
                FileFilter filter = new FileNameExtensionFilter("Arquivo algoritmo *.alg", "alg");
                fc.addChoosableFileFilter(filter);
                fc.setFileFilter(filter);
                int returnVal = fc.showOpenDialog(menu);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    
                    //PARA EVITAR A ABERTURA DE ITENS DUPLICADOS
                    if (!files_opens.isExistingOpen(file.getAbsoluteFile().toString())) {
                        try {
                            InputStreamReader r = new InputStreamReader(new FileInputStream(file));
                            /*Charset.defaultCharset()*/
                            tab.addTab(file.getName(), file.getAbsoluteFile().toString(), readFile(file.toString(), Charset.forName(r.getEncoding())));
                            tab.getEditorPane().setCaretPosition(0);
                            System.out.println("Arquivo " + file.getName() + " foi aberto com sucesso. Charset: " + r.getEncoding());
                        } catch (IOException ex) {
                            Logger.getLogger(OpenFile.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        tab.setSelectedIndex(files_opens.getPositionItem(file.getAbsoluteFile().toString()));
                    }
                }
            }
        }
    };
}
