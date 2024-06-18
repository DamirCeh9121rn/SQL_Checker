import view.AppCore;
import view.MainFrame;

public class Main {

    public static void main(String[] args) {
        AppCore appCore = new AppCore();
        MainFrame m = MainFrame.getInstance();
        m.setAppCore(appCore);
        m.setLocationRelativeTo(null);
        m.setVisible(true);
    }

}
