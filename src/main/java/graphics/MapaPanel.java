package graphics;

import logic.mrowki.Mrowisko;
import logic.obiekty.Lisc;
import logic.obiekty.Patyk;
import logic.rozne.ObiektMapy;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.Random;
/**
 * Klasa odpowiedzialna za zarządzanie logiką oraz wizualizacją symulacji mapy.
 * Tworzy i aktualizuje obiekty takie jak mrowiska, mrówki, liście i patyki.
 * Obsługuje również tryb czasowy oraz "do ostatniego mrowiska".
 */
public class MapaPanel extends JPanel {

    // Wymiary mapy w ilości pól
    private static final int wiersze = 100;
    private static final int kolumny = 100;

    // Rozmiar jednego pola w pikselach
    private final int rozmiarPola = 6;

    // Dwuwymiarowa mapa przechowująca informacje o typach pól
    private static final Pole[][] mapa = new Pole[wiersze][kolumny];

    // Generator liczb losowych
    private final Random random = new Random();

    // Lista mrowisk znajdujących się na mapie
    public static LinkedList<Mrowisko> listaMrowisk = new LinkedList<>();

    // Lista wszystkich obiektów na mapie (mrówki, liście, patyki, mrowiska)
    public static LinkedList<ObiektMapy> listaObiektow = new LinkedList<>();

    // Lista obiektów oznaczonych do usunięcia z mapy
    public static LinkedList<ObiektMapy> doUsuniecia = new LinkedList<>();

    // Liczba mrowisk początkowo (może być pomocna przy porównaniach)
    private int iloscMrowisk = listaMrowisk.size();

    // Czas trwania symulacji w trybie czasowym (ms)
    private int czasTrwaniaSymulacji = -1;

    // Czas rozpoczęcia symulacji (timestamp)
    private long startTime = -1;

    // Flaga wskazująca, czy symulacja jest aktywna
    private boolean symulacjaAktywna = true;

    // Flaga wyboru trybu symulacji (true = na czas, false = do końca)
    private boolean choice;

    /**
     * Konstruktor tworzy panel, inicjalizuje mapę oraz uruchamia generatory obiektów.
     */
    public MapaPanel(int liczbaMrowisk, int czasMrowki, int czasLisci, int czasPatykow, int czasTrwania, boolean choice) {
        this.setPreferredSize(new Dimension(kolumny * rozmiarPola, wiersze * rozmiarPola)); // Ustawienie rozmiaru panelu

        // Wypełnienie mapy pustymi polami
        for (int y = 0; y < wiersze; y++) {
            for (int x = 0; x < kolumny; x++) {
                mapa[y][x] = new Pole(TypObiektu.PUSTE); // Każde pole ma typ PUSTE na starcie
            }
        }

        updateMap();              // Uruchamia cykliczną aktualizację mapy
        spawnAnts(czasMrowki);   // Generuje mrówki co określony czas
        spawnLisc(czasLisci);    // Generuje liście
        spawnPatyk(czasPatykow); // Generuje patyki
    }

    // Callback wywoływany po zakończeniu symulacji (np. do zamknięcia GUI)
    private Runnable onSimulationEnd;

    // Metoda pomocnicza do ustawienia callbacku
    public void setOnSimulationEnd(Runnable callback) {
        this.onSimulationEnd = callback;
    }

    // Zwraca listę mrowisk
    public static LinkedList<Mrowisko> getMrowiska() {
        return listaMrowisk;
    }

    /**
     * Rozpoczyna symulację w jednym z dwóch trybów:
     * - Czasowym (jeśli `choice == true`)
     * - Bitwy do ostatniego mrowiska (jeśli `choice == false`)
     */
    public void rozpocznijSymulacje(boolean choice, int czasTrwania) {
        switch (String.valueOf(choice)) {
            case "false" -> {
                // Tryb: do ostatniego mrowiska
                Timer battleTimer = new Timer(1000, e -> {
                    // Sprawdzenie liczby aktywnych mrowisk (onMap == true)
                    long aktywneMrowiska = listaMrowisk.stream().filter(m -> m.onMap).count();
                    if (aktywneMrowiska <= 1 && symulacjaAktywna) {
                        symulacjaAktywna = false; // zatrzymanie symulacji

                        if (onSimulationEnd != null) onSimulationEnd.run(); // wywołanie callbacku
                        JOptionPane.showMessageDialog(this, "Zwycieskie mrowisko!");

                        ((Timer) e.getSource()).stop(); // zatrzymanie timera

                        // Wyjście z aplikacji po krótkim opóźnieniu
                        Timer exitTimer = new Timer(1000, ev -> System.exit(0));
                        exitTimer.setRepeats(false);
                        exitTimer.start();
                    }
                });
                battleTimer.start(); // uruchomienie trybu bitwy
            }

            case "true" -> {
                // Tryb: czasowy
                if (czasTrwaniaSymulacji <= 0) {
                    this.czasTrwaniaSymulacji = czasTrwania;
                    this.startTime = System.currentTimeMillis(); // zapis startu

                    Timer koniecTimer = new Timer(czasTrwania, e -> {
                        symulacjaAktywna = false; // zatrzymanie symulacji
                        JOptionPane.showMessageDialog(this, "Symulacja zakonczona.");
                        ((Timer) e.getSource()).stop();

                        if (onSimulationEnd != null) onSimulationEnd.run(); // wywołanie callbacku

                        // Wyjście z aplikacji po 1 sekundzie
                        Timer exitTimer = new Timer(1000, ev -> System.exit(0));
                        exitTimer.setRepeats(false);
                        exitTimer.start();
                    });

                    koniecTimer.setRepeats(false);
                    koniecTimer.start(); // uruchomienie licznika czasu
                }
            }
        }
    }

