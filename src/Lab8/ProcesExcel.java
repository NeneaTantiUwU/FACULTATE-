package Lab8;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

public class ProcesExcel {

    public static void main(String[] args) {
        String fisierIntrare = "laborator8_input.xlsx";

        System.out.println("--- 8.5.1: Citire ---");
        citesteSiAfiseaza(fisierIntrare);

        System.out.println("\n--- 8.5.2: Calcul Java (Output 2) ---");
        proceseazaSiCalculeazaMedia(fisierIntrare, "laborator8_output2.xlsx");

        System.out.println("\n--- 8.5.3: Formula Excel (Output 3) ---");
        genereazaFisierCuFormula(fisierIntrare, "laborator8_output3.xlsx");
    }

    public static void citesteSiAfiseaza(String numeFisier) {
        try (FileInputStream fis = new FileInputStream(new File(numeFisier));
             XSSFWorkbook workbook = new XSSFWorkbook(fis)) {

            XSSFSheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                for (Cell cell : row) {
                    // Folosim DataFormatter pentru a citi celula exact cum apare in Excel
                    DataFormatter formatter = new DataFormatter();
                    System.out.print(formatter.formatCellValue(cell) + "\t");
                }
                System.out.println();
            }
        } catch (IOException e) {
            System.err.println("Eroare la citire: " + e.getMessage());
        }
    }

    public static void proceseazaSiCalculeazaMedia(String fisierIntrare, String fisierIesire) {
        try (FileInputStream fis = new FileInputStream(new File(fisierIntrare));
             XSSFWorkbook inputWorkbook = new XSSFWorkbook(fis);
             XSSFWorkbook outputWorkbook = new XSSFWorkbook()) {

            XSSFSheet inputSheet = inputWorkbook.getSheetAt(0);
            XSSFSheet outputSheet = outputWorkbook.createSheet("Medii");

            for (int i = 0; i <= inputSheet.getLastRowNum(); i++) {
                Row inRow = inputSheet.getRow(i);
                if (inRow == null) continue;
                Row outRow = outputSheet.createRow(i);

                double suma = 0;
                int noteGasite = 0;

                for (int j = 0; j < inRow.getLastCellNum(); j++) {
                    Cell inCell = inRow.getCell(j);
                    Cell outCell = outRow.createCell(j);
                    if (inCell == null) continue;

                    // Copiere valori
                    if (inCell.getCellType() == CellType.NUMERIC) {
                        double val = inCell.getNumericCellValue();
                        outCell.setCellValue(val);
                        // Daca e rand de date (nu header) si coloana e >= 3 (Notele)
                        if (i > 0 && j >= 3) {
                            suma += val;
                            noteGasite++;
                        }
                    } else if (inCell.getCellType() == CellType.STRING) {
                        outCell.setCellValue(inCell.getStringCellValue());
                    }
                }

                // Adaugare medie (Java)
                Cell mediaCell = outRow.createCell(inRow.getLastCellNum());
                if (i == 0) mediaCell.setCellValue("Media Java");
                else if (noteGasite > 0) mediaCell.setCellValue(suma / noteGasite);
            }

            try (FileOutputStream fos = new FileOutputStream(fisierIesire)) {
                outputWorkbook.write(fos);
            }
            System.out.println("Fisier generat: " + fisierIesire);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void genereazaFisierCuFormula(String fisierIntrare, String fisierIesire) {
        try (FileInputStream fis = new FileInputStream(new File(fisierIntrare));
             XSSFWorkbook inputWorkbook = new XSSFWorkbook(fis);
             XSSFWorkbook outputWorkbook = new XSSFWorkbook()) {

            XSSFSheet inputSheet = inputWorkbook.getSheetAt(0);
            XSSFSheet outputSheet = outputWorkbook.createSheet("Formule");

            for (int i = 0; i <= inputSheet.getLastRowNum(); i++) {
                Row inRow = inputSheet.getRow(i);
                if (inRow == null) continue;
                Row outRow = outputSheet.createRow(i);

                int lastCol = 0;
                for (int j = 0; j < inRow.getLastCellNum(); j++) {
                    Cell inCell = inRow.getCell(j);
                    Cell outCell = outRow.createCell(j);
                    if (inCell == null) continue;

                    if (inCell.getCellType() == CellType.NUMERIC) outCell.setCellValue(inCell.getNumericCellValue());
                    else if (inCell.getCellType() == CellType.STRING) outCell.setCellValue(inCell.getStringCellValue());
                    lastCol = j;
                }

                Cell formulaCell = outRow.createCell(lastCol + 1);
                if (i == 0) {
                    formulaCell.setCellValue("Media Formula");
                } else {
                    // Randul in Excel este index + 1
                    int r = i + 1;
                    // Formula: AVERAGE(D2:F2)
                    formulaCell.setCellFormula("AVERAGE(D" + r + ":F" + r + ")");
                }
            }

            try (FileOutputStream fos = new FileOutputStream(fisierIesire)) {
                outputWorkbook.write(fos);
            }
            System.out.println("Fisier generat: " + fisierIesire);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}