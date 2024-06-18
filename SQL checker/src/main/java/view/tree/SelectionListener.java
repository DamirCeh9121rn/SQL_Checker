package view.tree;


import model.implementation.Entity;
import view.MainFrame;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

public class SelectionListener implements TreeSelectionListener {
    @Override
    public void valueChanged(TreeSelectionEvent e) {

        JTree tree = (JTree) e.getSource();
        TreeNode node = (TreeNode) tree.getLastSelectedPathComponent();
        if (node == null || !(node.getNode() instanceof Entity)) return;


        MainFrame.getInstance().getAppCore().readDataFromTable("SELECT * FROM " + node.getName());




    }
}
