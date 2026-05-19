import java.io.*;
import java.nio.file.Paths;
import java.util.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

class Student {
    public final int numărMatricol;
    public final String prenume;
    public final String nume;
    public final String formațieDeStudiu;
    public double nota;

    public Student(int numărMatricol, String prenume, String nume, String formațieDeStudiu, double nota) {
        this.numărMatricol = numărMatricol;
        this.prenume = prenume;
        this.nume = nume;
        this.formațieDeStudiu = formațieDeStudiu;
        this.nota = nota;
    }

    public int getNumarMatricol() { return numărMatricol; }
    public String getPrenume() { return prenume; }
    public String getNume() { return nume; }
    public String getFormatieDeStudiu() { return formațieDeStudiu; }
    public double getNota() { return nota; }

    @Override
    public String toString() {
        return String.format("Student[Matricol: %d, Nume: %s %s, Grupa: %s, Nota: %.2f]",
                numărMatricol, nume, prenume, formațieDeStudiu, nota);
    }
}

interface IStudentiExport {
    void doExport(List<Student> studenti);
}

interface IStudentiImport {
    List<Student> doImport();
}

class Exporter {
    void startExport(IStudentiExport strategyInstance, List<Student> students) {
        strategyInstance.doExport(students);
    }
}

class Importer {
    List<Student> startImport(IStudentiImport strategyInstance) {
        return strategyInstance.doImport();
    }
}

class StudentiInConsola implements IStudentiExport {
    @Override
    public void doExport(List<Student> studenti) {
        System.out.println("\n--- [Strategy] Afișare Studenți în Consolă ---");
        for (Student student : studenti) {
            System.out.println(student);
        }
    }
}

class StudentiInFiserText implements IStudentiExport {
    private String fileName;

    public StudentiInFiserText(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void doExport(List<Student> studenti) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            for (Student s : studenti) {
                writer.printf("%d,%s,%s,%s,%.2f%n",
                        s.getNumarMatricol(), s.getPrenume(), s.getNume(), s.getFormatieDeStudiu(), s.getNota());
            }
            System.out.println("[OK] Export TXT finalizat cu succes în: " + fileName);
        } catch (IOException e) {
            System.err.println("Eroare export TXT: " + e.getMessage());
        }
    }
}

class StudentiInFisierXlsx implements IStudentiExport {
    private String fileName;

    public StudentiInFisierXlsx(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void doExport(List<Student> studenti) {
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = workbook.createSheet("Studenti");

            // Generare rând Header
            Row header = sheet.createRow(0);
            String[] coloane = {"Nr. Matricol", "Nume", "Prenume", "Grupa", "Nota"};
            for (int i = 0; i < coloane.length; i++) {
                header.createCell(i).setCellValue(coloane[i]);
            }

            // Adăugare date studenți
            int rowIdx = 1;
            for (Student s : studenti) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(s.getNumarMatricol());
                row.createCell(1).setCellValue(s.getNume());
                row.createCell(2).setCellValue(s.getPrenume());
                row.createCell(3).setCellValue(s.getFormatieDeStudiu());
                row.createCell(4).setCellValue(s.getNota());
            }

            try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
                workbook.write(fileOut);
                System.out.println("[OK] Export XLSX finalizat cu succes în: " + fileName);
            }
        } catch (IOException e) {
            System.err.println("Eroare export XLSX: " + e.getMessage());
        }
    }
}

class StudentiDinFiserText implements IStudentiImport {
    private String fileName;

