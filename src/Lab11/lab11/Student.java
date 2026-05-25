package Lab11.lab11;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Student {
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

    public Student(int numărMatricol, String prenume, String nume, String formațieDeStudiu) {
        this.numărMatricol = numărMatricol;
        this.prenume = prenume;
        this.nume = nume;
        this.formațieDeStudiu = formațieDeStudiu;
        this.nota = 0.0;
    }

    public int getNumarMatricol() {
        return this.numărMatricol;
    }

    public String getPrenume() {
        return this.prenume;
    }

    public String getNume() {
        return this.nume;
    }

    public String getFormatieDeStudiu() {
        return this.formațieDeStudiu;
    }

    public double getNota() {
        return this.nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    public static void exportaStudentiExcel(List<Student> lista, String numeFisier) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Studenti");
        Row header = sheet.createRow(0);
        String[] coloane = new String[]{"Nr. Matricol", "Nume", "Prenume", "Grupa", "Nota"};

        for(int i = 0; i < coloane.length; ++i) {
            Cell cell = header.createCell(i);
            cell.setCellValue(coloane[i]);
        }

        int rowIdx = 1;

        for(Student s : lista) {
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue((double)s.getNumarMatricol());
            row.createCell(1).setCellValue(s.getNume());
            row.createCell(2).setCellValue(s.getPrenume());
            row.createCell(3).setCellValue(s.getFormatieDeStudiu());
            row.createCell(4).setCellValue(s.getNota());
        }

        try (FileOutputStream fileOut = new FileOutputStream(numeFisier)) {
            workbook.write(fileOut);
            workbook.close();
            System.out.println("Export XLS finalizat cu succes: " + numeFisier);
        } catch (IOException e) {
            System.err.println("Eroare la exportul Excel: " + e.getMessage());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            Student student = (Student)o;
            return this.prenume.equals(student.prenume) && this.nume.equals(student.nume) && this.formațieDeStudiu.equals(student.formațieDeStudiu);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.prenume, this.nume, this.formațieDeStudiu, this.nota);
    }

    public static void citireFisier(String fisier, List<Student> lista) throws FileNotFoundException {
        try {
            Scanner sc = new Scanner(Paths.get(fisier));

            while(sc.hasNextLine()) {
                String linie = sc.nextLine();
                String[] date = linie.split(",");
                if (date.length == 4) {
                    try {
                        int nrMatricol = Integer.parseInt(date[0]);
                        String nume = date[1];
                        String prenume = date[2];
                        String formatie = date[3];
                        Student student_nou = new Student(nrMatricol, prenume, nume, formatie);
                        lista.add(student_nou);
                    } catch (NumberFormatException var10) {
                        System.out.println("numar matricol invalid la linia " + linie);
                    }
                } else {
                    System.out.println("Date incorecte la linia " + linie);
                }
            }
        } catch (IOException var11) {
            System.out.println("Eroare la citirea fisierului " + fisier);
        }
    }

    public static void scriereFisier(List<Student> studenti, String fileName) throws FileNotFoundException {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(fileName))) {
            for(Student s : studenti) {
                int var10000 = s.getNumarMatricol();
                String linie = var10000 + "," + s.getNume() + "," + s.getPrenume() + "," + s.getFormatieDeStudiu();
                writer.write(linie);
                writer.newLine();
            }
        } catch (IOException var8) {
            System.out.println("Eroare la scrierea in fisier");
        }
    }

    public static void alocareNote(String filename, List<Student> listaStudenti, HashMap<Integer, Student> index_studenti) throws FileNotFoundException {
        for(Student s : listaStudenti) {
            index_studenti.put(s.getNumarMatricol(), s);
        }

        try (Scanner sc = new Scanner(Paths.get(filename))) {
            while(sc.hasNextLine()) {
                String linie = sc.nextLine();
                if (!linie.trim().isEmpty()) {
                    String[] date = linie.split(",");
                    if (date.length == 2) {
                        try {
                            int nrMatricol = Integer.parseInt(date[0].trim());
                            double nota = Double.parseDouble(date[1].trim());
                            Student student_cautat = index_studenti.get(nrMatricol);
                            if (student_cautat != null) {
                                student_cautat.setNota(nota);
                            }
                        } catch (NumberFormatException var11) {
                            System.out.println("numar matricol sau nota invalida");
                        }
                    }
                }
            }
        } catch (IOException var13) {
            System.out.println("Eroarea la citirea " + filename);
        }
    }

    public static double gasesteNota(String nume, String prenume, Map<Integer, Student> ultim_map) {
        HashMap<String, Student> index_dupa_nume = new HashMap<>();

        for(Student s : ultim_map.values()) {
            String var10000 = s.getPrenume();
            String cheie = var10000 + "-" + s.getNume();
            index_dupa_nume.put(cheie, s);
        }

        String cheie_cautare = prenume + "-" + nume;
        Student gasit = index_dupa_nume.get(cheie_cautare);
        return gasit != null ? gasit.getNota() : 0.0;
    }

    public static Student mutaInFormatie(Student s, String nouaFormatie) {
        return new Student(s.getNumarMatricol(), s.getPrenume(), s.getNume(), nouaFormatie, s.getNota());
    }

    public static List<List<Student>> divideInGrupuri(List<Student> listaInitiala, String numeG1, String numeG2) {
        List<Student> g1 = new ArrayList<>();
        List<Student> g2 = new ArrayList<>();
        int total = listaInitiala.size();
        int prag = (total + 1) / 2;

        for(int i = 0; i < total; ++i) {
            if (i < prag) {
                g1.add(mutaInFormatie(listaInitiala.get(i), numeG1));
            } else {
                g2.add(mutaInFormatie(listaInitiala.get(i), numeG2));
            }
        }

        return Arrays.asList(g1, g2);
    }

    public static void writeToXls(List<Student> studenti, String xlsFileName) {
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = workbook.createSheet("Lista Studenti");
            String[] headers = new String[]{"Nr. Matricol", "Nume", "Prenume", "Formatie de Studiu", "Nota"};
            Row headerRow = sheet.createRow(0);

            for(int i = 0; i < headers.length; ++i) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }

            int rowIdx = 1;

            for(Student s : studenti) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue((double)s.getNumarMatricol());
                row.createCell(1).setCellValue(s.getNume());
                row.createCell(2).setCellValue(s.getPrenume());
                row.createCell(3).setCellValue(s.getFormatieDeStudiu());
                row.createCell(4).setCellValue(s.getNota());
            }

            try (FileOutputStream out = new FileOutputStream(new File(xlsFileName))) {
                workbook.write(out);
                System.out.println("s-a creeat fisierul " + xlsFileName);
            }
        } catch (IOException var14) {
            System.out.println("nu s-a putut creea fisierul: " + xlsFileName);
        }
    }

    public static List<Student> readFromXls(String fileName) {
        List<Student> listaStudenti = new ArrayList<>();

        try (
                FileInputStream fis = new FileInputStream(new File(fileName));
                XSSFWorkbook workbook = new XSSFWorkbook(fis);
        ) {
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            if (rowIterator.hasNext()) {
                rowIterator.next();
            }

            while(rowIterator.hasNext()) {
                Row row = rowIterator.next();
                int nrMatricol = (int)row.getCell(0).getNumericCellValue();
                String nume = row.getCell(1).getStringCellValue();
                String prenume = row.getCell(2).getStringCellValue();
                String formatie = row.getCell(3).getStringCellValue();
                double nota = row.getCell(4).getNumericCellValue();
                listaStudenti.add(new Student(nrMatricol, prenume, nume, formatie, nota));
            }
        } catch (IOException e) {
            System.out.println("fisierul nu poate fi deschis " + e.getMessage());
        }

        return listaStudenti;
    }
}
