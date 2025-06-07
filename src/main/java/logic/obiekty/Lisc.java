package logic.obiekty;

import logic.rozne.ObiektMapy;

import java.awt.*;

public class Lisc extends Przedmiot {
    private int foodContribution; // decyduje ile jeden lisc daje jedzenia (foodCount mrowiska)
     // jeśli true to mrówka może go podnieść


    // Kontruktor domyślny
    public Lisc(int x, int y) {
        super(x,y);
        this.foodContribution = 5;
        this.onGround = true;
    }

    @Override
    public void drawObject(Graphics g, int rozmiarPola) {
        if(onGround) {
            g.setColor(Color.GREEN.darker());
            g.fillRect(x * rozmiarPola,y * rozmiarPola, rozmiarPola, rozmiarPola);
        }

    }

    public void update() {
    }


}

