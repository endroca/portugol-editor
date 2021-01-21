/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forms;

import com.classes.util.ExportPDF;
import com.classes.util.FilesOpens;
import com.classes.util.Menssage;
import com.classes.util.MenuSetting;
import com.classes.util.NewTab;
import com.classes.util.OpenFile;
import com.classes.util.SaveSaveAs;
import com.compilador.portugol.Compilador;
import com.compilador.portugol.ExecCompilador;
import com.editor.syntax.PortugolSyntaxKit;
import com.jidesoft.swing.JideTabbedPane;
import com.system.ConsoleIO;
import com.system.TableVariables;
import de.sciss.syntaxpane.DefaultSyntaxKit;
import de.sciss.syntaxpane.actions.DocumentSearchData;
import de.sciss.syntaxpane.components.LineNumbersRuler;
import de.sciss.syntaxpane.util.Configuration;
import de.sciss.syntaxpane.util.JarServiceProvider;
import java.awt.AWTKeyStroke;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.print.PrinterException;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import net.iharder.dnd.FileDrop;

/**
 *
 * @author SIMONETO-2
 */
public final class Editor extends javax.swing.JFrame {

    //PEGANDO AS DIMENSOES DA TELA
    public static Dimension tela = Toolkit.getDefaultToolkit().getScreenSize();

    //CLASS PARA ABRIR TABS
    public static NewTab tab = null;

    //ORGANIZANDO DE ARQUIVOS
    public static FilesOpens files_opens = null;

    //IMPORTE DE CONTROLE DOS MENUS
    public static MenuSetting setting_menu = null;

    //ACOES PARA ABRIR ARQUIVOS
    private static OpenFile _abrir = null;

    //VARIAVEL PARA CONTROLE DE MENUS ENABLEDS
    public static boolean enabled_menu = false;

    //VARIAVEL RESPONSAVEL PELO HIDE/SHOW LINE NUMBER
    public static boolean line_number = true;

    //VARIAVEL RESPONSAVEL PELA GRAVACAO DO DISPLAY
    private DisplayMode dispModeOld = null;

    //VARIAVEL ESTATICA RESPONSAVEL PELA MESANGEM
    public static Menssage msg = null;

    //###########################################################################
    //# VARIAVEIS ESTATICAS RESPONSAVEIS PELO FUNCIONAMENTO DO INTERPRETADOR    #
    //###########################################################################
    //VARIAVEL RESPONSAVEL POR CRIAR A CHAMADA DA CLASS COMPILADOR
    public static Compilador util;

    //VARIAVEL TO THEREAD DE EXECUCAO DO INTERPRETADOR BSH
    public static Thread Threadcode;

    //CRIA O STATIC DO CONSOLE_IO
    public static ConsoleIO ConsoleIO;

    //CRIA O STATIC DO TABLE_VARIAVEIS
    public static TableVariables table_variaveis;

    //###########################################################################
    //###########################################################################
    //###########################################################################
    //ARQUIVO EXTERNO DE CONFIGURAÇÕES
    public static File file_setting = new File(System.getProperty("user.dir") + "\\config.properties");

    //PRIVATE VARIAVEL DE TABS
    private JideTabbedPane jTab;

    //VARIAVEL DE OLA MUNDO
    private final String algoritmo_dafault = "algoritmo \"Algoritmo - Olá mundo\"\n"
            + "\n"
            + "inicio\n"
            + "    escreval(\"Olá mundo\")\n"
            + "fimalgoritmo";

    public Editor() {
        initComponents();

        URL urlicon = Editor.class.getClassLoader().getResource("com/images/logo_icon.png");
        Image icon = Toolkit.getDefaultToolkit().getImage(urlicon);
        setIconImage(icon);

        files_opens = new FilesOpens();

        jTab = new JideTabbedPane();
        jTab.setShowCloseButton(true);
        jTab.setShowCloseButtonOnTab(true);
        jTab.setTabShape(JideTabbedPane.SHAPE_DEFAULT);
        jTab.setColorTheme(JideTabbedPane.COLOR_THEME_WINXP);

        //AÇÃO DE FECHAMENTO DAS TABS
        jTab.setCloseAction(new Action() {

            @Override
            public Object getValue(String key) {
                return null;
            }

            @Override
            public void putValue(String key, Object value) {
            }

            @Override
            public void setEnabled(boolean b) {
            }

            @Override
            public boolean isEnabled() {
                return true;
            }

            @Override
            public void addPropertyChangeListener(PropertyChangeListener listener) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void removePropertyChangeListener(PropertyChangeListener listener) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                Component b = (Component) e.getSource();
                int id = new Integer(b.getName());
                int index = files_opens.getPositionItem(id);
                tab.unistallTab(id);
                jTab.remove(index);
            }
        });

