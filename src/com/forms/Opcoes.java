/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forms;

import com.classes.util.JFontChooser;
import com.editor.syntax.PortugolSyntaxKit;
import static com.forms.Editor.file_setting;
import static com.forms.Editor.tab;
import de.sciss.syntaxpane.DefaultSyntaxKit;
import de.sciss.syntaxpane.components.LineNumbersRuler;
import de.sciss.syntaxpane.util.Configuration;
import de.sciss.syntaxpane.util.JarServiceProvider;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.colorchooser.AbstractColorChooserPanel;

/**
 *
 * @author SIMONETO-2
 */
public class Opcoes extends javax.swing.JDialog {

    //CRIANDO A CLASS PARA LEITURA DO KIT
    private PortugolSyntaxKit kit = null;

    //ESTANCIANDO OS CHOOSER
    private final JFontChooser fc = new JFontChooser();
    private final JColorChooser cc = new JColorChooser();

    //ARRAY COM AS KEYS DE CORES
    private final String key[] = {"Style.IDENTIFIER", "Style.KEYWORD", "Style.KEYWORD2", "Style.TYPE", "Style.STRING", "Style.NUMBER", "Style.OPERATOR"};

    //ONDE SERA ARMAZENADO AS CONFIGURACOES JA PRE-CARREGADAS
    private final ArrayList<String> configuracoes_originais = new ArrayList<>();

    //ONDE SERA ARMAZENADO AS CONFIGURACOES DA CLASSE INTERNA
    private final ArrayList<String> configuracoes_originais_internas = new ArrayList<>();

    //CRIADO NO QUAL SERA ARMAZENADO AS MODIFICACOES
    private final ArrayList<String> modificacoes = new ArrayList<>();

    //PARA A CONFIGURACAO DA FONTE
    private Font fonte_padrao = null;
    private boolean font_editada = false;
        
    //ALGORITMO DE EXEMPLO
    private final String txt_amostra = "/*\n"
            + "* ALGORITMO DE AMOSTRA\n"
            + "*/\n"
            + "algoritmo \"Loop - tipo enquanto\"\n"
            + "\n"
            + "var loop : inteiro\n"
            + "\n"
            + "inicio\n"
            + "    loop <- 1\n"
            + "    enquanto loop <= 10 faca\n"
            + "        escreval(loop)\n"
            + "        espere(1000)\n"
            + "        loop <- loop + 1\n"
            + "    fimenquanto\n"
            + "fimalgoritmo";

    /**
     * Creates new form Opcoes
     *
     * @param parent
     * @param modal
     */
    public Opcoes(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
                
        initComponents();

        //PEGANDO AS CONFIGURACOES DA CLASS INTERNA
        String url = "com/editor/syntax/propriedades/config";
        Properties p = JarServiceProvider.readProperties(url);
        for (String retorno : key) {
            configuracoes_originais_internas.add(retorno.replaceAll("\\s+", "") + "," + p.getProperty(retorno).replaceAll("\\s+", ""));
        }

        //SETANDO ESTILO DO EDITOR
        editor_amostra.setContentType("text/portugol");

        //PEGANDO OS ESTILOS PRE-CARREGADOS
        kit = (PortugolSyntaxKit) editor_amostra.getEditorKit();
        kit.deinstallComponent(editor_amostra, LineNumbersRuler.class.getName());

        //SETANDO O ALGORITMO DE EXEMPLO
        editor_amostra.setText(txt_amostra);

        //LENDO CONFIGURACOES DO KIT
        getDataKit();

        /*
         * FONT LOAD
         */
        getDataFontKit();

        //DANDO O REQUEST FOCUS NA LISTAGEM DE CORES PARA EVITAR O BUG DE SELECAO DE COLERES SEM A LISTAGEM ESTAR SELECIONADA
        list_categoria.requestFocus();
        //SELECIONANDO O PRIMEIRO
        list_categoria.setSelectedIndex(0);
    }

    private void getDataKit() {
        System.out.println("Configuracoes padrão:");
        int i = 0;
        for (String retorno : key) {
            configuracoes_originais.add(retorno.replaceAll("\\s+", "") + "," + kit.getProperty(retorno).replaceAll("\\s+", ""));
            System.out.println("Indice (" + i + "): " + retorno + "," + kit.getProperty(retorno));
            i++;
        }
    }

