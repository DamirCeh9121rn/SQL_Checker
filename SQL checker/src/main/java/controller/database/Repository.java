package controller.database;

import model.Node;
import model.Row;

import java.util.List;

public interface Repository {

    Node getSchema();

    List<Row> get(String from);

    public List<Row> daj(String from, String upit);
}
