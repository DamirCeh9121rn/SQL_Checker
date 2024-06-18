package view.tree;



import model.Node;
import model.NodeComposite;
import model.implementation.InformationResource;

import javax.swing.tree.DefaultTreeModel;
import java.util.List;


public class TreeImplementation implements Tree {

    @Override
    public DefaultTreeModel generateTree(InformationResource informationResource) {

        TreeNode root = new TreeNode(informationResource, informationResource.getName());
        connectChildren(root);
        return new DefaultTreeModel(root);
    }


    private void connectChildren(TreeNode current){

        if (!(current.getNode() instanceof NodeComposite)) return;

        List<Node> children = ((NodeComposite) current.getNode()).getChildren();
        for (int i = 0; i<children.size();i++){
            TreeNode child = new TreeNode(children.get(i), children.get(i).getName());
            current.insert(child,i);
            connectChildren(child);
        }

    }

}
