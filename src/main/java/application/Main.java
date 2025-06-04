package application;
import javax.swing.*;
import graphics.MapaPanel;
import logic.mrowki.*;

/**
 * Główna klasa aplikacji
 * tutaj jest wywoływana symulacja
 * */

/**
 * Wszystkie obiekty zapisz w 2d tablicy i to wyświetlaj na mapę
 * Żeby sprawdzić czy coś jest blisko to z tego weź*/

/*
public class Main {
    public static void main(String[] args) {
        Mrowisko m = new Mrowisko(1);
        m.createAnt();
        m.wypiszMrowki();

    }
}
*/

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Symulacja Mrowisk");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(new MapaPanel());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });


    }
}
