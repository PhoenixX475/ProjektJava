package logic.obiekty;
import logic.rozne.Coordinates;
import logic.rozne.ObiektMapy;

public abstract class Przedmiot extends ObiektMapy {
    boolean onGround;
    public Przedmiot(int x, int y) {
        super(x,y);
        this.onGround = true;
    }
}


/*
public class Przedmiot extends ObiektMapy {
    public boolean picked;
    public Coordinates coordinates;

    public Przedmiot() {
        this.picked = false;
    }

    public void gotPickedUp() {
        // usuwamy go z mapy ( staje się niewidzialny )
    }
}

 */