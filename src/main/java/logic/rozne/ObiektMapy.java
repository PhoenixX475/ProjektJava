package logic.rozne;

import java.awt.*;

/**
 * Klasa bazowa dla wszystkich obiektów na mapie.
 * Przechowuje podstawowe informacje o położeniu, rozmiarze,
 * stanie życia oraz metodach do rysowania i aktualizacji stanu obiektu.
 */
public abstract class ObiektMapy {

    // Pozycja obiektu na mapie (w jednostkach siatki)
    public int x, y;

    // Wymiary obiektu - szerokość i wysokość (domyślnie 1x1)
    public int width, height = 1;

    // Informacja czy obiekt jest aktywny na mapie (żyje/istnieje)
    public boolean onMap;

    // Aktualne punkty życia obiektu
    public int hp;

    // Maksymalne punkty życia obiektu
    public int maxHp;

    /**
     * Konstruktor dla żywych obiektów o rozmiarze 1x1,
     * inicjalizuje pozycję, max hp i aktualne hp,
     * oraz ustawia obiekt jako aktywny na mapie.
     * @param x pozycja X
     * @param y pozycja Y
     * @param maxHp maksymalne punkty życia
     */
    public ObiektMapy(int x, int y, int maxHp) {
        this.x = x;
        this.y = y;
        this.maxHp = maxHp;
        this.hp = maxHp;
        this.onMap = true;
    }

    /**
     * Konstruktor dla obiektów większych niż 1x1 (np. Mrowisko),
     * pozwala ustawić dodatkowo szerokość i wysokość.
     * @param x pozycja X
     * @param y pozycja Y
     * @param maxHp maksymalne punkty życia
     * @param width szerokość obiektu
     * @param height wysokość obiektu
     */
    public ObiektMapy(int x, int y, int maxHp, int width, int height) {
        this(x, y, maxHp);
        this.width = width;
        this.height = height;
        this.onMap = true;
    }

    /**
     * Konstruktor dla przedmiotów, które nie mają punktów życia,
     * ustawia tylko pozycję i aktywność na mapie.
     * @param x pozycja X
     * @param y pozycja Y
     */
    public ObiektMapy(int x, int y) {
        this.x = x;
        this.y = y;
        this.onMap = true;
    }

    /**
     * Zadaj obrażenia obiektowi, zmniejszając jego punkty życia.
     * @param dmg ilość zadanych obrażeń
     */
    public void dealDamage(int dmg) {
        hp -= dmg;
    }

    /**
     * Sprawdza czy obiekt powinien "umrzeć" (hp <= 0)
     * i ustawia go jako nieaktywny na mapie.
     */
    public void die() {
        if (hp <= 0) {
            onMap = false;
        }
    }

    /**
     * Abstrakcyjna metoda do rysowania obiektu na mapie.
     * Musi zostać zaimplementowana w klasach dziedziczących.
     * @param g obiekt graficzny do rysowania
     * @param rozmiarPola wielkość pojedynczego pola na mapie
     */
    public abstract void drawObject(Graphics g, int rozmiarPola);

    /**
     * Abstrakcyjna metoda aktualizacji stanu obiektu,
     * np. ruchu, regeneracji czy innych zachowań.
     * Musi zostać zaimplementowana w klasach dziedziczących.
     */
    public abstract void update();
}
