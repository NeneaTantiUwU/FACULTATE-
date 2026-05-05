package Lab9;

import java.util.Arrays;
import java.util.Random;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

public class AplLab9 {
    public static void main(String[] args) {
        List<Integer> numere = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            int numarAleator = random.nextInt(21) + 5;
            numere.add(numarAleator);

            int sum = numere.stream().mapToInt(Integer::intValue).sum();

            int min = numere.stream().mapToInt(Integer::intValue).min().getAsInt();
            System.out.println(min);

            int max = numere.stream().mapToInt(Integer::intValue).max().getAsInt();
            System.out.println(max);
        }
    }
}
