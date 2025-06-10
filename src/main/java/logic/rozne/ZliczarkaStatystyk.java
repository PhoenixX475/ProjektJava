package graphics;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import logic.mrowki.Mrowisko;

/**
 * Klasa odpowiedzialna za generowanie i zapis statystyk symulacji mrowisk
 */
public class ZliczarkaStatystyk {

    /**
     * Generuje i zapisuje statystyki dla listy mrowisk
     * @param mrowiska Lista mrowisk do analizy
     */
    public static void zliczStatystyki(List<Mrowisko> mrowiska) {
        // Przygotowanie stringa z statystykami
        StringBuilder sb = new StringBuilder();
        sb.append("=== STATYSTYKI ===\n");

        // Iteracja przez wszystkie mrowiska i zbieranie statystyk
        for (int i = 0; i < mrowiska.size(); i++) {
            Mrowisko m = mrowiska.get(i);

            // Nagłówek mrowiska
            sb.append("Mrowisko #").append(i + 1).append("\n");

            // Statystyki zasobów
            sb.append("  Patyki: ").append(m.stickCount).append("\n");
            sb.append("  Liscie: ").append(m.leafCount).append("\n");

            // Statystyki rozwoju
            sb.append("  Level: ").append(m.getLevel()).append("\n");
            sb.append("  Liczba mrowek: ").append(m.getAntCount()).append("\n");

            // Statystyki walk
            long zabite = m.mrowki.stream().mapToLong(a -> a.zabiteMrowki).sum();
            sb.append("  Mrowki zabite przez mrowisko: ").append(zabite).append("\n\n");
        }

        String statystyki = sb.toString();
        System.out.println(statystyki);  // Wypisanie statystyk w konsoli

        // Sekcja zapisu do pliku
        Scanner scanner = new Scanner(System.in);
        System.out.print("Podaj ścieżkę do zapisu pliku (bez rozszerzenia, np. C:\\\\ścieżka\\\\plik): ");
        String userPath = scanner.nextLine();

        // Generowanie unikalnej nazwy pliku z timestampem
        String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        String filename = userPath + "_statystyki_symulacji_" + timestamp + ".txt";

        // Zapis do pliku z obsługą błędów
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(statystyki);
            System.out.println("Statystyki zapisane do pliku: " + filename);
        } catch (IOException e) {
            System.err.println("Bład zapisu statystyk: " + e.getMessage());
        } finally {
            scanner.close(); // Zamknięcie skanera
        }
    }
}