package logic.mrowki;

import logic.rozne.ObiektMapy;

/**
 * Interfejs definiujący podstawowe zachowania mrówki w symulacji.
 * Każda klasa implementująca ten interfejs musi zapewnić metody ruchu i ataku.
 */
public interface IMrowka {
    void randomMove(); //Metoda odpowiedzialna za losowy ruch mrówki na mapie

    void moveToTarget(); //Metoda ruchu mrówki w kierunku konkretnego celu

    void attackTarget(); //Metoda realizująca atak mrówki na wybrany cel
}
