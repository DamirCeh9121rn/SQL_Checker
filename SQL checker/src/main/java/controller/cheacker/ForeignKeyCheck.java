package controller.cheacker;

import model.Node;
import model.enums.ConstraintType;
import model.implementation.Attribute;
import model.implementation.AttributeConstraint;
import model.implementation.Entity;
import model.implementation.InformationResource;
import view.MainFrame;
import view.tree.TreeNode;

import java.util.Collection;

public class ForeignKeyCheck implements Rule{

    String name = "ForeignKeyCheck";

    @Override
    public boolean Check(String string) {
        InformationResource ir = (InformationResource) ((TreeNode) MainFrame.getInstance().getJTree().getModel().getRoot()).getNode();

        String pom = string.toLowerCase();
        pom.replace("\n", "");
        String[] nizStr = pom.split("\\s");
        int i = 0;
        for (String s: nizStr) {
            if(s.equals("join"))
                i++;
        }
        System.out.println(i);
        if(i == 1){
            if(pom.contains("join")) {
                int pos = pom.indexOf("from") + 4;
                int pos2 = 0;

                if (pom.contains("using")) {
                    pos2 = pom.indexOf("using");
                } else if (pom.contains("on")) {
                    pos2 = pom.indexOf("on");
                }

                String sub1 = pom.substring(pos, pos2);
                Entity tabela1 = null;
                Entity tabela2 = null;
                String[] reci = sub1.split("[\\s]");

                for (String s: reci) {
                    for (Node n: ir.getChildren()){
                        if(s.contains(n.getName())){
                            if(tabela1 == null){
                                tabela1 = (Entity) ir.getChildByName(n.getName());
                                tabela1.getChildren().addAll( ir.getChildren());
                            }else{
                                tabela2 =(Entity) ir.getChildByName(n.getName());
                                tabela2.getChildren().addAll( ir.getChildren());
                            }
                        }
                    }

                }
                String sub2 = pom.substring(pos2, pom.length());

                Attribute prvi = null;
                Attribute drugi= null;

                for (Node n : tabela1.getChildren()) {
                    //Attribute a = (Attribute) n;
                    if(sub2.contains(n.getName())){
                        prvi = (Attribute) n;
                    }
                }
                String sub3 ="";
                if(sub2.contains("=")){
                    sub3 = sub2.substring(sub2.indexOf("="), sub2.length());
                    for (Node n : tabela2.getChildren()) {
                        //Attribute a = (Attribute) n;
                        if(sub3.contains(n.getName())){
                            drugi = (Attribute) n;
                        }
                    }

                }else {
                    for (Node n : tabela2.getChildren()) {
                        //Attribute a = (Attribute) n;
                        if (sub2.contains(n.getName())) {
                            drugi = (Attribute) n;
                        }
                    }
                }

                System.out.println(prvi +" "+drugi);

                System.out.println(prvi.getChildren().size() +" " + drugi.getChildren().size());



                if(prvi == null || drugi == null) {
                    System.out.println("Greska");
                    return false;
                }else {
                    if (((AttributeConstraint) prvi.getChildByName(prvi.getName())).getConstraintType().equals(ConstraintType.PRIMARY_KEY) &&
                            ((AttributeConstraint) drugi.getChildByName(drugi.getName())).getConstraintType().equals(ConstraintType.FOREIGN_KEY)) {
                        System.out.println("USPESNO");
                    } else if (((AttributeConstraint) prvi.getChildByName(prvi.getName())).getConstraintType().equals(ConstraintType.FOREIGN_KEY) &&
                            ((AttributeConstraint) drugi.getChildByName(drugi.getName())).getConstraintType().equals(ConstraintType.PRIMARY_KEY)) {
                        System.out.println("USPESNO");
                    }
                }
            }
        }else if(i == 2){
            int j=0;
            Integer[] pozicije =new Integer[5];
            for (int index = pom.indexOf("join"); index >= 0; index = pom.indexOf("join", index + 1))
            {
                pozicije[j] = index;
                j+=2;
            }
            j=1;
            if (pom.contains("using")) {
                for (int index = pom.indexOf("using"); index >= 0; index = pom.indexOf("using", index + 1))
                {
                    pozicije[j] = index;
                    j+=2;
                }
            } else if (pom.contains("on")) {
                for (int index = pom.indexOf("on"); index >= 0; index = pom.indexOf("on", index + 1))
                {
                    pozicije[j] = index;
                    j+=2;
                }
            }

            if(pom.contains("where") && pom.contains("group")){
                if(pom.indexOf("where") < pom.indexOf("group"))
                    pozicije[4] = pom.indexOf("where");
                else
                    pozicije[4] =pom.indexOf("group");
            }else if(pom.contains("where") && !pom.contains("group")){
                pozicije[4] = pom.indexOf("where");
            }else if(!pom.contains("where") && pom.contains("group")){
                pozicije[4] =pom.indexOf("group");
            }else {
                pozicije[4] = pom.length();
            }

            String sub1 = pom.substring(pom.indexOf("from"), pozicije[0]);
            String sub2 = pom.substring(pozicije[0], pozicije[1]);
            String sub3 = pom.substring(pozicije[1], pozicije[2]);
            String sub4 = pom.substring(pozicije[2], pozicije[3]);
            String sub5 = pom.substring(pozicije[3], pozicije[4]);

            Entity[] tabele = new Entity[3];
             int k=0;
            for (Node n : ir.getChildren()) {
                if(sub1.contains(n.getName()))
                    tabele[0] = (Entity) n;
                else if(sub2.contains(n.getName()))
                    tabele[1] = (Entity) n;
                else if(sub4.contains(n.getName()))
                    tabele[2] = (Entity) n;
            }

            boolean prosaoPrvi = false;
            boolean prosaoDrugi = false;

            Attribute prvi = null;
            Attribute drugi= null;

            for (Node n : tabele[0].getChildren()) {
                //Attribute a = (Attribute) n;
                if(sub3.contains(n.getName())){
                    prvi = (Attribute) n;
                }
            }
            for (Node n : tabele[1].getChildren()) {
                //Attribute a = (Attribute) n;
                if(sub3.contains(n.getName())){
                    drugi = (Attribute) n;
                }
            }


            if(prvi == null || drugi == null) {
                System.out.println("Greska");
                return false;
            }else {
                if (((AttributeConstraint) prvi.getChildByName(prvi.getName())).getConstraintType().equals(ConstraintType.PRIMARY_KEY) &&
                        ((AttributeConstraint) drugi.getChildByName(drugi.getName())).getConstraintType().equals(ConstraintType.FOREIGN_KEY)) {
                    System.out.println("USPESNO");
                    prosaoPrvi = true;
                } else if (((AttributeConstraint) prvi.getChildByName(prvi.getName())).getConstraintType().equals(ConstraintType.FOREIGN_KEY) &&
                        ((AttributeConstraint) drugi.getChildByName(drugi.getName())).getConstraintType().equals(ConstraintType.PRIMARY_KEY)) {
                    System.out.println("USPESNO");
                    prosaoPrvi = true;
                }
            }

             prvi = null;
             drugi= null;

            for (Node n : tabele[1].getChildren()) {
                //Attribute a = (Attribute) n;
                if(sub5.contains(n.getName())){
                    prvi = (Attribute) n;
                }
            }
            for (Node n : tabele[2].getChildren()) {
                //Attribute a = (Attribute) n;
                if(sub5.contains(n.getName())){
                    drugi = (Attribute) n;
                }
            }
            System.out.println(prvi);
            System.out.println(drugi);

            if(prvi == null || drugi == null) {
                System.out.println("Greska");
                return false;
            }else {
                if (((AttributeConstraint) prvi.getChildByName(prvi.getName())).getConstraintType().equals(ConstraintType.PRIMARY_KEY) &&
                        ((AttributeConstraint) drugi.getChildByName(drugi.getName())).getConstraintType().equals(ConstraintType.FOREIGN_KEY)) {
                    System.out.println("USPESNO");
                    prosaoDrugi = true;
                } else if (((AttributeConstraint) prvi.getChildByName(prvi.getName())).getConstraintType().equals(ConstraintType.FOREIGN_KEY) &&
                        ((AttributeConstraint) drugi.getChildByName(drugi.getName())).getConstraintType().equals(ConstraintType.PRIMARY_KEY)) {
                    System.out.println("USPESNO");
                    prosaoDrugi = true;
                }
            }

            if(prosaoPrvi && prosaoDrugi){
                System.out.println("Uspesno");
            }else{
                System.out.println("greska");
                return false;
            }


        }
        return true;
    }
}
