package model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public abstract class NodeComposite extends Node{
    private List<Node> children;


    public NodeComposite(String name, Node parent) {
        super(name, parent);
        this.children = new ArrayList<>();
    }

    public abstract void addChild(Node child);

    public Node getChildByName(String name) {
        for (Node child: this.getChildren()) {
            if (name.equals(child.getName())) {
                return child;
            }
        }
        return null;
    }


}
