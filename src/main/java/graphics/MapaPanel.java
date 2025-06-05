package graphics;

import logic.mrowki.Mrowisko;
import logic.rozne.ObiektMapy;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.Random;


public class MapaPanel extends JPanel {
    private final int wiersze = 100;
    private final int kolumny = 100;
    private final int rozmiarPola = 6;

    private final Pole[][] mapa;
    private final Random random = new Random();

    public LinkedList<Mrowisko> listaMrowisk = new LinkedList<>();
    public static LinkedList<ObiektMapy> listaObiektow = new LinkedList<>();


    public MapaPanel() {
        this.setPreferredSize(new Dimension(kolumny * rozmiarPola, wiersze * rozmiarPola));
        mapa = new Pole[wiersze][kolumny];

        // Inicjalizacja mapy: puste pola
        for (int y = 0; y < wiersze; y++) {
            for (int x = 0; x < kolumny; x++) {
                mapa[y][x] = new Pole(TypObiektu.PUSTE);
            }
        }

        Timer timer = new Timer(100, e -> {
           for (ObiektMapy obj : listaObiektow) {
               obj.update();
           }
           repaint();
        });
        timer.start();



        // Losowe rozmieszczenie obiektów
        //losujObiekty(TypObiektu.MROWISKO, 5);
        //losujObiekty(TypObiektu.LISC, 20);
        //losujObiekty(TypObiektu.PATYK, 20);
    }

    /*
    // Metoda, która losowo rozmieszcza obiekty na mapie
    private void losujObiekty(TypObiektu typ, int ile) {
        int dodane = 0;
        while (dodane < ile) {

            // Wybieramy losowe współrzędne
            int x = random.nextInt(kolumny-2);
            int y = random.nextInt(wiersze-2);

            dodajObiekt(typ,x,y);
            dodane++;

        }
    }
    */

    public void dodajObiekt(TypObiektu typ, int x, int y) {
        // Sprawdzamy, czy pole, które wybraliśmy jest puste
        if (x >= 0 && x < kolumny && y >= 0 && y < wiersze) {
            if (mapa[y][x].typ == TypObiektu.PUSTE) {
                mapa[y][x].typ = typ;
            }
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
                    obj.drawObject(g, rozmiarPola);
                }
            }
        }
    }

}

