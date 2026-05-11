import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

class Student {
    public final int numărMatricol;
    public final String prenume;
    public final String nume;
    public final String formațieDeStudiu;
    public double nota;

    // Constructor complet
    public Student(int numărMatricol, String prenume, String nume, String formațieDeStudiu, double nota) {
        this.numărMatricol = numărMatricol;
        this.prenume = prenume;
        this.nume = nume;
        this.formațieDeStudiu = formațieDeStudiu;
        this.nota = nota;
    }

    // Constructor fără notă (default 0.0)
    public Student(int numărMatricol, String prenume, String nume, String formațieDeStudiu) {
        this.numărMatricol = numărMatricol;
        this.prenume = prenume;
        this.nume = nume;
        this.formațieDeStudiu = formațieDeStudiu;
        this.nota = 0.0;
    }

    // Getters și Setters
    public int getNumarMatricol() { return numărMatricol; }
    public String getPrenume() { return prenume; }
    public String getNume() { return nume; }
    public String getFormatieDeStudiu() { return formațieDeStudiu; }
    public double getNota() { return nota; }
    public void setNota(double nota) { this.nota = nota; }

    // Metoda toString este esențială pentru System.out.println
    @Override
    public String toString() {
        return String.format("Student[Matricol: %d, Nume: %s %s, Grupa: %s, Nota: %.2f]",
                numărMatricol, nume, prenume, formațieDeStudiu, nota);
    }

    // --- METODE DE GESTIUNE FIȘIERE ---

    public static void exportaStudentiExcel(List<Student> lista, String numeFisier) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Studenti");
        Row header = sheet.createRow(0);
        String[] coloane = {"Nr. Matricol", "Nume", "Prenume", "Grupa", "Nota"};
        for (int i = 0; i < coloane.length; i++) {
            header.createCell(i).setCellValue(coloane[i]);
        }
        int rowIdx = 1;
        for (Student s : lista) {
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(s.getNumarMatricol());
            row.createCell(1).setCellValue(s.getNume());
            row.createCell(2).setCellValue(s.getPrenume());
            row.createCell(3).setCellValue(s.getFormatieDeStudiu());
            row.createCell(4).setCellValue(s.getNota());
        }
        try (FileOutputStream fileOut = new FileOutputStream(numeFisier)) {
            workbook.write(fileOut);
            workbook.close();
            System.out.println("Export XLS finalizat: " + numeFisier);
        } catch (IOException e) {
            System.err.println("Eroare export Excel: " + e.getMessage());
        }
    }

    public static void citireFisier(String fisier, List<Student> lista) {
        try (Scanner sc = new Scanner(Paths.get(fisier))) {
            while (sc.hasNextLine()) {
                String[] date = sc.nextLine().split(",");
                if (date.length == 4) {
                    try {
                        lista.add(new Student(Integer.parseInt(date[0]), date[1], date[2], date[3]));
                    } catch (NumberFormatException e) {
                        System.out.println("Format invalid la nr. matricol.");
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Eroare la citire: " + e.getMessage());
        }
    }

    public static void alocareNote(String filename, List<Student> listaStudenti, HashMap<Integer, Student> index_studenti) {
        for (Student s : listaStudenti) index_studenti.put(s.getNumarMatricol(), s);
        try (Scanner sc = new Scanner(Paths.get(filename))) {
            while (sc.hasNextLine()) {
                String[] date = sc.nextLine().split(",");
                if (date.length == 2) {
                    int nr = Integer.parseInt(date[0].trim());
                    double nota = Double.parseDouble(date[1].trim());
                    if (index_studenti.containsKey(nr)) index_studenti.get(nr).setNota(nota);
                }
            }
        } catch (Exception e) {
            System.out.println("Eroare la alocare note.");
        }
    }

    public static void main(String[] args) {
        // LAB 9
        List<Student> studentiCuNote = Arrays.asList(
                new Student(1025, "Andrei",   "Popa",     "ISM141/2", 8.70),
                new Student(1024, "Ioan",     "Mihalcea", "ISM141/1", 10.0),
                new Student(1026, "Anamaria", "Prodan",   "TI131/1",  8.90),
                new Student(1029, "Bianca",   "Popescu",  "TI131/1",  10.0),
                new Student(1030, "Maria",    "Pana",     "TI131/2",  4.10),
                new Student(1031, "Gabriela", "Mohanu",   "TI131/2",  7.33),
                new Student(1032, "Marius",   "Nasta",    "TI131/2",  3.20),
                new Student(1033, "Marius",   "Nasta",    "TI131/1",  5.12),
                new Student(1034, "Andrei",   "Dobrescu", "TI131/2",  2.22)
        );

        studentiCuNote.stream()
                .filter(s -> s.getNota() == 10.0)
                .forEach(System.out::println);

        studentiCuNote.stream()
                .filter(s -> s.getNota() < 5)
                .forEach(System.out::println);

        List<Student> listaCorectata = studentiCuNote.stream()
                .map(s -> {
                    if (s.getNota() < 4) {
                        // Creem un student nou cu nota 4 (imutabilitate!)
                        return new Student(
                                s.getNumarMatricol(),
                                s.getPrenume(),
                                s.getNume(),
                                s.getFormatieDeStudiu(),
                                4.0f
                        );
                    }
                    return s;
                })
                .collect(Collectors.toList());
        listaCorectata.forEach(System.out::println);

        double sumaNote = studentiCuNote.stream()
                .reduce(0.0,
                        (suma, s) -> suma + s.getNota(),
                        Double::sum);
        System.out.println("\n=== Suma notelor: " + sumaNote + " ===");
    }
}
