package graphics;

import javax.swing.*;
import java.awt.*;

public class StartMenu extends JDialog {

    // Pola przechowujące suwaki do konfiguracji parametrów symulacji
    private final JSlider mrowiskaSlider;    // Suwak do ustawienia liczby mrowisk
    private final JSlider mrowkiSlider;      // Suwak do ustawienia częstotliwości tworzenia mrówek
    private final JSlider przedmiotySlider;  // Suwak do ustawienia częstotliwości pojawiania się przedmiotów
    private final JSlider czasSlider;        // Suwak do ustawienia czasu trwania symulacji (tylko w trybie Timer)

    // Flagi stanu
    private boolean started = false;         // Czy symulacja została rozpoczęta
    public boolean choice = false;           // Wybór trybu (false=Battle Royale, true=Timer)
    private boolean mode = false;            // Czy tryb został wybrany


    // Metoda zwracająca liczbę mrowisk ustawioną na suwaku
    public int getLiczbaMrowisk() {
        return mrowiskaSlider.getValue();
    }

    // Metoda zwracająca interwał tworzenia mrówek w milisekundach
    public int getMrowkiInterval() {
        return mrowkiSlider.getValue() * 1000;  // Konwersja sekund na milisekundy
    }

    // Metoda zwracająca interwał pojawiania się przedmiotów w milisekundach
    public int getPrzedmiotyInterval() {
        return przedmiotySlider.getValue() * 100; // Konwersja 0.1s na milisekundy
    }

    // Metoda zwracająca czas trwania symulacji w milisekundach
    public int getCzasInterval() {
        return czasSlider.getValue() * 1000;    // Konwersja sekund na milisekundy
    }

    // Metoda zwracająca wybrany tryb symulacji
    public boolean getChoice() {
        return choice;
    }

    // Metoda sprawdzająca czy symulacja została rozpoczęta
    public boolean isStarted() {
        return started;
    }

    // Konstruktor - inicjalizacja okna dialogowego
    public StartMenu(JFrame parent) {
        super(parent, "Ustawienia Symulacji", true);
        setLayout(new GridLayout(9, 1, 10, 10));  // 9 wierszy, 1 kolumna, odstępy 10px
        setSize(600, 500);
        setLocationRelativeTo(parent);  // Wyśrodkowanie względem rodzica

        // Inicjalizacja suwaków z podanymi parametrami (min, max, wartość początkowa)
        mrowiskaSlider = new JSlider(2, 10, 3);     // Liczba mrowisk (2-10, domyślnie 3)
        mrowkiSlider = new JSlider(1, 10, 4);       // Czas tworzenia mrówek (1-10s, domyślnie 4s)
        przedmiotySlider = new JSlider(0, 50, 15);   // Czas pojawiania przedmiotów (0-5s, domyślnie 1.5s)
        czasSlider = new JSlider(15, 180, 60);       // Czas symulacji (15-180s, domyślnie 60s)

        // Konfiguracja podziałek na suwakach
        mrowiskaSlider.setMajorTickSpacing(1);      // Podziałka co 1 mrowisko
        mrowkiSlider.setMajorTickSpacing(1);         // Podziałka co 1 sekundę
        przedmiotySlider.setMajorTickSpacing(5);     // Podziałka co 5 jednostek (0.5s)
        czasSlider.setMajorTickSpacing(15);          // Podziałka co 15 sekund

        // Włączenie wyświetlania podziałek
        mrowiskaSlider.setPaintTicks(true);
        mrowkiSlider.setPaintTicks(true);
        przedmiotySlider.setPaintTicks(true);
        czasSlider.setPaintTicks(true);

        // Włączenie wyświetlania etykiet wartości
        mrowiskaSlider.setPaintLabels(true);
        mrowkiSlider.setPaintLabels(true);
        przedmiotySlider.setPaintLabels(true);
        czasSlider.setPaintLabels(true);

        // Dodanie etykiet i suwaków do okna
        add(new JLabel("Liczba mrowisk:"));
        add(mrowiskaSlider);
        add(new JLabel("Czas tworzenia mrówek (s):"));
        add(mrowkiSlider);
        add(new JLabel("Czas pojawiania się przedmiotów (0,1s):"));
        add(przedmiotySlider);
        add(new JLabel("Czas trwania symulacji: (s)"));
        czasSlider.setEnabled(false);  // Domyślnie wyłączony (tylko dla trybu Timer)
        add(czasSlider);

        // Dodanie przycisków wyboru trybu
        add(new JLabel("Wybierz tryb gry:"));
        JButton LewoButton = new JButton("Battle Royale");
        JButton PrawoButton = new JButton("Timer");
        JButton StartButton = new JButton("Start");
        StartButton.setEnabled(false);  // Przycisk początkowo nieaktywny

        // Obsługa wyboru trybu Battle Royale
        LewoButton.addActionListener(e -> {
            choice = false;             // Ustawienie trybu na Battle Royale
            mode = true;               // Potwierdzenie wyboru trybu
            StartButton.setEnabled(true);  // Aktywacja przycisku Start
            czasSlider.setEnabled(false);  // Wyłączenie suwaka czasu
        });

        // Obsługa wyboru trybu Timer
        PrawoButton.addActionListener(e -> {
            choice = true;              // Ustawienie trybu na Timer
            mode = true;               // Potwierdzenie wyboru trybu
            StartButton.setEnabled(true);  // Aktywacja przycisku Start
            czasSlider.setEnabled(true);   // Włączenie suwaka czasu
        });

        // Dodanie przycisków do okna
        add(LewoButton);
        add(PrawoButton);

        // Obsługa przycisku Start
        StartButton.addActionListener(e -> {
            if (mode) {                // Sprawdzenie czy wybrano tryb
                started = true;        // Ustawienie flagi rozpoczęcia
                dispose();             // Zamknięcie okna dialogowego
            }
        });

        add(StartButton);
    }
}