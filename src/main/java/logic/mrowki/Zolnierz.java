package logic.mrowki;

import java.awt.*;
import java.util.List;

public class Zolnierz extends Mrowka{

    public List<Mrowka> fights;

    public Zolnierz(int x, int y,Mrowisko mrowisko) {
        super(30,8,x,y,mrowisko);
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
        g.setColor(Color.BLACK);
        g.fillRect(x * rozmiarPola,y * rozmiarPola, rozmiarPola * 2, rozmiarPola * 2 );
    }

    @Override
    public void update( ) {
        randomMove();
    }
}
