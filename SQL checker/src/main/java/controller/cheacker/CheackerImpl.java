package controller.cheacker;


import lombok.Getter;
import lombok.Setter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.text.ParseException;

@Getter
@Setter
public class CheackerImpl implements Cheacker{

    JSONArray greske = new JSONArray();
    JSONArray rules;


    Rule foreingKey;
    Rule mandatoryPartsQuery;
    Rule seqiemceOfStatemants;
    Rule whereCheck;
    Rule bulkImport;
    Rule tableAndColumn;
    Rule aliasesCheck;
    Rule groupByCheck;

    public CheackerImpl(){
         foreingKey = new ForeignKeyCheck();
         mandatoryPartsQuery = new MandatoryPartsQueryCheck();
         seqiemceOfStatemants = new SequenceOfStatementsCheck();
         whereCheck = new WhereCheck();
         bulkImport = new BulkImportCheck();
         tableAndColumn = new TableAndColumn();
         aliasesCheck = new AliasesCheck();
         groupByCheck = new GroupByCheck();
         rules = ucitajPravila();

        //System.out.println(rules);
     }

    @Override
    public boolean Check(String string) {
      if(!mandatoryPartsQuery.Check(string)){
           System.out.println("nedostaju obavezni delovi");
           return false;
       }else if(!seqiemceOfStatemants.Check(string)) {
           System.out.println("nije dobar redosled");
           return false;
       }else if(!aliasesCheck.Check(string)){
           System.out.println("aliasi nisu pod navodnicima");
           return false;
       }else if(!whereCheck.Check(string)){
           System.out.println("u where u se nalazi funkcija agregacije");
           return false;
       }else if(!groupByCheck.Check(string)){
           System.out.println("mora se dodati gorup by");
           return false;
       }else if(!foreingKey.Check(string)){
           System.out.println("kljuc za spajanje nije odgovarajuc");
           return false;
       }
        /*seqiemceOfStatemants.Check(string);
        aliasesCheck.Check(string);
        whereCheck.Check(string);

        if(!greske.isEmpty()) {
            System.out.println(greske);
            return false;
        }*/

        return true;
    }

    @Override
    public boolean bulkCheck(String string){
        if(!bulkImport.Check(string)) {
            System.out.println("nije moguce importovati ovaj csv fajl");
            return false;
        }/*bulkImport.Check(string);
        if(!greske.isEmpty()) {
            System.out.println(greske);
            return false;
        }*/
        return true;
    }

    private JSONArray ucitajPravila(){
        JSONArray rules = null;

        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("C:\\Users\\Damir\\Desktop\\baze-ceh-stojanovic\\Baze-Ceh-Stojanovic\\src\\main\\java\\controller\\cheacker\\rules.json"))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            rules = (JSONArray) obj;
            //System.out.println(rules.get(3));


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (org.json.simple.parser.ParseException e) {
            throw new RuntimeException(e);
        }

        return rules;
    }

}
