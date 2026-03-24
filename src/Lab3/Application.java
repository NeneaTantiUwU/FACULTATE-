package Lab3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        Path pathIn = Paths.get("in.txt");
        Path pathOut = Paths.get("out.txt");

        try {
            String continut = new String(Files.readAllBytes(pathIn));
            List<String> liniiRezultat = new ArrayList<>();
            String[] fragmente = continut.split("\\.");

            for (String fragment : fragmente) {
                String fragmentCurat = fragment.trim();

                if (!fragmentCurat.isEmpty()) {
                    String linieProcesata = fragmentCurat + ".\n";

                    System.out.print(linieProcesata);

                    liniiRezultat.add(linieProcesata);
                }
            }
            Files.write(pathOut, liniiRezultat);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}




