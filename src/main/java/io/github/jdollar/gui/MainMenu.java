package io.github.jdollar.gui;

import io.github.jdollar.parser.BillBoardParser;
import io.github.jdollar.writer.MsExcelWriter;
import javafx.util.Pair;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by jdollar on 3/22/2015.
 */
public class MainMenu extends JFrame {
    private JCheckBox billboardTop10CheckBox = new JCheckBox();
    private JCheckBox billboardTop100CheckBox = new JCheckBox();
    private JCheckBox ariaTop10CheckBox = new JCheckBox();
    private JCheckBox ariaTop50CheckBox = new JCheckBox();
    private JButton grabExcelDataButton = new JButton();
    private JButton exitButton = new JButton();
    private JPanel hiddenJPanel = new JPanel();
    private JPanel mainJPanel = new JPanel();
    private JPanel checkboxJPanel = new JPanel();
    private JPanel buttonJPanel = new JPanel();
    private JPanel titleJPanel = new JPanel();

    public MainMenu() {
        super("Main Menu");
        hiddenJPanel.setPreferredSize(new Dimension(500, 500));
        setContentPane(hiddenJPanel);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        grabExcelDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Map<Integer, Pair<String, String>>> songInformationList = new ArrayList<Map<Integer, Pair<String, String>>>();
                List<String> sheetNames = new ArrayList<String>();
                List<List<String>> sheetHeaderColumns = new ArrayList<List<String>>();
                Map<Integer, Pair<String, String>> songInformation;

                if (billboardTop10CheckBox.isSelected()) {
                    songInformation = BillBoardParser.getTop10Songs();
                    sheetNames.add("BillBoard Top 10");
                    sheetHeaderColumns.add(Arrays.asList(new String[]{"Position", "Title", "Artist"}));
                    songInformationList.add(songInformation);
                }

                if (billboardTop100CheckBox.isSelected()) {
                    songInformation = BillBoardParser.getTop100Songs();
                    sheetNames.add("BillBoard Top 100");
                    sheetHeaderColumns.add(Arrays.asList(new String[]{"Position", "Title", "Artist"}));
                    songInformationList.add(songInformation);
                }

                if (ariaTop10CheckBox.isSelected()) {
                    songInformation = BillBoardParser.getTop10AusieSongs();
                    sheetNames.add("Aria Top 10");
                    sheetHeaderColumns.add(Arrays.asList(new String[]{"Position", "Title", "Artist"}));
                    songInformationList.add(songInformation);
                }

                if (ariaTop50CheckBox.isSelected()) {
                    songInformation = BillBoardParser.getTop50AusieSongs();
                    sheetNames.add("Aria Top 50");
                    sheetHeaderColumns.add(Arrays.asList(new String[]{"Position", "Title", "Artist"}));
                    songInformationList.add(songInformation);
                }

                if (songInformationList != null && !songInformationList.isEmpty()) {
                    MsExcelWriter.generateExcelFile(songInformationList, sheetNames, sheetHeaderColumns, new File(System.getProperty("user.home") + "\\topHitsProgram\\"));
                }
            }
        });
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }
}
