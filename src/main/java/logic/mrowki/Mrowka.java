package logic.mrowki;

import logic.rozne.Coordinates;
import logic.rozne.ObiektMapy;

import java.util.LinkedList;
import java.util.Random;

/**
 * Abstrakcyjna klasa bazowa reprezentująca mrówkę w symulacji.
 * Zawiera podstawowe mechanizmy wspólne dla wszystkich typów mrówek:
 * - poruszanie się
 * - walkę
 * - interakcje z otoczeniem
 */
public abstract class Mrowka extends ObiektMapy {
    // ============= POLA DOTYCZĄCE STATYSTYK MRÓWKI =============
    protected int hp;               // Punkty życia mrówki (0 = śmierć)
    protected double damage;        // Siła ataku mrówki
    public Mrowka fights;          // Referencja do mrówki, z którą aktualnie walczy
    public Mrowisko myMrowisko;    // Referencja do macierzystego mrowiska
    public int zabiteMrowki = 0;   // Licznik zabitych mrówek przez tę mrówkę

    // ============= POLA DOTYCZĄCE POZYCJI I RUCHU =============
    public Coordinates coordinates; // Aktualne współrzędne mrówki
    public Random randomMoveX = new Random(); // Generator losowy dla ruchu w osi X
    public Random randomMoveY = new Random(); // Generator losowy dla ruchu w osi Y
    public ObiektMapy targeting;   // Cel, do którego mrówka zmierza

    /**
     * Konstruktor bazowy mrówki
     * @param hp Punkty życia początkowe
     * @param damage Obrażenia zadawane przez mrówkę
     * @param x Pozycja startowa X
     * @param y Pozycja startowa Y
     * @param mrowisko Referencja do macierzystego mrowiska
     */
    public Mrowka(int hp, double damage, int x, int y, Mrowisko mrowisko) {
        super(x, y);
        this.hp = hp;
        this.damage = damage;
        this.fights = null;
        this.myMrowisko = mrowisko;
        this.targeting = null;
    }

    // ============= METODY DOTYCZĄCE WALKI =============

    /**
     * Zwraca wartość obrażeń zadawanych przez mrówkę
     * @return Wartość obrażeń
     */
    public double getDamage() {
        return damage;
    }

    /**
     * Zadaje obrażenia mrówce
     * @param dmg Ilość obrażeń do zadania
     */
    public void dealDamage(double dmg) {
        hp -= dmg;
    }

    // ============= METODY DOTYCZĄCE RUCHU =============

    /**
     * Losowy ruch mrówki po mapie (gdy nie ma celu)
     * Ogranicza ruch do granic mapy (0-100)
     */
    public void randomMove() {
        if (targeting == null) {
            x += randomMoveX.nextInt(3) - 1; // -1, 0 lub 1
            y += randomMoveY.nextInt(3) - 1; // -1, 0 lub 1

            // Zabezpieczenie przed wyjściem poza mapę
            x = Math.max(1, Math.min(x, 99));
            y = Math.max(1, Math.min(y, 99));
        }
    }

    /**
     * Porusza mrówkę w kierunku aktualnego celu
     * Wykonuje tylko jeden krok w danym kierunku
     */
    public void moveToTarget() {
        if (targeting == null) return;

        int dx = Integer.compare(targeting.x, this.x); // -1, 0 lub 1
        int dy = Integer.compare(targeting.y, this.y); // -1, 0 lub 1

        this.x += dx;
        this.y += dy;
    }

    // ============= METODY DOTYCZĄCE INTERAKCJI =============

    /**
     * Sprawdza obszar wokół mrówki w poszukiwaniu obiektów
     * @param listaObiektow Lista wszystkich obiektów na mapie
     * @return Znaleziony obiekt w zasięgu lub null
     */
    public ObiektMapy checkArea(LinkedList<ObiektMapy> listaObiektow) {
        for (ObiektMapy obj : listaObiektow) {
            // Pomijanie siebie samej i obiektów już zebranych
            if (obj == this || !obj.onMap || obj == this.myMrowisko) continue;

            // Sprawdzenie czy obiekt jest w zasięgu 5x5 pól
            if (Math.abs(obj.x - this.x) <= 5 && Math.abs(obj.y - this.y) <= 5) {
                return obj;
            }
        }
        return null;
    }

    // ============= GETTERY =============

    /**
     * Zwraca aktualną ilość punktów życia mrówki
     * @return Wartość HP
     */
    public int getHp() {
        return hp;
    }
}