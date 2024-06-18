package controller.database;


import model.Node;
import model.Row;

import java.util.List;

public interface Database{

    Node loadResource();

   List<Row> readDataFromTable(String tableName);


    List<Row> citajDataFromTable(String tableName,String upit);
}
