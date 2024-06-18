package controller.action;

import model.enums.PrettyWords;
import view.MainFrame;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Locale;

public class PrettyAction extends MyAbstractAction {

    public PrettyAction() {
        putValue(NAME, "Pretty");
        putValue(SHORT_DESCRIPTION, "Pretty");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String text = MainFrame.getInstance().getTextPane().getText().toLowerCase(Locale.ROOT);
        String[] str = text.split("[\\n\\s]");
        MainFrame.getInstance().getTextPane().setText("");

        boolean isPretty = false;
        boolean isFirst = false;
        for(int i = 0; i < str.length; i++) {
            if (str[i].equals("insert") && (str[i + 1].equals("into"))){
                appendToPane(MainFrame.getInstance().getTextPane(),  (str[i] + " " + str[i + 1]).toUpperCase(), Color.blue);
                appendToPane(MainFrame.getInstance().getTextPane(), " ", Color.black);
                i++;
                isFirst = true;
                continue;
            }

            for (PrettyWords words : (PrettyWords.values())) {
                if (str[i].equalsIgnoreCase(String.valueOf(words))) {
                    String upper = str[i].toUpperCase();
                    isPretty = true;
                    if (!isFirst) {
                        appendToPane(MainFrame.getInstance().getTextPane(), upper, Color.blue);
                        appendToPane(MainFrame.getInstance().getTextPane(), " ", Color.black);
                        isFirst = true;
                        continue;
                    }
                    appendToPane(MainFrame.getInstance().getTextPane(), "\n", Color.blue);
                    appendToPane(MainFrame.getInstance().getTextPane(), upper, Color.blue);
                    appendToPane(MainFrame.getInstance().getTextPane(), " ", Color.black);
                }

            }


            if (str[i].equals("group") && (str[i + 1].equals("by")) || str[i].equals("order") && (str[i + 1].equals("by"))){

                    appendToPane(MainFrame.getInstance().getTextPane(), "\n" + (str[i] + " " + str[i + 1]).toUpperCase(), Color.blue);
                    appendToPane(MainFrame.getInstance().getTextPane(), " ", Color.black);
                    i++;
                    continue;

            }
                if (str[i].equals("cross") || str[i].equals("left") || str[i].equals("right") || str[i].equals("inner")) {
                   if( str[i + 1].equals("join")){
                    appendToPane(MainFrame.getInstance().getTextPane(), "\n" + (str[i] + " " + str[i + 1]).toUpperCase(), Color.blue);
                    appendToPane(MainFrame.getInstance().getTextPane(), " ", Color.black);
                    i++;
                    continue;
                }
            }
            if(str[i].equals("<") || str[i].equals(">") || str[i].equals("/") || str[i].equals("+")
                    ||str[i].equals("=") ||str[i].equals("-") ){
                appendToPane(MainFrame.getInstance().getTextPane(),  str[i].toUpperCase(), Color.blue);
                appendToPane(MainFrame.getInstance().getTextPane(), " ", Color.black);
                continue;
            }
            if (str[i].equals("is") && (str[i + 1].equals("null"))){
                    appendToPane(MainFrame.getInstance().getTextPane(), "\n" + (str[i] + " " + str[i + 1]).toUpperCase(), Color.blue);
                    appendToPane(MainFrame.getInstance().getTextPane(), " ", Color.black);
                i++;
                continue;
            }
            if (str[i].equals("is") &&  (str[i + 1].equals("not") &&  (str[i + 2].equals("null")))){
                    appendToPane(MainFrame.getInstance().getTextPane(), "\n" + (str[i] + " " + str[i + 1] + " " + str[i + 2]).toUpperCase(), Color.blue);
                    appendToPane(MainFrame.getInstance().getTextPane(), " ", Color.black);
                i += 2;
                continue;
            }

            if (str[i].equals("not") &&  (str[i + 1].equals("like"))){
                appendToPane(MainFrame.getInstance().getTextPane(), "\n" + (str[i] + " " + str[i + 1]).toUpperCase(), Color.blue);
                appendToPane(MainFrame.getInstance().getTextPane(), " ", Color.black);
                i++;
                continue;
            }
            if (!isPretty)
                appendToPane(MainFrame.getInstance().getTextPane(), str[i] + " ", Color.black);

            isPretty = false;
        }
        MainFrame.getInstance().getTextPane().setForeground(Color.BLACK);
    }

    private void appendToPane(JTextPane tp, String msg, Color c)
    {
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

        int len = tp.getDocument().getLength();
        tp.setCaretPosition(len);
        tp.setCharacterAttributes(aset, false);
        tp.replaceSelection(msg);
        MainFrame.getInstance().getTextPane().setForeground(Color.BLACK);
    }
}