    private void getDataFontKit() {
        //PEGANDO AS CONFIGURACOES LIDAS
        String font[] = kit.getProperty("DefaultFont").split("\\s");
        //SETANDO NO TEXTO DE FONTE
        font_txt.setText(font[0] + ", " + font[1]);
        //CRIANDO A FONTE
        Font t = new Font(font[0], 0, new Integer(font[1]));
        fonte_padrao = t;
        //DEIXANDO A FONTE SELECIONADA NO CHOOSER
        fc.setSelectedFont(t);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        font_txt = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        list_categoria = new javax.swing.JList();
        btn_chooserColor = new javax.swing.JButton();
        panel_cor = new javax.swing.JPanel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        editor_amostra = new javax.swing.JEditorPane();
        jButton3 = new javax.swing.JButton();
        btn_ok = new javax.swing.JButton();
        btn_aplicar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Opções");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                windowsClosing(evt);
            }
        });

        jLabel1.setText("Fonte do editor:");

        font_txt.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        font_txt.setEnabled(false);

        jButton2.setText("...");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openFontChooser(evt);
            }
        });

        jLabel2.setText("Categoria:");

        list_categoria.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Padrão", "Palavra-chave 1 (estrutural)", "Palavra-chave 2 (funções)", "Declaração de variáveis", "String e caracteres", "Numeros", "Operações" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        list_categoria.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                actionSelect(evt);
            }
        });
        jScrollPane2.setViewportView(list_categoria);

        btn_chooserColor.setText("Redefinir cor");
        btn_chooserColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                colorChooser(evt);
            }
        });

        javax.swing.GroupLayout panel_corLayout = new javax.swing.GroupLayout(panel_cor);
        panel_cor.setLayout(panel_corLayout);
        panel_corLayout.setHorizontalGroup(
            panel_corLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 172, Short.MAX_VALUE)
        );
        panel_corLayout.setVerticalGroup(
            panel_corLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jCheckBox1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jCheckBox1.setText("Negrito");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateBold(evt);
            }
        });

        jCheckBox2.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        jCheckBox2.setText("Itáilico");
        jCheckBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateItalic(evt);
            }
        });

        jLabel3.setText("Amostra:");

        editor_amostra.setEditable(false);
        jScrollPane3.setViewportView(editor_amostra);

        jButton3.setText("Cancelar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelAction(evt);
            }
        });

        btn_ok.setText("Ok");
        btn_ok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                brn_okAction(evt);
            }
        });

        btn_aplicar.setText("Aplicar");
        btn_aplicar.setEnabled(false);
        btn_aplicar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aplicarAcao(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jCheckBox2)
                            .addComponent(jCheckBox1)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(panel_cor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btn_chooserColor))
                                .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                    .addComponent(font_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jButton2))
                                .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btn_ok)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_aplicar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(font_txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btn_chooserColor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(panel_cor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jCheckBox2)
                        .addGap(0, 20, Short.MAX_VALUE))
                    .addComponent(jScrollPane3))
                .addGap(44, 44, 44))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(btn_aplicar)
                    .addComponent(btn_ok))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Editor", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //ABRINDO O FONTE CHOOSER
    private void openFontChooser(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openFontChooser
        // TODO add your handling code here:
        int result = fc.showDialog(null);
        if (result == JFontChooser.OK_OPTION) {
            Font font = fc.getSelectedFont();

            font_txt.setText(font.getName() + ", " + font.getSize());
            kit.setProperty("DefaultFont", font.getName() + " " + font.getSize());
            editor_amostra.setFont(font);
            System.out.println("Fonte alterada: " + fonte_padrao.getName() + " " + fonte_padrao.getSize() + " -> (" + font.getName() + " " + font.getSize() + ")");

            if (!fonte_padrao.getName().equals(font.getName()) || fonte_padrao.getSize() != font.getSize()) {
                font_editada = true;
            } else {
                font_editada = false;
            }
        }
        //PARA ATUALIZAR O BOTAO DE APLICAR
        checkBtnAplicar();
    }//GEN-LAST:event_openFontChooser

    //ABRINDO O COLOR CHOOSER
    private void colorChooser(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_colorChooser
        // TODO add your handling code here:
        String tmp[] = formatPropertiesColors(kit.getProperty(key[list_categoria.getSelectedIndex()]));
        Color color_tmp = Color.decode(tmp[0]);

        cc.setColor(color_tmp);
        final AbstractColorChooserPanel[] panels = cc.getChooserPanels();
        for (final AbstractColorChooserPanel accp : panels) {
            if (!accp.getDisplayName().equals("RGB")) {
                cc.removeChooserPanel(accp);
            }
        }

        try {
            removeTransparencySlider(cc);
        } catch (Exception ex) {
            Logger.getLogger(Opcoes.class.getName()).log(Level.SEVERE, null, ex);
        }

        Action setColorAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                panel_cor.setOpaque(true);
                panel_cor.setBackground(cc.getColor());
                updateProperties(key[list_categoria.getSelectedIndex()], "0x" + String.format("%06x", cc.getColor().getRGB() & 0x00FFFFFF), getCheckbox());
            }
        };

        JColorChooser.createDialog(null, "Redefinição de cor", true, cc, setColorAction, null).setVisible(true);
    }//GEN-LAST:event_colorChooser

    //PINTANDO O PAINEL DA COR ESPECIFICA DA TOKEN
    private void actionSelect(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_actionSelect
        // TODO add your handling code here:
        /*
         * COLOR KEYS
         */

        String tmp[] = formatPropertiesColors(kit.getProperty(key[list_categoria.getSelectedIndex()]));
        panel_cor.setOpaque(true);
        Color color_tmp = Color.decode(tmp[0]);
        panel_cor.setBackground(color_tmp);

        jCheckBox1.setSelected(false);
        jCheckBox2.setSelected(false);

        switch (tmp[1]) {
            case "1":
                jCheckBox1.setSelected(true);
                break;
            case "2":
                jCheckBox2.setSelected(true);
                break;
            case "3":
                jCheckBox1.setSelected(true);
                jCheckBox2.setSelected(true);
                break;
        }
    }//GEN-LAST:event_actionSelect

    //UPDATE FONT AO CLICAR NO CHECKBOX
    private void updateBold(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateBold
        // TODO add your handling code here:
        String color = formatPropertiesColors(kit.getProperty(key[list_categoria.getSelectedIndex()]))[0];
        updateProperties(key[list_categoria.getSelectedIndex()], color, getCheckbox());
    }//GEN-LAST:event_updateBold

    //UPDATE FONT AO CLICAR NO CHECKBOX
    private void updateItalic(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateItalic
        // TODO add your handling code here:
        String color = formatPropertiesColors(kit.getProperty(key[list_categoria.getSelectedIndex()]))[0];
        updateProperties(key[list_categoria.getSelectedIndex()], color, getCheckbox());
    }//GEN-LAST:event_updateItalic

    //BOTAO DE CANCELAR AS ACOES
    private void cancelAction(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelAction
        // TODO add your handling code here:
        closed();
    }//GEN-LAST:event_cancelAction

    //CLOSE WINDOWS
    private void windowsClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_windowsClosing
        // TODO add your handling code here:
        closed();
    }//GEN-LAST:event_windowsClosing

    //ACAO DO BOTAO APLICAR
    private void aplicarAcao(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aplicarAcao
        // TODO add your handling code here:
        aplicar();
    }//GEN-LAST:event_aplicarAcao
     
    //ACAO DO BOTAO OK
    private void brn_okAction(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_brn_okAction
        if(btn_aplicar.isEnabled()){
            aplicar();
        }
        dispose();
    }//GEN-LAST:event_brn_okAction

    //PEGAR UM ESTILO JA MODIFICADO NA ARRAY
    //  FORMATO [KEY,COLOR,FONTSTYLE]
    private String[] getConfiguracaoOriginal(String key) {
        String r[] = null;
        int i = 0;
        if (!configuracoes_originais.isEmpty()) {
            while (i < configuracoes_originais.size() && !configuracoes_originais.get(i).split(",")[0].equals(key)) {
                i++;
            }
            r = configuracoes_originais.get(i).split(",");
        }
        return r;
    }

    //FUNCAO RESPONSAVEL POR ATUALIZAR A ARRAY MODIFICACOES
    private void updateProperties(String key, String color, String font) {
        //PEGANDO A COR E O ESTILO DE FONTE E ARMAZENANDO NA ARRAY
        String comparacao[] = getConfiguracaoOriginal(key);

        //FAZ O UPDATE NAS PROPRIEDADES
        kit.setProperty(key, color + "," + font);

        //FAZ O UPDATE NA ARRAY MODIFICACOES
        updateArrayModificacoes(key, color, font);

        System.out.println("Modifição em " + key + " : " + comparacao[1] + " , " + comparacao[2] + " -> (" + color + " , " + font + ")");
        editor_amostra.updateUI();
        editor_amostra.revalidate();
        editor_amostra.validate();

        if (comparacao[1].equals(color) && comparacao[2].equals(font)) {
            //VERIFICANDO SE A ARRAY NAO ESTA VAZIA
            for (int i = 0; i < modificacoes.size(); i++) {
                if (modificacoes.get(i).split(",")[0].equals(key)) {
                    modificacoes.remove(i);
                    break;
                }
            }
        }
        //PARA ATUALIZAR O BOTAO DE APLICAR
        checkBtnAplicar();

    }

    //RESPONSAVEL POR FORMATAR A SETRING E RETORNAR O VALOR SEPARADO
    //  FORMATO [COLOR,FONTSTYLE]
    private String[] formatPropertiesColors(String key) {
        String retorno[] = null;
        String pattern = "(.*)\\,[ \\t]*(.*)";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(key);
        while (m.find()) {
            String add[] = {m.group(1).replaceAll("\\s+", ""), m.group(2).replaceAll("\\s+", "")};
            retorno = add;
        }
        return retorno;
    }

    //REPONSAVEL POR ATUALIZAR A ARRAY MODIFICACOES COMPARADO OS VALOR PRE EXISTENTES
    private void updateArrayModificacoes(String key, String color, String font) {
        if (!modificacoes.isEmpty()) {
            int i = 0;
            while (i < modificacoes.size() && !modificacoes.get(i).split(",")[0].equals(key)) {
                i++;
            }
            if (i < modificacoes.size()) {
                modificacoes.set(i, key + "," + color + "," + font);
            } else {
                modificacoes.add(key + "," + color + "," + font);
            }
        } else {
            modificacoes.add(key + "," + color + "," + font);
        }
    }

    //VERIFICANDO OS CHECKBOX 
    private String getCheckbox() {
        String r = null;
        boolean strong = jCheckBox1.isSelected();
        boolean italic = jCheckBox2.isSelected();

        if (strong && italic) {
            r = "3";
        } else if (strong && !italic) {
            r = "1";
        } else if (!strong && italic) {
            r = "2";
        } else {
            r = "0";
        }
        return r;
    }

    //ACAO PARA FECHAMENTO
    private void closed() {
        if (!modificacoes.isEmpty() || font_editada == true) {
            int result = JOptionPane.showConfirmDialog(null, "Deseja salvar as configurações modificadas ?", "Alerta", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                aplicar();
            } else {
                //RESETA AS CONFIGURACOES (PARTE INTERNA)
                Configuration conf = DefaultSyntaxKit.getConfig(PortugolSyntaxKit.class);
                String url = "com/editor/syntax/propriedades/config";
                Properties p = JarServiceProvider.readProperties(url);
                conf.putAll(p);

                // INCLUI AS CONFIGURACOES (PARTE EXTERNA)
                if (file_setting.exists()) {
                    InputStream leitura = null;
                    try {
                        leitura = new FileInputStream(file_setting);
                        Properties prop = new Properties();
                        prop.load(leitura);
                        conf.putAll(prop);
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(Editor.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(Editor.class.getName()).log(Level.SEVERE, null, ex);
                    } finally {
                        try {
                            leitura.close();
                        } catch (IOException ex) {
                            Logger.getLogger(Editor.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        }
        dispose();
    }

    //BRN APLICAR (SETAGEM DE ENABLE E DISABLE)
    private void checkBtnAplicar() {
        if (!modificacoes.isEmpty() || font_editada == true) {
            btn_aplicar.setEnabled(true);
        } else {
            btn_aplicar.setEnabled(false);
        }
    }

    private void aplicar() {
        ArrayList<String> write = new ArrayList<>();

        if (font_editada == true) {
            write.add(kit.getProperty("DefaultFont"));
        }

        if (!modificacoes.isEmpty()) {
            boolean controlWrite = true;
            for (String retorno : modificacoes) {
                String tmp[] = retorno.split(",");

                //VERIFICANDO SE EXISTE NAS CONFIGURACOES ORIGINIAS INTERNAS
                for (String original : configuracoes_originais_internas) {
                    String comparacao[] = original.split(",");

                    if (comparacao[0].equals(tmp[0]) && comparacao[1].equals(tmp[1]) && comparacao[2].equals(tmp[2])) {
                        controlWrite = false;
                        break;
                    } else {
                        controlWrite = true;
                    }
                }

                if (controlWrite) {
                    write.add(tmp[0] + "=" + tmp[1] + "," + tmp[2]);
                }
            }
        }

        if (!file_setting.delete()) {
            //APAGAR CONTEU DO ARQUIVO
            PrintWriter clean = null;
            try {
                clean = new PrintWriter(file_setting);
                clean.print("");

            } catch (FileNotFoundException ex) {
                Logger.getLogger(Opcoes.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                clean.close();
            }
        }

        if (!write.isEmpty()) {
            //PREPARA PARA SALVAR O ARQUIVO
            PrintWriter print = null;
            try {
                print = new PrintWriter(file_setting);

                for (String line : write) {
                    print.println(line);
                }

            } catch (FileNotFoundException ex) {
                Logger.getLogger(Opcoes.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                print.close();
            }
        }

        modificacoes.clear();
        configuracoes_originais.clear();

        getDataKit();
        getDataFontKit();

        font_editada = false;
        btn_aplicar.setEnabled(false);
        
        //FAZ O UPDATE NO EDITOR
        updateStyleText();
        
        System.out.println("Modificações aplicadas com sucesso.");
    }
    
    public void updateStyleText() {
        for(int i = 0 ; i < tab.getTabCount();i++){
            tab.getJEditorPaneAt(i).updateUI();
            tab.getJEditorPaneAt(i).revalidate();
            tab.getJEditorPaneAt(i).validate();
        }
    }

    //REMOVER ALGUMAS CONFIGURACOES DO COLOR CHOOSER
    private static void removeTransparencySlider(JColorChooser jc) throws Exception {

        final AbstractColorChooserPanel[] colorPanels = jc.getChooserPanels();
        final AbstractColorChooserPanel cp = colorPanels[0];

        Field f = null;
        try {
            f = cp.getClass().getDeclaredField("panel");
        } catch (NoSuchFieldException | SecurityException e) {
        }
        f.setAccessible(true);

        Object colorPanel = null;
        try {
            colorPanel = f.get(cp);
        } catch (IllegalArgumentException | IllegalAccessException e) {
        }

        Field f2 = null;
        try {
            f2 = colorPanel.getClass().getDeclaredField("spinners");
        } catch (NoSuchFieldException | SecurityException e4) {
        }
        f2.setAccessible(true);
        Object rows = null;
        try {
            rows = f2.get(colorPanel);
        } catch (IllegalArgumentException | IllegalAccessException e3) {
        }

        final Object transpSlispinner = Array.get(rows, 3);
        Field f3 = null;
        try {
            f3 = transpSlispinner.getClass().getDeclaredField("slider");
        } catch (NoSuchFieldException | SecurityException e) {
        }
        f3.setAccessible(true);
        JSlider slider = null;
        try {
            slider = (JSlider) f3.get(transpSlispinner);
        } catch (IllegalArgumentException | IllegalAccessException e2) {
        }
        slider.setVisible(false);
        Field f4 = null;
        try {
            f4 = transpSlispinner.getClass().getDeclaredField("spinner");
        } catch (NoSuchFieldException | SecurityException e1) {
        }
        f4.setAccessible(true);
        JSpinner spinner = null;
        try {
            spinner = (JSpinner) f4.get(transpSlispinner);
        } catch (IllegalArgumentException | IllegalAccessException e) {
        }
        spinner.setVisible(false);
        Field f5 = null;
        try {
            f5 = transpSlispinner.getClass().getDeclaredField("label");
        } catch (NoSuchFieldException | SecurityException e1) {
        }
        f5.setAccessible(true);
        JLabel label = null;
        try {
            label = (JLabel) f5.get(transpSlispinner);
        } catch (IllegalArgumentException | IllegalAccessException e) {
        }
        label.setVisible(false);

        Field f6 = null;
        try {
            f6 = transpSlispinner.getClass().getDeclaredField("value");
        } catch (NoSuchFieldException | SecurityException e1) {
        }
        f6.setAccessible(true);
        float value = 0;
        try {
            value = (float) f6.get(transpSlispinner);
        } catch (IllegalArgumentException | IllegalAccessException e) {
        }

    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_aplicar;
    private javax.swing.JButton btn_chooserColor;
    private javax.swing.JButton btn_ok;
    private javax.swing.JEditorPane editor_amostra;
    private javax.swing.JTextField font_txt;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JList list_categoria;
    private javax.swing.JPanel panel_cor;
    // End of variables declaration//GEN-END:variables
}
