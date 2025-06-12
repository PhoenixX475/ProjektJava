package graphics;

/**
 * Reprezentuje pojedyncze pole na mapie.
 * Przechowuje typ obiektu znajdującego się na tym polu (np. mrówka, liść, puste).
 */
public class Pole {

    // Typ obiektu znajdującego się na danym polu mapy (np. MROWKA, LISC, PUSTE)
    public TypObiektu typ;

    /**
     * Tworzy pole o zadanym typie.
     * @param typ typ obiektu na polu
     */
    public Pole(TypObiektu typ) {
        this.typ = typ;
    }
}

