package controller.action;

import com.opencsv.CSVReader;
import model.Node;
import model.NodeComposite;
import model.enums.AttributeType;
import model.implementation.Attribute;
import view.MainFrame;
import view.tree.TreeNode;

import javax.swing.*;
import javax.swing.tree.TreePath;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileReader;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

public class BulkImportAction extends MyAbstractAction{

    public BulkImportAction(){
        putValue(NAME, "Bulk import");
        putValue(SHORT_DESCRIPTION, "Bulk import");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        TreePath path = ((JTree) MainFrame.getInstance().getJTree()).getSelectionPath();

        TreeNode myTreeNode = (TreeNode)path.getLastPathComponent();
        NodeComposite model = (NodeComposite) myTreeNode.getNode();


        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setFileFilter(new CSVFilter());

        File file = null;
        if(jFileChooser.showSaveDialog(MainFrame.getInstance())==JFileChooser.APPROVE_OPTION)
            file = new File(jFileChooser.getSelectedFile().toString());
        else return;

        String upit = " INSERT INTO %s(%s) VALUES (%s)";

        try {
            FileReader filereader = new FileReader(file);

            CSVReader csvReader = new CSVReader(filereader);
            String[] nextRecord;
            int size=0;

            String heder="";
            int i = 0;

            nextRecord = csvReader.readNext();

            for (String cell : nextRecord) {
                size++;
            }
            String[] hederi = new String[size];
            for (String cell : nextRecord) {
                heder+=cell+",";
                hederi[i++] = cell;
            }
//            System.out.println(heder);
//            for(int j = 0; j< hederi.length; j++){
//                System.out.println(hederi[j]);
//            }
            StringBuilder hb = new StringBuilder(heder);
            hb.deleteCharAt(hb.length()-1);
            //hb.deleteCharAt(hb.length()-1);

            if(MainFrame.getInstance().getAppCore().getCheacker().bulkCheck(hb.toString())) {

                while ((nextRecord = csvReader.readNext()) != null) {
                    String data = "";
                    i = 0;
                    for (String cell : nextRecord) {
                        Attribute pom = (Attribute) model.getChildByName(hederi[i++]);
                        if (pom.getAttributeType().equals(AttributeType.CHAR) || pom.getAttributeType().equals(AttributeType.VARCHAR) || pom.getAttributeType().equals(AttributeType.DATE)) {
                            cell = "'" + cell + "'";
                        }

                        data += cell + ",";
                    }
                    StringBuilder sb = new StringBuilder(data);
                    sb.deleteCharAt(sb.length() - 1);
                    //System.out.println(String.format(upit, model.toString(), hb, sb));
                    MainFrame.getInstance().getAppCore().readDataFromTable(String.format(upit, model.toString(), hb, sb));
                    //MainFrame.getInstance().getAppCore().loadDataFromTable(model.toString(), String.format(upit, model.toString(),hb,sb));
                }
            }
        }
        catch (Exception f) {
            f.printStackTrace();
        }

    }
}
