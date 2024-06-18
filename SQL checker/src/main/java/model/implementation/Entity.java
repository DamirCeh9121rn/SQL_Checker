package model.implementation;

import lombok.Getter;
import lombok.Setter;
import model.Node;
import model.NodeComposite;


@Getter
@Setter
public class Entity extends NodeComposite {

    public Entity(String name, Node parent) {
        super(name, parent);
    }

    @Override
    public void addChild(Node child) {
        if (child != null && child instanceof Attribute){
            Attribute attribute = (Attribute) child;
            this.getChildren().add(attribute);
        }

    }
}