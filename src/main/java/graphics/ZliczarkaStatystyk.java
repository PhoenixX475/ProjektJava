package graphics;
import logic.mrowki.Mrowisko;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


import java.util.Scanner;


public class ZliczarkaStatystyk {


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
        System.out.println(statystyki);  // Wypisz w konsoli


        // Pobranie ścieżki zapisu od użytkownika
        Scanner scanner = new Scanner(System.in);
        System.out.print("Podaj sciezke do zapisu pliku (bez rozszerzenia, np. C:\\\\sciezka\\\\plik): ");
        String userPath = scanner.nextLine();



        // Dodanie znacznika czasu i rozszerzenia
        String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        String filename = userPath + "_statystyki_symulacji_" + timestamp + ".txt";





        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {


            writer.write(statystyki);


            System.out.println("Statystyki zapisane do pliku: " + filename);


        } catch (IOException e) {


            System.err.println("Blad zapisu statystyk: " + e.getMessage());


        }


    }

    public static void createFileDane(StringBuilder sb) {
        String dane = sb.toString();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("./Dane"))) {

            writer.write("[id, l.patykow, l.lisci, level, l.mrowek]\n");
            writer.write(dane);
        } catch (IOException e) {


            System.err.println("Blad zapisu danych: " + e.getMessage());


        }

    }

    public static void daneAdd(StringBuilder sb) {
        List<Mrowisko> mrowiska = MapaPanel.listaMrowisk;
        for (int i = 0; i < mrowiska.size(); i++) {
            Mrowisko m = mrowiska.get(i);
            sb.append("[").append(i + 1).append(",");
            sb.append("").append(m.stickCount).append(",");
            sb.append("").append(m.foodDelivered).append(",");
            sb.append("").append(m.getLevel()).append(",");
            sb.append("").append(m.getAntCount()).append("]\n");
            createFileDane(sb);
        }
    }


}