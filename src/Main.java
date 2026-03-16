import java.util.*;
import java.util.*;
import java.util.List;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.Collection;

//comment

public class Main {
    public static void main(String[] args) {


        Student s1 = new Student(1001, "Andrei", "Hogea", "Ing.Sist.Prgrm.1");

        Random random = new Random();

        List<Integer> x = new ArrayList<>();
        for (int i = 0; i < 5; i++) x.add(random.nextInt(11));

        List<Integer> y = new ArrayList<>();
        for (int i = 0; i < 7; i++) y.add(random.nextInt(11));

        Collections.sort(x);
        Collections.sort(y);

        List<Integer> xPlusY = new ArrayList<>(x);//a
        xPlusY.addAll(y);

        Set<Integer> zSet = new TreeSet<>(x);//b
        zSet.retainAll(y);

        List<Integer> xMinusY = new ArrayList<>(x);//c
        xMinusY.removeAll(y);

        int p = 4;
        List<Integer> xPlusYLimitedByP = new ArrayList<>(); //d
        for (Integer val : xPlusY) {
            if (val <= p) {
                xPlusYLimitedByP.add(val);
            }
        }

        List<Student> listaStudenti = new ArrayList<>();

        listaStudenti.add(new Student(112, "Maria", "Popa", "TI21/1"));
        listaStudenti.add(new Student(115, "Ion", "Ionescu", "TI21/2"));
        listaStudenti.add(new Student(118, "Vasile", "Andrei", "TI21/1"));
        listaStudenti.add(new Student(120, "Alis", "Popa", "TI21/2"));

        boolean containsElement = listaStudenti.contains("Alis");
        boolean containsElement1 = listaStudenti.contains("Maria");

        for (Student s : listaStudenti) {
            System.out.println(s);
        }

        System.out.println(containsElement1);
        System.out.println(containsElement);
    }
    public static boolean existaStudent(List<Student> lista, Student s) {
        return lista.contains(s);
    }
}



