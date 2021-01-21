package com.classes.editor;

import static com.forms.Editor.files_opens;
import static com.forms.Editor.tab;
import java.awt.Color;
import java.util.*;
import java.util.regex.*;
import javax.swing.event.*;
import javax.swing.text.*;

/**
 * Highlights jython syntax in a Document Created for the Jython Environment for
 * Students (JES) Hilghights keywords and environment words that are defined for
 * it. It will also highlight single-line comments that start with '#', and
 * single-line strings that start with "'" or '"'.
 *
 * @author Adam Wilson, awilson@cc.gatech.edu
 */
public class HighlightingStyledDocument extends DefaultStyledDocument {
    
    
    //Portugol port = new Portugol();

    
    /* Keyword text style */
    private SimpleAttributeSet keywordStyle = new SimpleAttributeSet();

    /* Environment word text style */
    private SimpleAttributeSet environmentWordStyle = new SimpleAttributeSet();
    
    /* Comment Style */
    private SimpleAttributeSet commentStyle = new SimpleAttributeSet();

    /* String style */
    private SimpleAttributeSet stringStyle = new SimpleAttributeSet();
    
    /* Default Style */
    private SimpleAttributeSet defaultStyle = new SimpleAttributeSet();

    /* Jython keywords */
    private Vector keywords = new Vector();

    /* Gutters */
    private Vector gutters = new Vector();

    /* Jython environment words */
    private Vector environmentWords = new Vector();

    /* Generated Regular expression for keywords */
    private Pattern keyReg = Pattern.compile("");

    /* Generated regular expression for environment words */
    private Pattern envReg = Pattern.compile("inteiro|caracter|real|leia|se|entao|fimse|senao|enquanto|escreva");

    /* Regular Expression for comments */
    private Pattern commentReg = Pattern.compile("#++[^\n]*");

    /* Regular Expression for double quote Strings */
    private Pattern doubleStringReg = Pattern.compile("\"[^\n\"]*\"");

    /* Regular Expression for single quote strings */
    private Pattern singleStringReg = Pattern.compile("'[^\n']*'");

    /* Regular Expression for string & comments */
    /* "\\\"" - why not?!? \p" */
    private Pattern stringComments = Pattern.compile("(\\/\\/[^\n]*|\"([^\n\"\\x5c]|(\\x5c\")|(\\x5c))*\"|'([^\n'\\x5c]|(\\x5c')|(\\x5c))*')");

    /* Regular Expression to match multi-line strings */
    private Pattern mlString = Pattern.compile("\"\"\".*\"\"\"", Pattern.DOTALL);

    /* Regular Expression to match triple qoutes */
    private Pattern triQuote = Pattern.compile("\"\"\"");

