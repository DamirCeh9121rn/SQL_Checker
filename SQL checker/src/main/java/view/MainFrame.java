package view;

import controller.action.ActionManager;
import lombok.Getter;
import lombok.Setter;
import view.tree.SelectionListener;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;

@Getter
@Setter
public class MainFrame extends JFrame {

    private static MainFrame instance = null;

    AppCore appCore;
    private ActionManager actionManager;

    private JScrollPane jScrollPane;
    private JPanel rightPanel;
    private JPanel pomPanel;
    private JSplitPane splitPane;

    private JTextPane textPane;
    private JScrollPane textAreaPanel;
    private JTable jTable;
    private JScrollPane paneTable;
    private MyToolbar toolbar;

    private JTree jTree;


    private MainFrame(){

    }

    public static MainFrame getInstance() {
        if(instance == null){
            instance = new MainFrame();
            instance.initialise();
        }
        return instance;
    }

    private void initialise(){
        this.setMinimumSize(new Dimension(1000, 600));
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        actionManager = new ActionManager();

        toolbar = new MyToolbar();
        textPane = new JTextPane();
        textPane.setMaximumSize(new Dimension(700,250));
        textPane.setMaximumSize(new Dimension(700,250));

        textAreaPanel = new JScrollPane(textPane);
        textAreaPanel.setMaximumSize(new Dimension(700,250));
        textAreaPanel.setMinimumSize(new Dimension(700,250));

        jTable = new JTable();
        paneTable = new JScrollPane(jTable);
        textAreaPanel.setMinimumSize(new Dimension(700,300));
        paneTable.setMaximumSize(new Dimension(700,200));

        pomPanel = new JPanel();
        pomPanel.setBackground(Color.darkGray);
        pomPanel.setLayout(new BoxLayout(pomPanel, BoxLayout.Y_AXIS));
        pomPanel.add(Box.createVerticalStrut(20));
        pomPanel.add(textAreaPanel);
        pomPanel.add(Box.createVerticalStrut(20));
        pomPanel.add(paneTable);
        pomPanel.add(Box.createVerticalStrut(20));

        rightPanel = new JPanel(new BorderLayout());

        rightPanel.add(toolbar, BorderLayout.NORTH);
        rightPanel.add(pomPanel, BorderLayout.CENTER);

        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, jScrollPane,rightPanel);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerSize(7);
        add(splitPane, BorderLayout.CENTER);
    }


    public void setAppCore(AppCore appCore) {
        this.appCore = appCore;
        this.jTable.setModel(appCore.getTableModel());
        initialiseTree();
    }
    private void initialiseTree() {
        DefaultTreeModel defaultTreeModel = appCore.loadResource();
        jTree = new JTree(defaultTreeModel);
        jTree.addTreeSelectionListener(new SelectionListener());
        jScrollPane = new JScrollPane(jTree, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
        jScrollPane.setMinimumSize(new Dimension(200,200));
        add(jScrollPane, BorderLayout.WEST);

        pack();
    }

}
