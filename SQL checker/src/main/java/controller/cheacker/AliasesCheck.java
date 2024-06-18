package controller.cheacker;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import view.MainFrame;

public class AliasesCheck implements Rule{

    String name = "AliasesCheck";

    @Override
    public boolean Check(String string) {

        String pom = string.toLowerCase();

        if(pom.contains("select")){
            String subStr ="";


            subStr = pom.substring(pom.indexOf("select")+6, pom.indexOf("from"));

            String[] reci = subStr.split("[,]");
            String[] noveReci = new String[reci.length];
            int j = 0;
            for(int i = 0; i< reci.length; i++){
                if(reci[i].contains("(") && !reci[i].contains(")")){
                    noveReci[j] = reci[i] + reci[i+1];
                    i++;
                }else{
                    noveReci[j] = noveReci[i];
                }
                j++;
            }
//            for (String s: reci){
//                System.out.println(s);
//            }

            for (String s: reci){
                if( s.contains(" as ")){
                    String subStr1 = s.substring(s.indexOf(" as ")+4, s.length());

                    String[] alias = subStr1.split("[\\s\\n]");

                    if(alias.length > 1){
                        if(alias[0].charAt(0) != '"' && alias[alias.length-1].charAt(alias[alias.length-1].length()-1) != '"' ){
                           System.out.println(alias[0] + " " + alias[alias.length-1] + "|" );
                            String rec="";
                            for (String g: alias) {
                                rec += g + " ";
                            }
                            return false;
//                            JSONObject sablon = (JSONObject) ((JSONArray)((CheackerImpl) MainFrame.getInstance().getAppCore().getCheacker()).getRules()).get(3);
//                            //System.out.println(sablon);
//                            JSONObject dodaj = new JSONObject();
//                            dodaj.put("name", name);
//                            dodaj.put("greska", String.format((String) sablon.get("greska"),rec));
//                            dodaj.put("sugestija", String.format((String) sablon.get("sugestija"), rec));
//                            ((CheackerImpl)MainFrame.getInstance().getAppCore().getCheacker()).greske.add(dodaj.toJSONString());

                        }
                    }

                }

            }


            //System.out.println(subStr);

        }

        return true;
    }
}
