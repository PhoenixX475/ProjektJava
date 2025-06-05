package logic.obiekty;

import logic.rozne.ObiektMapy;

import java.awt.*;

public class Lisc extends ObiektMapy {
    private int foodContribution; // decyduje ile jeden lisc daje jedzenia (foodCount mrowiska)
    public boolean onGround; // jeśli true to mrówka może go podnieść


    // Kontruktor domyślny
    public Lisc(int x, int y) {
        super(x,y);
        this.foodContribution = 5;
        this.onGround = true;
    }

    public void drawObject(Graphics g, int rozmiarPola) {
        if(onGround) {
            g.setColor(Color.GREEN.darker());
            g.fillRect(x * rozmiarPola,y * rozmiarPola, rozmiarPola, rozmiarPola);
        }

    }

    public void update() {

    }
}

