package logic.mrowki;

import graphics.MapaPanel;
import logic.rozne.ObiektMapy;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Klasa reprezentująca mrowisko - bazę dla mrówek w symulacji.
 * Zawiera informacje o stanie mrowiska (poziom, liczba zasobów, zdrowie),
 * zarządza listą mrówek oraz odpowiada za ich tworzenie i aktualizację stanu.
 */
public class Mrowisko extends ObiektMapy {
    // Poziom mrowiska, wpływa na maksymalną liczbę mrówek i wytrzymałość
    private int level;
    public int stickCount;  // liczba zebranego drewna
    public int foodCount;   // ilość pożywienia dostępnego w mrowisku
    public int foodDelivered; // dostarczone jedzenie (nieużywane w tej wersji)
    private MapaPanel mapa;  // referencja do mapy, na której jest mrowisko

    // Pola związane z mrówkami przypisanymi do tego mrowiska
    public int antCount;  // aktualna liczba mrówek
    private int antMax;   // maksymalna liczba mrówek, zależna od poziomu
    public List<Mrowka> mrowki; // lista mrówek należących do mrowiska

    private static final Random random = new Random();
    private final Color kolor;  // unikalny kolor reprezentujący mrowisko na mapie
    private static final Set<Color> zajeteKolory = new HashSet<>(); // kolory już użyte

    /**
     * Konstruktor mrowiska, inicjalizuje parametry i przypisuje unikalny kolor.
     * @param x pozycja X na mapie
     * @param y pozycja Y na mapie
     * @param mapa referencja do obiektu mapy
     */
    public Mrowisko(int x, int y, MapaPanel mapa) {
        super(x,y,100);

        this.level = 1;
        this.stickCount = 0;
        this.foodCount = 200;

        this.mapa = mapa;
        this.antCount = 0;
        this.antMax = 3;
        this.mrowki = new ArrayList<>();

        this.kolor = UnikalnyKolor();
    }

    // Gettery do podstawowych pól mrowiska
    public int getLevel(){
        return level;
    }
    public int getAntCount(){
        return antCount;
    }
    public int getX() {
        return this.x;
    }
    public int getY() {
        return this.y;
    }

    /**
     * Metoda ulepszająca mrowisko jeśli zgromadzono wystarczająco drewna.
     * Poziom rośnie, maksymalna liczba mrówek i wytrzymałość również.
     */
    public void levelUp () {
        if(stickCount > 5*level) {
            stickCount -= 5*level;  // zużycie drewna na ulepszenie

            level++;              // zwiększenie poziomu
            antMax += 2;          // większa pojemność na mrówki
            maxHp += 0.2*maxHp;   // zwiększenie maksymalnego HP
        }
    }

    /**
     * Sprawdzanie czy mrowisko nie głoduje - zmniejsza HP jeśli brak jedzenia.
     */
    public void starvation() {
        if(foodCount == 0) {
            hp -= 1;  // tracimy życie gdy brak jedzenia
        }
    }

    /**
     * Nadpisanie metody die() z klasy bazowej.
     * Usuwa mrowisko i wszystkie jego mrówki z mapy po śmierci.
     */
    @Override
    public void die() {
        if(hp <= 0 ) {
            // usuwamy mrówki z mapy
            for(Mrowka m: mrowki) {
                m.onMap = false;
            }
            onMap = false; // usuwamy mrowisko
        }
    }

    /**
     * Zużywanie jedzenia przez mrówki w mrowisku.
     * Jedzenie spada proporcjonalnie do liczby mrówek.
     */
    public void foodDrain() {
        if (foodCount > 0) foodCount -= 0.1 * mrowki.size();
        if (foodCount < 0) foodCount = 0;  // jedzenie nie może być ujemne
    }

    /**
     * Regeneracja HP mrowiska jeśli jest jedzenie i HP poniżej maxa.
     */
    private void regeneration() {
        if(foodCount > 0 && hp < maxHp) hp += 5;
        if(hp > maxHp) hp = maxHp; // limit HP do maxHp
    }

    /**
     * Generowanie unikalnego koloru dla mrowiska, nie powtarzającego się wśród innych.
     * @return kolor mrowiska
     */
    private Color UnikalnyKolor() {
        Color c;
        do {
            int r = random.nextInt(100);
            int g = random.nextInt(100);
            int b = random.nextInt(100);

            c = new Color(r,g,b);

        } while (zajeteKolory.contains(c));

        zajeteKolory.add(c);

        return c;
    }

    /**
     * Tworzy nową mrówkę (robotnicę lub żołnierza) i dodaje ją do listy oraz mapy.
     * Losuje pozycję startową mrówki wokół mrowiska.
     * Żołnierz ma 20% szansy na powstanie, reszta to robotnice.
     */
    public void createAnt(MapaPanel mapa) {
        if(onMap && antCount < antMax) {
            // losowanie pozycji mrówki wokół mrowiska (-2, 0 lub +2)
            Random rnd = new Random();
            int rndX = (rnd.nextInt(3) -1) * 2;
            int rndY = (rnd.nextInt(3) - 1) * 2;

            // szansa 1/5 na żołnierza
            if(rnd.nextInt(5) == 0) {
                Zolnierz nowyZolnierz = new Zolnierz(x + rndX, y + rndY, this, mapa, this.kolor);
                mrowki.add(nowyZolnierz);
                mapa.listaObiektow.add(nowyZolnierz);
            }
            else {
                Robotnica nowaRobotnica = new Robotnica(x + rndX, y + rndY, this, mapa, this.kolor);
                mrowki.add(nowaRobotnica);
                mapa.listaObiektow.add(nowaRobotnica);
            }

            antCount++; // zwiększamy licznik mrówek
        }
    }

    /**
     * Rysuje mrowisko na panelu graficznym jako prostokąt w unikalnym kolorze.
     * Wielkość zależy od poziomu mrowiska.
     * @param g obiekt Graphics do rysowania
     * @param rozmiarPola wielkość jednego pola na mapie w pikselach
     */
    public void drawObject(Graphics g, int rozmiarPola ) {
        g.setColor(kolor);
        int size;
        if(level <= 4) size = level;
        else size = 2 * level;

        // rysowanie prostokąta reprezentującego mrowisko
        g.fillRect(x * rozmiarPola, y * rozmiarPola, rozmiarPola * 5 + size, rozmiarPola * 5 + size);
    }

    /**
     * Aktualizacja stanu mrowiska i jego mrówek.
     * Wywoływana cyklicznie podczas symulacji.
     */
    public void update() {
        if(onMap) {
            // aktualizacja każdej mrówki w mrowisku
            for(Mrowka m: mrowki) {
                m.update();
            }
            foodDrain();   // zużycie jedzenia
            levelUp();     // próba ulepszenia mrowiska
            starvation();  // sprawdzenie głodu
            die();         // ewentualna śmierć
            regeneration();// regeneracja HP
        }
    }
}
