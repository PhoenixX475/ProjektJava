package logic.mrowki;

import java.util.List;

public class Zolnierz extends Mrowka{

    public List<Mrowka> fights;

    public Zolnierz(int x, int y) {
        super(30,8,x,y);
    }


    // Zolnierz zadaje obrażenia obszarowe
    @Override
    public void dealDamage(double dmg) {
        for(Mrowka m: fights) {
            m.hp -= dmg;
        }
    }

}
