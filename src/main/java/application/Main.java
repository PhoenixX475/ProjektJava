package application;

import graphics.MapaPanel;
import graphics.StartMenu;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame dummyFrame = new JFrame(); // Potrzebny do osadzenia StartMenu
            StartMenu menu = new StartMenu(dummyFrame);
            menu.setVisible(true);

            if (!menu.isStarted()) {
                System.exit(0); // Zamknięcie, jeśli użytkownik nie kliknął Start
            }

            int liczbaMrowisk = menu.getLiczbaMrowisk();
            int czasMrowki = menu.getMrowkiInterval();
            int czasPrzedmioty = menu.getPrzedmiotyInterval();

            JFrame frame = new JFrame("Symulacja Mrowisk");
            MapaPanel mapa = new MapaPanel(liczbaMrowisk, czasMrowki, czasPrzedmioty, czasPrzedmioty);

            mapa.dodajLosoweMrowiska(liczbaMrowisk);

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(mapa);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            // Wyjście ESC
            mapa.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), "exit");
            mapa.getActionMap().put("exit", new AbstractAction() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    System.exit(0);
                }
            });
        });
    }
}
