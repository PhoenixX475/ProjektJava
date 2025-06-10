package logic.obiekty;

import java.awt.*;

/**
 * Klasa reprezentująca liść w symulacji mrowiska.
 * Liść jest przedmiotem, który robotnice mogą zbierać i przynosić do mrowiska,
 * gdzie jest przetwarzany na żywność dla kolonii.
 * Dziedziczy po klasie Przedmiot.
 */
public class Lisc extends Przedmiot {

    // ============= STAŁE KLASY =============
    /**
     * Wartość odżywcza liścia - określa ile jedzenia
     * otrzymuje mrowisko po dostarczeniu liścia
     */
    public static final int foodContribution = 25;

    // ============= KONSTRUKTOR =============
    /**
     * Tworzy nowy liść na określonych współrzędnych
     * @param x Współrzędna X na mapie
     * @param y Współrzędna Y na mapie
     */
    public Lisc(int x, int y) {
        super(x, y);  // Wywołanie konstruktora klasy bazowej Przedmiot
    }

    // ============= METODY GRAFICZNE =============
    /**
     * Rysuje liść na mapie jeśli jest aktywny (onMap = true)
     * @param g Obiekt Graphics do rysowania
     * @param rozmiarPola Rozmiar pojedynczego pola w pikselach
     */
    @Override
    public void drawObject(Graphics g, int rozmiarPola) {
        if(onMap) {
            g.setColor(Color.GREEN.darker());  // Ciemnozielony kolor liścia
            g.fillRect(x * rozmiarPola, y * rozmiarPola, rozmiarPola, rozmiarPola);
        }
    }

    // ============= METODA AKTUALIZUJĄCA =============
    /**
     * Aktualizuje stan liścia (pusta implementacja, ponieważ liść jest statycznym obiektem)
     */
    @Override
    public void update() {
        // Liść nie wymaga specjalnej logiki aktualizacji
        // Można dodać np. więdnięcie z czasem
    }
}
