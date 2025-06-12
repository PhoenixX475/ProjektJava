package logic.mrowki;

import graphics.MapaPanel;
import logic.rozne.ObiektMapy;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Klasa reprezentująca żołnierza - mrówkę specjalizującą się w walce,
 * zadającą obrażenia obszarowe wokół siebie.
 */
public class Zolnierz extends Mrowka {

    // Lista obiektów, które są aktualnie atakowane (w zasięgu walki)
    public List<ObiektMapy> fights = new ArrayList<>();

    // Lista obiektów, które powinny zostać usunięte z listy walki (np. zabite)
    private List<ObiektMapy> notFights = new ArrayList<>();

    // Kolor żołnierza do rysowania (jaśniejszy od koloru mrowiska)
    private final Color kolor;

    /**
     * Konstruktor żołnierza.
     * @param x pozycja X
     * @param y pozycja Y
     * @param mrowisko mrowisko, do którego należy żołnierz
     * @param mapa panel mapy (potrzebny do interakcji)
     * @param kolor kolor żołnierza (bazowany na kolorze mrowiska)
     */
    public Zolnierz(int x, int y, Mrowisko mrowisko, MapaPanel mapa, Color kolor) {
        super(30, 8, x, y, mrowisko);
        this.kolor = kolor.brighter().brighter();
    }

    /**
     * Metoda atakująca wszystkie cele z listy fights znajdujące się w zasięgu (5 pól).
     * Zadaje obrażenia obszarowe i usuwa obiekty zabite lub już niedostępne z listy.
     */
    public void attackTarget() {
        if (fights == null) return;
        if (hp <= 0) return;
        if (!onMap) return;
        if (targeting == null) return;

        for (ObiektMapy o : fights) {
            if (o.onMap) {
                // Jeśli obiekt jest w zasięgu 5 jednostek w poziomie i pionie
                if (Math.abs(o.x - this.x) <= 5 && Math.abs(o.y - this.y) <= 5) {
                    o.dealDamage(damage);  // zadaj obrażenia

                    // Jeśli obiekt został zabity, oznacz do usunięcia z listy walki
                    if (o.hp <= 0) {
                        notFights.add(o);
                        o.onMap = false;
                    }
                }
            } else {
                // Obiekt już nie jest na mapie - dodaj do usunięcia
                notFights.add(o);
            }
        }

        // Usuń z listy walki obiekty, które już nie są aktywne
        for (ObiektMapy ob : notFights) {
            fights.remove(ob);
        }
        notFights.clear();
    }

    /**
     * Rysowanie żołnierza na mapie jako większy kwadrat w określonym kolorze.
     * @param g obiekt graficzny do rysowania
     * @param rozmiarPola wielkość pola na mapie
     */
    @Override
    public void drawObject(Graphics g, int rozmiarPola) {
        g.setColor(kolor);
        g.fillRect(x * rozmiarPola, y * rozmiarPola, rozmiarPola * 2, rozmiarPola * 2);
    }

    /**
     * Aktualizacja stanu żołnierza wywoływana co klatkę gry.
     * Odpowiada za ruch, wykrywanie celów i atakowanie.
     */
    @Override
    public void update() {
        // Ruch do celu, jeśli jest, w przeciwnym razie ruch losowy
        if (targeting != null) {
            moveToTarget();
        } else {
            randomMove();
        }

        // Sprawdzenie obiektów w zasięgu
        ObiektMapy obj = checkArea(MapaPanel.listaObiektow);

        // Jeśli nie mamy celu i napotkaliśmy mrówkę lub mrowisko - ustawiamy cel
        if (targeting == null && (obj instanceof Mrowka || obj instanceof Mrowisko)) {
            targeting = obj;
        }

        // Jeśli celem jest mrówka lub mrowisko - dodajemy do listy celów walki i atakujemy
        if (targeting instanceof Mrowka || targeting instanceof Mrowisko) {
            fights.add(targeting);
            attackTarget();
        }

        // Sprawdź czy żołnierz nie umarł
        die();

        // Regeneracja życia jeśli nie walczy
        regeneration();
    }
}
