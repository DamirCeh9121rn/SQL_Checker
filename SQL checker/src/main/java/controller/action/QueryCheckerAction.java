package controller.action;


import controller.cheacker.ForeignKeyCheck;
import model.Node;
import model.NodeComposite;
import model.enums.AttributeType;
import model.enums.ConstraintType;
import model.implementation.Attribute;
import model.implementation.AttributeConstraint;
import view.MainFrame;
import view.tree.TreeNode;

import javax.swing.*;
import javax.swing.tree.TreePath;
import java.awt.event.ActionEvent;

public class QueryCheckerAction extends  MyAbstractAction{

    public QueryCheckerAction(){
        putValue(NAME, "Query checker");
        putValue(SHORT_DESCRIPTION, "Query checker");
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String upit = MainFrame.getInstance().getTextPane().getText();
        MainFrame.getInstance().getAppCore().readDataFromTable(upit);
    }
}
