package logic.obiekty;

import java.awt.*;

/**
 * Klasa reprezentująca patyk w symulacji mrowiska.
 * Patyk jest przedmiotem, który robotnice mogą zbierać i przynosić do mrowiska,
 * gdzie jest wykorzystywany do rozbudowy i ulepszania mrowiska.
 * Dziedziczy po klasie Przedmiot.
 */
public class Patyk extends Przedmiot {

    // ============= STAŁE KLASY =============
    /**
     * Wartość budulcowa patyka - określa jak bardzo przyczynia się
     * do ulepszenia mrowiska po dostarczeniu
     */
    public static final int upgradeContribution = 5;

    // ============= KONSTRUKTOR =============
    /**
     * Tworzy nowy patyk na określonych współrzędnych
     * @param x Współrzędna X na mapie
     * @param y Współrzędna Y na mapie
     */
    public Patyk(int x, int y) {
        super(x, y);  // Wywołanie konstruktora klasy bazowej Przedmiot
    }

    // ============= METODY GRAFICZNE =============
    /**
     * Rysuje patyk na mapie jeśli jest aktywny (onMap = true)
     * @param g Obiekt Graphics do rysowania
     * @param rozmiarPola Rozmiar pojedynczego pola w pikselach
     */
    @Override
    public void drawObject(Graphics g, int rozmiarPola) {
        if(onMap) {
            // Użycie koloru brązowego (RGB: 139, 69, 19)
            g.setColor(new Color(139, 69, 19));
            g.fillRect(x * rozmiarPola, y * rozmiarPola, rozmiarPola, rozmiarPola);
        }
    }

    // ============= METODA AKTUALIZUJĄCA =============
    /**
     * Aktualizuje stan patyka (pusta implementacja, ponieważ patyk jest statycznym obiektem)
     */
    @Override
    public void update() {
        // Patyk nie wymaga specjalnej logiki aktualizacji
        // Można rozważyć dodanie np. mechanizmu butwienia z czasem
    }
}