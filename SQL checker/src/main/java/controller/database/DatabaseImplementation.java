package controller.database;

import lombok.AllArgsConstructor;
import lombok.Data;
import model.Node;
import model.Row;

import java.util.List;

@Data
@AllArgsConstructor
public class DatabaseImplementation implements Database {

    private Repository repository;


    @Override
    public Node loadResource() {
        return repository.getSchema();
    }

    @Override
    public List<Row> readDataFromTable(String tableName) {
        return repository.get(tableName);
    }

    @Override
    public List<Row> citajDataFromTable(String tableName, String upit) {
        return repository.daj(tableName, upit);
    }
}
