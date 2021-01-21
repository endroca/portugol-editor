/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compilador.portugol;

import com.forms.Console;
import com.forms.Editor;
import static com.forms.Editor.tela;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

/**
 *
 * @author SIMONETO-2
 */
public class Compilador {

    private final boolean printCode = false;
    private final boolean execCode = true;


    public ArrayList<String[]> Variaveis = new ArrayList<>();
    /*
     *   MENU:
     *   0-VARIAVEL NOME
     *   1-VARIAVEL TIPO
     */
    public ArrayList<String[]> VariaveisVetor = new ArrayList<>();
    /*
     *   MENU:
     *   0-VARIAVEL NOME
     *   1-VARIAVEL TIPO
     *   2-INDICE A
     *   3-INDICE B
     */

    private String code_name;

    private String[] code = {"", "", "", ""}; // 0 - CABECALHO, 1 - VARIAVEIS , 2 - PROGRAMA , 3 - INCLUDES
    public String[] code_exec = {"", "", ""};

    private int erros = 0;

    private final String[] operacoes_portugol = {"+", "-", "*", "/", "^", "mod", "div"};
    private final String[] operacoes_java = {"+", "-", "*", "/", "^", "%", "/"};

    private final String[] valores_logicos_portugol = {"verdadeiro", "falso"};
    private final String[] valores_logicos_java = {"true", "false"};

    private final String[] tipos_variaveis_portugol = {"string", "caracter", "inteiro", "real", "logico"};
    private final String[] tipos_variaveis_java = {"String", "char", "int", "float", "boolean"};

    private final String[] condicionais_portugol = {"=", "<>", "e", "ou"};
    private final String[] condicionais_java = {"==", "!=", "&&", "||"};

    //para o comando de saida   
    private boolean scanner = false;

    public void setLeia(boolean n) {
        if (this.scanner == false) {
            this.scanner = true;
            setBiblioteca("import java.util.Scanner;");
            setCodeVariables("\t\tScanner scanIn = new Scanner(System.in);");
            //setCodeVariablesExec("\t\tScanner scanIn = new Scanner(System.in);");
        }
    }

    public void resetarSaidas() {
        code_name = "";

        code_exec[0] = "";
        code_exec[1] = "";
        code_exec[2] = "";

        code[0] = "";
        code[1] = "";
        code[2] = "";
        code[3] = "";

        scanner = false;
        erros = 0;

        Variaveis.clear();
        VariaveisVetor.clear();
    }

    /*
     * AUXILIAR NAS OPERACOES
     */
    private boolean operador_tmp = false;
    private boolean operador_soma = false;
    private boolean numero_float_tmp = false;
    private boolean numero_int_tmp = false;
    private boolean string_tmp = false;
    private boolean char_tmp = false;
    private boolean boolean_tmp = false;
    List<String> TipoID_tmp = new ArrayList<>();

    public void setOperadorTMP(boolean p) {
        operador_tmp = p;
    }

    public void setOperadorSomaTMP(boolean p) {
        operador_soma = p;
    }

    public void setNumeroIntTMP(boolean p) {
        numero_int_tmp = p;
    }

    public void setNumerofloatTMP(boolean p) {
        numero_float_tmp = p;
    }

    public void setStringTMP(boolean p) {
        string_tmp = p;
    }

    public void setCharTMP(boolean p) {
        char_tmp = p;
    }

    public void setBooleanTMP(boolean p) {
        boolean_tmp = p;
    }

    public void setTipoIdTMP(String e) {
        TipoID_tmp.add(e);
    }

    public void resetTMPSOperadores() {
        operador_tmp = false;
        operador_soma = false;
        numero_float_tmp = false;
        numero_int_tmp = false;
        string_tmp = false;
        boolean_tmp = false;
        char_tmp = false;
        TipoID_tmp.clear();

    }

    /*
     * AXULIAR NAS CONDICIONAIS
     */
    //para as sub-condicionais
    private boolean condicionais_numericas = false;
    private boolean condicionais_comparativas = false;
    private boolean condicinal_e = false;
    private boolean condicinal_ou = false;
    List<String> TiposOperacoesTMP = new ArrayList<>();

    //para as condicionais 
    private boolean condicionais_numericas_permanentes = false;
    private boolean condicionais_comparativas_permanentes = false;
    List<String> TiposOperacoesTMP_permanentes = new ArrayList<>();

