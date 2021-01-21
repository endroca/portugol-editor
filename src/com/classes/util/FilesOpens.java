/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.classes.util;

import java.util.ArrayList;

/**
 *
 * @author Andrew
 */
public class FilesOpens {

    ArrayList<String[]> ArrayFilesOpens = new ArrayList<>();

    /*
     *   TABELA DE INFORMACOES
     *   0 - ID
     *   1 - TEXT_PANE NAME
     *   2 - DIRETORIO
     */
    public void addFileOpen(int id, String text_pane_name, String diretory) {
        //ARMAZENANDO NA ARRAY TEMPORARIA
        String[] tmp = {"" + id, text_pane_name, diretory};
        //ADICINANDO NA ARRAY PRINCIPAIS
        ArrayFilesOpens.add(tmp);

        System.out.println("Total de arquivos abertos: " + ArrayFilesOpens.size());
    }

    public String[] getInfo(String jTextPane) {
        String[] info = new String[4];
        int position = 0;

        for (String[] FilesOpen : ArrayFilesOpens) {
            if (FilesOpen[1].equals(jTextPane)) {
                String[] retorno = {FilesOpen[0], FilesOpen[1], FilesOpen[2], "" + position};
                info = retorno;
                break;
            }
            position++;
        }
        return info;
    }

    public int getPositionItem(String diretory) {
        int position = 0;
        for (String[] FilesOpen : ArrayFilesOpens) {
            if (FilesOpen[2].equals(diretory)) {
                break;
            }
            position++;
        }
        return position;
    }
    
    public int getPositionItem(int id) {
        int position = 0;
        for (String[] FilesOpen : ArrayFilesOpens) {
            if (FilesOpen[0].equals(""+id)) {
                break;
            }
            position++;
        }
        return position;
    }

    public String[] getInfo(int id) {
        String[] info = new String[4];
        int position = 0;

        for (String[] FilesOpen : ArrayFilesOpens) {
            if (FilesOpen[0].equals("" + id)) {
                String[] retorno = {FilesOpen[0], FilesOpen[1], FilesOpen[2], "" + position};
                info = retorno;
                break;
            }
            position++;
        }
        return info;
    }

    public boolean isExisting(String jTextPane) {
        boolean response = false;
        String[] info = getInfo(jTextPane);
        System.out.println(info[1]);
        if (!"".equals(info[1])) {
            response = true;
        }
        return response;
    }

    public boolean isExistingOpen(String location) {
        boolean response = false;

        for (String[] FilesOpen : ArrayFilesOpens) {
            if (FilesOpen[2].equals(location)) {
                response = true;
                break;
            }
        }
        return response;
    }

    public void updateDir(String id, String value) {
        String[] info = getInfo(id);

        int position = Integer.parseInt(info[3]);
        ArrayFilesOpens.get(position)[2] = value;

    }

    public void unistall(int id) {
        String[] info = getInfo(id);

        int position = Integer.parseInt(info[3]);
        ArrayFilesOpens.remove(position);
    }
}
