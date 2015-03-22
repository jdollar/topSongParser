package io.github.jdollar.writer;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

/**
 * Created by jdollar on 3/21/2015.
 */
public class MsExcelWriter {
    public static void generateExcelFile(List<Map<String, String>> songInformation, List<String> sheetName, List<List<String>> headerTitles, File folderToSave) {
        try {
            HSSFWorkbook workbook = new HSSFWorkbook();
            for (Map<String, String> songInformationEntry : songInformation) {
                HSSFSheet sheet = workbook.createSheet(sheetName.get(songInformation.indexOf(songInformationEntry)));

                sheet.createFreezePane(0, 1);
                Row headerRow = sheet.createRow(0);

                int cellCount = 0;
                Cell cell;
                for (String headerTitle : headerTitles.get(songInformation.indexOf(songInformationEntry))) {
                    if (headerTitle != null) {
                        cell = headerRow.createCell(cellCount);
                        cell.setCellValue(headerTitle);
                    }

                    cellCount++;
                }


                int songPosition = 1;
                for (Map.Entry<String, String> songEntry : songInformationEntry.entrySet()) {
                    Row row = sheet.createRow(songPosition);
                    cell = row.createCell(0);
                    cell.setCellValue(songPosition);
                    cell = row.createCell(1);
                    cell.setCellValue(songEntry.getKey());
                    cell = row.createCell(2);
                    cell.setCellValue(songEntry.getValue());

                    songPosition++;
                }

                sheet.autoSizeColumn(0);
                sheet.autoSizeColumn(1);
                sheet.autoSizeColumn(2);
            }

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date today = new Date();
            String formattedDate = simpleDateFormat.format(today);

            if (folderToSave == null) {
                folderToSave = new File(System.getProperty("user.home") + "\\topHitsProgram\\");
            }

            if(!folderToSave.exists()) {
                folderToSave.mkdirs();
            }

            FileOutputStream out = new FileOutputStream(new File(folderToSave.getPath() + "\\" + formattedDate + "_topHits.xls"));
            workbook.write(out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
