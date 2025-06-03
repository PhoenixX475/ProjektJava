package logic.mrowki;
import logic.obiekty.*;

import java.util.List;

/**Robotnica jest odpowiedzialna za podnoszenie przedmitów i przenoszenie ich do Mrowiska
 * W celu utrzymania go
 */


public class Robotnica extends Mrowka {

    // Pola robotnicy
    protected Przedmiot holding;


    public Robotnica() {
        super(10,3);
        this.holding = null;
    }

    public void checkPrzedmiotnearby() {

    }

    public void grab() {
        // Jeśli robotnica nic nie trzyma to może podnieść przedmiot
        if(holding == null) {}
    }

    public void addPrzedmiot() {
        /*if(holding != null && nearMrowisko == true) {
            if(holding instanceof Lisc) {

            }
        }*/
    }

}
