package controller.cheacker;

import java.util.ArrayList;
import java.util.List;

public class GroupByCheck implements Rule{

    String name = "GroupByCheck";

    @Override
    public boolean Check(String string) {

        String pom = string.toLowerCase();
        pom.replace("\n", "");

        if(pom.contains("select")){
            String subStr = pom.substring(pom.indexOf("select") +6, pom.indexOf("from")-1);

            String[] kolone = subStr.split("[,]");
            int funkcijaAgr = 0;
            List<String> koloneZaGroup = new ArrayList<>();
            List<String> copy = new ArrayList<>();

            if(kolone.length >1){
                for (String s: kolone) {
                    if(!s.contains(" max ") && !s.contains(" max(") && !s.contains(" min ") && !s.contains(" min(") && !s.contains(" max ") && !s.contains(" min(")
                        && !s.contains(" avg ") && !s.contains(" avg(") && !s.contains(" count ") && !s.contains(" count(") && !s.contains(" sum ") && !s.contains(" sum(")) {
                        koloneZaGroup.add(s);
                    }else{
                        funkcijaAgr++;
                    }
                }
                if (funkcijaAgr == 0)
                    return true;
                copy.addAll(koloneZaGroup);
                String subGroup ="";
                String[] koloneGroup;
                if(pom.contains("group by") && funkcijaAgr>0){
                    if(pom.contains("having ")){
                       subGroup = pom.substring(pom.indexOf("group by") + 8, pom.indexOf("having "));
                       koloneGroup = subGroup.split("[,]");
                    }else if(pom.contains("order by")){
                        subGroup = pom.substring(pom.indexOf("group by") +8, pom.indexOf("order by"));
                        koloneGroup = subGroup.split("[,]");
                    }else{
                        subGroup = pom.substring(pom.indexOf("group by") +8 , pom.length());
                        koloneGroup = subGroup.split("[,]");
                    }
                    int potrebneKolone = 0;
                    for (String s: koloneGroup) {
                        if(koloneZaGroup.remove(s)){
                            //System.out.println(s);
                            potrebneKolone++;
                        }
                    }
                    //System.out.println(potrebneKolone + " " + (kolone.length - koloneGroup.length));
                }
                //System.out.println(koloneZaGroup + " " + funkcijaAgr);
                //System.out.println(copy);
                if(!koloneZaGroup.isEmpty()){
                    return false;
                }

            }
        }

        return true;
    }
}
