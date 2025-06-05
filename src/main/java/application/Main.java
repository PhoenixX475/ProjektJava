package application;
import javax.swing.*;
import graphics.MapaPanel;
import graphics.TypObiektu;
import logic.mrowki.*;
import logic.obiekty.Lisc;
import logic.obiekty.Patyk;

import java.util.Random;
import java.util.Scanner;

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
            // Stworzenie mapy
            JFrame frame = new JFrame("Symulacja Mrowisk");
            MapaPanel mapa = new MapaPanel();

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

            Mrowisko mrowisko = new Mrowisko(50,50, mapa);
            mapa.listaMrowisk.add(mrowisko);
            mapa.listaObiektow.add(mrowisko);



            Mrowisko mrowisko1 = new Mrowisko(30,90, mapa);
            mapa.listaMrowisk.add(mrowisko1);
            mapa.listaObiektow.add(mrowisko1);
            Mrowisko mrowisko2 = new Mrowisko(70,10, mapa);
            mapa.listaMrowisk.add(mrowisko2);
            mapa.listaObiektow.add(mrowisko2);




            // Tworzenie patyków i liści na mapie
            Timer timer1 = new Timer(500, e -> {
                Random random = new Random();
                int x = random.nextInt(100);
                int y = random.nextInt(100);
                if(random.nextInt(2) == 1) mapa.listaObiektow.add(new Lisc(x,y));
                //mapa.dodajObiekt(TypObiektu.LISC,x,y);
                else mapa.dodajObiekt(TypObiektu.PATYK,x,y);

                mapa.repaint();
            });
            timer1.start();

            // Tworzenie mrówek przez mrowiska
            Timer timer2 = new Timer(3000, e -> {
                for (Mrowisko m : mapa.listaMrowisk) {
                    m.createAnt(mapa);
                    m.update();
                }
                mapa.repaint();
            });
            timer2.start();

            // Przykładowe ulepszanie mrowiska nie pojawi się w końcowej wersji programu
            Timer timer3 = new Timer(4000, e -> {
               mrowisko.stickCount += 10;
            });
            timer3.start();


        });




    }
}
