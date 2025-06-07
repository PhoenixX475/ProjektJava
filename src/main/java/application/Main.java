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
        int n;
        Scanner sc = new Scanner(System.in);
        System.out.println("podaj ilosc mrowisk");
        n = sc.nextInt();

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

            // Tworzenie losowo mrowisk na mapie
            for(int i = 0; i<n;i++) {
                Random rndX = new Random();
                Random rndY = new Random();
                Mrowisko mrowisko = new Mrowisko(rndX.nextInt(90)+5,rndY.nextInt(90)+5,mapa);
                mapa.listaMrowisk.add(mrowisko);
                mapa.listaObiektow.add(mrowisko);
            }




            // Tworzenie patyków i liści na mapie
            Timer timer1 = new Timer(100, e -> {
                Random random = new Random();
                int x = random.nextInt(100);
                int y = random.nextInt(100);
                if(random.nextInt(2) == 1) MapaPanel.listaObiektow.add(new Lisc(x,y));
                else  MapaPanel.listaObiektow.add(new Patyk(x,y));

                mapa.repaint();
            });
            timer1.start();


            MapaPanel.listaObiektow.add(new Patyk(45,45));
            // Tworzenie mrówek przez mrowiska
            Timer timer2 = new Timer(2500, e -> {
                for (Mrowisko m : mapa.listaMrowisk) {
                    if (m.onMap) {
                        m.createAnt(mapa);
                        m.update();
                    }
                }
                mapa.repaint();
            });
            timer2.start();




        });




    }
}