    /**
     * insertString Overrides the default method from DefaultStyledDocument.
     * Calls appropriate syntax highlighting code and then class super.
     * @throws javax.swing.text.BadLocationException
     */
    @Override
    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
        super.insertString(offs, str, a);
        updateHighlightingInRange(offs, str.length());
    }

    /**
     * fireRemoveUpdate Overrides the default method from DefaultStyledDocument.
     * Calls appropriate syntax highlighting code and then class super.
     *
     * @param e the DocumentEvent
     */
    @Override
    protected void fireRemoveUpdate(DocumentEvent e) {
        int offset = e.getOffset();
        int length = e.getLength();
        updateHighlightingInRange(offset - 1, 0);
        super.fireRemoveUpdate(e);
    }

    /**
     * Method: updateHighlightingInRange Looks at a given range of text in a
     * document and highlights it according to keywords, environment, strings,
     * and comments.
     *
     * @param offset Where in the document the change started
     * @param length The length of change measured from the offset
     */
    public void updateHighlightingInRange(int offset, int length) {
        try {
            String textAll = getText(0, getLength());
            int start = getLineStart(textAll, offset);
            int end = getLineEnd(textAll, offset + length);
            String text = getText(start, end - start);
            setCharacterAttributes(start, end - start, defaultStyle, true);

            //Do Block Highlighting:
            //Find and highlight keywords:
            Matcher m = keyReg.matcher(text);
            while (m.find()) {
                setCharacterAttributes(start + m.start(), m.end() - m.start(), keywordStyle, true);
            }

            //Find and highlight keywords:
            m = envReg.matcher(text);
            while (m.find()) {
                setCharacterAttributes(start + m.start(), m.end() - m.start(), environmentWordStyle, true);
            }

            //Find and highlight Comments and strings:
            m = stringComments.matcher(text);
            while (m.find()) {
                //System.out.println("Matched: " + getText(start + m.start(), m.end() - m.start()));
                if (text.charAt(m.start()) == '/') {
                    setCharacterAttributes(start + m.start(), m.end() - m.start(), commentStyle, true);
                }
                if (text.charAt(m.start()) == '\'' || text.charAt(m.start()) == '"') {
                    setCharacterAttributes(start + m.start(), m.end() - m.start(), stringStyle, true);
                }
            }
            //Matches Multi-line strings starting with triple quotes:
            /*m = mlString.matcher(textAll);
             while(m.find())
             setCharacterAttributes(m.start(), m.end() - m.start(), stringStyle, true);*/
        } catch (BadLocationException e) {
        }
    }

    /**
     * getLineStart Finds the start of a line in a document. Takes in an offset
     * and finds the index of the beginning of that line.
     *
     * @param text The text to find the start of the line in
     * @param offset An index of a character on that line
     */
    private int getLineStart(String text, int offset) {
        while (offset > 0) {
            if (text.charAt(offset) == '\n') {
                return (offset + 1);
            }
            offset--;
        }
        return offset;
    }

    /**
     * getLineEnd Takes in an index and finds the offset of the end of the line
     *
     * @param text The text to find the end of the line in
     * @param offset An index of a character on that line.
     */
    private int getLineEnd(String text, int offset) {
        while (offset < text.length()) {
            if (text.charAt(offset) == '\n') {
                return (offset);
            }
            offset++;
        }
        return offset;
    }

    /**
     * Method: isString Looks at a location in the given document and determines
     * if that location is inside a string. Supports """ for multi-line strings.
     *
     * @param doc The document to look in for the text
     * @param offset The location to check for string-ness
     * @return True for is a string, false for is not a string
     */
    private boolean isString(int offset) {
        return false;
    }

    /**
     * Method: isComment Looks at a location inside a document and determines if
     * it is a comment.
     *
     * @param doc The document to look in for the text
     * @param offset The location to check for stringness
     * @return True for is a comment, false for is not a comment
     */
    private boolean isComment(int offset) {
        return false;
    }

    /**
     * Method: setKeywords Sets a collection of keywords to highlight.
     *
     * @param words An array of all the words
     */
    public void setKeywords(String[] words) {
        keywords.clear();
        for (int i = 0; i < words.length; i++) {
            keywords.add(words[i]);
        }
        compileKeywords();
    }

    /**
     * Method: setEnvironmentWords Sets a collection of environment words to
     * highlight.
     *
     * @param words An array of all the words
     */
    public void setEnvironmentWords(String[] words) {
        environmentWords.clear();
        for (int i = 0; i < words.length; i++) {
            environmentWords.add(words[i]);
        }
        compileEnvironmentWords();
    }

    /**
     * Method: addKeyword Adds a keyword to the Vector of keywords.
     *
     * @param word The word to add
     */
    public void addKeyword(String word) {
        keywords.add(word);
        compileKeywords();
    }

    /**
     * Method: addEnvironmentWord Adds an environment word to the Vector of
     * environment words.
     *
     * @param word The word to add
     */
    public void addEnvironmentWord(String word) {
        environmentWords.add(word);
        compileEnvironmentWords();
    }

    /**
     * Method: setKeywordStyle Sets the style of text to use for keywords
     *
     * @param style The new text style
     */
    public void setKeywordStyle(SimpleAttributeSet style) {
        keywordStyle = style;
    }

    /**
     * Method: setEnvironmentWordStyle Sets the style of text to use for
     * environment words
     *
     * @param style The new text style
     */
    public void setEnvironmentWordStyle(SimpleAttributeSet style) {
        environmentWordStyle = style;
    }

    /**
     * Method: setCommentStyle Sets the style of text to use for comments
     *
     * @param style The new text style
     */
    public void setCommentStyle(SimpleAttributeSet style) {
        commentStyle = style;
    }

    /**
     * Method: setStringStyle Sets the style of text to use for strings
     *
     * @param style The new text style
     */
    public void setStringStyle(SimpleAttributeSet style) {
        stringStyle = style;
    }

    /**
     * Method: setDefaultStyle Sets the default style of text to use
     *
     * @param style The new text style
     */
    public void setDefaultStyle(SimpleAttributeSet style) {
        defaultStyle = style;
    }

    /**
     * compileKeywords Recompiles the regular expression used for matching key
     * words. Takes the collection of keywords and generates a regular
     * expression string. It then compiles that string into the Pattern class
     * and stores it in keyReg. Example: if the keywords were "if" and "for",
     * the regular expression would be: "\W(if|for)\W". The \W isolate the
     * keywords by non-word characters.
     */
    private void compileKeywords() {
        String exp = new String();
        exp = "\\b("; 	//Start the expression to match non-word characters,
        //i.e. [^a-zA-Z0-9], and then start the OR block.
        for (int i = 0; i < keywords.size(); i++) {
            if (i == 0) {
                exp = exp + ((String) keywords.elementAt(i)).trim();
            }
            exp = exp + "|" + ((String) keywords.elementAt(i)).trim();
        }
        exp = exp + ")\\b";
        keyReg = Pattern.compile(exp);
    }

    /**
     * compileEnvironmentWords Recompiles the regular expression used for
     * matching environment words. Takes the collection of environment words and
     * generates a regular expression string. It then compiles that string into
     * the Pattern class and stores it in envReg. Example: if the envwords were
     * "if" and "for", the regular expression would be: "\W(if|for)\W". The \W
     * isolate the envwords by non-word characters.
     */
    private void compileEnvironmentWords() {
        String exp = new String();
        exp = "\\b("; 	//Start the expression to match non-word characters,
        //i.e. [^a-zA-Z0-9], and then start the OR block.
        for (int i = 0; i < environmentWords.size(); i++) {
            if (i == 0) {
                exp = exp + ((String) environmentWords.elementAt(i)).trim();
            }
            exp = exp + "|" + ((String) environmentWords.elementAt(i)).trim();
        }
        exp = exp + ")\\b";
        envReg = Pattern.compile(exp);
    }

}//END OF HighlightingStyledDocument Class
