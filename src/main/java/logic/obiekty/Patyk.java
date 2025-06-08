package logic.obiekty;

import java.awt.*;

public class Patyk extends Przedmiot {
    private int upgradeContribution; // decyduje ile pojedynczy patyk daje do ulepszenia mrowiska

    // Kontruktor domyślny
    public Patyk(int x, int y)
    {
        super(x,y);
        this.upgradeContribution = 1;
    }

    @Override
    public void drawObject(Graphics g, int rozmiarPola) {
        if(onMap) {
            g.setColor(new Color(139, 69, 19)); // brązowy
            g.fillRect(x * rozmiarPola,y * rozmiarPola, rozmiarPola, rozmiarPola);
        }

    }

    public void update() {
    }
}