    public void setCondicinalNumericaTMP(boolean p) {
        condicionais_numericas = p;
        condicionais_numericas_permanentes = p; //para as condicionais permanentes
    }

    public void setCondicionalComparativaTMP(boolean p) {
        condicionais_comparativas = p;
        condicionais_comparativas_permanentes = p; //para as condicionais permanentes
    }

    public void setCondicinalETMP(boolean p) {
        condicinal_e = p;
    }

    public void setCondicionalOuTMP(boolean p) {
        condicinal_ou = p;
    }

    public void setTiposOperacaoeTMP(String t) {
        TiposOperacoesTMP.add(t);
        TiposOperacoesTMP_permanentes.add(t); //para as condicionais permanentes
    }

    public void resetTMPSSubCondicionais() {
        condicionais_numericas = false;
        condicionais_comparativas = false;
        condicinal_e = false;
        condicinal_ou = false;
        TiposOperacoesTMP.clear();
    }

    public void resetTMPSCondicionais() {
        condicionais_numericas_permanentes = false;
        condicionais_comparativas_permanentes = false;
        TiposOperacoesTMP_permanentes.clear();
    }


    /*
     * PARA LISTAGEM
     */
    public void setCodeName(String name) {
        this.code_name = name;
    }

    public void setInitClass() {
        this.code[0] = "\n\tpublic " + this.code_name + "(){";
    }

    public void setEndClass() {
        setCode("\t}\n\n\tpublic static void main(String[] args){\n\t\t" + this.code_name + " programa = new " + this.code_name + "();\n\t}\n}");
    }

    public void setCode(String code) {
        this.code[2] += code + "\n";
    }

    public void setCodeVariables(String code) {
        this.code[1] += code + "\n";
    }

    public void setBiblioteca(String biblioteca) {
        this.code[3] += biblioteca + "\n";
    }

    public void printCode() {
        if (printCode) {
            if ("".equals(this.code[3])) {
                System.out.print(code[0] + '\n' + code[1] + '\n' + code[2]);
            } else {
                System.out.print(code[3] + '\n' + code[0] + '\n' + code[1] + '\n' + code[2]);
            }
        }
    }
    /*
     * PARA EXECUTAR
     */

    public void setCodeExec(String code) {
        this.code_exec[2] += code;
    }

    public void setCodeExec(String code, int line) {
        String _line = "\n";
        int cline = line + 1;
        
        
        int total_line = (code_exec[0]+code_exec[1]+code_exec[2]).split("\n").length;
        
        for (int i = 0; i < (line-(total_line+1)); i++) {
            _line += "\n";
        }
        this.code_exec[2] += _line + code;
    }

    public void setCodeVariablesExec(String code) {
        this.code_exec[1] += code + "\n";
    }

    public void setCodeLibraryExec(String code) {
        this.code_exec[0] += code + "\n";
    }
    /*
    public void execCode() {
        if (execCode) {

            Threadcode = new Thread(new Runnable() {

                @Override
                public void run() {
                    long startTime = 0;
                    long endTime = 0;
                    boolean error = false;
                    Interpreter i = new Interpreter();
                    //System.out.println("#"+code_exec[0] + code_exec[1] + code_exec[2]);
                    try {
                        startTime = System.currentTimeMillis();
                        i.set("ConsoleIO", ConsoleIO);
                        i.set("table_variaveis", table_variaveis);
                        i.eval(code_exec[0] + code_exec[1] + code_exec[2]);
                    } catch (EvalError ex) {
                        JOptionPane.showMessageDialog(null, "Erro de syntax:Há algum erro critico de syntax no seu código. Linha:" + (ex.getErrorLineNumber() - 1), "Erro de syntax", JOptionPane.WARNING_MESSAGE);
                        error = true;
                    } finally {
                        endTime = System.currentTimeMillis();
                        ConsoleIO.println("");
                        ConsoleIO.println("");
                        if (!error) {
                            ConsoleIO.print("Programa finalizado. Tempo de duração: " + (endTime - startTime) / 1000 + " segundos", AttrColor("codigo_finalizado"));
                        } else {
                            ConsoleIO.print("Programa finalizado. Tempo de duração: " + (endTime - startTime) / 1000 + " segundos", AttrColor("codigo_finalizado_error"));
                        }
                        ConsoleIO.finalizacao();

                    }
                }

            });
            Threadcode.start();
        }
    }
    */

    public void setErro() {
        erros++;
    }

