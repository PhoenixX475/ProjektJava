package logic.mrowki;
import logic.rozne.ObiektMapy;

import java.awt.*;
import java.util.LinkedList;
import java.util.Random;

/**
 * Abstrakcyjna klasa bazowa reprezentująca mrówkę.
 * Definiuje podstawowe pola i zachowania wspólne dla wszystkich typów mrówek,
 * takie jak ruch, atak, sprawdzanie otoczenia oraz życie/śmierć.
 */
public abstract class Mrowka extends ObiektMapy implements IMrowka {
    // Obrażenia zadawane przez mrówkę przy ataku
    protected int damage;
    public ObiektMapy fights;   // obecny przeciwnik, z którym mrówka walczy
    public Mrowisko myMrowisko; // mrowisko, do którego należy mrówka

    // Generatory losowych ruchów mrówki
    public Random randomMoveX = new Random();
    public Random randomMoveY = new Random();

    public int zabiteMrowki = 0; // liczba zabitych mrówek przez tę mrówkę

    public ObiektMapy targeting; // obecny cel mrówki (do którego się porusza)

    /**
     * Konstruktor mrówki.
     * @param hp - życie mrówki
     * @param damage - obrażenia mrówki
     * @param x - pozycja X na mapie
     * @param y - pozycja Y na mapie
     * @param mrowisko - mrowisko, do którego należy mrówka
     */
    public Mrowka(int hp, int damage, int x, int y, Mrowisko mrowisko) {
        super(x, y, hp);
        this.damage = damage;
        this.fights = null;
        this.myMrowisko = mrowisko;
        this.targeting = null;
    }

    /**
     * Losowe poruszanie się mrówki,
     * przesuwa ją o -1, 0 lub +1 na osi X i Y, jeśli nie ma aktualnego celu.
     */
    public void randomMove() {
        if (targeting == null) {
            x += randomMoveX.nextInt(3) - 1; // ruch w osi X: -1, 0 lub +1
            y += randomMoveY.nextInt(3) - 1; // ruch w osi Y: -1, 0 lub +1

            // ograniczenie ruchu mrówki w granicach planszy 0-100
            if (x < 0) x = 1;
            if (x > 100) x = 99;
            if (y < 0) y = 1;
            if (y > 100) y = 99;
        }
    }

    /**
     * Porusza mrówkę o 1 krok w kierunku celu (targeting).
     * Jeśli cel nie istnieje lub nie jest na mapie, cel zostaje usunięty.
     */
    public void moveToTarget() {
        if (targeting == null) return;
        if (!targeting.onMap) {
            targeting = null;
            return;
        }

        // Obliczamy kierunek ruchu względem celu (-1, 0 lub +1)
        int dx = Integer.compare(targeting.x, this.x);
        int dy = Integer.compare(targeting.y, this.y);

        // Przesuwamy mrówkę o 1 krok w osi X i Y w stronę celu
        this.x += dx;
        this.y += dy;
    }

    /**
     * Sprawdza czy w otoczeniu mrówki (w promieniu 5 pól) znajdują się inne obiekty,
     * które mogą być celem (np. wrogie mrówki lub inne obiekty na mapie).
     * Ignoruje siebie, własne mrowisko oraz przyjazne mrówki.
     * @param listaObiektow lista wszystkich obiektów na mapie
     * @return znaleziony obiekt w zasięgu lub null jeśli brak
     */
    public ObiektMapy checkArea(LinkedList<ObiektMapy> listaObiektow) {
        for (ObiektMapy obj : listaObiektow) {
            if (obj == this) continue;               // pomijamy siebie
            if (!obj.onMap) continue;                // pomijamy obiekty usunięte z mapy
            if (obj == this.myMrowisko) continue;   // pomijamy własne mrowisko
            if (this.myMrowisko.mrowki.contains(obj)) continue; // pomijamy przyjazne mrówki

            // Sprawdzamy, czy obiekt jest w zasięgu 5 jednostek w osi X i Y
            if (Math.abs(obj.x - this.x) <= 5 && Math.abs(obj.y - this.y) <= 5) {
                return obj;  // zwracamy pierwszy znaleziony obiekt w zasięgu
            }
        }
        return null; // brak obiektu w zasięgu
    }

    /**
     * Atakuje aktualny cel (fights) jeśli jest w zasięgu (3 jednostki),
     * zadaje obrażenia i oznacza cel jako usunięty jeśli HP <= 0.
     */
    public void attackTarget() {
        if (fights == null) return;
        if (hp <= 0) return;     // jeśli mrówka martwa, nie atakuje
        if (!onMap) return;      // jeśli mrówka nie jest na mapie, nie atakuje
        if (targeting == null) return;

        // Sprawdzamy czy cel jest w zasięgu ataku (3 jednostki)
        if (Math.abs(targeting.x - this.x) <= 3 && Math.abs(targeting.y - this.y) <= 3) {
            fights.dealDamage(damage); // zadajemy obrażenia celowi
            if (fights.hp <= 0) {      // jeśli cel martwy, usuwamy go z mapy
                fights.onMap = false;
            }
        }
    }

    /**
     * Metoda wywoływana gdy mrówka umiera.
     * Oznacza mrówkę jako nieobecną na mapie i zmniejsza licznik mrówek w mrowisku.
     */
    @Override
    public void die() {
        if (hp <= 0) {
            onMap = false;    // mrówka znika z mapy
            targeting = null; // zerujemy cel
            fights = null;    // zerujemy przeciwnika
            if (myMrowisko.antCount > 0) myMrowisko.antCount--;  // zmniejszamy licznik mrówek w mrowisku
        }
        // Jeśli mrowisko nie istnieje lub jest martwe, mrówka znika z mapy
        if (myMrowisko == null || myMrowisko.hp <= 0) onMap = false;
    }

    /**
     * Regeneracja życia mrówki, jeśli nie walczy i nie ma maksymalnego HP.
     */
    protected void regeneration() {
        if (fights == null && hp < maxHp) {
            hp++;  // powolne leczenie mrówki
        }
    }
}
