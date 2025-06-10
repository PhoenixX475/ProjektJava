package graphics;

import logic.mrowki.Mrowisko;
import logic.obiekty.Lisc;
import logic.obiekty.Patyk;
import logic.rozne.ObiektMapy;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.Random;

public class MapaPanel extends JPanel {

    // Stałe definiujące rozmiar mapy
    private static final int wiersze = 100;  // Liczba wierszy mapy
    private static final int kolumny = 100;  // Liczba kolumn mapy
    private final int rozmiarPola = 6;       // Rozmiar pojedynczego pola w pikselach
    private static final Pole[][] mapa = new Pole[wiersze][kolumny]; // Dwuwymiarowa tablica pól mapy
    private final Random random = new Random(); // Generator liczb losowych

    // Kolekcje przechowujące obiekty na mapie
    public static LinkedList<Mrowisko> listaMrowisk = new LinkedList<>();    // Lista mrowisk
    public static LinkedList<ObiektMapy> listaObiektow = new LinkedList<>(); // Lista wszystkich obiektów
    public static LinkedList<ObiektMapy> doUsuniecia = new LinkedList<>();   // Lista obiektów do usunięcia

    // Zmienne kontrolujące przebieg symulacji
    private int iloscMrowisk = listaMrowisk.size();         // Aktualna liczba mrowisk
    private int czasTrwaniaSymulacji = -1;                  // Czas trwania symulacji w ms (-1 = nieskończoność)
    private long startTime = -1;                            // Czas rozpoczęcia symulacji
    private boolean symulacjaAktywna = true;                // Flaga aktywności symulacji
    private boolean choice;                                 // Wybór trybu symulacji

    // Konstruktor panelu mapy
    public MapaPanel(int liczbaMrowisk, int czasMrowki, int czasLisci, int czasPatykow, int czasTrwania, boolean choice) {
        this.setPreferredSize(new Dimension(kolumny * rozmiarPola, wiersze * rozmiarPola));

        // Inicjalizacja pustej mapy
        for (int y = 0; y < wiersze; y++) {
            for (int x = 0; x < kolumny; x++) {
                mapa[y][x] = new Pole(TypObiektu.PUSTE);
            }
        }

        updateMap();            // Uruchomienie aktualizacji mapy
        spawnAnts(czasMrowki);  // Uruchomienie tworzenia mrówek
        spawnLisc(czasLisci);   // Uruchomienie tworzenia liści
        spawnPatyk(czasPatykow);// Uruchomienie tworzenia patyków
    }

    // Metoda rozpoczynająca symulację (wersja z czasem trwania)
    public void rozpocznijSymulacje(int czasTrwania) {
        this.czasTrwaniaSymulacji = czasTrwania;
        rozpocznijSymulacje(this.choice, czasTrwania);
    }

    // Interfejs callbacku końca symulacji
    private Runnable onSimulationEnd;

    // Ustawienie callbacku wywoływanego po zakończeniu symulacji
    public void setOnSimulationEnd(Runnable callback) {
        this.onSimulationEnd = callback;
    }

    // Metoda zwracająca listę mrowisk
    public static LinkedList<Mrowisko> getMrowiska() {
        return listaMrowisk;
    }

    // Metoda aktualizująca stan mapy w regularnych odstępach czasu
    public void updateMap() {
        Timer timer = new Timer(250, e -> {
            // Aktualizacja stanu wszystkich obiektów
            for (ObiektMapy obj : listaObiektow) {
                if(obj.onMap) {
                    obj.update();
                }
            }

            // Znajdź obiekty do usunięcia
            for(ObiektMapy o : listaObiektow) {
                if(!o.onMap) doUsuniecia.add(o);
            }

            // Usuń oznaczone obiekty
            for(ObiektMapy u : doUsuniecia) {
                if(listaObiektow.contains(u)) {
                    listaObiektow.remove(u);
                    System.out.println("Usunieto obiekt" + u);
                }
            }
            doUsuniecia.clear();

            repaint(); // Odśwież widok
        });
        timer.start();
    }

    // Metoda tworząca nowe mrówki w regularnych odstępach czasu
    private void spawnAnts(int czasMrowki) {
        Timer mrowkiTimer = new Timer(czasMrowki, e -> {
            for (Mrowisko m : listaMrowisk) {
                m.createAnt(this);
            }
        });
        mrowkiTimer.start();
    }

    // Metoda tworząca nowe liście w losowych miejscach mapy
    private void spawnLisc(int czasLisci) {
        Timer liscieTimer = new Timer(czasLisci, e -> {
            int x = random.nextInt(kolumny);
            int y = random.nextInt(wiersze);

            if (mapa[y][x].typ == TypObiektu.PUSTE) {
                Lisc lisc = new Lisc(x, y);
                listaObiektow.add(lisc);
            }
        });
        liscieTimer.start();
    }

