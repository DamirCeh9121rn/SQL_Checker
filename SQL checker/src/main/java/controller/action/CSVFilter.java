package controller.action;

import javax.swing.filechooser.FileFilter;
import java.io.File;

public class CSVFilter extends FileFilter {
    @Override
    public boolean accept(File f) {
        return (f.isDirectory() ||
                f.getName().toLowerCase().endsWith(".csv"));
    }

    @Override
    public String getDescription() {
        return "*.csv";
    }
}
