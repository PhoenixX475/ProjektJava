package application;
import javax.swing.*;

import graphics.*;
import logic.mrowki.*;
import logic.rozne.ZliczarkaStatystyk;

import java.util.LinkedList;

/**
 * Główna klasa aplikacji
 * tutaj jest wywoływana symulacja
 * */

/**
 * Wszystkie obiekty zapisz w 2d tablicy i to wyświetlaj na mapę
 * Żeby sprawdzić czy coś jest blisko to z tego weź*/


public class Main {
    public static void main(String[] args) {



        SwingUtilities.invokeLater(() -> {

            //============MENU ==================================================
            JFrame dummyFrame = new JFrame(); // Potrzebny do osadzenia StartMenu
            StartMenu menu = new StartMenu(dummyFrame);
            menu.setVisible(true);
            if (!menu.isStarted()) {
                System.exit(0); // Zamknięcie, jeśli użytkownik nie kliknął Start
            }

            // Zmienne otrzymywane z menu
            int liczbaMrowisk = menu.getLiczbaMrowisk();
            int czasMrowki = menu.getMrowkiInterval();
            int czasPrzedmioty = menu.getPrzedmiotyInterval();
            int czasTrwania = menu.getCzasInterval();
            boolean choice = menu.getChoice();

            //============SYMULACJA==============================================

            // Stworzenie mapy
            JFrame frame = new JFrame("Symulacja Mrowisk");
            MapaPanel mapa = new MapaPanel(liczbaMrowisk, czasMrowki, czasPrzedmioty, czasPrzedmioty,czasTrwania,choice);

            //Stworzenie info
            StringBuilder sb1 = new StringBuilder();
            ZliczarkaStatystyk.createFileDane(sb1);

            Timer timeInfo = new Timer(250, e -> {
               ZliczarkaStatystyk.daneAdd(sb1);
            });
            timeInfo.start();

            //Dodanie Mrowisk na mapę
            mapa.dodajLosoweMrowiska(liczbaMrowisk);

            mapa.setOnSimulationEnd(() -> {
                LinkedList<Mrowisko> mrowiska = mapa.getMrowiska();
                ZliczarkaStatystyk.zliczStatystyki(mrowiska);

            });

            mapa.rozpocznijSymulacje(choice, czasTrwania);



            // Zakończenie wyświetlania gdy użytkownik nacisnie klawisz ESC
            mapa.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), "exit");
            mapa.getActionMap().put("exit", new AbstractAction() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    System.exit(0);
                }
            });

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(mapa);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

        });
    }
}