    public boolean isErros() {
        boolean retorno = false;
        if (erros > 0) {
            retorno = true;
        }
        return retorno;
    }

    public void codeComplete() {
        if (isErros() == false) {
            Console console = new Console();
            console.setLocation((tela.width - console.getSize().width) / 2, (tela.height - console.getSize().height) / 2);
            console.setModal(true);
            console.setVisible(true);
            /*
            //LISTAGEM DE VARIAVEIS
            for (String[] Variavei : Variaveis) {
                table_variaveis.add(Variavei[0], getTipoVariavelJavaToPt(Variavei[1]), null);
            }
            
            //LISTAGEM DE VARIAVEIS DO TIPO VETOR
            for (String[] Variavei : VariaveisVetor) {
                for (int i = new Integer(Variavei[2]); i <= new Integer(Variavei[3]); i++) {
                    table_variaveis.add(Variavei[0] + "[" + i + "]", getTipoVariavelJavaToPt(Variavei[1]), null);
                }
            }
            */
            printCode();
            //execCode();
        }
    }

    /*
     *   PARA AS VARIAVEIS
     */
    public String getTipoVariavel(String tipo) { //retornar o operador equivalente java 
        int i = 0;
        while (i < tipos_variaveis_portugol.length && !tipos_variaveis_portugol[i].equals(tipo)) {
            i++;
        }
        return tipos_variaveis_java[i];
    }

    public String getTipoVariavelJavaToPt(String tipo) { //retornar o operador equivalente java 
        int i = 0;
        while (i < tipos_variaveis_java.length && !tipos_variaveis_java[i].equals(tipo)) {
            i++;
        }
        return tipos_variaveis_portugol[i];
    }

    public String getOperador(String tipo) { // retornar o operador equivalente java
        int i = 0;
        while (i < operacoes_portugol.length && !operacoes_portugol[i].equals(tipo)) {
            i++;
        }
        return operacoes_java[i];
    }

    public String getValorLogico(String value) {
        int i = 0;
        while (i < valores_logicos_portugol.length && !valores_logicos_portugol[i].equals(value)) {
            i++;
        }
        return valores_logicos_java[i];
    }
    /*
     public String getValorRealfloat(String value) {
     return value.replace(",", ".");
     }
     */

    public String getValorFloatreal(String value) {
        return value.replace(".", ",");
    }

    public void addVariavel(String nome, String tipo) { //add a variavel na array para controle e seta o codigo
        String[] variaveis = nome.split(",");
        for (String var : variaveis) {
            String[] tmp = {var, getTipoVariavel(tipo)};
            Variaveis.add(tmp);
        }
    }

    public void addVariavelVetor(String nome, String tipo, String indice1, String indice2) {
        String[] variaveis = nome.split(",");
        for (String var : variaveis) {
            String[] tmp = {var, getTipoVariavel(tipo), indice1, indice2};
            VariaveisVetor.add(tmp);
            setCodeVariables("\t" + getTipoVariavel(tipo) + "[] " + var + " = new " + getTipoVariavel(tipo) + "[" + (new Integer(indice2) - new Integer(indice1) + 1) + "];");
            setCodeVariablesExec("\t" + getTipoVariavel(tipo) + "[] " + var + " = new " + getTipoVariavel(tipo) + "[" + (new Integer(indice2) - new Integer(indice1) + 1) + "];");
        }

    }

    public String[] getVariavel(String variavel) {
        String[] info = new String[2];

        for (String[] var : Variaveis) {
            if (var[0].equals(variavel)) {
                String retorno[] = {var[0], var[1]};
                info = retorno;
                break;
            }
        }
        return info;
    }

    public String[] getVariavelVetor(String variavel) {
        String[] info = new String[4];

        for (String[] var : VariaveisVetor) {
            if (var[0].equals(variavel)) {
                String retorno[] = {var[0], var[1], var[2], var[3]};
                info = retorno;
                break;
            }
        }
        return info;
    }

    public boolean checkVariavelExiste(String variavel) {
        boolean tmp = false;
        String[] var = getVariavel(variavel);
        if (var[1] == null) {
            tmp = false;
        } else {
            tmp = true;
        }
        return tmp;
    }

    public String setValorVariavelLogica(String variavel, String value) {
        String[] var = getVariavel(variavel);
        String retorno = null;

        if (var[1] == null) {
            retorno = "error1"; //Variavel nao declarada
        } else if ("boolean".equals(var[1])) {
            retorno = "\t\t" + variavel + " = " + getValorLogico(value) + ";";
        } else {
            retorno = "error2"; //Variavel nao suporta String
        }
        return retorno;
    }

