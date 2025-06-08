package logic.mrowki;
import graphics.MapaPanel;
import logic.obiekty.*;
import logic.rozne.ObiektMapy;

import java.awt.*;

import static java.lang.Math.abs;

/**Robotnica jest odpowiedzialna za podnoszenie przedmiotów i przenoszenie ich do Mrowiska
 * W celu utrzymania go
 */


public class Robotnica extends Mrowka {

    // Pola robotnicy
    public ObiektMapy holding;
    private final Color kolor;


    public Robotnica(int x, int y,Mrowisko mrowisko,MapaPanel mapa, Color kolor) {
        super(10,3,x,y,mrowisko);
        this.holding = null;
        this.fights = null;
        this.targeting = null;
        this.kolor = kolor.brighter().brighter();
    }


    public void grab() {
        if (holding == null && targeting instanceof Przedmiot) {
            if (targeting.x == x && targeting.y == y) {
                ((Przedmiot)targeting).onMap = false;
                holding = targeting;
                targeting = myMrowisko;
                //System.out.println(this + " podniosła przedmiot i wraca do mrowiska");
            }
        }
    }

    public void returnToMrowisko() {
        if (holding != null && targeting == myMrowisko) {
            if (x == myMrowisko.x && y == myMrowisko.y) {
                if (holding instanceof Lisc) {
                    myMrowisko.foodCount += Lisc.foodContribution;
                } else if (holding instanceof Patyk) {
                    myMrowisko.stickCount += Patyk.upgradeContribution;
                }
                holding = null;
                targeting = null;
                //System.out.println("Mrowka dodała przedmiot do mrowiska");
            }
        }
    }


    @Override
    public void drawObject(Graphics g, int rozmiarPola ) {
        g.setColor(kolor);
        g.fillRect(x * rozmiarPola,y * rozmiarPola, rozmiarPola, rozmiarPola );
    }

    @Override
    public void update() {

        // Ruch
        if (targeting != null) {
            moveToTarget();
        } else {
            randomMove();
        }

        // Sprawdź obiekty w zasięgu
        ObiektMapy obj = checkArea(MapaPanel.listaObiektow);

        // Jeśli nic nie trzymamy i nie mamy celu – targetuj przedmiot
        if (holding == null && targeting == null && obj instanceof Przedmiot) {
            targeting = obj;
        }

        // Jeśli trzymamy przedmiot i nie mamy celu – wracamy do mrowiska
        if (holding != null) {
            targeting = myMrowisko;
        }

        // Jeśli napotkamy na mrówkę
        if(holding == null && targeting == null && ( obj instanceof Mrowka || obj instanceof Mrowisko)) {
            targeting = obj;
        }

        if(targeting instanceof Mrowka || targeting instanceof  Mrowisko) {
            fights = targeting;
            attackTarget();
        }






        die();
        regeneration();
        grab();
        returnToMrowisko();



    }

}
