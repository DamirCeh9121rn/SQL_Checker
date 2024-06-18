package view.tree;

import lombok.Getter;
import lombok.Setter;
import model.Node;

import javax.swing.tree.DefaultMutableTreeNode;

@Getter
@Setter
public class TreeNode extends DefaultMutableTreeNode {

    private Node node;
    String name;

    public TreeNode(Node ruNode){
        this.node = node;
    }
    public TreeNode(Node Node, String name) {
        this.name = name;
        this.node = Node;
    }

    @Override
    public String toString() {
        return node.toString();
    }

    public Node getNode() {
        return node;
    }


}
