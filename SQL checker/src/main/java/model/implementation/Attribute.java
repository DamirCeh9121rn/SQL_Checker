package model.implementation;

import lombok.*;
import model.Node;
import model.NodeComposite;
import model.enums.AttributeType;


@Getter
@Setter
public class Attribute extends NodeComposite {


    private AttributeType attributeType;
    private int length;
    private Attribute inRelationWith;

    public Attribute(String name, Node parent) {
        super(name, parent);
    }

    public Attribute(String name, Node parent, AttributeType attributeType, int length) {
        super(name, parent);
        this.attributeType = attributeType;
        this.length = length;
    }

    @Override
    public void addChild(Node child) {
        if (child != null && child instanceof AttributeConstraint){
            AttributeConstraint attributeConstraint = (AttributeConstraint) child;
            this.getChildren().add(attributeConstraint);
        }
    }


}