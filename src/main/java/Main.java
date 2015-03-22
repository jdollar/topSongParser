import io.github.jdollar.gui.MainMenu;
import io.github.jdollar.parser.BillBoardParser;
import io.github.jdollar.writer.MsExcelWriter;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by jdollar on 3/21/2015.
 */
public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MainMenu mainMenu = new MainMenu();
            }
        });
    }
}
