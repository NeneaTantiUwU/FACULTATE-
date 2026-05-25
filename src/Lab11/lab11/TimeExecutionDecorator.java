package Lab11.lab11;

import java.util.List;

public class TimeExecutionDecorator implements IStudentiExport {
    private final IStudentiExport decoratedExporter;

    public TimeExecutionDecorator(IStudentiExport exporter) {
        this.decoratedExporter = exporter;
    }

    @Override
    public void export(List<Student> studenti, String numeFisier) {
        // Obținem timpul de început în milisecunde
        long startTime = System.currentTimeMillis();

        // Rulăm funcția de export neschimbată
        decoratedExporter.export(studenti, numeFisier);

        // Obținem timpul de final și calculăm diferența
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        // Afișăm în consolă timpul de execuție
        System.out.println("[Decorator] Timp de executie pentru "
                + decoratedExporter.getClass().getSimpleName() + ": " + executionTime + " ms");
    }
}