    public String getTipoOperacao() {
        String tipo = null;
        String[] tmp_tipo = {null, null};

        int ids_string = 0;
        int ids_char = 0;
        int ids_int = 0;
        int ids_float = 0;
        int ids_boolean = 0;

        //caso tenha IDs
        if (!TipoID_tmp.isEmpty()) {
            for (String tmp : TipoID_tmp) {
                if ("String".equals(tmp)) {
                    ids_string++;
                } else if ("char".equals(tmp)) {
                    ids_char++;
                } else if ("int".equals(tmp)) {
                    ids_int++;
                } else if ("float".equals(tmp)) {
                    ids_float++;
                } else if ("boolean".equals(tmp)) {
                    ids_boolean++;
                }
            }
        }
        /*
         System.out.println("====================");
         System.out.println("string="+ids_string);
         System.out.println("char="+ids_char);
         System.out.println("int="+ids_int);
         System.out.println("float="+ids_float);
         System.out.println("====================");  
         */
        /*
         System.out.println("====================");
         System.out.println("string="+string_tmp);
         System.out.println("char="+char_tmp);
         System.out.println("int="+numero_int_tmp);
         System.out.println("float="+numero_float_tmp);
         System.out.println("====================");         
         */

        //caso int
        if ((ids_int > 0 || numero_int_tmp == true) && string_tmp == false && ids_string == 0 && ids_float == 0 && numero_float_tmp == false && boolean_tmp == false && ids_boolean == 0 && ids_char == 0 && char_tmp == false) {
            tipo = "int";

            //caso float
        } else if ((ids_float > 0 || ids_int > 0 || numero_float_tmp == true || numero_int_tmp == true) && string_tmp == false && ids_string == 0 && boolean_tmp == false && ids_boolean == 0 && ids_char == 0 && char_tmp == false) {
            tipo = "float";

            //caso String
        } else if ((ids_string > 0 || string_tmp == true) && ids_int == 0 && ids_float == 0 && operador_tmp == false && numero_float_tmp == false && numero_int_tmp == false && boolean_tmp == false && ids_boolean == 0 && ids_char == 0 && char_tmp == false) {
            tipo = "String";

            //caso char
        } else if ((ids_char > 0 || char_tmp == true) && operador_soma == false && ids_string == 0 && string_tmp == false && ids_int == 0 && ids_float == 0 && operador_tmp == false && numero_float_tmp == false && numero_int_tmp == false && ids_boolean == 0 && boolean_tmp == false) {
            tipo = "char";
        } //caso logico
        else if ((ids_boolean > 0 || boolean_tmp == true) && operador_soma == false && ids_string == 0 && string_tmp == false && ids_int == 0 && ids_float == 0 && operador_tmp == false && numero_float_tmp == false && numero_int_tmp == false && ids_char == 0 && char_tmp == false) {
            tipo = "boolean";
        }

        resetTMPSOperadores();

        return tipo;
    }

    public String setVariavelOperador(String variavel, String value) {
        String retorno = null;
        String[] var = getVariavel(variavel);

        if (var[1] == null) {
            retorno = "error1"; //Variavel nao declarada
        } else {

            String tmp_tipo_operacao = getTipoOperacao();

            if (var[1].equals(tmp_tipo_operacao)) {
                retorno = "\t\t" + variavel + " = (" + var[1] + ") (" + value + "); /*Variavel do tipo: " + var[1] + ", operacao do tipo: " + tmp_tipo_operacao + "*/";
            } else if (("int".equals(tmp_tipo_operacao) || "float".equals(tmp_tipo_operacao)) && ("int".equals(var[1]) || "float".equals(var[1]))) {
                retorno = "\t\t" + variavel + " = (" + var[1] + ") (" + value + "); /*Variavel do tipo: " + var[1] + ", operacao do tipo: " + tmp_tipo_operacao + "*/";
            } else {
                retorno = "error2"; //Variavel nao suporta
            }
        }
        return retorno;
    }

