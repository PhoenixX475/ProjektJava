package application;

import javax.swing.*;
import graphics.MapaPanel;
import graphics.StartMenu;
import graphics.ZliczarkaStatystyk;
import logic.mrowki.*;
import java.util.LinkedList;

/**
 * Główna klasa aplikacji odpowiedzialna za:
 * - Inicjalizację interfejsu użytkownika
 * - Uruchomienie menu startowego
 * - Zarządzanie przebiegiem symulacji
 * - Obsługę zakończenia programu
 */
public class Main {

    /**
     * Główna metoda uruchamiająca aplikację
     * @param args Argumenty wiersza poleceń (niewykorzystywane)
     */
    public static void main(String[] args) {
        // Uruchomienie w wątku EDT (Event Dispatch Thread) dla bezpieczeństwa Swing
        SwingUtilities.invokeLater(() -> {

            //============ INICJALIZACJA MENU STARTOWEGO ========================

            // Tymczasowa ramka dla menu startowego
            JFrame dummyFrame = new JFrame();

            // Utworzenie i wyświetlenie menu startowego
            StartMenu menu = new StartMenu(dummyFrame);
            menu.setVisible(true);

            // Zamknięcie aplikacji jeśli użytkownik nie rozpoczął symulacji
            if (!menu.isStarted()) {
                System.exit(0);
            }

            // Pobranie parametrów symulacji z menu
            int liczbaMrowisk = menu.getLiczbaMrowisk();        // Liczba mrowisk
            int czasMrowki = menu.getMrowkiInterval();         // Czas tworzenia mrówek (ms)
            int czasPrzedmioty = menu.getPrzedmiotyInterval(); // Czas pojawiania się przedmiotów (ms)
            int czasTrwania = menu.getCzasInterval();          // Czas trwania symulacji (ms)
            boolean choice = menu.getChoice();                // Wybrany tryb symulacji

            //============ PRZYGOTOWANIE SYMULACJI =============================

            // Główna ramka aplikacji
            JFrame frame = new JFrame("Symulacja Mrowisk");

            // Utworzenie panelu mapy z przekazanymi parametrami
            MapaPanel mapa = new MapaPanel(
                    liczbaMrowisk,
                    czasMrowki,
                    czasPrzedmioty,
                    czasPrzedmioty,
                    czasTrwania,
                    choice
            );

            // Losowe rozmieszczenie mrowisk na mapie
            mapa.dodajLosoweMrowiska(liczbaMrowisk);

            //============ OBSŁUGA ZAKOŃCZENIA SYMULACJI =======================

            // Ustawienie callbacku wywoływanego po zakończeniu symulacji
            mapa.setOnSimulationEnd(() -> {
                // Pobranie listy mrowisk i wygenerowanie statystyk
                LinkedList<Mrowisko> mrowiska = mapa.getMrowiska();
                ZliczarkaStatystyk.zliczStatystyki(mrowiska);
            });

            // Rozpoczęcie symulacji z wybranymi parametrami
            mapa.rozpocznijSymulacje(choice, czasTrwania);

            //============ KONFIGURACJA INTERFEJSU =============================

            // Obsługa klawisza ESC - zamykanie aplikacji
            mapa.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                    .put(KeyStroke.getKeyStroke("ESCAPE"), "exit");
            mapa.getActionMap().put("exit", new AbstractAction() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    System.exit(0);
                }
            });

            // Konfiguracja głównego okna
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Zamknij program przy zamykaniu okna
            frame.getContentPane().add(mapa);                     // Dodanie panelu mapy
            frame.pack();                                        // Dopasowanie rozmiaru
            frame.setLocationRelativeTo(null);                    // Wyśrodkowanie okna
            frame.setVisible(true);                               // Pokazanie okna
        });
    }
}