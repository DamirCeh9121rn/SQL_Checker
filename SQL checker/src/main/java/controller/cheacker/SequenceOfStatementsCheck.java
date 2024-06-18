package controller.cheacker;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import view.MainFrame;

import java.util.HashMap;
import java.util.Map;

public class SequenceOfStatementsCheck implements Rule{

    String name = "SequenceOfStatementsCheck";

    Map<String, Integer> mapa= null;
    @Override
    public boolean Check(String string) {

        String pom = string.toLowerCase();
        String[] reci = pom.split("[\\s\\n]");
        int lastKey = 0;
        String lastValue = "";

//        JSONObject sablon = (JSONObject) ((JSONArray)((CheackerImpl) MainFrame.getInstance().getAppCore().getCheacker()).getRules()).get(1);
//        //System.out.println(sablon);
//        JSONObject dodaj = new JSONObject();

        if(pom.contains("select ")){
            mapa = getMap("select");

            for (String s: reci) {
                if(mapa.containsKey(s)){
                    if(mapa.get(s) > lastKey){
                        lastKey = mapa.get(s);
                        lastValue = s;
                    }else if((mapa.get(s) < lastKey) && (lastValue.equalsIgnoreCase("on") || (lastValue.equalsIgnoreCase("using"))) && s.equalsIgnoreCase("join")){
                        lastKey = mapa.get(s);
                        lastValue = s;
                    }else {
                        return false;
//                        dodaj.put("name", name);
//                        dodaj.put("greska", String.format((String) sablon.get("greska"),s));
//                        dodaj.put("sugestija", String.format((String) sablon.get("sugestija"), s, lastValue));
//                        ((CheackerImpl)MainFrame.getInstance().getAppCore().getCheacker()).greske.add(dodaj.toJSONString());
                    }
                }
            }
        }else if(pom.contains("insert into")){
            mapa = getMap("insert into");
            for (String s: reci) {
                if(mapa.containsKey(s)){
                    if(mapa.get(s) > lastKey){
                        lastKey = mapa.get(s);
                        lastValue = s;
                    }else{
                        return false;
//                        dodaj.put("name", name);
//                        dodaj.put("greska", String.format((String) sablon.get("greska"),s));
//                        dodaj.put("sugestija", String.format((String) sablon.get("sugestija"), s, lastValue));
//                        ((CheackerImpl)MainFrame.getInstance().getAppCore().getCheacker()).greske.add(dodaj.toJSONString());
                    }
                }
            }

        }else if(pom.contains("delete")){
            mapa = getMap("delete");

            for (String s: reci) {
                if(mapa.containsKey(s)){
                    if(mapa.get(s) > lastKey){
                        lastKey = mapa.get(s);
                        lastValue = s;
                    }else{
                        return false;
//                        dodaj.put("name", name);
//                        dodaj.put("greska", String.format((String) sablon.get("greska"),s));
//                        dodaj.put("sugestija", String.format((String) sablon.get("sugestija"), s, lastValue));
//                        ((CheackerImpl)MainFrame.getInstance().getAppCore().getCheacker()).greske.add(dodaj.toJSONString());
                    }
                }
            }
        }else if(pom.contains("update")){
            mapa = getMap("update");
            for (String s: reci) {
                if(mapa.containsKey(s)){
                    if(mapa.get(s) > lastKey){
                        lastKey = mapa.get(s);
                        lastValue = s;
                    }else{
                        return false;
//                        dodaj.put("name", name);
//                        dodaj.put("greska", String.format((String) sablon.get("greska"),s));
//                        dodaj.put("sugestija", String.format((String) sablon.get("sugestija"), s, lastValue));
//                        ((CheackerImpl)MainFrame.getInstance().getAppCore().getCheacker()).greske.add(dodaj.toJSONString());
                    }
                }
            }
        }else{
            {
                return false;
//                dodaj.put("name", name);
//                dodaj.put("greska", "Upit mora imati select, insert into, delete ili update");
//                dodaj.put("sugestija", "Zapoceti upit sa nekim od ovih kljucnih reci");
//                ((CheackerImpl)MainFrame.getInstance().getAppCore().getCheacker()).greske.add(dodaj.toJSONString());
            }
        }
        return true;


    }

    private Map<String, Integer> getMap(String str){
        Map<String, Integer> map = new HashMap<>();

        if(str.equalsIgnoreCase("select")){
            map.put("select", 1);
            map.put("from", 2);
            map.put("join", 3);
            map.put("on", 4);
            map.put("using", 4);
            map.put("where", 5);
            map.put("group by", 6);
            map.put("having", 7);
            map.put("order by", 8);
        } else if (str.equalsIgnoreCase("insert into")) {
            map.put("insert", 1);
            map.put("values", 2);
        } else if (str.equalsIgnoreCase("delete")) {
            map.put("delete", 1);
            map.put("from", 2);
            map.put("where", 3);
        } else if (str.equalsIgnoreCase("update")) {
            map.put("update", 1);
            map.put("set", 2);
            map.put("where", 3);
        }

        return map;
    }



}
