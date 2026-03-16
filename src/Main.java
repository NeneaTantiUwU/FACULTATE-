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
            }
        }