    /**
     * Cyklicznie aktualizuje wszystkie obiekty na mapie i ją odświeża.
     */
    public void updateMap() {
        Timer timer = new Timer(250, e -> {
            // Aktualizacja każdego obiektu (np. ruch mrówki)
            for (ObiektMapy obj : listaObiektow) {
                if (obj.onMap) obj.update();
            }

            // Zbieranie obiektów do usunięcia
            for (ObiektMapy o : listaObiektow) {
                if (!o.onMap) doUsuniecia.add(o);
            }

            // Usuwanie obiektów z mapy i listy
            for (ObiektMapy u : doUsuniecia) {
                if (listaObiektow.contains(u)) {
                    listaObiektow.remove(u);
                    listaMrowisk.remove(u);
                }
            }
            doUsuniecia.clear();

            // Warunek kończący timer przy zakończeniu symulacji
            if (listaMrowisk.size() <= 1 || !symulacjaAktywna) ((Timer) e.getSource()).stop();

            repaint(); // odświeżenie widoku
        });
        timer.start(); // start aktualizacji
    }

    /**
     * Generuje mrówki z każdego mrowiska w stałym interwale.
     */
    private void spawnAnts(int czasMrowki) {
        Timer mrowkiTimer = new Timer(czasMrowki, e -> {
            // Każde mrowisko tworzy nową mrówkę
            for (Mrowisko m : listaMrowisk) {
                m.createAnt(this);
            }

            // Warunek zakończenia generowania
            if (listaMrowisk.size() <= 1 || !symulacjaAktywna) ((Timer) e.getSource()).stop();
        });
        mrowkiTimer.start(); // start generowania
    }

    // Generuje losowo liście na pustych polach mapy
    private void spawnLisc(int czasLisci) {
        Timer liscieTimer = new Timer(czasLisci, e -> {
            int x = random.nextInt(kolumny);
            int y = random.nextInt(wiersze);

            if (mapa[y][x].typ == TypObiektu.PUSTE) {
                Lisc lisc = new Lisc(x, y);
                listaObiektow.add(lisc); // dodanie liścia
            }

            if (listaMrowisk.size() <= 1 || !symulacjaAktywna) ((Timer) e.getSource()).stop();
        });
        liscieTimer.start();
    }

    // Generuje patyki na pustych polach mapy
    private void spawnPatyk(int czasPatykow) {
        Timer patykiTimer = new Timer(czasPatykow, e -> {
            int x = random.nextInt(kolumny);
            int y = random.nextInt(wiersze);

            if (mapa[y][x].typ == TypObiektu.PUSTE) {
                Patyk patyk = new Patyk(x, y);
                listaObiektow.add(patyk); // dodanie patyka
            }

            if (listaMrowisk.size() <= 1 || !symulacjaAktywna) ((Timer) e.getSource()).stop();
        });
        patykiTimer.start();
    }

    // Dodaje nowe mrowiska z zachowaniem minimalnej odległości między nimi
    public void dodajLosoweMrowiska(int ile) {
        Random r = new Random();
        int minimalnaOdleglosc = 15;  // minimalna odległość między mrowiskami
        int probaLimit = 1000;        // maksymalna liczba prób

        for (int i = 0; i < ile; i++) {
            boolean znaleziono = false;
            int proby = 0;

            // Szukanie odpowiedniego miejsca
            while (!znaleziono && proby < probaLimit) {
                int x = r.nextInt(kolumny - 20) + 10;
                int y = r.nextInt(wiersze - 20) + 10;

                // Sprawdzenie, czy nie jest za blisko innego mrowiska
                boolean zaDaleko = true;
                for (Mrowisko m : listaMrowisk) {
                    double dx = m.getX() - x;
                    double dy = m.getY() - y;
                    if (Math.sqrt(dx * dx + dy * dy) < minimalnaOdleglosc) {
                        zaDaleko = false;
                        break;
                    }
                }

                if (zaDaleko) {
                    Mrowisko m = new Mrowisko(x, y, this);
                    listaMrowisk.add(m);
                    listaObiektow.add(m);
                    znaleziono = true;
                }

                proby++;
            }

            if (!znaleziono) {
                System.out.println("Nie udalo się znalezc odpowiedniego miejsca dla mrowiska nr " + (i + 1));
            }
        }
    }

    /**
     * Rysuje tło mapy oraz wszystkie obiekty (pola i obiekty z listy).
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Rysowanie każdego pola według jego typu
        for (int y = 0; y < wiersze; y++) {
            for (int x = 0; x < kolumny; x++) {
                TypObiektu typ = mapa[y][x].typ;

                // Ustaw kolor w zależności od typu
                switch (typ) {
                    case PUSTE -> g.setColor(Color.LIGHT_GRAY);
                    case MROWISKO -> g.setColor(Color.RED.darker());
                    case MROWKA -> g.setColor(Color.BLACK);
                    case LISC -> g.setColor(Color.GREEN.darker());
                    case PATYK -> g.setColor(new Color(139, 69, 19));
                }

                // Rysuj pole
                g.fillRect(x * rozmiarPola, y * rozmiarPola, rozmiarPola, rozmiarPola);
            }
        }

        // Rysowanie wszystkich aktywnych obiektów na mapie
        for (ObiektMapy obj : listaObiektow) {
            if (obj.onMap) {
                obj.drawObject(g, rozmiarPola); // wywołanie metody rysującej obiekt
            }
        }
    }
}