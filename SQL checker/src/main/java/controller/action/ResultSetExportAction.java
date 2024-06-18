package controller.action;

import com.opencsv.CSVWriter;
import view.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class ResultSetExportAction extends MyAbstractAction{

    public ResultSetExportAction(){
        putValue(NAME, "Result set export");
        putValue(SHORT_DESCRIPTION, "Result set export");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(MainFrame.getInstance().getJTable().getRowCount() != 0 && MainFrame.getInstance().getJTable().getColumnCount() !=0){

            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.setFileFilter(new CSVFilter());

            File file = null;
            if(jFileChooser.showSaveDialog(MainFrame.getInstance())==JFileChooser.APPROVE_OPTION)
                file = new File(jFileChooser.getSelectedFile().toString()+".csv");
            else return;


            String[] header = new String[MainFrame.getInstance().getJTable().getColumnCount()];
            for(int i = 0; i< MainFrame.getInstance().getJTable().getColumnCount(); i++){
                header[i] = MainFrame.getInstance().getJTable().getColumnName(i);
            }

            try {
                FileWriter outputfile = new FileWriter(file);

                CSVWriter writer = new CSVWriter(outputfile, ',', CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);

                writer.writeNext(header);
                String[] data = new String[MainFrame.getInstance().getJTable().getColumnCount()];
                for(int j = 0; j <MainFrame.getInstance().getJTable().getRowCount(); j++ ) {
                    for (int i = 0; i < MainFrame.getInstance().getJTable().getColumnCount(); i++) {
                        if( MainFrame.getInstance().getJTable().getValueAt(j,i) != null)
                            data[i] = MainFrame.getInstance().getJTable().getValueAt(j,i).toString();
                    }
                    writer.writeNext(data);
                }

                writer.close();
            }
            catch (IOException f) {
                f.printStackTrace();
            }
        }
    }
}
