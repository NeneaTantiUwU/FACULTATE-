package Lab11.lab11;

import java.util.List;

public class StudentiInFisierText implements IStudentiExport {
    @Override
    public void export(List<Student> studenti, String numeFisier) {
        try {
            Student.scriereFisier(studenti, numeFisier);
            System.out.println("Export text finalizat cu succes: " + numeFisier);
        } catch (Exception e) {
            System.out.println("Eroare la exportul text: " + e.getMessage());
        }
    }
}