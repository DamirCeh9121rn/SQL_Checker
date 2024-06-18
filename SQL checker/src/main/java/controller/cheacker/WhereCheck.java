package controller.cheacker;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import view.MainFrame;

public class WhereCheck implements Rule {

    String name = "WhereCheck";

    @Override
    public boolean Check(String string) {

        String pom = string.toLowerCase();

        if (pom.contains("where")) {

            String subStr = "";

            subStr = pom.substring(pom.indexOf("where"), pom.length());

            if (subStr.contains("group by")) {
                subStr = subStr.substring(subStr.indexOf("where"), subStr.indexOf("group"));
            }

            String[] reci = subStr.split("[\\s\\n(]");

            for (String s : reci) {
                if (s.equalsIgnoreCase("max") || s.equalsIgnoreCase("min") || s.equalsIgnoreCase("count") || s.equalsIgnoreCase("avg") || s.equalsIgnoreCase("sum")) {
                    return false;
//                    JSONObject sablon = (JSONObject) ((JSONArray)((CheackerImpl) MainFrame.getInstance().getAppCore().getCheacker()).getRules()).get(3);
//                    //System.out.println(sablon);
//                    JSONObject dodaj = new JSONObject();
//                    dodaj.put("name", name);
//                    dodaj.put("greska", String.format((String) sablon.get("greska"),s));
//                    dodaj.put("sugestija", String.format((String) sablon.get("sugestija"), s));
//                    ((CheackerImpl)MainFrame.getInstance().getAppCore().getCheacker()).greske.add(dodaj.toJSONString());
                }


                //                    return false;
//                else if(s.equalsIgnoreCase("min"))
//                    return false;
//                else if(s.equalsIgnoreCase("count"))
//                    return false;
//                else if(s.equalsIgnoreCase("avg"))
//                    return false;
//                else if(s.equalsIgnoreCase("sum"))
//                    return false;
            }
        }

        return true;
    }
}