    public StudentiDinFiserText(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Student> doImport() {
        List<Student> lista = new ArrayList<>();
        try (Scanner sc = new Scanner(Paths.get(fileName))) {
            while (sc.hasNextLine()) {
                String linie = sc.nextLine().trim();
                if (linie.isEmpty()) continue;

                String[] date = linie.split(",");
                if (date.length == 5) {
                    int matricol = Integer.parseInt(date[0].trim());
                    String prenume = date[1].trim();
                    String nume = date[2].trim();
                    String grupa = date[3].trim();
                    double nota = Double.parseDouble(date[4].trim());

                    lista.add(new Student(matricol, prenume, nume, grupa, nota));
                }
            }
            System.out.println("[OK] Import din TXT finalizat. Studenți încărcați: " + lista.size());
        } catch (Exception e) {
            System.err.println("Eroare import TXT: " + e.getMessage());
        }
        return lista;
    }
}

class StudentiDinFiserXlsx implements IStudentiImport {
    private String fileName;

    public StudentiDinFiserXlsx(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Student> doImport() {
        List<Student> lista = new ArrayList<>();
        try (FileInputStream fileIn = new FileInputStream(fileName);
             XSSFWorkbook workbook = new XSSFWorkbook(fileIn)) {

            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            if (rowIterator.hasNext()) {
                rowIterator.next();
            }

            // Parcurgere rânduri Excel
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                int matricol = (int) row.getCell(0).getNumericCellValue();
                String nume = row.getCell(1).getStringCellValue();
                String prenume = row.getCell(2).getStringCellValue();
                String grupa = row.getCell(3).getStringCellValue();
                double nota = row.getCell(4).getNumericCellValue();

                lista.add(new Student(matricol, prenume, nume, grupa, nota));
            }
            System.out.println("[OK] Import din XLSX finalizat. Studenți încărcați: " + lista.size());
        } catch (Exception e) {
            System.err.println("Eroare import XLSX: " + e.getMessage());
        }
        return lista;
    }
}

public class AplicatieCuStrategy {
    public static void main(String[] args) {
        // Datele oficiale primite în cerința din imagine
        List<Student> studenti = Arrays.asList(
                new Student(1025, "Andrei", "Popa", "ISM141/2", 8.70),
                new Student(1024, "Ioan", "Mihalcea", "ISM141/1", 10.0),
                new Student(1026, "Anamaria", "Prodan", "TI131/1", 8.90),
                new Student(1029, "Bianca", "Popescu", "TI131/1", 10.0),
                new Student(1029, "Maria", "Pana", "TI131/2", 4.10),
                new Student(1029, "Gabriela", "Mohanu", "TI131/2", 7.33),
                new Student(1029, "Marius", "Nasta", "TI131/2", 3.20),
                new Student(1029, "Marius", "Nasta", "TI131/1", 5.12),
                new Student(1029, "Andrei", "Dobrescu", "TI131/2", 2.22)
        );

        Exporter exporter = new Exporter();

        // Rulare a) Afișare în consolă
        IStudentiExport strategyConsole = new StudentiInConsola();
        exporter.startExport(strategyConsole, studenti);

        // Rulare b) Export în fișier TXT
        String fisierTxt = "studentiStrategyText.txt";
        IStudentiExport strategyText = new StudentiInFiserText(fisierTxt);
        exporter.startExport(strategyText, studenti);

        // Rulare c) Export în fișier XLSX
        String fisierXlsx = "studentiStrategyExcel.xlsx";
        IStudentiExport strategyExcel = new StudentiInFisierXlsx(fisierXlsx);
        exporter.startExport(strategyExcel, studenti);

        System.out.println("\n-------------------------------------------------------");

        Importer importer = new Importer();

        // Rulare d) Citire din fișier TXT
        IStudentiImport importTextStrategy = new StudentiDinFiserText(fisierTxt);
        List<Student> listaDinTxt = importer.startImport(importTextStrategy);

        // Rulare e) Citire din fișier XLSX
        IStudentiImport importXlsxStrategy = new StudentiDinFiserXlsx(fisierXlsx);
        List<Student> listaDinXlsx = importer.startImport(importXlsxStrategy);

        // Scurtă verificare în consolă a datelor importate prin strategii
        System.out.println("\nVerificare obiect re-creat din XLSX: " + listaDinXlsx.get(0));
    }
}