        /*jTab.setTabHistoryEnabled(true);
         jTab.setCloseButtonStrategy(CloseButtonStrategy.ALL_TABS);
         jTab.setTabOverviewEnabled(false);
         jTab.setPaintSelectedTabBold(true);
         jTab.setTabReorderByDraggingEnabled(false);

         jTab.setTabStyle(JYTabbedPane.TabStyle.SELECTED_TAB_ONLY);

         jTab.getActionMap().put("closeTab", new Tab.CloseTabAction() {
         @Override
         public void actionPerformed(ActionEvent evt) {
         AbstractButton closeButton = (AbstractButton) evt.getSource();
         Tab _tab = (Tab) closeButton.getParent().getParent();
         int tabIndex = _tab.getTabIndex();

         String id = jTab.getComponentAt(tabIndex).getName();
         tab.unistallTab(new Integer(id));

         System.out.println("Tab fechada com sucesso. ID:" + id);
         super.actionPerformed(evt);
         }
         });
         */
        jTab.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (jTab.getTabCount() > 1) {
                    DefaultSyntaxKit kit = (DefaultSyntaxKit) tab.getEditorPane().getEditorKit();
                    if (line_number) {
                        if (!kit.isComponentInstalled(tab.getEditorPane(), LineNumbersRuler.class.getName())) {
                            kit.installComponent(tab.getEditorPane(), LineNumbersRuler.class.getName());
                        }
                    } else {
                        if (kit.isComponentInstalled(tab.getEditorPane(), LineNumbersRuler.class.getName())) {
                            kit.deinstallComponent(tab.getEditorPane(), LineNumbersRuler.class.getName());
                        }
                    }
                }
            }
        });

        painelTab.setLayout(new BorderLayout());
        painelTab.add(jTab);

        DefaultSyntaxKit.registerContentType("text/portugol", PortugolSyntaxKit.class.getCanonicalName());

        Configuration conf = DefaultSyntaxKit.getConfig(PortugolSyntaxKit.class);

        String url = "com/editor/syntax/propriedades/config";
        Properties p = JarServiceProvider.readProperties(url);
        conf.putAll(p);

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

        tab = new NewTab(jTab);
        setupTabTraversalKeys(jTab);
        //setting_menu = new MenuSetting(jMenuBar);

        //JMenuItem2 (ABRIR ARQUIVO)
        _abrir = new OpenFile(jMenuItem2);
        //jMenuItem4 , jMenuItem3 SAVE E SAVE AS
        SaveSaveAs _save = new SaveSaveAs(jMenuItem4, jMenuItem3);
        //MENU DESABILITADO
        //setting_menu.setDisabledTop(enabled_menu);

        //ABRE A PRIMEIRA TAB
        tab.addTab("Olá mundo", "", algoritmo_dafault);

        //DRAG AND DROP DE ARQUIVOS
        FileDrop dropScrollPane = new FileDrop(jTab, _drop);
        FileDrop dropEditorPane = new FileDrop(tab.getEditorPane(), _drop);

        //INICIA AS MENSAGENS
        msg = new Menssage(label_msg);
    }

    FileDrop.Listener _drop = new FileDrop.Listener() {
        @Override
        public void filesDropped(java.io.File[] files) {
            for (File file : files) {
                String extensao = getFileExtension(file);
                if (".alg".equals(extensao) || ".txt".equals(extensao)) {
                    if (!files_opens.isExistingOpen(file.getAbsoluteFile().toString())) {
                        try {
                            tab.addTab(file.getName(), file.getAbsoluteFile().toString(), _abrir.readFile(file.toString(), Charset.defaultCharset()));
                            tab.getEditorPane().setCaretPosition(0);
                            System.out.println("Arquivo " + file.getName() + " foi aberto com sucesso.");
                        } catch (IOException ex) {
                            Logger.getLogger(Editor.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        tab.setSelectedIndex(files_opens.getPositionItem(file.getAbsoluteFile().toString()));
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Tipo de arquivo não suportado.", "Alerta", JOptionPane.WARNING_MESSAGE);
                }
            }
        }
    };

    private String getFileExtension(File file) {
        String name = file.getName();
        int lastIndexOf = name.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return ""; // empty extension
        }
        return name.substring(lastIndexOf);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu_console = new javax.swing.JPopupMenu();
        jMenuItem21 = new javax.swing.JMenuItem();
        jMenuItem18 = new javax.swing.JMenuItem();
        painelTab = new javax.swing.JPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        linha_coluna_txt = new javax.swing.JLabel();
        label_msg = new javax.swing.JLabel();
        jMenuBar = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jMenu6 = new javax.swing.JMenu();
        jMenuItem15 = new javax.swing.JMenuItem();
        jSeparator7 = new javax.swing.JPopupMenu.Separator();
        jMenuItem6 = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenuItem11 = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        jMenuItem12 = new javax.swing.JMenuItem();
        jSeparator6 = new javax.swing.JPopupMenu.Separator();
        jMenuItem13 = new javax.swing.JMenuItem();
        jMenuItem14 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem16 = new javax.swing.JCheckBoxMenuItem();
        jCheckBoxMenuItem1 = new javax.swing.JCheckBoxMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem17 = new javax.swing.JMenuItem();
        jMenu7 = new javax.swing.JMenu();
        jMenuItem20 = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        jMenuItem19 = new javax.swing.JMenuItem();
        jMenu8 = new javax.swing.JMenu();

        jMenuItem21.setText("Limpar console");
        jMenuItem21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cleanConsole(evt);
            }
        });
        jPopupMenu_console.add(jMenuItem21);

        jMenuItem18.setText("jMenuItem18");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Portugol Editor (v1.0.0) @alpha");
        setForeground(java.awt.Color.white);

        javax.swing.GroupLayout painelTabLayout = new javax.swing.GroupLayout(painelTab);
        painelTab.setLayout(painelTabLayout);
        painelTabLayout.setHorizontalGroup(
            painelTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 740, Short.MAX_VALUE)
        );
        painelTabLayout.setVerticalGroup(
            painelTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 455, Short.MAX_VALUE)
        );

        jSplitPane1.setBorder(null);
        jSplitPane1.setDividerSize(0);
        jSplitPane1.setResizeWeight(0.5);

        linha_coluna_txt.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        linha_coluna_txt.setText("-");
        jSplitPane1.setLeftComponent(linha_coluna_txt);

        label_msg.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        label_msg.setText("-");
        jSplitPane1.setRightComponent(label_msg);

        jMenu1.setText("Arquivo");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setText("Novo");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NewProject(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem2.setText("Abrir");
        jMenu1.add(jMenuItem2);
        jMenu1.add(jSeparator1);

        jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem4.setText("Salvar");
        jMenu1.add(jMenuItem4);

        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem3.setText("Salvar como");
        jMenu1.add(jMenuItem3);
        jMenu1.add(jSeparator2);

        jMenu6.setText("Exportar como");

        jMenuItem15.setText("PDF");
        jMenuItem15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExportPDF(evt);
            }
        });
        jMenu6.add(jMenuItem15);

        jMenu1.add(jMenu6);
        jMenu1.add(jSeparator7);

        jMenuItem6.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem6.setText("Imprimir");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ImprirTab(evt);
            }
        });
        jMenu1.add(jMenuItem6);
        jMenu1.add(jSeparator3);

        jMenuItem5.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_MASK));
        jMenuItem5.setText("Fechar");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fechar_program(evt);
            }
        });
        jMenu1.add(jMenuItem5);

        jMenuBar.add(jMenu1);

        jMenu2.setText("Editar");

        jMenuItem7.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem7.setText("Desfazer");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                undoAction(evt);
            }
        });
        jMenu2.add(jMenuItem7);

        jMenuItem8.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Y, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem8.setText("Refazer");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                redoAction(evt);
            }
        });
        jMenu2.add(jMenuItem8);
        jMenu2.add(jSeparator4);

        jMenuItem9.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem9.setText("Recortar");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RecortarAction(evt);
            }
        });
        jMenu2.add(jMenuItem9);

        jMenuItem10.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem10.setText("Copiar");
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CopiarAction(evt);
            }
        });
        jMenu2.add(jMenuItem10);

        jMenuItem11.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem11.setText("Colar");
        jMenuItem11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ColarAction(evt);
            }
        });
        jMenu2.add(jMenuItem11);
        jMenu2.add(jSeparator5);

        jMenuItem12.setText("Aplicar identação");
        jMenuItem12.setEnabled(false);
        jMenu2.add(jMenuItem12);
        jMenu2.add(jSeparator6);

        jMenuItem13.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem13.setText("Selecionar tudo");
        jMenuItem13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SelectAll(evt);
            }
        });
        jMenu2.add(jMenuItem13);

        jMenuItem14.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem14.setText("Localizar e substituir");
        jMenuItem14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LocalizarESubstituir(evt);
            }
        });
        jMenu2.add(jMenuItem14);

        jMenuBar.add(jMenu2);

        jMenu3.setText("Exibir");

        jMenuItem16.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F12, 0));
        jMenuItem16.setSelected(true);
        jMenuItem16.setText("Numero de linhas");
        jMenuItem16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NumberLineAction(evt);
            }
        });
        jMenu3.add(jMenuItem16);

        jCheckBoxMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F11, 0));
        jCheckBoxMenuItem1.setText("Exibir em tela cheia");
        jCheckBoxMenuItem1.setEnabled(false);
        jCheckBoxMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toggleFullScreen(evt);
            }
        });
        jMenu3.add(jCheckBoxMenuItem1);

        jMenuBar.add(jMenu3);

        jMenu4.setText("Algoritmo");

        jMenuItem17.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F9, 0));
        jMenuItem17.setText("Executar");
        jMenuItem17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PlayToCode(evt);
            }
        });
        jMenu4.add(jMenuItem17);

        jMenuBar.add(jMenu4);

        jMenu7.setText("Ferramentas");

        jMenuItem20.setText("Opções");
        jMenuItem20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showOpcoes(evt);
            }
        });
        jMenu7.add(jMenuItem20);

        jMenuBar.add(jMenu7);

        jMenu5.setText("Ajuda");

        jMenuItem19.setText("Sobre");
        jMenuItem19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sobre(evt);
            }
        });
        jMenu5.add(jMenuItem19);

        jMenuBar.add(jMenu5);

        jMenu8.setText("Relatar bug");
        jMenu8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                relatarBug(evt);
            }
        });

        jMenuBar.add(Box.createHorizontalGlue());

        jMenuBar.add(jMenu8);

        setJMenuBar(jMenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(painelTab, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSplitPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(painelTab, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSplitPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void NewProject(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NewProject
        /* NEW PROJECT */
        String print_name = (tab.tabs_criadas != 0) ? "" + tab.tabs_criadas : "";
        tab.addTab("semnome" + print_name, "", algoritmo_dafault);
    }//GEN-LAST:event_NewProject

    private void fechar_program(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fechar_program
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_fechar_program

    private void ImprirTab(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ImprirTab
        // TODO add your handling code here:
        boolean done;
        try {
            done = tab.getEditorPane().print();
        } catch (PrinterException ex) {
            Logger.getLogger(Editor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_ImprirTab

    private void ExportPDF(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExportPDF
        ExportPDF e = new ExportPDF(jMenuItem15);
    }//GEN-LAST:event_ExportPDF

    private void undoAction(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_undoAction
        // TODO add your handling code here:
        de.sciss.syntaxpane.SyntaxDocument t = ((de.sciss.syntaxpane.SyntaxDocument) tab.getEditorPane().getDocument());
        if (t.canUndo()) {
            t.doUndo();
        }
        //jMenuItem7.setEnabled(t.canUndo());
    }//GEN-LAST:event_undoAction

    private void redoAction(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_redoAction
        // TODO add your handling code here:
        de.sciss.syntaxpane.SyntaxDocument t = ((de.sciss.syntaxpane.SyntaxDocument) tab.getEditorPane().getDocument());
        if (t.canRedo()) {
            t.doRedo();
        }
        //jMenuItem8.setEnabled(t.canUndo());
    }//GEN-LAST:event_redoAction

    private void RecortarAction(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RecortarAction
        // TODO add your handling code here:
        tab.getEditorPane().cut();
    }//GEN-LAST:event_RecortarAction

    private void CopiarAction(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CopiarAction
        // TODO add your handling code here:
        tab.getEditorPane().copy();
    }//GEN-LAST:event_CopiarAction

    private void ColarAction(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ColarAction
        // TODO add your handling code here:
        tab.getEditorPane().paste();
    }//GEN-LAST:event_ColarAction

    private void SelectAll(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SelectAll
        // TODO add your handling code here:
        tab.getEditorPane().selectAll();
    }//GEN-LAST:event_SelectAll

    private void LocalizarESubstituir(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LocalizarESubstituir
        // TODO add your handling code here:
        DocumentSearchData dsd = DocumentSearchData.getFromEditor(tab.getEditorPane());
        dsd.showReplaceDialog(tab.getEditorPane());
    }//GEN-LAST:event_LocalizarESubstituir

    private void cleanConsole(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cleanConsole
        // TODO add your handling code here:
        //jconsole.setText(null);
    }//GEN-LAST:event_cleanConsole


    private void NumberLineAction(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NumberLineAction
        // TODO add your handling code here:
        DefaultSyntaxKit kit = (DefaultSyntaxKit) tab.getEditorPane().getEditorKit();
        boolean selected = jMenuItem16.getModel().isSelected();

        if (selected) {
            if (!kit.isComponentInstalled(tab.getEditorPane(), LineNumbersRuler.class.getName())) {
                kit.installComponent(tab.getEditorPane(), LineNumbersRuler.class.getName());
            }
            line_number = true;
        } else {
            if (kit.isComponentInstalled(tab.getEditorPane(), LineNumbersRuler.class.getName())) {
                kit.deinstallComponent(tab.getEditorPane(), LineNumbersRuler.class.getName());
            }
            line_number = false;
        }
    }//GEN-LAST:event_NumberLineAction

    private void PlayToCode(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PlayToCode
        // TODO add your handling code here:
        ExecCompilador compilar = new ExecCompilador();
    }//GEN-LAST:event_PlayToCode

    private void sobre(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sobre
        // TODO add your handling code here:
        Sobre about = new Sobre(this, true);
        about.setLocation((tela.width - about.getSize().width) / 2, (tela.height - about.getSize().height) / 2);
        about.setVisible(true);
    }//GEN-LAST:event_sobre


    private void showOpcoes(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showOpcoes
        // TODO add your handling code here:
        Opcoes setting = new Opcoes(this, true);
        setting.setLocation((tela.width - setting.getSize().width) / 2, (tela.height - setting.getSize().height) / 2);
        setting.setVisible(true);
    }//GEN-LAST:event_showOpcoes

    private void toggleFullScreen(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toggleFullScreen
        // TODO add your handling code here:
        boolean selected = jCheckBoxMenuItem1.getModel().isSelected();

        if (selected) {
            setFullscreen(true);
        } else {
            setFullscreen(false);
        }
    }//GEN-LAST:event_toggleFullScreen

    private void relatarBug(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_relatarBug
        // TODO add your handling code here:
        RelatarBug bug = new RelatarBug(this, true);
        bug.setLocation((tela.width - bug.getSize().width) / 2, (tela.height - bug.getSize().height) / 2);
        bug.setVisible(true);
    }//GEN-LAST:event_relatarBug

    /**
     * Method allows changing whether this window is displayed in fullscreen or
     * windowed mode.
     *
     * @param fullscreen true = change to fullscreen, false = change to windowed
     * http://stackoverflow.com/questions/4462454/java-full-screen-program-swing-tab-alt-f4
     */
    public void setFullscreen(boolean fullscreen) {
        //get a reference to the device.
        GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        DisplayMode dispMode = device.getDisplayMode();
        //save the old display mode before changing it.
        dispModeOld = device.getDisplayMode();

        if (!fullscreen) {
            //change to windowed mode.
            //set the display mode back to the what it was when
            //the program was launched.
            device.setDisplayMode(dispModeOld);
            //hide the frame so we can change it.
            setVisible(false);
            //remove the frame from being displayable.
            dispose();
            //put the borders back on the frame.
            setUndecorated(false);
            //needed to unset this window as the fullscreen window.
            device.setFullScreenWindow(null);
            //recenter window
            setLocationRelativeTo(null);
            setResizable(true);

            //reset the display mode to what it was before
            //we changed it.
            setVisible(true);

        } else { //change to fullscreen.
            //hide everything
            setVisible(false);
            //remove the frame from being displayable.
            dispose();
            //remove borders around the frame
            setUndecorated(true);
            //make the window fullscreen.
            device.setFullScreenWindow(this);
            //attempt to change the screen resolution.
            device.setDisplayMode(dispMode);
            setResizable(false);
            setAlwaysOnTop(false);
            //show the frame
            setVisible(true);
        }
        //make sure that the screen is refreshed.
        repaint();
    }


    /*
     *   CHANGE TABS COM O CTRL+TAB
     */
    private static void setupTabTraversalKeys(JTabbedPane tabbedPane) {
        KeyStroke ctrlTab = KeyStroke.getKeyStroke("ctrl TAB");
        KeyStroke ctrlShiftTab = KeyStroke.getKeyStroke("ctrl shift TAB");

        // Remove ctrl-tab from normal focus traversal
        Set<AWTKeyStroke> forwardKeys = new HashSet<>(tabbedPane.getFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS));
        forwardKeys.remove(ctrlTab);
        tabbedPane.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, forwardKeys);

        // Remove ctrl-shift-tab from normal focus traversal
        Set<AWTKeyStroke> backwardKeys = new HashSet<>(tabbedPane.getFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS));
        backwardKeys.remove(ctrlShiftTab);
        tabbedPane.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, backwardKeys);

        // Add keys to the tab's input map
        InputMap inputMap = tabbedPane.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        inputMap.put(ctrlTab, "navigateNext");
        inputMap.put(ctrlShiftTab, "navigatePrevious");
    }

    public static void main(String arg[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {

                DefaultSyntaxKit.initKit();

                //try {
                //WebLookAndFeel.install();
                //if (System.getProperty("os.name").toLowerCase().contains("win")) {
                //UIManager.put("Synthetica.window.decoration", Boolean.FALSE);
                //} else {
                //    UIManager.put("Synthetica.window.decoration", Boolean.FALSE);
                //}
                //UIManager.put("Synthetica.focus.textComponents.enabled", Boolean.FALSE);
                    /*try {
                 UIManager.setLookAndFeel(new SyntheticaWhiteVisionLookAndFeel());
                 } catch (ParseException | UnsupportedLookAndFeelException ex) {
                 Logger.getLogger(Editor.class.getName()).log(Level.SEVERE, null, ex);
                 }*/
                /*    UIManager.setLookAndFeel("org.pushingpixels.substance.api.skin.SubstanceNebulaLookAndFeel");
                 } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                 Logger.getLogger(Editor.class.getName()).log(Level.SEVERE, null, ex);
                 }*/
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    //LookAndFeelFactory.installJideExtension(LookAndFeelFactory.XERTO_STYLE);
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                    Logger.getLogger(Editor.class.getName()).log(Level.SEVERE, null, ex);
                }

                //UIManager.put("Synthetica.window.opaque", false);
                //UIManager.put("Synthetica.window.shape", "");
                Editor Editor = new Editor();
                Editor.setLocation((tela.width - Editor.getSize().width) / 2, (tela.height - Editor.getSize().height) / 2);
                Editor.setExtendedState(Editor.MAXIMIZED_BOTH);
                Editor.setVisible(true);

            }
        });

    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenu jMenu7;
    private javax.swing.JMenu jMenu8;
    private javax.swing.JMenuBar jMenuBar;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem13;
    private javax.swing.JMenuItem jMenuItem14;
    private javax.swing.JMenuItem jMenuItem15;
    private javax.swing.JCheckBoxMenuItem jMenuItem16;
    private javax.swing.JMenuItem jMenuItem17;
    private javax.swing.JMenuItem jMenuItem18;
    private javax.swing.JMenuItem jMenuItem19;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem20;
    private javax.swing.JMenuItem jMenuItem21;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JPopupMenu jPopupMenu_console;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JPopupMenu.Separator jSeparator6;
    private javax.swing.JPopupMenu.Separator jSeparator7;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JLabel label_msg;
    public static javax.swing.JLabel linha_coluna_txt;
    private javax.swing.JPanel painelTab;
    // End of variables declaration//GEN-END:variables
}
