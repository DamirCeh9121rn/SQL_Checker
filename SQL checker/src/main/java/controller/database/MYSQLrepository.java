package controller.database;

import lombok.Data;
import model.Node;
import model.Row;
import model.enums.AttributeType;
import model.enums.ConstraintType;
import model.implementation.Attribute;
import model.implementation.AttributeConstraint;
import model.implementation.Entity;
import model.implementation.InformationResource;
import view.MainFrame;
import view.tree.TreeNode;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class MYSQLrepository implements Repository{

    private Settings settings;
    private Connection connection;

    public MYSQLrepository(Settings settings) {
        this.settings = settings;
    }

    private void initConnection() throws SQLException, ClassNotFoundException{
        String ip = (String) settings.getParameter("mysql_ip");
        String database = (String) settings.getParameter("mysql_database");
        String username = (String) settings.getParameter("mysql_username");
        String password = (String) settings.getParameter("mysql_password");
        connection = DriverManager.getConnection("jdbc:mysql://"+ip+"/"+database,username,password);


    }

    private void closeConnection(){
        try{
            connection.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            connection = null;
        }
    }


    @Override
    public Node getSchema() {

        try{
            this.initConnection();

            DatabaseMetaData metaData = connection.getMetaData();
            InformationResource ir = new InformationResource("BP");

            String tableType[] = {"TABLE"};
            ResultSet tables = metaData.getTables(connection.getCatalog(), null, null, tableType);

            while (tables.next()){

                String tableName = tables.getString("TABLE_NAME");
                if(tableName.contains("trace"))continue;
                Entity newTable = new Entity(tableName, ir);
                ir.addChild(newTable);

                ResultSet columns = metaData.getColumns(connection.getCatalog(), null, tableName, null);

               // System.out.println(tableName);

                while (columns.next()){

                    String columnName = columns.getString("COLUMN_NAME");
                    String columnType = columns.getString("TYPE_NAME");

                    //System.out.println("\t"+ columns.getString("COLUMN_NAME"));

                    int columnSize = Integer.parseInt(columns.getString("COLUMN_SIZE"));
                    //System.out.println(columnType);
                    ResultSet pkeys = metaData.getPrimaryKeys(connection.getCatalog(), null, tableName);

//                    while (pkeys.next()){
//                        String pkColumnName = pkeys.getString("COLUMN_NAME");
//
//                        if(newTable.getChildByName(pkColumnName) != null){
//                            AttributeConstraint type =new AttributeConstraint(pkColumnName, (Attribute) newTable.getChildByName(pkColumnName), ConstraintType.PRIMARY_KEY);
//
//                            if(!((Attribute) newTable.getChildByName(pkColumnName)).getChildren().contains(type))
//                                ((Attribute) newTable.getChildByName(pkColumnName)).addChild(type);
//                        }
//
//                        System.out.println("\t\t"+pkeys.getString("COLUMN_NAME"));
//                    }
//
//                    ResultSet fkeys = metaData.getImportedKeys(connection.getCatalog(), null, tableName);
//
//                    while (fkeys.next()){
//                        String pkColumnName = fkeys.getString("FKCOLUMN_NAME");
//
//                        if(newTable.getChildByName(pkColumnName) != null){
//
//                            AttributeConstraint type =new AttributeConstraint(pkColumnName, (Attribute) newTable.getChildByName(pkColumnName), ConstraintType.FOREIGN_KEY);
//
//                            if(!((Attribute) newTable.getChildByName(pkColumnName)).getChildren().contains(type))
//                                ((Attribute) newTable.getChildByName(pkColumnName)).addChild(type);
//                        }
//
//                       System.out.println("\t\t\t"+fkeys.getString("FKCOLUMN_NAME"));
//                    }

                    //System.out.println(Arrays.stream(columnType.toUpperCase().split(" ")).collect(Collectors.joining("_")));

                    Attribute attribute = new Attribute(columnName, newTable,
                            AttributeType.valueOf(
                                    Arrays.stream(columnType.toUpperCase().split(" "))
                                            .collect(Collectors.joining("_"))),
                            columnSize);
                    newTable.addChild(attribute);


                    while (pkeys.next()){
                        String pkColumnName = pkeys.getString("COLUMN_NAME");

                        if(newTable.getChildByName(pkColumnName) != null){
                            AttributeConstraint type =new AttributeConstraint(pkColumnName, (Attribute) newTable.getChildByName(pkColumnName), ConstraintType.PRIMARY_KEY);

                            if(!((Attribute) newTable.getChildByName(pkColumnName)).getChildren().contains(type))
                                ((Attribute) newTable.getChildByName(pkColumnName)).addChild(type);
                        }

                       // System.out.println("\t\t"+pkeys.getString("COLUMN_NAME"));
                    }

                    ResultSet fkeys = metaData.getImportedKeys(connection.getCatalog(), null, tableName);

                    while (fkeys.next()){
                        String pkColumnName = fkeys.getString("FKCOLUMN_NAME");

                        if(newTable.getChildByName(pkColumnName) != null){

                            AttributeConstraint type =new AttributeConstraint(pkColumnName, (Attribute) newTable.getChildByName(pkColumnName), ConstraintType.FOREIGN_KEY);

                            if(!((Attribute) newTable.getChildByName(pkColumnName)).getChildren().contains(type))
                                ((Attribute) newTable.getChildByName(pkColumnName)).addChild(type);
                        }

                        //System.out.println("\t\t\t"+fkeys.getString("FKCOLUMN_NAME"));
                    }

                }



            }

            return ir;
        }
        catch (SQLException e1) {
            e1.printStackTrace();
        }
        catch (ClassNotFoundException e2){ e2.printStackTrace();}
        finally {
            this.closeConnection();
        }

        return null;
    }

    @Override
    public List<Row> get(String query) {

        List<Row> rows = new ArrayList<>();
        query =query.toLowerCase();
        if(query.contains("select")) {
            try {
                this.initConnection();

                //String query = "SELECT * FROM " + from;
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet rs = preparedStatement.executeQuery();
                ResultSetMetaData resultSetMetaData = rs.getMetaData();

                while (rs.next()) {

                    Row row = new Row();
                    //row.setName(from);

                    for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                        row.addField(resultSetMetaData.getColumnName(i), rs.getString(i));
                    }
                    rows.add(row);


                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                this.closeConnection();
            }
        }else{
            try{
                this.initConnection();

                //String query = upit;
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                if(!preparedStatement.execute()) {
                    InformationResource ir = (InformationResource) ((TreeNode) MainFrame.getInstance().getJTree().getModel().getRoot()).getNode();
                    String from = "";
                    for (Node n: ir.getChildren()) {
                        if(query.contains(n.getName()))
                            from = n.getName();
                    }
                    System.out.println(from);
                    PreparedStatement preparedStatement2 = connection.prepareStatement("SELECT * FROM " + from);
                    ResultSet rs = preparedStatement2.executeQuery();
                    ResultSetMetaData resultSetMetaData = rs.getMetaData();

                    while (rs.next()) {

                        Row row = new Row();
                        row.setName(from);

                        for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                            row.addField(resultSetMetaData.getColumnName(i), rs.getString(i));
                        }
                        rows.add(row);


                    }
                }else System.out.println("nije prosao");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                this.closeConnection();
            }

        }

        return rows;
    }

    public List<Row> daj(String from, String upit) {

        List<Row> rows = new ArrayList<>();


        try{
            this.initConnection();

            String query = upit;
            PreparedStatement preparedStatement = connection.prepareStatement(upit);
            if(!preparedStatement.execute()) {
                PreparedStatement preparedStatement2 = connection.prepareStatement("SELECT * FROM " + from);
                ResultSet rs = preparedStatement2.executeQuery();
                ResultSetMetaData resultSetMetaData = rs.getMetaData();

                while (rs.next()) {

                    Row row = new Row();
                    row.setName(from);

                    for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                        row.addField(resultSetMetaData.getColumnName(i), rs.getString(i));
                    }
                    rows.add(row);


                }
            }else System.out.println("nije prosao");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            this.closeConnection();
        }

        return rows;
    }

}
