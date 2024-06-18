package model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Node {

    private String name;
    private Node parent;

    public Node(String name, Node parent){
        this.name = name;
        this.parent = parent;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof Node) {
            Node node = (Node) obj;
            return this.getName().equals(node.getName());
        }
        return false;
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
