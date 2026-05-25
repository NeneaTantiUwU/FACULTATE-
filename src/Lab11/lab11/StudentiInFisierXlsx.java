package Lab11.lab11;

import java.util.List;

public class StudentiInFisierXlsx implements IStudentiExport {
    @Override
    public void export(List<Student> studenti, String numeFisier) {
        Student.writeToXls(studenti, numeFisier);
    }
}
