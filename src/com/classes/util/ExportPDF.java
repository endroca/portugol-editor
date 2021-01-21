/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.classes.util;

import static com.forms.Editor.msg;
import static com.forms.Editor.tab;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

/**
 *
 * @author SIMONETO-2
 */
public class ExportPDF {
    
    private JMenuItem t;

    public ExportPDF(JMenuItem t) {
        try {
            this.t = t;
            export();
        } catch (IOException | COSVisitorException ex) {
            Logger.getLogger(ExportPDF.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void export() throws FileNotFoundException, IOException, COSVisitorException {
        //ABRINDO CHOOSE PARA SALVAR ARQUIVO
        JFileChooser SavedFile = new JFileChooser();
        SavedFile.setAcceptAllFileFilterUsed(false);
        FileFilter ft = new FileNameExtensionFilter("Salvar como arquivo pdf *.pdf", "pdf");
        SavedFile.addChoosableFileFilter(ft);
        SavedFile.setDialogTitle("Aonde você deseja salvar o seu algoritmo ?");

        //VARIAVEL DE RETORNO
        int returnVal = SavedFile.showSaveDialog(t);

        //ABRINDO DIALOGO PARA SALVAR ARQUIVO CRIPTOGRAFADO
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File salvar_arquivo = SavedFile.getSelectedFile();

            //VERIFICANDO SE O ARQUIVO EXISTE PARA O REPLACE
            if (salvar_arquivo.exists()) {
                int result = JOptionPane.showConfirmDialog(null, "O arquivo que você está tentando salvar já existe. \nDeseja substituir esse arquivo?", "Alerta", JOptionPane.YES_NO_CANCEL_OPTION);
                if (result == JOptionPane.NO_OPTION) {
                    export();
                    return;
                }
            }

            //TRATANDO DO NOME
            String nome = salvar_arquivo.toString();

            //VERIFICA SE JA EXISTE O .ALG NO NOME
            if (!nome.endsWith(".pdf")) {
                nome = nome + ".pdf";
            }

            //MODIFICANDO NOME
            salvar_arquivo = new File(nome);

            //APAGAR CONTEU DO ARQUIVO
            PrintWriter clean = new PrintWriter(salvar_arquivo);
            clean.print("");
            clean.close();
            
            generatePDF(salvar_arquivo);
        }
    }

    public void generatePDF(File file) throws IOException, COSVisitorException {
        PDDocument doc = null;
        try {
            doc = new PDDocument();
            PDPage page = new PDPage();
            doc.addPage(page);
            PDPageContentStream contentStream = new PDPageContentStream(doc, page);

            PDFont pdfFont = PDType1Font.HELVETICA;
            float fontSize = 15;
            float leading = 1.5f * fontSize;

            PDRectangle mediabox = page.findMediaBox();
            float margin = 72;
            float width = mediabox.getWidth() - 2 * margin;
            float startX = mediabox.getLowerLeftX() + margin;
            float startY = mediabox.getUpperRightY() - margin;

            String text[] = tab.getEditorPane().getText().split("\\n");
            
            List<String> lines = new ArrayList<>();
            int lastSpace = -1;
            
            for(int i = 0; i<text.length ; i++){
                while (text[i].length() > 0) {
                    int spaceIndex = text[i].indexOf(' ', lastSpace + 1);
                    if (spaceIndex < 0) {
                        lines.add(text[i]);
                        text[i] = "";
                    } else {
                        String subString = text[i].substring(0, spaceIndex);
                        float size = fontSize * pdfFont.getStringWidth(subString) / 1000;
                        if (size > width) {
                            if (lastSpace < 0) // So we have a word longer than the line... draw it anyways
                            {
                                lastSpace = spaceIndex;
                            }
                            subString = text[i].substring(0, lastSpace);
                            lines.add(subString);
                            text[i] = text[i].substring(lastSpace).trim();
                            lastSpace = -1;
                        } else {
                            lastSpace = spaceIndex;
                        }
                    }
                }
            }

            contentStream.beginText();
            contentStream.setFont(pdfFont, fontSize);
            contentStream.moveTextPositionByAmount(startX, startY);
            for (String line : lines) {
                contentStream.drawString(line);
                contentStream.moveTextPositionByAmount(0, -leading);
            }
            contentStream.endText();
            contentStream.close();

            doc.save(file);
            System.out.println("Algoritmo exportado com sucesso. "+file.getPath());
            msg.write("PDF gerado com sucedo, em: "+file.getPath());
        } finally {
            if (doc != null) {
                doc.close();
            }
        }
    }

}