    // Metoda tworząca nowe patyki w losowych miejscach mapy
    private void spawnPatyk(int czasPatykow) {
        Timer patykiTimer = new Timer(czasPatykow, e -> {
            int x = random.nextInt(kolumny);
            int y = random.nextInt(wiersze);

            if (mapa[y][x].typ == TypObiektu.PUSTE) {
                Patyk patyk = new Patyk(x, y);
                listaObiektow.add(patyk);
            }
        });
        patykiTimer.start();
    }

    // Metoda dodająca losowo rozmieszczone mrowiska z zachowaniem minimalnej odległości
    public void dodajLosoweMrowiska(int ile) {
        Random r = new Random();
        int minimalnaOdleglosc = 15; // Minimalna odległość między mrowiskami
        int probaLimit = 1000;       // Maksymalna liczba prób znalezienia miejsca

        for (int i = 0; i < ile; i++) {
            boolean znaleziono = false;
            int proby = 0;

            // Próba znalezienia odpowiedniego miejsca
            while (!znaleziono && proby < probaLimit) {
                int x = r.nextInt(kolumny);
                int y = r.nextInt(wiersze);
                boolean zaDaleko = true;

                // Sprawdź odległość od istniejących mrowisk
                for (Mrowisko m : listaMrowisk) {
                    int mx = m.getX();
                    int my = m.getY();

                    double dx = mx - x;
                    double dy = my - y;
                    double odleglosc = Math.sqrt(dx * dx + dy * dy);

                    if (odleglosc < minimalnaOdleglosc) {
                        zaDaleko = false;
                        break;
                    }
                }

                // Jeśli miejsce odpowiednie, dodaj mrowisko
                if (zaDaleko) {
                    Mrowisko m = new Mrowisko(x, y, this);
                    listaMrowisk.add(m);
                    listaObiektow.add(m);
                    znaleziono = true;
                }

                proby++;
            }

            if (!znaleziono) {
                System.out.println("Nie udało się znaleźć odpowiedniego miejsca dla mrowiska nr " + (i + 1));
            }
        }
    }

    // Główna metoda rozpoczynająca symulację w wybranym trybie
    public void rozpocznijSymulacje(boolean choice, int czasTrwania) {
        switch (String.valueOf(choice)) {
            case "false" -> { // Tryb Battle Royale
                Timer battleTimer = new Timer(1000, e -> {
                    // Sprawdź liczbę aktywnych mrowisk
                    long aktywneMrowiska = listaMrowisk.stream().filter(m -> m.onMap).count();

                    // Jeśli zostało 1 lub mniej, zakończ symulację
                    if (aktywneMrowiska <= 1 && symulacjaAktywna) {
                        symulacjaAktywna = false;

                        // Wywołaj callback końca symulacji
                        if (onSimulationEnd != null) {
                            onSimulationEnd.run();
                        }

                        JOptionPane.showMessageDialog(this, "Zwycięskie mrowisko!");
                        ((Timer) e.getSource()).stop();

                        // Zamknij program po chwili
                        Timer exitTimer = new Timer(1000, ev -> System.exit(0));
                        exitTimer.setRepeats(false);
                        exitTimer.start();
                    }
                });
                battleTimer.start();
            }

            case "true" -> { // Tryb Timer
                if (czasTrwaniaSymulacji <= 0) {
                    this.czasTrwaniaSymulacji = czasTrwania;
                    this.startTime = System.currentTimeMillis();

                    // Timer kończący symulację po zadanym czasie
                    Timer koniecTimer = new Timer(czasTrwania, e -> {
                        symulacjaAktywna = false;

                        JOptionPane.showMessageDialog(this, "Symulacja zakończona.");
                        ((Timer) e.getSource()).stop();

                        // Wywołaj callback końca symulacji
                        if (onSimulationEnd != null) {
                            onSimulationEnd.run();
                        }

                        // Zamknij program po chwili
                        Timer exitTimer = new Timer(1000, ev -> System.exit(0));
                        exitTimer.setRepeats(false);
                        exitTimer.start();
                    });
                    koniecTimer.setRepeats(false);
                    koniecTimer.start();
                }
            }
        }
    }

    // Metoda rysująca mapę i obiekty na niej
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Rysowanie tła mapy
        for (int y = 0; y < wiersze; y++) {
            for (int x = 0; x < kolumny; x++) {
                TypObiektu typ = mapa[y][x].typ;

                // Ustaw kolor w zależności od typu pola
                switch (typ) {
                    case PUSTE -> g.setColor(Color.LIGHT_GRAY);
                    case MROWISKO -> g.setColor(Color.RED.darker());
                    case MROWKA -> g.setColor(Color.BLACK);
                    case LISC -> g.setColor(Color.GREEN.darker());
                    case PATYK -> g.setColor(new Color(139, 69, 19)); // brązowy
                }

                g.fillRect(x * rozmiarPola, y * rozmiarPola, rozmiarPola, rozmiarPola);

                // Rysowanie obiektów na mapie
                for (ObiektMapy obj : listaObiektow) {
                    if(obj.onMap) {
                        obj.drawObject(g, rozmiarPola);
                    }
                }
            }
        }
    }
}