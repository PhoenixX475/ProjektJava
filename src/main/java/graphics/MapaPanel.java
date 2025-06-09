package graphics;

import logic.mrowki.Mrowisko;
import logic.mrowki.Mrowka;
import logic.mrowki.Robotnica;
import logic.obiekty.Lisc;
import logic.obiekty.Patyk;
import logic.rozne.ObiektMapy;

import javax.swing.*;
import java.awt.*;
import java.io.ObjectStreamException;
import java.util.LinkedList;
import java.util.Random;
import java.util.ArrayList;


public class MapaPanel extends JPanel {

    // Pola odpowiedzialne za budowę mapy
    private static final int wiersze = 100;
    private static final int kolumny = 100;
    private final int rozmiarPola = 6;
    private static final Pole[][] mapa = new Pole[wiersze][kolumny];

    private final Random random = new Random();

    // Listy przechowujące obiekty używane w symulacji
    public static LinkedList<Mrowisko> listaMrowisk = new LinkedList<>();
    public static LinkedList<ObiektMapy> listaObiektow = new LinkedList<>();
    public static LinkedList<ObiektMapy> doUsuniecia = new LinkedList<>();


    //pole odpowiedzialne za zakonczenie symulacji
    private int iloscMrowisk = listaMrowisk.size();
    private int czasTrwaniaSymulacji = -1; // w milisekundach
    private long startTime = -1;
    private boolean symulacjaAktywna = true;
    private boolean choice;






    public MapaPanel(int liczbaMrowisk, int czasMrowki, int czasLisci, int czasPatykow, int czasTrwania, boolean choice) {

        this.setPreferredSize(new Dimension(kolumny * rozmiarPola, wiersze * rozmiarPola));

        // Inicjalizacja mapy: puste pola
        for (int y = 0; y < wiersze; y++) {
            for (int x = 0; x < kolumny; x++) {
                mapa[y][x] = new Pole(TypObiektu.PUSTE);
            }
        }

        updateMap();
        spawnAnts(czasMrowki);
        spawnLisc(czasLisci);
        spawnPatyk(czasPatykow);
    }

    public void rozpocznijSymulacje(int czasTrwania) {
        this.czasTrwaniaSymulacji = czasTrwania;
        rozpocznijSymulacje(this.choice, czasTrwania);
    }
    private Runnable onSimulationEnd;
    public void setOnSimulationEnd(Runnable callback) {
        this.onSimulationEnd = callback;
    }


    public static LinkedList<Mrowisko> getMrowiska() {
        return listaMrowisk;
    }


