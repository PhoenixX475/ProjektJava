package logic.rozne;

import graphics.MapaPanel;
import logic.mrowki.Mrowisko;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 * Klasa odpowiedzialna za zbieranie i zapisywanie statystyk
 * dotyczących mrowisk w symulacji.
 */
public class ZliczarkaStatystyk {

    /**
     * Zlicza i wypisuje podstawowe statystyki dla listy mrowisk,
     * następnie zapisuje je do pliku na ścieżce podanej przez użytkownika.
     * @param mrowiska lista mrowisk do zliczenia statystyk
     */
    public static void zliczStatystyki(List<Mrowisko> mrowiska) {

        StringBuilder sb = new StringBuilder();

        sb.append("=== STATYSTYKI PODSUMOWANIE ===\n");
        for (int i = 0; i < mrowiska.size(); i++) {
            Mrowisko m = mrowiska.get(i);
            sb.append("Mrowisko #").append(i + 1).append("\n");
            sb.append("  Patyki: ").append(m.stickCount).append("\n");
            sb.append("  Liscie: ").append(m.foodDelivered).append("\n");
            sb.append("  Level: ").append(m.getLevel()).append("\n");
            sb.append("  Liczba mrowek: ").append(m.getAntCount()).append("\n\n");
        }
        String statystyki = sb.toString();
        System.out.println(statystyki);  // Wyświetl statystyki w konsoli

        // Pobierz od użytkownika ścieżkę do pliku (bez rozszerzenia)
        Scanner scanner = new Scanner(System.in);
        System.out.print("Podaj sciezke do zapisu pliku (bez rozszerzenia, np. C:\\\\sciezka\\\\plik): ");
        String userPath = scanner.nextLine();

        // Dodaj znacznik czasu i rozszerzenie do nazwy pliku
        String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        String filename = userPath + "_statystyki_symulacji_" + timestamp + ".txt";

        // Zapisz statystyki do pliku
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(statystyki);
            System.out.println("Statystyki zapisane do pliku: " + filename);
        } catch (IOException e) {
            System.err.println("Blad zapisu statystyk: " + e.getMessage());
        }
    }

    /**
     * Tworzy (lub nadpisuje) plik Dane.txt i zapisuje w nim dane statystyczne
     * z przekazanego StringBuildera.
     * @param sb StringBuilder zawierający dane do zapisania
     */
    public static void createFileDane(StringBuilder sb) {
        String dane = sb.toString();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("./Dane.txt"))) {
            writer.write("[id, l.patykow, l.lisci, level, l.mrowek]\n");
            writer.write(dane);
        } catch (IOException e) {
            System.err.println("Blad zapisu danych: " + e.getMessage());
        }
    }

    /**
     * Dodaje dane statystyczne o mrowiskach do StringBuildera,
     * a następnie wywołuje zapis tych danych do pliku Dane.txt.
     * @param sb StringBuilder do którego dopisywane są dane
     */
    public static void daneAdd(StringBuilder sb) {
        List<Mrowisko> mrowiska = MapaPanel.listaMrowisk;
        for (int i = 0; i < mrowiska.size(); i++) {
            Mrowisko m = mrowiska.get(i);
            sb.append("[").append(i + 1).append(",")
                    .append(m.stickCount).append(",")
                    .append(m.foodDelivered).append(",")
                    .append(m.getLevel()).append(",")
                    .append(m.getAntCount()).append("]\n");
        }
        createFileDane(sb);
    }
}