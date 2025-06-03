package application;

import logic.mrowki.*;

/**
 * Główna klasa aplikacji
 * tutaj jest wywoływana symulacja
 * */

public class Main {
    public static void main(String[] args) {
        Mrowisko m = new Mrowisko(1);
        m.createAnt();
        m.wypiszMrowki();

    }
}