    public String setVariavelVetor(String variavel, String value, String indice) {
        String retorno = null;
        String[] var = getVariavelVetor(variavel);

        if (var[1] == null) {
            retorno = "error1"; //Variavel nao declarada
        } else {

            String tmp_tipo_operacao = getTipoOperacao();

            //int indice_a = new Integer(var[2]);
            //int indice_xo = new Integer(indice);
            if (var[1].equals(tmp_tipo_operacao)) {
                retorno = "\t\t" + variavel + "[" + indice + " - (" + var[2] + ")] = (" + var[1] + ") (" + value + "); /*Variavel do tipo: " + var[1] + ", operacao do tipo: " + tmp_tipo_operacao + "*/";
                retorno += "t\ttable_variaveis.update(\"" + variavel + "[\"+" + indice + "+\"]\"," + variavel + "[" + indice + " - (" + var[2] + ")]);";
            } else if (("int".equals(tmp_tipo_operacao) || "float".equals(tmp_tipo_operacao)) && ("int".equals(var[1]) || "float".equals(var[1]))) {
                retorno = "\t\t" + variavel + "[" + indice + " - (" + var[2] + ")] = (" + var[1] + ") (" + value + "); /*Variavel do tipo: " + var[1] + ", operacao do tipo: " + tmp_tipo_operacao + "*/";
                retorno += "t\ttable_variaveis.update(\"" + variavel + "[\"+" + indice + "+\"]\"," + variavel + "[" + indice + " - (" + var[2] + ")]);";
            } else {
                retorno = "error2"; //Variavel nao suporta
            }
        }
        return retorno;
    }

    /*
     * PARA VALORES LOGICOS
     */
    public String getTipoCondiconal(String tipo) { //retornar o operador equivalente java 
        int i = 0;
        while (!condicionais_portugol[i].equals(/*tipo.toLowerCase()*/tipo)) {
            i++;
        }
        return condicionais_java[i];
    }

    /*
     * CONDICINAIS
     */
    public String setSubCondicional(String condicional) {
        String retorno = null;

        int op_tipo_int = 0;
        int op_tipo_char = 0;
        int op_tipo_string = 0;
        int op_tipo_float = 0;
        int op_tipo_logico = 0;

        for (String tmp : TiposOperacoesTMP) {
            if ("String".equals(tmp)) {
                op_tipo_string++;
            } else if ("char".equals(tmp)) {
                op_tipo_char++;
            } else if ("int".equals(tmp)) {
                op_tipo_int++;
            } else if ("float".equals(tmp)) {
                op_tipo_float++;
            } else if ("boolean".equals(tmp)) {
                op_tipo_logico++;
            } else {
                retorno = "error1";
            }
        }
        /*
        System.out.println("====================");
        System.out.println("op_tipo_int=" + op_tipo_int);
        System.out.println("op_tipo_char=" + op_tipo_char);
        System.out.println("op_tipo_string=" + op_tipo_string);
        System.out.println("op_tipo_float=" + op_tipo_float);
        System.out.println("op_tipo_logico=" + op_tipo_logico);
        System.out.println("====================");
        */
        if (!"error1".equals(retorno)) {
            //caso int e float (permitido qualquer tipo de comparacao)
            if ((op_tipo_int > 0 | op_tipo_float > 0) && op_tipo_string == 0 && op_tipo_logico == 0 && op_tipo_char == 0) {
                //caso String (permitido apenas compacao de == e !=)
            } else if ((op_tipo_string > 0) && op_tipo_int == 0 && op_tipo_float == 0 && op_tipo_logico == 0 && op_tipo_char == 0 /*COMPARACOES*/ && condicionais_numericas == false) {
                retorno = "String";
                //caso Logico
            } else if ((op_tipo_logico > 0) && op_tipo_int == 0 && op_tipo_float == 0 && op_tipo_string == 0 && op_tipo_char == 0 /*COMPARACOES*/ && condicionais_numericas == false) {
                //caso char
            } else if ((op_tipo_char > 0) && op_tipo_int == 0 && op_tipo_float == 0 && op_tipo_string == 0 && op_tipo_logico == 0 /*COMPARACOES*/ && condicionais_numericas == false) {
                //caso erro
            } else {
                retorno = condicional;
            }
        }

        resetTMPSSubCondicionais();

        return retorno;
    }