    public void rozpocznijSymulacje(boolean choice, int czasTrwania) {
        switch (String.valueOf(choice)) {
            case "false" -> {
                Timer battleTimer = new Timer(1000, e -> {
                    long aktywneMrowiska = listaMrowisk.stream().filter(m -> m.onMap).count();
                    if (aktywneMrowiska <= 1 && symulacjaAktywna) {
                        symulacjaAktywna = false;
                        if (onSimulationEnd != null) {
                            onSimulationEnd.run();  // <- wywołanie hooka
                        }
                        JOptionPane.showMessageDialog(this, "Zwycieskie mrowisko!");
                        ((Timer) e.getSource()).stop(); // 🔴 Zatrzymaj timer
                        // Opcjonalnie zakończ aplikację
                        Timer exitTimer = new Timer(1000, ev -> System.exit(0));
                        exitTimer.setRepeats(false);
                        exitTimer.start();
                    }
                });
                battleTimer.start();
            }
            case "true" -> {


                // Tryb Timer – kończy się po zadanym czasie


                if (czasTrwaniaSymulacji <= 0) {


                    this.czasTrwaniaSymulacji = czasTrwania;


                    this.startTime = System.currentTimeMillis();


                    Timer koniecTimer = new Timer(czasTrwania, e -> {


                        symulacjaAktywna = false;


                        JOptionPane.showMessageDialog(this, "Symulacja zakonczona.");

                        if (listaMrowisk.size() <= 1) ((Timer)e.getSource()).stop();
                        ((Timer) e.getSource()).stop(); // Zatrzymaj timer


                        if (onSimulationEnd != null) {
                            onSimulationEnd.run();  // <- wywołanie hooka (czyli statystyki)
                        }


                        // Opcjonalnie: zakończenie programu po chwili
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




    public void updateMap() {
        // Aktualizowanie programu
        Timer timer = new Timer(250, e -> {

            for (ObiektMapy obj : listaObiektow) {
                if(obj.onMap) {
                    obj.update();
                }
            }
            // Jeśli obiekt już nie powinien być na mapie to dodaj go do listy obiektów do usuniecia
            for(ObiektMapy o : listaObiektow) {
                if(!o.onMap) doUsuniecia.add(o);
            }
            // usun te obiekty
            for(ObiektMapy u : doUsuniecia) {
                if(listaObiektow.contains(u)) {
                    listaObiektow.remove(u);
                    listaMrowisk.remove(u);
                }
            }
            doUsuniecia.clear();


            if (listaMrowisk.size() <= 1 || !symulacjaAktywna) ((Timer)e.getSource()).stop();


            repaint();
        });
        timer.start();
    }




    private void spawnAnts(int czasMrowki) {
        // Timer: tworzenie mrówek
        Timer mrowkiTimer = new Timer(czasMrowki, e -> {
            for (Mrowisko m : listaMrowisk) {
                m.createAnt(this);
            }

            if (listaMrowisk.size() <= 1 || !symulacjaAktywna) ((Timer)e.getSource()).stop();
        });
        mrowkiTimer.start();
    }
    private void spawnLisc(int czasLisci) {
        Timer liscieTimer = new Timer(czasLisci, e -> {
            int x = random.nextInt(kolumny);
            int y = random.nextInt(wiersze);

            if (mapa[y][x].typ == TypObiektu.PUSTE) {
                Lisc lisc = new Lisc(x, y);
                listaObiektow.add(lisc);
                //mapa[y][x].typ = TypObiektu.LISC;
            }

            if (listaMrowisk.size() <= 1 || !symulacjaAktywna) ((Timer)e.getSource()).stop();


        });


        liscieTimer.start();
    }
    private void spawnPatyk(int czasPatykow) {
        Timer patykiTimer = new Timer(czasPatykow, e -> {
            int x = random.nextInt(kolumny);


            int y = random.nextInt(wiersze);
            if (mapa[y][x].typ == TypObiektu.PUSTE) {
                Patyk patyk = new Patyk(x, y);
                listaObiektow.add(patyk);
                //mapa[y][x].typ = TypObiektu.PATYK;
            }

            if (listaMrowisk.size() <= 1 || !symulacjaAktywna) ((Timer)e.getSource()).stop();


        });


        patykiTimer.start();
    }

    public void dodajLosoweMrowiska(int ile){
        Random r = new Random();
        for (int i = 0; i < ile; i++) {
            int x = r.nextInt(90);
            int y = r.nextInt(90);

            Mrowisko m = new Mrowisko(x, y, this);
            listaMrowisk.add(m);
            listaObiektow.add(m);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Malowanie mapy
        for (int y = 0; y < wiersze; y++) {
            for (int x = 0; x < kolumny; x++) {
                TypObiektu typ = mapa[y][x].typ;

                switch (typ) {
                    case PUSTE -> g.setColor(Color.LIGHT_GRAY);
                    case MROWISKO -> g.setColor(Color.RED.darker());
                    case MROWKA -> g.setColor(Color.BLACK);
                    case LISC -> g.setColor(Color.GREEN.darker());
                    case PATYK -> g.setColor(new Color(139, 69, 19)); // brązowy
                }

                g.fillRect(x * rozmiarPola, y * rozmiarPola, rozmiarPola, rozmiarPola);

                for (ObiektMapy obj : listaObiektow) {
                    if(obj.onMap) {
                        obj.drawObject(g, rozmiarPola);
                    }
                }
            }
        }
    }

}

