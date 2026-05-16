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
    public int getNumarMatricol() {
        return numărMatricol;
    }

    public String getPrenume() {
        return prenume;
    }

    public String getNume() {
        return nume;
    }

    public String getFormatieDeStudiu() {
        return formațieDeStudiu;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

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
                new Student(1025, "Andrei", "Popa", "ISM141/2", 8.70),
                new Student(1024, "Ioan", "Mihalcea", "ISM141/1", 10.0),
                new Student(1026, "Anamaria", "Prodan", "TI131/1", 8.90),
                new Student(1029, "Bianca", "Popescu", "TI131/1", 10.0),
                new Student(1030, "Maria", "Pana", "TI131/2", 4.10),
                new Student(1031, "Gabriela", "Mohanu", "TI131/2", 7.33),
                new Student(1032, "Marius", "Nasta", "TI131/2", 3.20),
                new Student(1033, "Marius", "Nasta", "TI131/1", 5.12),
                new Student(1034, "Andrei", "Dobrescu", "TI131/2", 2.22)
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
                        // Cream un student nou cu nota 4 (imutabilitate!)
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

        // Interfața Strategy
        interface ExportStrategy {
            void export(List<Student> studenti);
        }

// Strategia 1: Export simplu în consolă (Format tabelar)
        class ConsoleExportStrategy implements ExportStrategy {
            @Override
            public void export(List<Student> studenti) {
                System.out.println("\n--- Export Consolă ---");
                studenti.forEach(System.out::println);
            }
        }

// Strategia 2: Export în format CSV (Comma Separated Values)
        class CSVExportStrategy implements ExportStrategy {
            @Override
            public void export(List<Student> studenti) {
                System.out.println("\n--- Export format CSV ---");
                System.out.println("ID,Nume,Prenume,Grupa,Medie");
                for (Student s : studenti) {
                    System.out.printf("%d,%s,%s,%s,%.2f%n",
                            s.getNumarMatricol(), s.getNume(), s.getPrenume(), s.getFormatieDeStudiu(), s.getNota());
                }
            }
        }

// Contextul: Clasa care utilizează o strategie
        class StudentExporter {
            private ExportStrategy strategy;

            public void setStrategy(ExportStrategy strategy) {
                this.strategy = strategy;
            }

            public void executeExport(List<Student> studenti) {
                if (strategy == null) {
                    System.out.println("Eroare: Nu a fost setată nicio strategie de export!");
                    return;
                }
                strategy.export(studenti);
            }
        }

        interface IStudentiExport {
            void doExport(List<Student> studenti);
        }

// 3. Implementarea Concretă a Strategiei (StudentiInConsola)
        class StudentiInConsola implements IStudentiExport {
            @Override
            public void doExport(List<Student> studenti) {
                System.out.println("\n--- Export Studenți în Consolă ---");
                for (Student student : studenti) {
                    System.out.println(student);
                }
            }
        }

// 4. Contextul (Clasa Exporter)
        class Exporter {
            private List<Student> studenti;
            private IStudentiExport studentExport;

            // Constructor care primește lista de studenți și instanța strategiei
            public Exporter(List<Student> studenti, IStudentiExport studentExport) {
                this.studenti = studenti;
                this.studentExport = studentExport;
            }

            // Metoda care declanșează exportul
            public void proceseazaExport() {
                if (studentExport != null) {
                    studentExport.doExport(studenti);
                } else {
                    System.out.println("Eroare: Strategia de export nu a fost setată!");
                }
            }

            // Setter opțional pentru a schimba strategia ulterior (dacă este necesar)
            public void setStudentExport(IStudentiExport studentExport) {
                this.studentExport = studentExport;
            }
        }

// 5. Clasa Principală
        class AplicatieCuStrategy {
            public void main(String[] args) {
                // Datele inițiale
                List<Student> studenti = Arrays.asList(
                        new Student(1025, "Andrei", "Popa", "ISM141/2", 8.70),
                        new Student(1024, "Ioan", "Mihalcea", "ISM141/1", 10),
                        new Student(1026, "Anamaria", "Prodan", "TI131/1", 8.90),
                        new Student(1029, "Bianca", "Popescu", "TI131/1", 10),
                        new Student(1029, "Maria", "Pana", "TI131/2", 4.10),
                        new Student(1029, "Gabriela", "Mohanu", "TI131/2", 7.33),
                        new Student(1029, "Marius", "Nasta", "TI131/2", 3.20),
                        new Student(1029, "Marius", "Nasta", "TI131/1", 5.12),
                        new Student(1029, "Andrei", "Dobrescu", "TI131/2", 2.22)
                );

                // Instanțiem strategia concretă
                IStudentiExport strategieConsola = new StudentiInConsola();

                // Creăm instanța Exporter (Contextul) cu lista de studenți și strategia
                Exporter exporter = new Exporter(studenti, strategieConsola);

                // Apelăm metoda care execută exportul
                exporter.proceseazaExport();
            }
        }
    }
}
