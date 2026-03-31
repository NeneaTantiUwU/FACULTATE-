import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

class Student extends Object{
    public int numărMatricol;
    public String prenume;
    public String nume;
    public String formațieDeStudiu;
    public double nota;

    public Student(int numărMatricol, String prenume, String nume, String formațieDeStudiu ,int nota){
        this.numărMatricol = numărMatricol;
        this.prenume = prenume;
        this.nume = nume;
        this.formațieDeStudiu = formațieDeStudiu;
        this.nota = nota;
    }

    Student(int numărMatricol, String prenume, String nume, String formațieDeStudiu){
        this.numărMatricol = numărMatricol;
        this.prenume = prenume;
        this.nume = nume;
        this.formațieDeStudiu = formațieDeStudiu;
        this.nota = 0.0;
    }

    public String getNume() {
        return nume;
    }

    public String getFormatieDeStudiu()
    {
        return formațieDeStudiu;
    }

    public int getNumarMatricol()
    {
        return numărMatricol;
    }

    public String getPrenume()
    {
        return prenume;
    }

    public void setNota(double nota) {this.nota=nota;}

    public double getNota() {return nota;}

    public String toString()
    {
        return
                "Nume: " + nume + "\n" +
                        "Prenume: " + prenume + "\n" +
                        "Numar matricol: " + numărMatricol + "\n" +
                        "Formatie de studiu: " + formațieDeStudiu + "\n" +
                        "Nota: "+ nota + "\n";


    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return prenume.equals(student.prenume) &&
                nume.equals(student.nume) &&
                formațieDeStudiu.equals(student.formațieDeStudiu);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(prenume, nume, formațieDeStudiu, nota);
    }

    public static void citireFisier(String fisier, List<Student> lista) throws FileNotFoundException
    {
        try
        {
            Scanner sc = new Scanner(Paths.get(fisier));
            while (sc.hasNextLine())
            {
                String linie = sc.nextLine();
                String[] date = linie.split(",");

                if (date.length == 4) {
                    try {
                        int nrMatricol = Integer.parseInt(date[0]);
                        String nume = date[1];
                        String prenume = date[2];
                        String formatie = date[3];

                        Student student_nou = new Student(nrMatricol, nume, prenume, formatie);
                        lista.add(student_nou);

                    } catch (NumberFormatException e) {
                        System.out.println("numar matricol invalid la linia "+linie);
                    }
                }
                else
                    System.out.println("Date incorecte la linia "+linie);
            }

        } catch (IOException e) {
            System.out.println("Eroare la citirea fisierului " + fisier);
        }
    }

    public static void scriereFisier(List<Student> studenti, String fileName) throws FileNotFoundException
    {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(fileName)))
        {

            for(Student s : studenti)
            {
                String linie = s.getNumarMatricol() + "," +
                        s.getNume() + "," +
                        s.getPrenume() + "," +
                        s.getFormatieDeStudiu();

                writer.write(linie);
                writer.newLine();
            }
        }
        catch (IOException e)
        {
            System.out.println("Eroare la scrierea in fisier");
        }


    }

    public static void alocareNote(String filename, List<Student> listaStudenti,  HashMap<Integer, Student> index_studenti) throws FileNotFoundException
    {
        for (Student s : listaStudenti)
        {
            index_studenti.put(s.getNumarMatricol(), s);
        }

        try (Scanner sc = new Scanner(Paths.get(filename))) {
            while (sc.hasNextLine()) {
                String linie = sc.nextLine();
                if (linie.trim().isEmpty()) continue;

                String[] date = linie.split(",");
                if (date.length == 2) {
                    try {
                        int nrMatricol = Integer.parseInt(date[0].trim());
                        double nota = Double.parseDouble(date[1].trim());

                        Student student_cautat = index_studenti.get(nrMatricol);

                        if (student_cautat != null) {
                            student_cautat.setNota(nota);
                        } else {
                            System.out.println("nu a t fi gasit studentul cu numarul matricol "+nrMatricol);
                        }

                    } catch (NumberFormatException e) {
                        System.out.println("numar matricol sau nota invalida la linia " + linie);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Eroarea la citirea " + filename);
        }
    }



    public static double gasesteNota(String nume, String prenume, Map<Integer, Student> ultim_map)
    {
        HashMap<String, Student> index_dupa_nume = new HashMap<>();
        for (Student s : ultim_map.values())
        {
            String cheie = s.getPrenume()+"-" + s.getNume();
            index_dupa_nume.put(cheie, s);
        }

        String cheie_cautare = prenume + "-" + nume;
        Student gasit = index_dupa_nume.get(cheie_cautare);

        if (gasit != null) {

            return gasit.getNota();
        } else {
            return 0.0;
        }
    }

}


