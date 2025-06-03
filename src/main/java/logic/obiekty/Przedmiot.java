package logic.obiekty;
import logic.rozne.Coordinates;

public class Przedmiot {
    public boolean picked;
    public Coordinates coordinates;

    public Przedmiot() {
        this.picked = false;
    }

    public void gotPickedUp() {
        // usuwamy go z mapy ( staje się niewidzialny )
    }
}