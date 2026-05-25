package Lab11.lab11;

import java.util.Arrays;
import java.util.List;

public class AplicatieCuDecorator {
    public static void main(String[] args) {

        // 1. Popularea listei de studenți folosind datele exacte din laborator (pag. 12)
        List<Student> studentiCuNote = Arrays.asList(
                new Student(1029, "Andrei", "Popa", "ISM141/2", 8.78),
                new Student(1024, "Ioan", "Mihalcea", "ISM141/1", 4.10),
                new Student(1020, "Anamaria", "Prodan", "T1131/1", 8.90),
                new Student(1029, "Bianca", "Popescu", "T1131/1", 7.38),
                new Student(1029, "Maria", "Pana", "T1131/2", 5.20),
                new Student(1029, "Gabriela", "Moraru", "T1131/2", 4.10),
                new Student(1029, "Marius", "Nasta", "T1131/2", 5.12),
                new Student(1029, "Andrei", "Dobrescu", "T1131/2", 2.22)
        );

        // 2. Definirea listei de strategii de export (Componentele Concrete)
        List<IStudentiExport> strategies = Arrays.asList(
                new StudentiInFisierText(),
                new StudentiInFisierXlsx()
        );

        // 3. Iterarea prin strategii și aplicarea dinamică a Decoratorului (TimeExecutionDecorator)
        for (IStudentiExport strategy : strategies) {

            System.out.println("\n--- Se lansează exportul pentru: " + strategy.getClass().getSimpleName() + " ---");

            // Împachetăm componenta de bază în decoratorul care măsoară timpul
            IStudentiExport decorator = new TimeExecutionDecorator(strategy);

            // Decidem numele fișierului în funcție de instanța strategiei curele
            String numeFisier = "studentiStrategyText.txt";
            if (strategy instanceof StudentiInFisierXlsx) {
                numeFisier = "studentiStrategyExcel.xlsx";
            }

            // Apelul metodei prin decorator declanșează cronometrul, exportul nativ și afișarea timpului
            decorator.export(studentiCuNote, numeFisier);
        }
    }
}