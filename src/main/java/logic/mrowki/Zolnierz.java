package logic.mrowki;

import graphics.MapaPanel;
import java.awt.*;
import java.util.List;

/**
 * Klasa reprezentująca żołnierza w symulacji mrowiska.
 * Specjalizuje się w walce z innymi mrówkami, posiada:
 * - Większą wytrzymałość i siłę ataku niż robotnica
 * - Możliwość zadawania obrażeń obszarowych
 * - Większy rozmiar wizualny
 * Dziedziczy po klasie Mrowka, rozszerzając jej funkcjonalność o mechanizmy walki.
 */
public class Zolnierz extends Mrowka {

    // ============= POLA SPECYFICZNE DLA ŻOŁNIERZA =============
    public List<Mrowka> fights;    // Lista mrówek, z którymi aktualnie walczy
    private final Color kolor;     // Kolor identyfikacyjny żołnierza

    /**
     * Konstruktor żołnierza
     * @param x Pozycja startowa X
     * @param y Pozycja startowa Y
     * @param mrowisko Referencja do macierzystego mrowiska
     * @param mapa Referencja do panelu mapy
     * @param kolor Kolor bazowy (zostanie rozjaśniony dla żołnierza)
     */
    public Zolnierz(int x, int y, Mrowisko mrowisko, MapaPanel mapa, Color kolor) {
        super(30, 8, x, y, mrowisko); // Wysokie HP=30 i Damage=8
        this.kolor = kolor.brighter().brighter(); // Rozjaśnienie koloru dla odróżnienia
    }

    // ============= METODY DOTYCZĄCE WALKI =============

    /**
     * Zadaje obrażenia obszarowe wszystkim mrówkom na liście walki
     * @param dmg Ilość obrażeń do zadania każdej mrówce
     */
    @Override
    public void dealDamage(double dmg) {
        for(Mrowka m: fights) {
            m.hp -= dmg; // Zadaj obrażenia każdej mrówce w zasięgu
            if(m.hp <= 0) {
                this.zabiteMrowki++; // Inkrementuj licznik zabitych mrówek
            }
        }
    }

    // ============= METODY GRAFICZNE =============

    /**
     * Rysuje żołnierza na mapie (większy rozmiar niż robotnica)
     * @param g Obiekt Graphics do rysowania
     * @param rozmiarPola Rozmiar pojedynczego pola w pikselach
     */
    @Override
    public void drawObject(Graphics g, int rozmiarPola) {
        g.setColor(kolor);
        // Żołnierz jest 2x większy od robotnicy
        g.fillRect(x * rozmiarPola, y * rozmiarPola, rozmiarPola * 2, rozmiarPola * 2);
    }

    // ============= GŁÓWNA METODA AKTUALIZUJĄCA =============

    /**
     * Aktualizuje stan żołnierza w każdej klatce symulacji
     * Zarządza:
     * - Ruchem żołnierza
     * - Logiką walki
     * - Interakcjami z wrogami
     */
    @Override
    public void update() {
        // Podstawowy losowy ruch (można rozszerzyć o śledzenie wrogów)
        randomMove();
    }
}