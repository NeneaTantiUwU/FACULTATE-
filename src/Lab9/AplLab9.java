package Lab9;

import java.util.Arrays;
import java.util.Random;
import java.util.List;
import java.util.*;
import java.util.stream.*;


public class AplLab9 {
    public static void main(String[] args) {
        List<Integer> numere = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            int numarAleator = random.nextInt(21) + 5;
            numere.add(numarAleator);
        }
            int sum = numere.stream().mapToInt(Integer::intValue).sum();

            int min = numere.stream().mapToInt(Integer::intValue).min().getAsInt();
            System.out.println(min);

            int max = numere.stream().mapToInt(Integer::intValue).max().getAsInt();
            System.out.println(max);

            List<Integer> listanoua = numere.stream().filter(n -> n >= 10 && n <= 20).toList();

            List<Double> listaDouble = numere.stream()
                    .map(n -> n.doubleValue())
                    .toList();

            boolean doisprezece = numere.stream().anyMatch(n -> n >= 12);
            System.out.println(doisprezece);

        String text = "Acesta este un program scris in java pentru expresii lambda";

        List<String> cuvinte = Arrays.asList(text.split(" "));

        List<String> cuvinteLungi = cuvinte.stream()
                .filter(c -> c.length() >= 5)
                .collect(Collectors.toList());

        long numar = cuvinteLungi.stream().count();
        System.out.println("\n Cuvinte cu lungime >= 5 (" + numar + "): "
                + cuvinteLungi);

        List<String> sortata = cuvinteLungi.stream()
                .sorted()
                .collect(Collectors.toList());
        Optional<String> cuP = cuvinte.stream()
                .filter(c -> c.startsWith("p"))
                .findFirst();

        if (cuP.isPresent()) {
            System.out.println(" Cuvant care incepe cu 'p': " + cuP.get());
        } else {
            System.out.println(" Nu s-a gasit niciun cuvant cu p");
        }
    }
}

