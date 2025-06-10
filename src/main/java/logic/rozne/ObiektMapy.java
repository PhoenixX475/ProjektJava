package logic.rozne;

import java.awt.*;

/**
 * Abstrakcyjna klasa bazowa reprezentująca wszystkie obiekty znajdujące się na mapie symulacji.
 * Definiuje podstawowe właściwości i zachowania wspólne dla wszystkich obiektów w symulacji.
 */
public abstract class ObiektMapy {
    // ============= POLA KLASY =============

    /**
     * Współrzędne obiektu na mapie (lewy górny róg)
     */
    public int x, y;

    /**
     * Rozmiary obiektu (domyślnie 1x1)
     */
    public int width, height = 1;

    /**
     * Flaga określająca czy obiekt jest aktualnie na mapie
     */
    public boolean onMap;

    // ============= KONSTRUKTORY =============

    /**
     * Konstruktor podstawowy dla obiektów 1x1
     * @param x Współrzędna X obiektu
     * @param y Współrzędna Y obiektu
     */
    public ObiektMapy(int x, int y) {
        this.x = x;
        this.y = y;
        this.onMap = true; // Obiekt domyślnie jest umieszczany na mapie
    }

    /**
     * Konstruktor dla obiektów o większych rozmiarach (np. mrowiska)
     * @param x Współrzędna X obiektu
     * @param y Współrzędna Y obiektu
     * @param width Szerokość obiektu
     * @param height Wysokość obiektu
     */
    public ObiektMapy(int x, int y, int width, int height) {
        this(x, y); // Wywołanie konstruktora podstawowego
        this.width = width;
        this.height = height;
    }

    // ============= METODY =============

    /**
     * Sprawdza kolizję obiektu z podanymi współrzędnymi
     * @param dx Współrzędna X punktu do sprawdzenia
     * @param dy Współrzędna Y punktu do sprawdzenia
     * @return true jeśli punkt (dx,dy) znajduje się wewnątrz obiektu
     */
    public boolean occupied(int dx, int dy) {
        // Sprawdzenie czy punkt (dx,dy) znajduje się w obszarze zajmowanym przez obiekt
        return dx >= x && dx < x + width && dy >= y && dy < y + height;
    }

    // ============= METODY ABSTRAKCYJNE =============

    /**
     * Metoda abstrakcyjna odpowiedzialna za rysowanie obiektu na mapie
     * @param g Obiekt Graphics używany do rysowania
     * @param rozmiarPola Rozmiar pojedynczego pola mapy w pikselach
     */
    public abstract void drawObject(Graphics g, int rozmiarPola);

    /**
     * Metoda abstrakcyjna odpowiedzialna za aktualizację stanu obiektu
     * Wywoływana w każdej klatce symulacji
     */
    public abstract void update();
}
