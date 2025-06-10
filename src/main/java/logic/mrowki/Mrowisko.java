package logic.mrowki;

import graphics.MapaPanel;
import logic.rozne.ObiektMapy;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Klasa reprezentująca mrowisko w symulacji.
 * Dziedziczy po klasie ObiektMapy i zarządza:
 * - stanem mrowiska (poziom, wytrzymałość, zasoby)
 * - populacją mrówek (robotnice, żołnierze)
 * - interakcjami z otoczeniem
 */
public class Mrowisko extends ObiektMapy {
    // ============= POLA DOTYCZĄCE STANU MROWISKA =============
    private int level;              // Aktualny poziom rozwoju mrowiska
    private int durability;         // Aktualna wytrzymałość (HP)
    private int maxDurability;      // Maksymalna wytrzymałość
    public int stickCount;          // Liczba zebranych patyków (do ulepszeń)
    public int foodCount;           // Liczba zgromadzonego jedzenia
    private MapaPanel mapa;         // Referencja do panelu mapy

    // ============= POLA DOTYCZĄCE POPULACJI MRÓWEK =============
    private int antCount;           // Aktualna liczba mrówek
    private int antMax;             // Maksymalna liczba mrówek (zależna od poziomu)
    public List<Mrowka> mrowki;     // Lista mrówek należących do mrowiska
    public int leafCount = 0;       // Liczba zebranych liści

    // ============= POLA DOTYCZĄCE WYGLĄDU =============
    private static final Random random = new Random();
    private final Color kolor;      // Unikalny kolor mrowiska
    private static final Set<Color> zajeteKolory = new HashSet<>(); // Zbiór użytych kolorów

    /**
     * Konstruktor mrowiska
     * @param x Koordynata X na mapie
     * @param y Koordynata Y na mapie
     * @param mapa Referencja do panelu mapy
     */
    public Mrowisko(int x, int y, MapaPanel mapa) {
        super(x,y);

        // Inicjalizacja stanu początkowego
        this.level = 1;
        this.durability = 100;
        this.maxDurability = 100;
        this.stickCount = 0;
        this.foodCount = 50;

        this.mapa = mapa;
        this.antCount = 0;
        this.antMax = 3;
        this.mrowki = new ArrayList<>();

        this.kolor = UnikalnyKolor(); // Generowanie unikalnego koloru
    }

    // ============= METODY DOTYCZĄCE ROZWOJU MROWISKA =============

    /**
     * Ulepsza mrowisko, jeśli zgromadzono wystarczającą liczbę patyków
     * Zwiększa poziom, maksymalną liczbę mrówek i wytrzymałość
     */
    public void levelUp() {
        if(stickCount > 5*level) {
            stickCount -= 5*level;
            level++;
            antMax += 2;
            maxDurability += 0.2*maxDurability;
            System.out.println("[Mrowisko zostało ulepszone]");
        }
    }

    /**
     * Metoda głodzenia - zmniejsza wytrzymałość gdy brakuje jedzenia
     */
    public void starvation() {
        if(foodCount == 0) {
            durability -= 1;
        }
    }

    /**
     * Metoda porażki - niszczy mrowisko gdy wytrzymałość spadnie poniżej zera
     */
    public void defeat() {
        if(durability < 0) {
            onMap = false;
            for(Mrowka m: mrowki) {
                m.onMap = false; // Usuwanie wszystkich mrówek
            }
        }
    }

    /**
     * Zużywa jedzenie na utrzymanie mrówek
     */
    public void foodDrain() {
        if (foodCount > 0) foodCount -= 0.1 * mrowki.size();
        if (foodCount < 0) foodCount = 0;
    }

    /**
     * Regeneruje wytrzymałość mrowiska gdy jest jedzenie
     */
    private void regeneration() {
        if(foodCount > 0 && durability < maxDurability) durability += 5;
    }

    // ============= METODY DOTYCZĄCE WYGLĄDU =============

    /**
     * Generuje unikalny kolor dla mrowiska
     * @return Unikalny kolor Color
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

    // ============= METODY DOTYCZĄCE MRÓWEK =============

    /**
     * Tworzy nową mrówkę (robotnicę lub żołnierza)
     * @param mapa Referencja do panelu mapy
     */
    public void createAnt(MapaPanel mapa) {
        if(antCount < antMax) {
            Random rnd = new Random();
            int rndX = (rnd.nextInt(3) - 1) * 2;
            int rndY = (rnd.nextInt(3) - 1) * 2;

            // 20% szans na stworzenie żołnierza
            if(rnd.nextInt(5) == 0) {
                Zolnierz nowyZolnierz = new Zolnierz(x+rndX, y+rndY, this, mapa, this.kolor);
                mrowki.add(nowyZolnierz);
                mapa.listaObiektow.add(nowyZolnierz);
            } else {
                Robotnica nowaRobotnica = new Robotnica(x+rndX, y+rndY, this, mapa, this.kolor);
                mrowki.add(nowaRobotnica);
                mapa.listaObiektow.add(nowaRobotnica);
            }
            antCount++;
        }
    }

    // ============= GETTERY =============

    public int getX() { return this.x; }
    public int getY() { return this.y; }
    public int getLevel() { return level; }
    public int getAntCount() { return antCount; }

    // ============= METODY GRAFICZNE =============

    /**
     * Rysuje mrowisko na mapie
     * @param g Obiekt Graphics do rysowania
     * @param rozmiarPola Rozmiar pojedynczego pola w pikselach
     */
    public void drawObject(Graphics g, int rozmiarPola) {
        g.setColor(kolor);
        g.fillRect(x * rozmiarPola, y * rozmiarPola,
                rozmiarPola * 5 + level, rozmiarPola * 5 + level);
    }

    // ============= GŁÓWNA METODA AKTUALIZUJĄCA =============

    /**
     * Aktualizuje stan mrowiska i wszystkich jego mrówek
     */
    public void update() {
        if(onMap) {
            // Aktualizacja każdej mrówki
            for(Mrowka m: mrowki) {
                m.update();
            }
            // Aktualizacja stanu mrowiska
            foodDrain();
            levelUp();
            starvation();
            defeat();
            regeneration();
            System.out.println("food: " + foodCount);
            System.out.println("hp: " + durability);
        }
    }
}

