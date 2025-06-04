package graphics;

import logic.mrowki.Mrowisko;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

public class MapaPanel extends JPanel {
    private final int wiersze = 100;
    private final int kolumny = 100;
    private final int rozmiarPola = 6;

    private final Pole[][] mapa;
    private final Random random = new Random();

    private LinkedList<Mrowisko> listaMrowisk = new LinkedList<>();


    public MapaPanel() {
        this.setPreferredSize(new Dimension(kolumny * rozmiarPola, wiersze * rozmiarPola));
        mapa = new Pole[wiersze][kolumny];

        // Inicjalizacja mapy: puste pola
        for (int y = 0; y < wiersze; y++) {
            for (int x = 0; x < kolumny; x++) {
                mapa[y][x] = new Pole(TypObiektu.PUSTE);
            }
        }

        // Losowe rozmieszczenie obiektów
        losujObiekty(TypObiektu.MROWISKO, 5);
        losujObiekty(TypObiektu.LISC, 20);
        losujObiekty(TypObiektu.PATYK, 20);
    }

    // Metoda, która losowo rozmieszcza obiekty na mapie
    private void losujObiekty(TypObiektu typ, int ile) {
        int dodane = 0;
        while (dodane < ile) {

            // Wybieramy losowe współrzędne
            int x = random.nextInt(kolumny-2);
            int y = random.nextInt(wiersze-2);

            // Sprawdzamy, czy pole, które wybraliśmy jest puste
            boolean wolne = true;
            for(int i = 0; i<3;i++) {
                for(int j = 0;j<3;j++) {
                    if (mapa[y+i][x+j].typ != TypObiektu.PUSTE) {
                        wolne = false;
                    }
                }
            }

            // Jeśli puste to stawiamy tam obiekt
            if(wolne && typ == TypObiektu.MROWISKO) {
                for(int i = 0;i<3;i++) {
                    for(int j = 0;j<3;j++) {
                        mapa[y+i][x+j].typ = typ;
                        listaMrowisk.add(new Mrowisko (x+1,y+1));
                    }
                }
            }
            else{
                mapa[y][x].typ = typ;
            }
            dodane++;

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
                    case MROWKA -> g.setColor(Color.RED);
                    case LISC -> g.setColor(Color.GREEN.darker());
                    case PATYK -> g.setColor(new Color(139, 69, 19)); // brązowy
                }

                g.fillRect(x * rozmiarPola, y * rozmiarPola, rozmiarPola, rozmiarPola);
            }
        }
    }

}

