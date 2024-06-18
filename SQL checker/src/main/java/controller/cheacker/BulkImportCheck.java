package controller.cheacker;

import model.Node;
import model.NodeComposite;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import view.MainFrame;
import view.tree.TreeNode;

import javax.swing.*;
import javax.swing.tree.TreePath;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class BulkImportCheck implements Rule{

    String name = "BulkImportCheck";

    @Override
    public boolean Check(String string) {

        string.replace(" ", "");
        String[] hederi = string.split("[,]");

        List<String> koloneCSV = new ArrayList<>();
        koloneCSV.addAll(List.of(hederi));

        List<String> koloneTabela = new LinkedList<>();

        TreePath path = ((JTree) MainFrame.getInstance().getJTree()).getSelectionPath();

        TreeNode myTreeNode = (TreeNode)path.getLastPathComponent();
        NodeComposite model = (NodeComposite) myTreeNode.getNode();

        for (Node n : model.getChildren()) {
            koloneTabela.add(n.getName());
        }

        List<String> copy = new LinkedList<>();
        copy.addAll(koloneTabela);

        for (String s: koloneTabela) {
            if(koloneCSV.contains(s)){
                System.out.println(s);
                koloneCSV.remove(s);
                copy.remove(s);
            }
        }

        if(!copy.isEmpty() && !koloneCSV.isEmpty()){
            return false;

        }
        return true;

    }
}
