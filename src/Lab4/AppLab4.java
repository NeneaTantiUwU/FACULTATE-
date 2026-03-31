package Lab4;

import java.util.HashMap;
import java.util.Map;
import java.util.Hashtable;
import java.util.EnumMap;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.Properties;
import java.util.TreeMap;
import java.util.WeakHashMap;
import java.io.*;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import

public class FInSiOut {
    // citire
    public static List<String> readLines(String fileName) {
        List<String> lines = new ArrayList<>();

        try (BufferedReader citeste = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = citeste.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lines;
    }
}


    // scriere
    public static void BagaInFisier(String fileName, List<String> content) {
        Path path = Paths.get(fileName);

        try (BufferedWriter scrie = Files.newBufferedWriter(path)) {
            for (String line : content) {
                scrie.write(line);
                scrie.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public class AppLab4 {

    public class Tanar {
        private String name;
        private int age;
        private String address;

        // Constructor
        public Tanar(String name, int age, String address) {
            this.name = name;
            this.age = age;
            this.address = address;
        }

        //getter si setter
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        // toString afisare
        @Override
        public String toString() {
            return name +"-"  + age + " - " + address;
        }
    }

    static void main() {

        HashMap<String, Integer> varste = new HashMap<>();
        varste.put("Ioan", 21);
        varste.put("Maria", 22);
        varste.put("Victor", 20);
        varste.put("Simina", 20);
        varste.put("Marius", 21);
        varste.put("Mihai", 21);
        varste.put("Daniela", 23);
        Map<String, String> adrese = Map.of("Ioan", "Sibiu", "Maria", "Bucuresti", "Victor", "Cluj","Simina", "Alba-Iulia","Marius", "Medias", "Mihai", "Cisnadie","Daniela", "Sibiu");

        varste.put("Vlad", 19);
        varste.put("Iulia", 19);

        System.out.println("Varste:");
        for (Map.Entry<String, Integer> entry : varste.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());

        }

        HashMap<String, Student.Tanar> tineri = new HashMap<>();

        for (String nume : varste.keySet()) {
            int varsta = varste.get(nume);
            String adresa = adrese.getOrDefault(nume, "Necunoscut");

            Student.Tanar tanar = new Student.Tanar(nume, varsta, adresa);

            tineri.put(nume, tanar);
        }

        tineri.forEach((key, value) -> System.out.println(key + " -> " + value));

    }

}
