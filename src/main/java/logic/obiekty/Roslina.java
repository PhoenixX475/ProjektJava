package logic.obiekty;

import java.util.Random;


public class Roslina {
    private int durability;
    private Random rnd = new Random();


    // Agregacja
    private Lisc lisc;
    private Patyk patyk;


    // Kontruktor domyślny
    public Roslina() {
        this.durability = 10;
    }


    // Metoda odpowiedzialna za zniszczenie rosliny i pozostawienie liści i patyków na ziemi



}
