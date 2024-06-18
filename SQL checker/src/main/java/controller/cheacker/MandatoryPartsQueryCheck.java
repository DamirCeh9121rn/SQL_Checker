package controller.cheacker;

public class MandatoryPartsQueryCheck implements Rule{

    String name = "MandatoryPartsQueryCheck";
    @Override
    public boolean Check(String string) {

        String pom = string.toLowerCase();
        String[] reci = pom.split("[\\n\\s]");

        if(pom.contains("select")){
            if(!pom.contains("from"))
                return false;

            if(pom.contains("join")){
                //System.out.println(" usao join ");
                int on =0;
                int using = 0;
                int join = 0;
                for(String s: reci){
                    if(s.equalsIgnoreCase("join"))
                        join++;
                    else if(s.equalsIgnoreCase("on"))
                        on++;
                    else if(s.equalsIgnoreCase("using"))
                        using++;
                }

               // System.out.println(on + " " +using);

                if(join == 1){
                   if(on > 0 && using>0)
                       return false;
                   else if(on == 0 && using == 0)
                       return false;
                }else if(join == 2){
                    if(on > 0 && using>0)
                        return false;
                    else if(on == 0 && using == 0)
                        return false;
                    else if(on < 2 && using == 0)
                        return false;
                    else if (on == 2 && using < 2)
                        return false;
                }
            }

                for (int i = 0; i < reci.length; i++) {
                    if(reci[i].contains("where") && i == reci.length-1) {
                        return false;
                    }
                }

            if(pom.contains("group by")){
                for (int i = 0; i < reci.length; i++) {
                    if(reci[i].contains("group") && reci[i+1].equals("by") && i+1 == reci.length-1) {
                        return false;
                    }
                }
            }
            if(pom.contains("order by")){
                for (int i = 0; i < reci.length; i++) {
                    if(reci[i].contains("order") && reci[i+1].equals("by") && i+1 == reci.length-1) {
                        return false;
                    }
                }
            }

        }else if(pom.contains("insert into")){
            if(!pom.contains("values"))
                return false;

        }else if(pom.contains("delete")){
            if(!pom.contains("from"))
                return false;

            for (int i = 0; i < reci.length; i++) {
                if(reci[i].contains("where") && i == reci.length-1) {
                    return false;
                }
            }

        }else if(pom.contains("upadate")){
            if(!pom.contains("set"))
                return false;

        }else
            return false;

        return true;
    }
}
