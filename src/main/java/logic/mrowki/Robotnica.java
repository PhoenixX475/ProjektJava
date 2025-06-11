package logic.mrowki;

import graphics.MapaPanel;
import logic.obiekty.*;
import logic.rozne.ObiektMapy;

import java.awt.*;

/**
 * Klasa reprezentująca robotnicę w symulacji mrowiska.
 * Robotnica specjalizuje się w:
 * - Zbieraniu przedmiotów (liści i patyków)
 * - Transportowaniu ich do mrowiska
 * - Wspieraniu rozwoju kolonii
 * Dziedziczy po klasie Mrowka, rozszerzając jej funkcjonalność o mechanizmy zbieractwa.
 */
public class Robotnica extends Mrowka {

    // ============= POLA SPECYFICZNE DLA ROBOTNICY =============
    public ObiektMapy holding;      // Przedmiot aktualnie niesiony przez robotnicę
    private final Color kolor;      // Kolor identyfikacyjny robotnicy
    public int leafCount;           // Liczba zebranych liści (do statystyk)

    /**
     * Konstruktor robotnicy
     * @param x Pozycja startowa X
     * @param y Pozycja startowa Y
     * @param mrowisko Referencja do macierzystego mrowiska
     * @param mapa Referencja do panelu mapy
     * @param kolor Kolor bazowy (zostanie rozjaśniony dla robotnicy)
     */
    public Robotnica(int x, int y, Mrowisko mrowisko, MapaPanel mapa, Color kolor) {
        super(10, 3, x, y, mrowisko); // HP=10, Damage=3
        this.holding = null;          // Początkowo nie niesie żadnego przedmiotu
        this.fights = null;           // Nie walczy (to główna różnica względem Żołnierza)
        this.targeting = null;        // Brak początkowego celu
        this.kolor = kolor.brighter().brighter(); // Rozjaśnienie koloru dla odróżnienia
    }

    // ============= METODY DOTYCZĄCE ZBIERANIA PRZEDMIOTÓW =============

    /**
     * Podnosi przedmiot jeśli jest w tym samym polu co cel
     * Po podniesieniu ustawia mrowisko jako nowy cel
     */
    public void grab() {
        // Sprawdzenie warunków podniesienia przedmiotu
        if (holding == null && targeting instanceof Przedmiot) {
            if (targeting.x == x && targeting.y == y) {
                ((Przedmiot)targeting).onMap = false; // Oznacz przedmiot jako zebrany
                holding = targeting;                   // Robotnica przejmuje przedmiot
                targeting = myMrowisko;                // Ustaw mrowisko jako nowy cel
                System.out.println(this + " podniosła przedmiot i wraca do mrowiska");
            }
        }
    }

    /**
     * Oddaje przedmiot do mrowiska jeśli dotarła na miejsce
     * Aktualizuje odpowiednio zasoby mrowiska w zależności od typu przedmiotu
     */
    public void returnPrzedmiotToMrowisko() {
        // Sprawdzenie warunków oddania przedmiotu
        if (holding != null && targeting == myMrowisko) {
            if (x == myMrowisko.x && y == myMrowisko.y) {
                // Aktualizacja zasobów mrowiska w zależności od typu przedmiotu
                if (holding instanceof Lisc) {
                    myMrowisko.foodCount += Lisc.foodContribution; // Zwiększ jedzenie
                    myMrowisko.leafCount++;                       // Zwiększ licznik liści
                } else if (holding instanceof Patyk) {
                    myMrowisko.stickCount += Patyk.upgradeContribution; // Zwiększ liczbę patyków
                }

                holding = null;    // Zwolnij przedmiot
                targeting = null;  // Wyczyść cel
                System.out.println("Mrowka dodała przedmiot do mrowiska");
            }
        }
    }

    // ============= METODY GRAFICZNE =============

    /**
     * Rysuje robotnicę na mapie
     * @param g Obiekt Graphics do rysowania
     * @param rozmiarPola Rozmiar pojedynczego pola w pikselach
     */
    @Override
    public void drawObject(Graphics g, int rozmiarPola) {
        g.setColor(kolor);
        g.fillRect(x * rozmiarPola, y * rozmiarPola, rozmiarPola, rozmiarPola);
    }

    // ============= GŁÓWNA METODA AKTUALIZUJĄCA =============

    /**
     * Aktualizuje stan robotnicy w każdej klatce symulacji
     * Zarządza:
     * - Ruchem robotnicy
     * - Logiką zbierania przedmiotów
     * - Interakcjami z otoczeniem
     */
    @Override
    public void update() {
        // Logika ruchu
        if (targeting != null) {
            moveToTarget();  // Poruszaj się w kierunku celu
        } else {
            randomMove();    // Poruszaj się losowo
        }

        // Wyszukiwanie przedmiotów w pobliżu
        ObiektMapy obj = checkArea(MapaPanel.listaObiektow);

        // Logika wyboru celu
        if (holding == null && targeting == null && obj instanceof Przedmiot) {
            targeting = obj;  // Znaleziono przedmiot - ustaw jako cel
        }

        // Logika powrotu do mrowiska
        if (holding != null && targeting == null) {
            targeting = myMrowisko; // Wracaj z przedmiotem do mrowiska
        }

        // Wykonanie akcji
        grab();                   // Próba podniesienia przedmiotu
        returnPrzedmiotToMrowisko(); // Próba oddania przedmiotu
    }
}