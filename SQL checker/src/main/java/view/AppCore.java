package view;

import controller.cheacker.Cheacker;
import controller.cheacker.CheackerImpl;
import controller.cheacker.ForeignKeyCheck;
import controller.cheacker.MandatoryPartsQueryCheck;
import controller.database.*;
import lombok.Getter;
import lombok.Setter;
import model.implementation.InformationResource;
import view.tree.Tree;
import view.tree.TreeImplementation;

import javax.swing.tree.DefaultTreeModel;
@Getter
@Setter
public class AppCore {
    private Database database;
    private Settings settings;
    private TableModel tableModel;
    private DefaultTreeModel defaultTreeModel;
    private Tree tree;
    private Cheacker cheacker;


    public AppCore() {
        this.settings = initSettings();
        this.database = new DatabaseImplementation(new MYSQLrepository(this.settings));
        this.tableModel = new TableModel();
        this.tree = new TreeImplementation();
        this.cheacker = new CheackerImpl();
    }

    private Settings initSettings() {
        Settings settingsImplementation = new SettingsImplementation();
        settingsImplementation.addParameter("mysql_ip", Constants.MYSQL_IP);
        settingsImplementation.addParameter("mysql_database", Constants.MYSQL_DATABASE);
        settingsImplementation.addParameter("mysql_username", Constants.MYSQL_USERNAME);
        settingsImplementation.addParameter("mysql_password", Constants.MYSQL_PASSWORD);
        return settingsImplementation;
    }


    public DefaultTreeModel loadResource(){
        InformationResource ir = (InformationResource) this.database.loadResource();
        return this.tree.generateTree(ir);
    }

    public void readDataFromTable(String fromTable){
        if(MainFrame.getInstance().getAppCore().getCheacker().Check(fromTable))
            tableModel.setRows(this.database.readDataFromTable(fromTable));

    }
    public void citajDataFromTable(String fromTable, String upit){
        //if(MainFrame.getInstance().getAppCore().getCheacker().Check(new ForeignKeyCheck()))
            tableModel.setRows(this.database.citajDataFromTable(fromTable, upit));

    }

}
