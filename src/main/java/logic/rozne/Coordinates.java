package logic.rozne;

import java.util.Random;

/**
 * Klasa reprezentująca współrzędne (x,y) obiektów w symulacji.
 * Przechowuje pozycję obiektów na dwuwymiarowej mapie oraz udostępnia
 * funkcjonalność losowego generowania pozycji.
 */
public class Coordinates {

    // ============= POLA KLASY =============

    /**
     * Generator liczb losowych używany do tworzenia losowych współrzędnych
     */
    public Random random = new Random();

    /**
     * Współrzędna X obiektu na mapie
     */
    public int x;

    /**
     * Współrzędna Y obiektu na mapie
     */
    public int y;

    // ============= KONSTRUKTORY =============

    /**
     * Konstruktor domyślny tworzący losowe współrzędne
     * Wartości x i y są generowane z pełnego zakresu int
     */
    public Coordinates() {
        this.x = random.nextInt();
        this.y = random.nextInt();
    }

    /**
     * Konstruktor tworzący współrzędne o podanych wartościach
     * @param x Wartość współrzędnej X
     * @param y Wartość współrzędnej Y
     */
    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // ============= SUGEROWANE ROZSZERZENIA =============
    /*
    public boolean equals(Coordinates other) {
        return this.x == other.x && this.y == other.y;
    }

    public double distanceTo(Coordinates other) {
        return Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
    }
    */
}