package model.implementation;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import model.Node;
import model.enums.ConstraintType;

@Getter
@Setter
public class AttributeConstraint extends Node {

    private ConstraintType constraintType;

    public AttributeConstraint(String name, Node parent, ConstraintType constraintType) {
        super(name, parent);
        this.constraintType = constraintType;
    }

}
