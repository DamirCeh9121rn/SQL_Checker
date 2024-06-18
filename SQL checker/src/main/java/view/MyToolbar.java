package view;

import javax.swing.*;

public class MyToolbar extends JToolBar {

    public MyToolbar(){
        super(HORIZONTAL);
        setFloatable(false);

        add(MainFrame.getInstance().getActionManager().getBulkImportAction());
        add(MainFrame.getInstance().getActionManager().getResultSetExportAction());
        add(MainFrame.getInstance().getActionManager().getPrettyAction());
        add(MainFrame.getInstance().getActionManager().getQueryCheckerAction());
    }


}
