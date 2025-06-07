package logic.mrowki;

import graphics.MapaPanel;

import java.awt.*;
import java.util.List;

public class Zolnierz extends Mrowka{

    public List<Mrowka> fights;
    private final Color kolor;

    public Zolnierz(int x, int y, MapaPanel mapa, Color kolor) {
        super(30,8,x,y);
        this.kolor = kolor;
    }


    // Zolnierz zadaje obrażenia obszarowe
    @Override
    public void dealDamage(double dmg) {
        for(Mrowka m: fights) {
            m.hp -= dmg;
        }
    }

    @Override
    public void drawObject(Graphics g, int rozmiarPola) {
        g.setColor(kolor);
        g.fillRect(x * rozmiarPola,y * rozmiarPola, rozmiarPola * 2, rozmiarPola * 2 );
    }

    @Override
    public void update( ) {
        randomMove();
    }
}
