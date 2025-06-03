package logic.mrowki;

import java.util.List;

public class Zolnierz extends Mrowka{

    public List<Mrowka> fights;

    public Zolnierz() {
        super(30,8);
    }


    // Zolnierz zadaje obrażenia obszarowe
    @Override
    public void setDamage(double dmg) {
        for(Mrowka m: fights) {
            m.hp -= dmg;
        }
    }

}
