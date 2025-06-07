package graphics;

import logic.mrowki.Mrowisko;
import logic.mrowki.Robotnica;
import logic.obiekty.Lisc;
import logic.obiekty.Patyk;
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

    public MapaPanel(int liczbaMrowisk, int czasMrówki, int czasLiści, int czasPatyków) {
        this.setPreferredSize(new Dimension(kolumny * rozmiarPola, wiersze * rozmiarPola));
        mapa = new Pole[wiersze][kolumny];

        for (int y = 0; y < wiersze; y++) {
            for (int x = 0; x < kolumny; x++) {
                mapa[y][x] = new Pole(TypObiektu.PUSTE);
            }
        }



        // Timer do animacji i update'ów
        Timer renderTimer = new Timer(100, e -> {
            for (ObiektMapy obj : listaObiektow) {
                obj.update();
            }
            repaint();
        });
        renderTimer.start();

        // Timer: tworzenie mrówek
        Timer mrowkiTimer = new Timer(czasMrówki, e -> {
            for (Mrowisko m : listaMrowisk) {
                m.createAnt(this);
            }
        });
        mrowkiTimer.start();

        // Timer: tworzenie liści
        Timer liscieTimer = new Timer(czasLiści, e -> {
            int x = random.nextInt(kolumny);
            int y = random.nextInt(wiersze);
            if (mapa[y][x].typ == TypObiektu.PUSTE) {
                Lisc lisc = new Lisc(x, y);
                listaObiektow.add(lisc);
                mapa[y][x].typ = TypObiektu.LISC;
            }
        });
        liscieTimer.start();

        // Timer: tworzenie patyków
        Timer patykiTimer = new Timer(czasPatyków, e -> {
            int x = random.nextInt(kolumny);
            int y = random.nextInt(wiersze);
            if (mapa[y][x].typ == TypObiektu.PUSTE) {
                Patyk patyk = new Patyk(x, y);
                listaObiektow.add(patyk);
                mapa[y][x].typ = TypObiektu.PATYK;
            }
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

        // Malowanie tła
        for (int y = 0; y < wiersze; y++) {
            for (int x = 0; x < kolumny; x++) {
                TypObiektu typ = mapa[y][x].typ;

                switch (typ) {
                    case PUSTE -> g.setColor(Color.LIGHT_GRAY);
                    case MROWISKO -> g.setColor(Color.RED.darker());
                    case MROWKA -> g.setColor(Color.BLACK);
                    case LISC -> g.setColor(Color.GREEN.darker());
                    case PATYK -> g.setColor(new Color(139, 69, 19));
                }

                g.fillRect(x * rozmiarPola, y * rozmiarPola, rozmiarPola, rozmiarPola);
            }
        }

        for (ObiektMapy obj : listaObiektow) {
            obj.drawObject(g, rozmiarPola);
        }
    }
}


