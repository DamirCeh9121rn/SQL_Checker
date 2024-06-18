package controller.action;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActionManager {

    BulkImportAction bulkImportAction;
    ResultSetExportAction resultSetExportAction;
    PrettyAction prettyAction;
    QueryCheckerAction queryCheckerAction;

    public ActionManager(){
        initialiseActions();
    }

    private void initialiseActions(){
        bulkImportAction = new BulkImportAction();
        resultSetExportAction = new ResultSetExportAction();
        prettyAction = new PrettyAction();
        queryCheckerAction = new QueryCheckerAction();
    }

}
