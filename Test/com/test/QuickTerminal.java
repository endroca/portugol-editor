/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.test;

/**
 *
 * @author Andrew
 */
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.text.BadLocationException;
import org.w3c.dom.ls.LSException;

public class QuickTerminal {

    public static void main(String[] args) {
        new QuickTerminal();
    }

    public QuickTerminal() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                }

                JFrame frame = new JFrame("Testing");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(new BorderLayout());
                frame.add(new ConsolePane());
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }

        });
    }

    public interface CommandListener {

        public void commandOutput(String text);

        public void commandCompleted(String cmd, int result);

        public void commandFailed(Exception exp);

    }

    public class ConsolePane extends JPanel implements CommandListener {

        private JTextArea textArea;
        private int userInputStart = 0;
        private Command cmd;

        public ConsolePane() {

            cmd = new Command(this);

            setLayout(new BorderLayout());
            textArea = new JTextArea(20, 30);
            add(new JScrollPane(textArea));

            textArea.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        int range = textArea.getCaretPosition() - userInputStart;
                        try {
                            String text = textArea.getText(userInputStart, range).trim();
                            System.out.println("[" + text + "]");
                            userInputStart += range;
                            if (!cmd.isRunning()) {
                                cmd.execute(text);
                            } else {
                            }
                        } catch (BadLocationException ex) {
                            Logger.getLogger(QuickTerminal.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
//                        if (!cmd.isRunning()) {
//                            cmd.send(...);
//                        }
                    }
                }

            });
        }

        @Override
        public void commandOutput(String text) {
            SwingUtilities.invokeLater(new AppendTask(textArea, text));
        }

        @Override
        public void commandFailed(Exception exp) {
            SwingUtilities.invokeLater(new AppendTask(textArea, "Command failed - " + exp.getMessage()));
        }

        @Override
        public void commandCompleted(String cmd, int result) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    int pos = textArea.getCaretPosition();

                    System.out.println("pos = " + pos + "; length = " + textArea.getText().length());

                    textArea.setCaretPosition(textArea.getText().length());
                    userInputStart = pos;
                }
            });
        }

    }

    public class AppendTask implements Runnable {

        private JTextArea textArea;
        private String text;

        public AppendTask(JTextArea textArea, String text) {
            this.textArea = textArea;
            this.text = text;
        }

        @Override
        public void run() {
            textArea.append(text);
        }

    }

    public class Command {

        private CommandListener listener;

        private ProcessRunner runner;

        public Command(CommandListener listener) {
            this.listener = listener;
        }

        public boolean isRunning() {

            return runner != null && runner.isAlive();

        }

        public void execute(String cmd) {

            if (!cmd.trim().isEmpty()) {


                List<String> values = new ArrayList<>(25);
                if (cmd.contains("\"")) {

                    while (cmd.contains("\"")) {

                        String start = cmd.substring(0, cmd.indexOf("\""));
                        cmd = cmd.substring(start.length());
                        String quote = cmd.substring(cmd.indexOf("\"") + 1);
                        cmd = cmd.substring(cmd.indexOf("\"") + 1);
                        quote = quote.substring(0, cmd.indexOf("\""));
                        cmd = cmd.substring(cmd.indexOf("\"") + 1);

                        if (!start.trim().isEmpty()) {
                            String parts[] = start.trim().split(" ");
                            values.addAll(Arrays.asList(parts));
                        }
                        values.add(quote.trim());

                    }

                    if (!cmd.trim().isEmpty()) {
                        String parts[] = cmd.trim().split(" ");
                        values.addAll(Arrays.asList(parts));
                    }

                    for (String value : values) {
                        System.out.println("[" + value + "]");
                    }

                } else {

                    if (!cmd.trim().isEmpty()) {
                        String parts[] = cmd.trim().split(" ");
                        values.addAll(Arrays.asList(parts));
                    }

                }

                runner = new ProcessRunner(listener, values);

            }

        }

        public void send(String cmd) {
            // Send user input to the running process...
        }

    }

    public class ProcessRunner extends Thread {

        private List<String> cmds;
        private CommandListener listener;

        public ProcessRunner(CommandListener listener, List<String> cmds) {
            this.cmds = cmds;
            this.listener = listener;
            start();
        }

        @Override
        public void run() {
            try {
                System.out.println("cmds = " + cmds);
                ProcessBuilder pb = new ProcessBuilder(cmds);
                pb.redirectErrorStream();
                Process p = pb.start();
                StreamReader reader = new StreamReader(listener, p.getInputStream());
                // Need a stream writer...

                int result = p.waitFor();

                // Terminate the stream writer
                reader.join();

                listener.commandCompleted(null, result);
            } catch (Exception exp) {
                exp.printStackTrace();
            }
        }

    }

    public class StreamReader extends Thread {

        private InputStream is;
        private CommandListener listener;

        public StreamReader(CommandListener listener, InputStream is) {
            this.is = is;
            this.listener = listener;
            start();
        }

        @Override
        public void run() {
            try {
                int value = -1;
                while ((value = is.read()) != -1) {
                    listener.commandOutput(Character.toString((char) value));
                }
            } catch (IOException exp) {
                exp.printStackTrace();
            }
        }

    }

}