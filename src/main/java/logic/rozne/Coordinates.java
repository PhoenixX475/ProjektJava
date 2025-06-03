package logic.rozne;

import java.util.Random;


/**
 * Klasa rozne.Coordinates jest odpowiedzialna za przechowywanie
 * położenia dla konkretnego obiektu gdzie jest użyta
 */

public class Coordinates {
    public Random random = new Random();
    public int x;
    public int y;


    public Coordinates() {
        this.x = random.nextInt();
        this.y = random.nextInt();
    }
}
