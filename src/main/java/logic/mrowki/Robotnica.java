package logic.mrowki;
import graphics.MapaPanel;
import logic.obiekty.*;
import logic.rozne.ObiektMapy;

import java.awt.*;
import java.util.List;

/**Robotnica jest odpowiedzialna za podnoszenie przedmiotów i przenoszenie ich do Mrowiska
 * W celu utrzymania go
 */


public class Robotnica extends Mrowka {

    // Pola robotnicy
    protected Przedmiot holding;
    private final Color kolor;


    public Robotnica(int x, int y, MapaPanel mapa, Color kolor) {
        super(10,3,x,y);
        this.holding = null;
        this.kolor = kolor;
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

    @Override
    public void drawObject(Graphics g, int rozmiarPola ) {
        g.setColor(kolor);
        g.fillRect(x * rozmiarPola,y * rozmiarPola, rozmiarPola, rozmiarPola );
    }

    @Override
    public void update( ) {
        randomMove();
        checkArea(MapaPanel.listaObiektow);
    }

}
