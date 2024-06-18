package model.implementation;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import model.Node;
import model.NodeComposite;


@Getter
@Setter
public class InformationResource extends NodeComposite {


    public InformationResource(String name) {
        super(name, null);
    }

    @Override
    public void addChild(Node child) {
        if (child != null && child instanceof Entity){
            Entity entity = (Entity) child;
            this.getChildren().add(entity);
        }
    }

}