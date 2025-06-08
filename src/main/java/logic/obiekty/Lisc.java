package logic.obiekty;

import java.awt.*;

public class Lisc extends Przedmiot {
    public static int foodContribution = 25; // decyduje ile jeden lisc daje jedzenia (foodCount mrowiska)
     // jeśli true to mrówka może go podnieść


    // Kontruktor domyślny
    public Lisc(int x, int y) {
        super(x,y);
    }

    @Override
    public void drawObject(Graphics g, int rozmiarPola) {
        if(onMap) {
            g.setColor(Color.GREEN.darker());
            g.fillRect(x * rozmiarPola,y * rozmiarPola, rozmiarPola, rozmiarPola);
        }

    }

    public void update() {
    }


}

