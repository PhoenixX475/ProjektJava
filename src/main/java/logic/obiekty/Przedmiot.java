package logic.obiekty;
import logic.rozne.ObiektMapy;

//Klasa abstrakcyjna łącząca Liście i patyki jako obiekty, które mrówki mogą podnieść

public abstract class Przedmiot extends ObiektMapy {
    public Przedmiot(int x, int y) {
        super(x,y);
    }
}

