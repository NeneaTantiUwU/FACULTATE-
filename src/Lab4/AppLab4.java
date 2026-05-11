

package Lab4;
import java.util.*;

public class AppLab4 {

    // 1. Clasa interna statica
    public static class Tanar {
        private String name;
        private int age;
        private String address;

        public Tanar(String name, int age, String address) {
            this.name = name;
            this.age = age;
            this.address = address;
        }

        @Override
        public String toString() {
            return name + " (" + age + " ani) - " + address;
        }
    }

    // 2. METODA MAIN - Punctul de pornire
    public static void main(String[] args) {

        HashMap<String, Integer> varste = new HashMap<>();
        varste.put("Ioan", 21);
        varste.put("Maria", 22);
        varste.put("Victor", 20);
        varste.put("Simina", 20);
        varste.put("Marius", 21);
        varste.put("Mihai", 21);
        varste.put("Daniela", 23);
        varste.put("Vlad", 19);
        varste.put("Iulia", 19);

        Map<String, String> adrese = Map.of(
                "Ioan", "Sibiu", "Maria", "Bucuresti", "Victor", "Cluj",
                "Simina", "Alba-Iulia", "Marius", "Medias", "Mihai", "Cisnadie", "Daniela", "Sibiu"
        );

        HashMap<String, Tanar> tineri = new HashMap<>();

        for (String nume : varste.keySet()) {
            int varsta = varste.get(nume);
            String adresa = adrese.getOrDefault(nume, "Necunoscut");
            tineri.put(nume, new Tanar(nume, varsta, adresa));
        }

        System.out.println("--- Rezultate Lab (Package AppLab4) ---");
        tineri.forEach((k, v) -> System.out.println(k + " -> " + v));
    }
}