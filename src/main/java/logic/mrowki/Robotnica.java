package logic.mrowki;
import graphics.MapaPanel;
import logic.obiekty.*;
import logic.rozne.ObiektMapy;

import java.awt.*;

import static java.lang.Math.abs;

/**
 * Klasa reprezentująca robotnicę - mrówkę odpowiedzialną za podnoszenie przedmiotów
 * i przenoszenie ich do mrowiska w celu jego utrzymania.
 */
public class Robotnica extends Mrowka {

    // Przedmiot aktualnie niesiony przez robotnicę (może być null)
    public ObiektMapy holding;

    // Kolor robotnicy do rysowania (jaśniejszy od koloru mrowiska)
    private final Color kolor;

    /**
     * Konstruktor robotnicy.
     * @param x pozycja X
     * @param y pozycja Y
     * @param mrowisko mrowisko, do którego należy robotnica
     * @param mapa panel mapy (potrzebny do interakcji)
     * @param kolor kolor robotnicy (bazowany na kolorze mrowiska)
     */
    public Robotnica(int x, int y, Mrowisko mrowisko, MapaPanel mapa, Color kolor) {
        super(10, 3, x, y, mrowisko);
        this.holding = null;
        this.fights = null;
        this.targeting = null;
        // Kolor robotnicy jest jaśniejszy od koloru mrowiska
        this.kolor = kolor.brighter().brighter();
    }

    /**
     * Metoda odpowiedzialna za podnoszenie przedmiotów.
     * Jeśli robotnica stoi na tym samym polu co przedmiot (targeting jest Przedmiotem),
     * podnosi go i zmienia cel na mrowisko, by tam go dostarczyć.
     */
    public void grab() {
        if (holding == null && targeting instanceof Przedmiot) {
            if (targeting.x == x && targeting.y == y) {
                ((Przedmiot) targeting).onMap = false; // przedmiot znika z mapy
                holding = targeting;                    // robotnica trzyma przedmiot
                targeting = myMrowisko;                 // cel to teraz mrowisko (powrót)
                //System.out.println(this + " podniosła przedmiot i wraca do mrowiska");
            }
        }
    }

    /**
     * Metoda zwracająca się do mrowiska z niesionym przedmiotem.
     * Po dojściu do mrowiska przekazuje zasoby (żywność lub patyki),
     * a następnie zwalnia niesiony przedmiot i cel.
     */
    public void returnToMrowisko() {
        if (holding != null && targeting == myMrowisko) {
            if (x == myMrowisko.x && y == myMrowisko.y) {
                // Przekazanie zasobów do mrowiska
                if (holding instanceof Lisc) {
                    myMrowisko.foodCount += Lisc.foodContribution;
                    myMrowisko.foodDelivered++;
                } else if (holding instanceof Patyk) {
                    myMrowisko.stickCount += Patyk.upgradeContribution;
                }
                holding = null;     // robotnica już nic nie niesie
                targeting = null;   // brak celu (może szukać nowego celu)
            }
        }
    }

    /**
     * Rysowanie robotnicy na mapie jako kwadrat w określonym kolorze.
     * @param g obiekt graficzny do rysowania
     * @param rozmiarPola wielkość pola na mapie
     */
    @Override
    public void drawObject(Graphics g, int rozmiarPola) {
        g.setColor(kolor);
        g.fillRect(x * rozmiarPola, y * rozmiarPola, rozmiarPola, rozmiarPola);
    }

    /**
     * Aktualizacja stanu robotnicy wywoływana co klatkę gry.
     * Odpowiada za ruch, podejmowanie celów, atakowanie oraz podnoszenie/przekazywanie przedmiotów.
     */
    @Override
    public void update() {

        // Jeśli mamy cel, idziemy do niego, w przeciwnym razie poruszamy się losowo
        if (targeting != null) {
            moveToTarget();
        } else {
            randomMove();
        }

        // Sprawdzenie obiektów w zasięgu robotnicy
        ObiektMapy obj = checkArea(MapaPanel.listaObiektow);

        // Jeśli robotnica nic nie trzyma i nie ma celu - celuj w przedmiot w pobliżu
        if (holding == null && targeting == null && obj instanceof Przedmiot) {
            targeting = obj;
        }

        // Jeśli robotnica trzyma przedmiot, cel jest ustawiony na mrowisko (powrót)
        if (holding != null) {
            targeting = myMrowisko;
        }

        // Jeśli nie trzymamy nic i nie mamy celu, a napotkamy mrówkę lub mrowisko, to je targetujemy (możliwy atak)
        if (holding == null && targeting == null && (obj instanceof Mrowka || obj instanceof Mrowisko)) {
            targeting = obj;
        }

        // Jeśli celem jest mrówka lub mrowisko, to walczymy z celem
        if (targeting instanceof Mrowka || targeting instanceof Mrowisko) {
            fights = targeting;
            attackTarget();
        }

        // Sprawdzamy czy robotnica nie umarła
        die();

        // Regeneracja zdrowia jeśli nie walczy
        regeneration();

        // Próba podniesienia przedmiotu jeśli jest na polu
        grab();

        // Próba oddania niesionego przedmiotu w mrowisku
        returnToMrowisko();
    }
}