    public String setCondicional(String condicional) {
        String retorno = null;

        int op_tipo_int = 0;
        int op_tipo_char = 0;
        int op_tipo_string = 0;
        int op_tipo_float = 0;
        int op_tipo_logico = 0;

        for (String tmp : TiposOperacoesTMP) {
            if ("String".equals(tmp)) {
                op_tipo_string++;
            } else if ("char".equals(tmp)) {
                op_tipo_char++;
            } else if ("int".equals(tmp)) {
                op_tipo_int++;
            } else if ("float".equals(tmp)) {
                op_tipo_float++;
            } else if ("boolean".equals(tmp)) {
                op_tipo_logico++;
            } else {
                retorno = "error1";
            }
        }

        if (!"error1".equals(retorno)) {
            // caso exista int ou float sem comparacao
            if ((op_tipo_int > 0 || op_tipo_float > 0) && condicionais_numericas_permanentes == false && condicionais_comparativas_permanentes == false) {
                retorno = condicional;
            } else if (op_tipo_string > 0 && condicionais_comparativas_permanentes == false) {
                retorno = condicional;
            } else if (op_tipo_char > 0 && condicionais_comparativas_permanentes == false) {
                retorno = condicional;
            }
        }
        return retorno;
    }

    public SimpleAttributeSet AttrColor(String tipe) {
        SimpleAttributeSet retorno = new SimpleAttributeSet();

        switch (tipe) {
            case "error":
                retorno.addAttribute(StyleConstants.CharacterConstants.Bold, Boolean.FALSE);
                retorno.addAttribute(StyleConstants.CharacterConstants.Foreground, Color.RED);
                break;

            case "codigo_finalizado":
                retorno.addAttribute(StyleConstants.CharacterConstants.Bold, Boolean.TRUE);
                retorno.addAttribute(StyleConstants.CharacterConstants.Foreground, Color.decode("#558737"));
                break;

            case "codigo_finalizado_error":
                retorno.addAttribute(StyleConstants.CharacterConstants.Bold, Boolean.TRUE);
                retorno.addAttribute(StyleConstants.CharacterConstants.Foreground, Color.decode("#960E0E"));
                break;
        }

        return retorno;
    }

    public String Erros(String error, String complemento) {
        String retorno = null;
        switch (error) {
            ////////////////////////////
            //PARA AS VARIAVEIS NORMAIS
            ////////////////////////////
            case "VARIAVEL_ERRO1":
                retorno = "Erro de syntax: Variável %s não foi declarada.".replace("%s", complemento);
                break;
            case "VARIAVEL_ERRO2":
                retorno = "Erro de syntax: Variável %s não suporta esse tipo de valor.".replace("%s", complemento);
                break;
            //////////////////
            //PARA OS VETORES
            //////////////////
            case "VARIAVEL_VETOR_ERRO1":
                retorno = "Erro de syntax: Vetor %s não foi declarado.".replace("%s", complemento);
                break;
            case "VARIAVEL_VETOR_ERRO2":
                retorno = "Erro de syntax: Vetor %s não suporta esse tipo de valor.".replace("%s", complemento);
                break;
            case "VARIAVEL_VETOR_ERRO3":
                retorno = "Erro de syntax: Índice do vetor %s está incorreto.".replace("%s", complemento);
                break;
            case "VARIAVEL_VETOR_ERRO4":
                retorno = "Erro de syntax: Índice do vetor %s deve ser um valor inteiro.".replace("%s", complemento);
                break;
            ///////////////////////
            //PARA AS CONDICIONAIS
            ///////////////////////
            case "CONDICIONAL_ERRO1":
                retorno = "Erro de syntax: Há uma operacão indevida na condicional.";
                break;
            case "CONDICIONAL_ERRO2":
                retorno = "Erro de syntax: Comparação indevida em: %s.".replace("%s", complemento);
                break;

            /////////////////////
            //ERRO DE OPERACAO///
            case "OPERACAO_ERRO1":
                retorno = "Erro de syntax: Operação indevida.";
                break;

            /////////////////////
            //ERRO FOR///////////
            /////////////////////
            case "INDICE_FOR":
                retorno = "Erro de syntax: Índice do para não podem ser invertidos.";
                break;
            case "TIPO_VARIAVEL_FOR":
                retorno = "Erro de syntax: Tipo de operação não permitida para o para.";
                break;
                
                
            ///////
            //ALL//
            ///////
            case "SOMENTE_INT":
                retorno = "Erro de syntax: Não é permitido outro tipo de operação: Somente tipo int.";
                break;
                
            case "SOMENTE_NUMEROS":
                retorno = "Erro de syntax: Não é permitido outro tipo de operação: Somente numeros.";
                break;
        }
        return retorno;
    }
}
