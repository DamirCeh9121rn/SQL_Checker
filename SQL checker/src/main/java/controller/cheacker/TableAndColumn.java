package controller.cheacker;

import model.Node;
import model.NodeComposite;
import model.implementation.InformationResource;
import view.MainFrame;
import view.tree.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class TableAndColumn implements Rule{

    String name = "TableAndColumn";
    @Override
    public boolean Check(String string) {

        String pom = string.toLowerCase();

        List<String> tabele = new ArrayList<>();
        List<String> kolone = new ArrayList<>();

        List<String> neKolone = new ArrayList<>();
        List<String> neTabele = new ArrayList<>();

        InformationResource root = (InformationResource) ((TreeNode) MainFrame.getInstance().getJTree().getModel().getRoot()).getNode();

        for (Node n : root.getChildren()) {
            if(!tabele.contains(n.getName()))
                tabele.add(n.getName());

            NodeComposite nc = (NodeComposite) n;
            for (Node atr: nc.getChildren()) {
                if(!kolone.contains(atr.getName()))
                    kolone.add(atr.getName());
            }
        }

//        System.out.println(tabele);
//        System.out.println(kolone);

        if(pom.contains("select")){

            String subStr1 = pom.substring(0,pom.indexOf("from"));

            pom.replace("\n", "");
            String[] nizStr = pom.split("\\s");
            int i = 0;
            int on = 0;
            int using =0;
            for (String s: nizStr) {
                if(s.equals("join"))
                    i++;
                else if(s.equals("on"))
                    on++;
                else if(s.equals("using"))
                    using++;
            }


            if(i==1){
                String subStr2="";
                if(on>0)
                     subStr2 = pom.substring(pom.indexOf("from"), pom.indexOf(" on "));
                else if(using>0){
                    subStr2 = pom.substring(pom.indexOf("from"), pom.indexOf(" using "));
                }

                String subStr3="";
                if(pom.contains("where")){

                }


            }else if(i==2){


            }



        }
        return false;
    }
}
