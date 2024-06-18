package view.tree;

import model.implementation.InformationResource;

import javax.swing.tree.DefaultTreeModel;

public interface Tree {

    DefaultTreeModel generateTree(InformationResource informationResource);

}