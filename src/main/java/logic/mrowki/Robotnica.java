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
        Add comment
        More actions


        if (holding == null && targeting instanceof Przedmiot) {


            if (targeting.x == x && targeting.y == y) {


                ((Przedmiot) targeting).onMap = false;


                holding = targeting;
                targeting = myMrowisko;
                //System.out.println(this + " podniosła przedmiot i wraca do mrowiska");
            }
        }
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
